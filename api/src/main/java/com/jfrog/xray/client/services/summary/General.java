package com.jfrog.xray.client.services.summary;

/**
 * Created by romang on 2/27/17.
 */
public interface General {

    String getName();

    String getPath();

    String getPkgType();

    String getSha256();

    String getComponentId();
}
