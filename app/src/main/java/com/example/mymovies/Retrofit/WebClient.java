package com.example.mymovies.Retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.mymovies.Helper.URL.BASE_URL;

public class WebClient {
    private static Retrofit getRetrofitInstance() {


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .callTimeout(3, TimeUnit.MINUTES)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);


        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();


    }

    /**
     * Get API Service
     *
     * @return API Service
     */
    public static WebService getWebService() {
        return getRetrofitInstance().create(WebService.class);
    }
}
