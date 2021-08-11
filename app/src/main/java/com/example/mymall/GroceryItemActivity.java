package com.example.mymall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class GroceryItemActivity extends AppCompatActivity implements AddReviewDialog.AddReview {
    private static final String TAG = "GroceryItemActivity";

    private RecyclerView reviewRecView;
    private TextView txtName, txtPrice, txtDescription, txtAddReview;
    private ImageView itemImage, firstEmptyStar, secondEmptyStar, thirdEmptyStar,
            firstFilledStar, secondFilledStar, thirdFilledStar;
    private Button btnAddToCart;
    private RelativeLayout firstStarRelLayout, secondStarRelLayout, thirdStarRelLayout;
    private GroceryItem incomingItem;
    private ReviewsAdapter adapter;
    private MaterialToolbar toolbar;
    public static final String GROCERY_ITEM_KEY="incoming_item";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_item);
        initViews();

        setSupportActionBar(toolbar);
        adapter=new ReviewsAdapter();
        Intent intent = getIntent();
        if (null!=intent) {
            incomingItem=intent.getParcelableExtra(GROCERY_ITEM_KEY);
            if (null!=incomingItem) {
                txtName.setText(incomingItem.getName());
                txtPrice.setText(String.valueOf(incomingItem.getPrice()));
                txtDescription.setText(incomingItem.getDescription());
                Glide.with(this).asBitmap().
                        load(incomingItem.getImageUrl()).into(itemImage);
                ArrayList<Review> review = Utils.getReviewsById(this, incomingItem.getId());
                reviewRecView.setAdapter(adapter);
                reviewRecView.setLayoutManager(new LinearLayoutManager(this));
                if (null!=review) {
                    if (review.size()>0) {
                        adapter.setReview(review);

                    }
                }
            }
            btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: add item to cart
                    Utils.addItemToCart(GroceryItemActivity.this, incomingItem);
                    Log.d(TAG, "onClick: cartItems " + Utils.getCartItems(GroceryItemActivity.this));
                }
            });

            txtAddReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: 4/24/2020 Show a dialog
                    AddReviewDialog dialog= new AddReviewDialog();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(GROCERY_ITEM_KEY, incomingItem);
                    dialog.setArguments(bundle);
                    dialog.show(getSupportFragmentManager(),"add review");

                }
            });
            handelRating();
        }
    }
    private void handelRating(){
        switch(incomingItem.getRate()){
            case 0:
                firstEmptyStar.setVisibility(View.VISIBLE);
                firstFilledStar.setVisibility(View.GONE);
                secondEmptyStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.GONE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                break;
            case 1:
                firstEmptyStar.setVisibility(View.GONE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.GONE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                break;
            case 2:
                firstEmptyStar.setVisibility(View.GONE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.GONE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                break;
            case 3:
                firstEmptyStar.setVisibility(View.GONE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.GONE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.GONE);
                thirdFilledStar.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

        firstStarRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(incomingItem.getRate()!=1){
                    Utils.changeRate(GroceryItemActivity.this, incomingItem.getId(), 1);
                    incomingItem.setRate(1);
                    handelRating();
                }
            }
        });
        secondStarRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(incomingItem.getRate()!=2){
                    Utils.changeRate(GroceryItemActivity.this, incomingItem.getId(), 2);
                    incomingItem.setRate(2);
                    handelRating();
                }
            }
        });
        thirdStarRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(incomingItem.getRate()!=3){
                    Utils.changeRate(GroceryItemActivity.this, incomingItem.getId(), 3);
                    incomingItem.setRate(3);
                    handelRating();
                }
            }
        });
    }
    private void initViews(){
        txtName=findViewById(R.id.txtName);
        txtPrice=findViewById(R.id.txtPrice);
        txtDescription=findViewById(R.id.txtDescription);
        txtAddReview=findViewById(R.id.txtAddReview);
        itemImage=findViewById(R.id.itemImage);
        firstEmptyStar=findViewById(R.id.firstEmptyStar);
        secondEmptyStar=findViewById(R.id.secondEmptyStar);
        thirdEmptyStar=findViewById(R.id.thirdEmptyStar);
        firstFilledStar=findViewById(R.id.firstEmptyStar);
        secondFilledStar=findViewById(R.id.secondFilledStar);
        thirdFilledStar=findViewById(R.id.thirdFilledStar);
        toolbar=findViewById(R.id.toolbar);
    }

    @Override
    public void onAddReview(Review review) {
          Utils.adReview(this, review);
          ArrayList<Review> reviews=Utils.getReviewsById(this, review.getGroceryItemId());
          if(null!=reviews){
              adapter.setReview(reviews);
          }
    }
}