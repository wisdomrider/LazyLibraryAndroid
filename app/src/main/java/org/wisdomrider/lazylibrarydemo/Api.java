package org.wisdomrider.lazylibrarydemo;


import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    @GET("/api/5e2eac3b6208fd0017011b1f/family")
    Call<Dataclass> a();

    @GET("/api/v1/dashboard")
    Call<Dash> getData();

}

