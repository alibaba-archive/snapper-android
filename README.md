# Snapper-Android [![](https://jitpack.io/v/teambition/snapper-android.svg)](https://jitpack.io/#teambition/snapper-android)

This is an engine-io client for Android platform. Work with [Snapper-Core](https://github.com/teambition/snapper-core).

## Usage

```java
Snapper.getInstance()
        .init("Your uri")
        .setAutoRetry(true)
        .setListener(new SnapperListener() {
            @Override
            public void onMessage(String msg) {
                Log.d("Snapper", "message:" + msg);
            }
        })
        .log(true)
        .setRetryInterval(3 * 1000)
        .setMaxRetryTimes(5)
        .open();

Snapper.getInstance().send("message");

Snapper.isRunning();
```

## Installation

Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```
Add the dependency
```gradle
dependencies {
    compile 'com.github.teambition:snapper-android:0.9.7'
}
```

## License

    The MIT License (MIT)

    Copyright (c) 2016 Teambition

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
