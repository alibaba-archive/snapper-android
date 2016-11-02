package com.teambition.snapper;

import android.util.Log;

import java.net.URISyntaxException;

import io.socket.emitter.Emitter;
import io.socket.engineio.client.Socket;
import io.socket.engineio.client.transports.WebSocket;
import io.socket.engineio.parser.Packet;

/**
 * Created by zeatual on 16/3/15.
 */
public class Snapper {

    private static final int CONNECTED = 0;
    private static final int CONNECTING = 1;
    private static final int DISCONNECTED = 2;

    private static Snapper snapper;
    private int status;

    private Socket socket;

    private String hostname;
    private String query;
    private String uri;
    private String userAgent;

    private boolean log;
    private boolean autoRetry;
    private int maxRetryTimes = 10;
    private int retryInterval = 10 * 1000;
    private int retryCount;
    private Listener listener;

    public static Snapper getInstance() {
        if (snapper == null) {
            snapper = new Snapper();
        }
        return snapper;
    }

    public Snapper init(String hostname, String query) {
        snapper.hostname = hostname;
        snapper.query = query;
        return snapper;
    }

    public Snapper init(String uri) {
        snapper.uri = uri;
        return snapper;
    }

    private boolean isRunning() {
        return socket != null && status != DISCONNECTED;
    }

    public Snapper setAutoRetry(boolean autoRetry) {
        if (snapper != null) {
            snapper.autoRetry = autoRetry;
        }
        return snapper;
    }

    public Snapper setListener(Listener listener) {
        if (snapper != null) {
            snapper.listener = listener;
        }
        return snapper;
    }

    public Snapper setMaxRetryTimes(int times) {
        if (snapper != null) {
            snapper.maxRetryTimes = times;
        }
        return snapper;
    }

    public Snapper setRetryInterval(int times) {
        if (snapper != null) {
            snapper.retryInterval = times;
        }
        return snapper;
    }

    public Snapper log(boolean log) {
        if (snapper != null) {
            snapper.log = log;
        }
        return snapper;
    }

    public Snapper setUserAgent(String userAgent) {
        if (snapper != null) {
            snapper.userAgent = userAgent;
        }
        return snapper;
    }

    public String getUserAgent() {
        return userAgent;
    }

    private void setSocket(final boolean autoRetry, final Listener listener) {
        socket.on(Socket.EVENT_OPEN, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if (listener != null && args.length > 0) {
                    status = CONNECTED;
                    listener.onOpen((String) args[0]);
                }
            }
        }).on(Socket.EVENT_MESSAGE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if (listener != null && args.length > 0) {
                    listener.onMessage((String) args[0]);
                }
            }
        }).on(Socket.EVENT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if (listener != null && args.length > 0) {
                    log("Snapper", "error:" + ((Exception) args[0]).getMessage());
                    listener.onError((Exception) args[0]);
                }
            }
        }).on(Socket.EVENT_PACKET, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Packet packet = (Packet) args[0];
                log("Snapper", "packet: {type:\"" + packet.type + "\", data:\"" + packet.data + "\"}");
            }
        }).on(Socket.EVENT_PING, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                log("Snapper", "ping");
            }
        }).on(Socket.EVENT_PONG, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                log("Snapper", "pong");
            }
        }).on(Socket.EVENT_CLOSE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                log("Snapper", "close: " + args[0]);
                status = DISCONNECTED;
                if (listener != null) {
                    listener.onClose();
                }
                if (socket != null && autoRetry && retryCount < maxRetryTimes) {
                    try {
                        if (retryCount > 0) {
                            Thread.sleep(retryInterval);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        socket.open();
                        log("Snapper", "retrying: " + retryCount + "/" + maxRetryTimes);
                        retryCount++;
                    }
                }
            }
        });
    }

    public void send(String message) {
        if (socket != null) {
            socket.send(message);
        }
    }

    public void open() {
        if (!isRunning()) {
            if (hostname != null) {
                Socket.Options options = new Socket.Options();
                options.hostname = hostname;
                options.query = query;
                socket = new Socket(options);
            } else if (uri != null) {
                try {
                    Socket.Options options = new Socket.Options();
                    options.path = "/websocket/";
                    socket = new Socket(uri, options);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                setSocket(autoRetry, listener);
                status = CONNECTING;
                socket.open();
            }
        }
    }

    public void close() {
        if (socket != null) {
            socket.close();
            socket = null;
        }
    }

    private void log(String a, String msg) {
        if (log) {
            Log.d(a, msg);
        }
    }

    public interface Listener {
        void onOpen(String sid);

        void onMessage(String msg);

        void onError(Exception e);

        void onClose();
    }

}
