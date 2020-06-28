package com.aminsujit.foodrecipes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.aminsujit.foodrecipes.BaseActivity;
import com.aminsujit.foodrecipes.R;
import com.aminsujit.foodrecipes.models.Recipe;
import com.aminsujit.foodrecipes.viewmodel.RecipeListViewModel;

import java.util.List;

public class RecipeListActivity extends BaseActivity {

    private static final String TAG = "RecipeListActivity";
    private RecipeListViewModel mRecipeListViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        mRecipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
        subscribeObservers();
        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testRetrofitRequest();
            }
        });
    }

    private void subscribeObservers() {
        mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                for(Recipe recipe : recipes){
                    Log.d(TAG, "RecipeListActivity onChanged: "+recipe);
                }
            }
        });
    }
    private void searchRecipesApi(String query, int pageNumber) {
        mRecipeListViewModel.searchRecipesApi(query,pageNumber);
    }

    private void testRetrofitRequest(){
        searchRecipesApi("chicken", 1);
//        RecipeApi recipeApi = ServiceGenerator.getRecipeApi();
//        // Do search using Retrofit
////        Call<RecipeSearchResponse> responseCall = recipeApi
////                .searchRecipe(
////                        Constants.API_KEY,
////                        "chicken",
////                        "1"
////                );
////
////        responseCall.enqueue(new Callback<RecipeSearchResponse>() {
////            @Override
////            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
////                Log.d(TAG, "onResponse: Server Response: " + response.toString());
////                if(response.code() == 200){
////                    Log.d(TAG, "onResponse: " + response.body().toString());
////                    List<Recipe> recipes = new ArrayList<>(response.body().getRecipes());
////                    for(Recipe recipe: recipes){
////                        Log.d(TAG, "onResponse: " + recipe.toString());
////                    }
////                }
////                else {
////                    try {
////                        Log.d(TAG, "onResponse: " + response.errorBody().string());
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
////                }
////            }
//
//        // do get using retrofit
//        Call<RecipeResponse> responseCall = recipeApi
//                .getRecipe(
//                        "41470"
//                );
//        responseCall.enqueue(new Callback<RecipeResponse>() {
//            @Override
//            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
//                Log.d(TAG, "RecipeListActivity: Server Response: " + response.toString());
//                if (response.code() == 200) {
//                    Recipe recipe = response.body().getRecipe();
//                    Log.d(TAG, "RecipeListActivity: " + recipe.toString());
//                }
//                else{
//                    try {
//                        Log.d(TAG, "RecipeListActivity: " + response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Call<RecipeResponse> call, Throwable t) {
//                Log.d(TAG, "RecipeListActivity: ERROR: " + t.getMessage());
//            }
//        });
    }

}