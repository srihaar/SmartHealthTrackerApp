package com.project.smarthealthtracker;

/**
 * Created by SrIHaaR on 4/30/2017.
 */


import com.loopj.android.http.*;

public class NodeRestClient {
    private static final String BASE_URL = "http://10.0.2.2:3000";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
