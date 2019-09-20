package org.wisdomrider.lazylibrarydemo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;


public interface Api {
    @GET("/")
    Call<MainActivity.API> a();

}
