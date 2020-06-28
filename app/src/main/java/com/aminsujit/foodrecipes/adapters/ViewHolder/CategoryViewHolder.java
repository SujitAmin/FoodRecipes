package com.aminsujit.foodrecipes.adapters.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aminsujit.foodrecipes.R;
import com.aminsujit.foodrecipes.adapters.OnRecipeListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener
{
    public CircleImageView categoryImage;
    public TextView categoryTitle;
    public OnRecipeListener listener;

    public CategoryViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener) {
        super(itemView);
        categoryImage = itemView.findViewById(R.id.category_image);
        categoryTitle = itemView.findViewById(R.id.category_title);
        listener = onRecipeListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        listener.onCategoryClick(categoryTitle.getText().toString());
    }
}
