package com.example.testproject.Network;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

//    public static String BASE_URL="https://nicedemo.logikoof.org/api/";
    public static String BASE_URL="https://jnkv.logikoof.org/api/";
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit==null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
          //  JavaNetCookieJar jncj = new JavaNetCookieJar(CookieHandler.getDefault());




            // init cookie manager
            CookieHandler cookieHandler = new CookieManager();

            OkHttpClient  client = new OkHttpClient.Builder().addNetworkInterceptor(interceptor)
                   // .cookieJar(new JavaNetCookieJar(cookieHandler))
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
