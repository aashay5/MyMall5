package com.example.mymall;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private ArrayList<Review> review = new ArrayList<>();

    public ReviewsAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtReview.setText(review.get(position).getText());
        holder.txtDate.setText(review.get(position).getDate());
        holder.txtUserName.setText(review.get(position).getUserName());
    }

    @Override
    public int getItemCount() {
        return review.size();
    }

    public void setReview(ArrayList<Review> review) {
        this.review = review;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtUserName, txtReview, txtDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUserName=itemView.findViewById(R.id.txtUserName);
            txtReview=itemView.findViewById(R.id.txtReview);
            txtDate=itemView.findViewById(R.id.txtDate);
        }
    }
}
