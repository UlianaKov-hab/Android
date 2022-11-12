package com.example.androidproject.categorycard;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidproject.R;
import com.example.androidproject.dto.categories.CategoryItemDTO;

public class CategoryCardViewHolder extends RecyclerView.ViewHolder{
    private View view;
    public ImageView categoryimage;
    public TextView categoryname;



    public CategoryCardViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
        categoryimage = itemView.findViewById(R.id.categoryimage);
        categoryname = itemView.findViewById(R.id.categoryname);
    }
    public View getView() {return view;  }
}
