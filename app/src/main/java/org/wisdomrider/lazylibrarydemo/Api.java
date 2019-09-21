package org.wisdomrider.lazylibrarydemo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;


public interface Api {
    @GET("/api/5d737fd388be6a18c592a704/demo1")
    Call<ArrayList<MainActivity.API>> a();

}
