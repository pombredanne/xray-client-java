package com.jfrog.xray.client.impl;

import com.jfrog.xray.client.Xray;
import com.jfrog.xray.client.impl.services.system.System;
import com.jfrog.xray.client.impl.services.system.SystemImpl;
import org.apache.http.HttpException;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.Callable;


/**
 * @author Roman Gurevitch
 */
public class XrayImpl implements Xray {
    private static final Logger log = LoggerFactory.getLogger(XrayImpl.class);
    private static final String API_BASE = "/api/v1/";

    private CloseableHttpClient client;
    private URL baseApiUrl;
    private ResponseHandler<HttpResponse> responseHandler = new XrayResponseHandler();

    public XrayImpl(CloseableHttpClient client, String url) throws MalformedURLException {
        this.client = client;
        this.baseApiUrl = new URL(new URL(url), API_BASE);
    }

    static public void addContentTypeJsonHeader(Map<String, String> headers) {
        headers.put(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
    }

    static public void addContentTypeBinaryHeader(Map<String, String> headers) {
        headers.put(HttpHeaders.CONTENT_TYPE, ContentType.DEFAULT_BINARY.getMimeType());
    }

    private static boolean statusNotOk(int statusCode) {
        return statusCode != HttpStatus.SC_OK
                && statusCode != HttpStatus.SC_CREATED
                && statusCode != HttpStatus.SC_ACCEPTED;
    }

    @Override
    public System system() {
        return new SystemImpl(this);
    }

    @Override
    public void close() {
        HttpClientUtils.closeQuietly(client);
    }

    private void setHeaders(HttpUriRequest request, Map<String, String> headers) {
        if (headers != null && !headers.isEmpty()) {
            for (String header : headers.keySet()) {
                request.setHeader(header, headers.get(header));
            }
        }
    }

    public HttpResponse get(String uri, Map<String, String> headers) throws HttpException, IOException {
        HttpGet getRequest = new HttpGet(createUrl(uri));
        return setHeadersAndExecute(getRequest, headers);
    }

    public HttpResponse head(String uri, Map<String, String> headers) throws HttpException, IOException {
        HttpHead headRequest = new HttpHead(createUrl(uri));
        return setHeadersAndExecute(headRequest, headers);
    }

    private String createUrl(String queryPath) throws MalformedURLException {
        log.debug("Trying to encode uri: '{}' with base url: {}", queryPath, API_BASE);
        return new URL(baseApiUrl, queryPath).toString();
    }

    private HttpResponse setHeadersAndExecute(HttpUriRequest request, Map<String, String> headers) throws IOException {
        setHeaders(request, headers);
        return execute(request, null);
    }


    private HttpResponse execute(HttpUriRequest request, HttpClientContext context) throws IOException {
        log.debug("Executing {} request to path '{}', with headers: {}", request.getMethod(), request.getURI(),
                Arrays.toString(request.getAllHeaders()));
        if (context != null) {
            return client.execute(request, responseHandler, context);
        } else {
            return client.execute(request, responseHandler);
        }
    }

    /**
     * A callable that executes a single put request, returns a String containing an error or '200' if successful
     */
    private static class RequestRunner implements Callable<String> {

        private final HttpRequestBase request;
        private final CloseableHttpClient client;
        private final HttpClientContext context;
        private final ResponseHandler<HttpResponse> responseHandler;

        public RequestRunner(HttpRequestBase request, CloseableHttpClient client, ResponseHandler<HttpResponse> responseHandler) {
            this.request = request;
            this.client = client;
            this.context = HttpClientContext.create();
            this.responseHandler = responseHandler;
        }

        @Override
        public String call() {
            return null;
        }
    }

    private class XrayResponseHandler implements ResponseHandler<HttpResponse> {

        @Override
        public HttpResponse handleResponse(HttpResponse response) {
            return response;
        }
    }
}
