package org.wisdomrider.lazylibrarydemo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;


public interface Api {

    @GET("/posts")
    Call<ArrayList<MainActivity.Response>> list();
}