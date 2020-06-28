package com.aminsujit.foodrecipes.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.aminsujit.foodrecipes.models.Recipe;
import com.aminsujit.foodrecipes.repository.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {
    private RecipeRepository mRecipeRepository;
    private boolean mIsViewingRecipes;

    public RecipeListViewModel() {
        mRecipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipeRepository.getRecipes();
    }

    public void searchRecipesApi(String query, int pageNumber){
        mIsViewingRecipes = true;
        mRecipeRepository.searchRecipesApi(query, pageNumber);
    }

    public boolean isViewingRecipes() {
        return mIsViewingRecipes;
    }

    public void setIsViewingRecipes(boolean isViewingRecipes){
        mIsViewingRecipes = isViewingRecipes;
    }
}
