package com.cloud.util;

import com.google.common.collect.Maps;
import com.ning.http.client.*;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.AsyncHttpClientConfig.Builder;
import com.ning.http.multipart.FilePart;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 异步httpclient
 * <br>使用netty NIO特性
 * <br>所有方法调用http请求后直接返回
 *
 * @author summer
 */
public class AsyncHttpUtils {

    public static final int DEFAULT_CONNECTION_TIME_OUT_IN_MS = 15 * 1000;
    public static final int DEFAULT_REQUEST_TIMEOUT_IN_MS = 90 * 1000;
    public static final int DEFAULT_IDLE_CONNECTION_IN_POOL_TIMEOUT_IN_MS = 90 * 1000;
    public static final int DEFAULT_IDLE_CONNECTION_TIMEOUT_IN_MS = 90 * 1000;
    public static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 3000;
    public static final int DEFAULT_MAX_CONNECTION_PER_HOST = 300;
    private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(AsyncHttpUtils.class);
    private static AsyncHttpClient asyncHttpClient2;
    private static ConcurrentHashMap<Integer, AsyncHttpClient> clientMap = new ConcurrentHashMap<Integer, AsyncHttpClient>();

    private static AsyncHttpClient getAsyncHttpClient(int reqTimeout) {
        AsyncHttpClient client = clientMap.get(reqTimeout);
        if (client == null) {
            Builder cf = new AsyncHttpClientConfig.Builder();
            cf.setCompressionEnabled(true);
            cf.setConnectionTimeoutInMs(DEFAULT_CONNECTION_TIME_OUT_IN_MS);
            cf.setMaximumConnectionsTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
            cf.setMaximumConnectionsPerHost(DEFAULT_MAX_CONNECTION_PER_HOST);
            cf.setRequestTimeoutInMs(reqTimeout);
            cf.setIdleConnectionInPoolTimeoutInMs(DEFAULT_IDLE_CONNECTION_IN_POOL_TIMEOUT_IN_MS);
            cf.setIdleConnectionTimeoutInMs(DEFAULT_IDLE_CONNECTION_TIMEOUT_IN_MS);
            client = new AsyncHttpClient(cf.build());
            clientMap.putIfAbsent(reqTimeout, client);
        }
        return client;
    }

    public static AsyncHttpClient getDefault() {
        if (asyncHttpClient2 == null) {
            asyncHttpClient2 = getAsyncHttpClient(DEFAULT_REQUEST_TIMEOUT_IN_MS);
        }
        return asyncHttpClient2;
    }

    /**
     * 通过GET方法请求url
     *
     * @param url
     * @param params
     */
    public static void getUrlAsString(String url, Map<String, String> params, HttpResultCallback callback) {
        if (StringUtils.isBlank(url)) {
            return;
        }

        String gurl = getFullUrl(url, params, "utf-8");
        try {
            BoundRequestBuilder rb = getDefault().prepareGet(gurl);
            rb.setHeader("Accept-Encoding", "gzip,deflate");
            rb.execute(new AsynchHandler(callback));
        } catch (IOException e) {
            DB_LOGGER.error(e, 30);
        }
    }

    /**
     * 通过GET方法请求url
     *
     * @param url
     * @param params
     */
    public static void getUrlAsString(String url, Map<String, String> params, int reqTimeout, HttpResultCallback callback) {
        if (StringUtils.isBlank(url)) {
            return;
        }

        String gurl = getFullUrl(url, params, "utf-8");
        try {
            BoundRequestBuilder rb = getAsyncHttpClient(reqTimeout).prepareGet(gurl);
            rb.setHeader("Accept-Encoding", "gzip,deflate");
            rb.execute(new AsynchHandler(callback));
        } catch (IOException e) {
            DB_LOGGER.error(e, 30);
        }
    }

    /**
     * 通过POST方法请求url
     *
     * @param url
     * @param params
     */
    public static void postUrlAsString(String url, Map<String, String> params, int reqTimeout, HttpResultCallback callback) {
        try {
            BoundRequestBuilder rb = getAsyncHttpClient(reqTimeout).preparePost(url);
            rb.setHeader("Accept-Encoding", "gzip,deflate");
            rb.setBodyEncoding("utf-8");
            setPostParams(params, rb);
            rb.execute(new AsynchHandler(callback));
        } catch (IOException e) {
            DB_LOGGER.error(e, 30);
        }
    }

