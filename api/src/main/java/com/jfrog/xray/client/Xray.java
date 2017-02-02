package com.jfrog.xray.client;

import com.jfrog.xray.client.impl.services.system.System;

import java.io.Closeable;

public interface Xray extends Closeable {

    System system();

    @Override
    void close();
}