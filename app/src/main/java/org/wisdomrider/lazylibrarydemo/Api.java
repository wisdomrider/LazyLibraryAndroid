package org.wisdomrider.lazylibrarydemo;


import retrofit2.Call;
import retrofit2.http.GET;


public interface Api {
    @GET("/api/5df7249209e7340017b72221/mac")
    Call<Dataclass> a();
}