package com.orange.score.common.utils;

import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OkHttpUtil {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).build();

    public static String get(String url, List<RequestPair> pairs) throws IOException {

        StringBuilder urlBuilder = new StringBuilder(url);
        if (pairs != null) {
            for (int i = 0; i < pairs.size(); i++) {
                if (i == 0) {
                    urlBuilder.append("?").append(pairs.get(i).getKey()).append("=").append(pairs.get(i).getValue());
                } else {
                    urlBuilder.append("&").append(pairs.get(i).getKey()).append("=").append(pairs.get(i).getValue());
                }
            }
        }
        url = urlBuilder.toString();
        Request request = new Request.Builder().url(url).get().build();
        Response response = client.newCall(request).execute();
        return response.body() == null ? "[]" : response.body().string();
    }

    public static String postJson(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        return response.body() == null ? "[]" : response.body().string();
    }

    public static String postForm(String url, List<RequestPair> pairs) throws IOException {
        FormBody.Builder params = new FormBody.Builder();
        for (RequestPair pair : pairs) {
            params.add(pair.getKey(), String.valueOf(pair.getValue()));
        }
        Request request = new Request.Builder().url(url).post(params.build()).build();
        Response response = client.newCall(request).execute();
        return response.body() == null ? "[]" : new String(response.body().bytes(), "gbk");
    }
}
