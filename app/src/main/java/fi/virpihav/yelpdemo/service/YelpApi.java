package fi.virpihav.yelpdemo.service;

import fi.virpihav.yelpdemo.model.SearchResponse;
import fi.virpihav.yelpdemo.model.Token;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface YelpApi {

    @GET("v3/businesses/search")
    Call<SearchResponse> search(@Query("term") String term,
                                @Query("longitude") double longitude,
                                @Query("latitude") double latitude);

    @POST("oauth2/token")
    @FormUrlEncoded
    Call<Token> authenticate(@Field("grant_type") String grant_type,
                             @Field("client_id") String client_id,
                             @Field("client_secret") String client_secret);
}
