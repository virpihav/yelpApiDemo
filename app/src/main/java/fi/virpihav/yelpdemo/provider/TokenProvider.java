package fi.virpihav.yelpdemo.provider;

import android.util.Log;

import fi.virpihav.yelpdemo.service.YelpApi;
import fi.virpihav.yelpdemo.model.Token;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenProvider {

    private static final String TAG = TokenProvider.class.getSimpleName();

    public void getToken(final Callback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.yelp.com/")
                .build();
        Call<Token> tokenCall = retrofit.create(YelpApi.class).authenticate("client_credentials",
                "KEY",
                "SECRET");
        tokenCall.enqueue(new retrofit2.Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Log.d(TAG, "onResponse() called with: call = [" + call + "], response = [" + response + "]");
                callback.onToken(response.body());
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                // TODO
            }
        });
    }

    public interface Callback {
        void onToken(Token token);
    }
}
