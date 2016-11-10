package fi.virpihav.yelpdemo.service;

import android.location.Location;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import fi.virpihav.yelpdemo.model.SearchResponse;
import fi.virpihav.yelpdemo.model.Business;
import fi.virpihav.yelpdemo.model.Token;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YelpService {

    private static final String TAG = YelpService.class.getSimpleName();

    public void searchBusinesses(String query, Location location, Token token, final SearchCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.yelp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient(token))
                .build();
        YelpApi yelpApi = retrofit.create(YelpApi.class);
        Call<SearchResponse> call = yelpApi.search(query, location.getLongitude(), location.getLatitude());
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse body = response.body();
                Log.d(TAG, "onResponse() called with: call = [" + call + "], response = [" + body + "]");
                if (body != null) {
                    callback.onBusinesses(body.getBusinesses());
                } else {
                    callback.onFailure("Got no results");
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                callback.onFailure(t.getMessage());
            }
        });
    }

    private OkHttpClient getClient(final Token token) {
        return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder();
                if (token != null) {
                    builder.header("Accept", "application/json")
                            .header("Authorization", token.getTokenType() + " " + token.getToken())
                            .method(original.method(), original.body());
                }
                Request request = builder.build();
                return chain.proceed(request);
            }
        }).build();
    }

    public interface SearchCallback {
        void onBusinesses(List<Business> businesses);

        void onFailure(String message);
    }
}
