package ite.kmitl.project.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ite.kmitl.project.R;

public class ProductViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public ImageView ivProduct;
        public TextView tvAmount;
        // same custom view group
        public ProductViewHolder(View itemView) {
                super(itemView);
                        tvName = itemView.findViewById(R.id.tvName);
                        ivProduct = itemView.findViewById(R.id.ivProduct);
                        tvAmount = itemView.findViewById(R.id.tvAmount);
                }
        }