package com.aminsujit.foodrecipes.requests;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import com.aminsujit.foodrecipes.AppExecutors;
import com.aminsujit.foodrecipes.models.Recipe;
import com.aminsujit.foodrecipes.requests.responses.RecipeSearchResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.aminsujit.foodrecipes.util.Constants.NETWORK_TIMEOUT;

public class RecipeApiClient {
    private static final String TAG = "RecipeApiClient";

    private static RecipeApiClient instance;
    private MutableLiveData<List<Recipe>> mReceipes;
    private RetrieveRunnable mRetrieveRunnable;

    public static RecipeApiClient getInstance(){
        if(instance == null) {
            instance = new RecipeApiClient();
        }
        return instance;
    }
    private RecipeApiClient() {
        mReceipes = new MutableLiveData<>();
    }

    public MutableLiveData<List<Recipe>> getReceipes() {
        return mReceipes;
    }

    private class RetrieveRunnable implements Runnable {
        String query;
        int pageNumber;
        boolean cancelRequest;

        public RetrieveRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try{
                Response response = getReceipesFromApi(query, pageNumber).execute();
                if(cancelRequest) {
                    Log.e(TAG, "run: "+response.errorBody().toString());
                    return;
                }

                if (response.code() == 200) {
                    List<Recipe> list = new ArrayList<>(((RecipeSearchResponse) response.body()).getRecipes());
                    if (pageNumber == 1) {
                        mReceipes.postValue(list);
                    }else {
                        List<Recipe> currentRecipes = mReceipes.getValue();
                        currentRecipes.addAll(list);
                        mReceipes.postValue(currentRecipes);
                    }
                } else {
                    assert response.errorBody() != null;
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: error: "+error);
                    mReceipes.postValue(null);
                }
            }catch (Exception e) {
                e.printStackTrace();
                mReceipes.postValue(null);
            }
        }
        //        Call<RecipeSearchResponse> responseCall = recipeApi
        //                .searchRecipe(
        //                        Constants.API_KEY,
        //                        "chicken",
        //                        "1"
        //                );
        private Call<RecipeSearchResponse> getReceipesFromApi(String query, int pageNumber) {
            return ServiceGenerator.getRecipeApi().searchRecipe(
              query,
              String.valueOf(pageNumber)
            );
        }
        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: cancelling the retrieval query");
            cancelRequest = true;
        }

    }
    public void searchRecipesApi(String query, int pageNumber) {
        if(mRetrieveRunnable != null) {
            mRetrieveRunnable = null;
        }
        mRetrieveRunnable = new RetrieveRunnable(query, pageNumber);
        final Future handler = AppExecutors.getInstance().getmNetworkIO().submit(mRetrieveRunnable);

        //set a timeout for the data refresh
        AppExecutors.getInstance().getmNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }
}
