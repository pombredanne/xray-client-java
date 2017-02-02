package com.jfrog.xray.client.impl.services.system;

import com.jfrog.xray.client.impl.XrayImpl;
import org.apache.http.HttpResponse;

/**
 * Created by romang on 2/2/17.
 */
public class SystemImpl implements System {

    private XrayImpl xray;

    public SystemImpl(XrayImpl xray) {
        this.xray = xray;
    }

    @Override
    public boolean ping() {
        try {
            HttpResponse response = xray.get("system/ping", null);
            if (response.getStatusLine().getStatusCode() == 200) {
                return true;
            }
        } catch (Exception e) {
            // Do nothing
        }
        return false;
    }

    @Override
    public Version version() {
        return null;
    }
}