    /**
     * 通过POST方法请求url
     *
     * @param url
     * @param params
     */
    public static void postUrlAsString(String url, Map<String, String> params, HttpResultCallback callback) {
        try {
            BoundRequestBuilder rb = getDefault().preparePost(url);
            rb.setHeader("Accept-Encoding", "gzip,deflate");
            rb.setBodyEncoding("utf-8");
            setPostParams(params, rb);
            rb.execute(new AsynchHandler(callback));
        } catch (IOException e) {
            DB_LOGGER.error(e, 30);
        }
    }

    public static void postUrlAsString(String url, Map<String, String> params, Map<String, String> headerMap, HttpResultCallback callback) {
        try {
            BoundRequestBuilder rb = getDefault().preparePost(url);
            if (headerMap != null) {
                for (String key : headerMap.keySet()) {
                    rb.setHeader(key, headerMap.get(key));
                }
            }
            rb.setHeader("Accept-Encoding", "gzip,deflate");
            rb.setBodyEncoding("utf-8");

            setPostParams(params, rb);
            rb.execute(new AsynchHandler(callback));
        } catch (IOException e) {
            DB_LOGGER.error(e, 30);
        }
    }

    /**
     * 通过POST方法请求url
     *
     * @param url
     * @param body
     */
    public static void postBodyAsString(String url, String body, HttpResultCallback callback) {
        try {
            BoundRequestBuilder rb = getDefault().preparePost(url);
            rb.setHeader("Accept-Encoding", "gzip,deflate");
            rb.setBodyEncoding("utf-8");
            rb.setBody(body);

            rb.execute(new AsynchHandler(callback));
        } catch (IOException e) {
            DB_LOGGER.error(e, 30);
        }
    }

    public static void uploadFile(String url, Map<String, String> params, byte[] bytes, String inputName, String fileName, HttpResultCallback callback) {
        try {
            BoundRequestBuilder rb = getDefault().preparePost(url);
            setPostParams(params, rb);
            ByteArrayPart part = new ByteArrayPart(inputName, fileName, bytes, null, "utf-8");
            rb.addBodyPart(part);

            rb.execute(new AsynchHandler(callback));
        } catch (IOException e) {
            DB_LOGGER.error(e, 30);
        }
    }

    public static void uploadFile(String url, Map<String, String> params, File file, String inputName, String fileName, HttpResultCallback callback) {
        try {
            BoundRequestBuilder rb = getDefault().preparePost(url);
            setPostParams(params, rb);
            FilePart part = new FilePart(inputName, fileName, file);
            rb.addBodyPart(part);
            rb.execute(new AsynchHandler(callback));
        } catch (IOException e) {
            DB_LOGGER.error(e, 30);
        }
    }

    private static void setPostParams(Map<String, String> params, BoundRequestBuilder rb) {
        rb.setHeader("Accept-Encoding", "gzip,deflate");
        rb.setBodyEncoding("utf-8");

        if (params != null && !params.isEmpty()) {
            Map<String, Collection<String>> parameters = Maps.newHashMap();
            for (String key : params.keySet()) {
                Collection<String> cs = new ArrayList<String>(1);
                cs.add(params.get(key));
                parameters.put(key, cs);
            }
            rb.setParameters(parameters);
        }
    }

    private static String getFullUrl(String url, Map<String, String> params, String encode) {
        if (params != null) {
            if (url.indexOf('?') == -1) {
                url += "?";
            } else {
                url += "&";
            }
            for (String name : params.keySet()) {
                try {
                    if (StringUtils.isNotBlank(params.get(name))) {
                        url += name + "=" + URLEncoder.encode(params.get(name), encode) + "&";
                    }
                } catch (UnsupportedEncodingException e) {
                }
            }
            url = url.substring(0, url.length() - 1);
        }
        return url;
    }

    private static void addHeader(HttpResult ret, Response response) {
        try {
            FluentCaseInsensitiveStringsMap headers = response.getHeaders();
            for (String key : headers.keySet()) {
                ret.addHeader(key, headers.getJoinedValue(key, ","));
            }
        } catch (Exception e) {
        }
    }

    private static class AsynchHandler extends AsyncCompletionHandler<HttpResult> {
        private HttpResultCallback callback;

        public AsynchHandler(HttpResultCallback callback) {
            this.callback = callback;
        }

        @Override
        public HttpResult onCompleted(Response response) throws Exception {
            boolean success = (response.getStatusCode() == HttpStatus.SC_OK);
            HttpResult result = new HttpResult(success, response.getResponseBody(), "httpStatus:" + response.getStatusCode(), response.getStatusCode());
            addHeader(result, response);
            if (callback != null) {
                callback.processResult(result);
            }
            return result;
        }

        @Override
        public void onThrowable(Throwable t) {
            //logger.error(t.getMessage(), t);
            callback.processError(t);
        }
    }

}
