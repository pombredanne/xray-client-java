package com.jfrog.xray.client;

import com.jfrog.xray.client.services.binarymanagers.BinaryManagers;
import com.jfrog.xray.client.services.summary.Summary;
import com.jfrog.xray.client.services.system.System;

import java.io.Closeable;

public interface Xray extends Closeable {

    System system();

    BinaryManagers binaryManagers();

    Summary summary();

    @Override
    void close();
}