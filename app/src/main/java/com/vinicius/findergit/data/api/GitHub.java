package com.vinicius.findergit.data.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by paulo on 26/11/17.
 *
 */

public interface GitHub {
    @GET("/search/repositories")
    Call<Repositories> searchRepositories(@Query("q") String query);
}
