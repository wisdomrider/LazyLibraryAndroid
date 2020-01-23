package org.wisdomrider.lazylibrarydemo;


import retrofit2.Call;
import retrofit2.http.GET;


public interface Api {
    @GET("/api/5df8b1e018f7090017c1df92/hello")
    Call<Dataclass> a();

    @GET("/api/v1/dashboard")
    Call<Dash> getData();

}

