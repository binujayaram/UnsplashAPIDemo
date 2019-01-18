package com.binu.unsplashdemo.api;

import android.util.Log;

import com.binu.unsplashdemo.UnsplashApplication;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nmillward on 3/25/17.
 */

public class UnsplashApi {

    private static final String BASE_URL = "https://api.unsplash.com";
    private static final String AUTH_ID = "Authorization: Client-ID yor auth id"; //TODO: ENTER VALID CLIENT ID
    private static UnsplashApi instance;
    private UnsplashService service;

    private UnsplashApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(UnsplashService.class);
    }

    public static UnsplashApi getInstance() {
        if (instance == null) {
            instance = new UnsplashApi();
        }

        return instance;
    }

    public UnsplashService getService() {
        return service;
    }


    private OkHttpClient client = new OkHttpClient.Builder()
            .addNetworkInterceptor(new ResponseCacheInterceptor())
            .addInterceptor(new AuthorizationHeaderInterceptor())
            .addInterceptor(new OfflineCacheInterceptor())
            .cache(new Cache(new File(
                            UnsplashApplication.getApplicationInstance().getCacheDir(),
                            "unsplashApiResponses"),
                            5 * 1024 * 1024) // 5 MB
            )
            .build();


    private static class AuthorizationHeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request.Builder builder = originalRequest.newBuilder().header("Authorization", AUTH_ID);
            Request newRequest = builder.build();
            return chain.proceed(newRequest);
        }
    }


    private static class ResponseCacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-size=" + 60)
                    .build();
        }
    }


    private static class OfflineCacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!UnsplashApplication.hasNetwork()) {
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + (60 * 60 * 24 * 7)) // 1 Week
                        .build();
                Log.d("API", "New offline cache stored");
            }
            return chain.proceed(request);
        }
    }

}
