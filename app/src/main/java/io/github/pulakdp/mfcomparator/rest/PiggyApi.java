package io.github.pulakdp.mfcomparator.rest;

import io.github.pulakdp.mfcomparator.model.DetailResponse;
import io.github.pulakdp.mfcomparator.model.SearchQuery;
import io.github.pulakdp.mfcomparator.model.SearchResponse;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Author: PulakDebasish
 */

public interface PiggyApi {

    @Headers({
            "authorization: Token a41d2b39e3b47412504509bb5a1b66498fb1f43a",
            "cache-control: no-cache",
            "content-type: application/json"
    })
    @POST("/v2/mf/search/")
    Observable<SearchResponse> searchMutualFunds(@Body SearchQuery query);

    @Headers({
            "authorization: Token a41d2b39e3b47412504509bb5a1b66498fb1f43a",
            "cache-control: no-cache",
            "content-type: application/json"
    })
    @GET("/v1/mf/")
    Observable<DetailResponse> getMutualFundDetail(@Query("key") String key);
}
