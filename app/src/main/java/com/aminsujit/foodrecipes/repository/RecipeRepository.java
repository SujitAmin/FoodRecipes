package com.aminsujit.foodrecipes.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aminsujit.foodrecipes.models.Recipe;
import com.aminsujit.foodrecipes.requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository {
    private static RecipeRepository instance;
    private RecipeApiClient mReceipeApiClient;

    public static RecipeRepository getInstance() {
        if (instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }
    private RecipeRepository() {
        mReceipeApiClient = RecipeApiClient.getInstance();
    }
    public LiveData<List<Recipe>> getRecipes() {
        return mReceipeApiClient.getReceipes();
    }
    public void searchRecipesApi(String query, int pageNumber) {
        if (pageNumber == 0) {
            pageNumber =  1;
        }
        mReceipeApiClient.searchRecipesApi(query,pageNumber);
    }
}
