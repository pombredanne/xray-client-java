package com.jfrog.xray.client.impl;

import com.jfrog.xray.client.Xray;
import org.apache.http.impl.client.CloseableHttpClient;

import java.net.MalformedURLException;

/**
 * Created by romang on 2/2/17.
 */
public class XrayClient {

    static public Xray create(CloseableHttpClient preConfiguredClient, String url) throws MalformedURLException {
        return new XrayImpl(preConfiguredClient, url);
    }

    /**
     * Username, API key, and custom url
     */
    static public Xray create(String url, String username, String password) throws MalformedURLException {
        XrayClientConfigurator configurator = new XrayClientConfigurator();
        configurator.setHostFromUrl(url);
        configurator.setCredentials(username, password, true);

        return new XrayImpl(configurator.getClient(), url);
    }
}
