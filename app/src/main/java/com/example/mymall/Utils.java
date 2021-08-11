package com.example.mymall;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.nio.file.attribute.GroupPrincipal;
import java.util.ArrayList;

public class Utils {
    private static final String ALL_ITEMS_KEY ="all_items";
    public static int ID =0;
    private static final String DB_NAME = "fake_database";
    private static Gson gson = new Gson();
    private static Type groceryListType = new TypeToken<ArrayList<GroceryItem>>() {
    }.getType();

    public static void initSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);
        if(null==allItems){
            initAllItems(context);
        }
    }
    public static void clearSharedPreferences(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.clear();
        editor.commit();

    }
    private static void initAllItems(Context context){
        ArrayList<GroceryItem> allItems = new ArrayList<>();
        GroceryItem milk = new GroceryItem("Milk","Milk is a nutrient-rich liquid food produced by mammary glands of mammals. It is primary source of calcium.",
                "https://static.scientificamerican.com/sciam/cache/file/A9E4C1EB-4BBC-48D2-88CFA877B19815CE_source.jpg",
                "drinks", 2.3, 0);
        milk.setUserPoint(15);
        allItems.add(milk);


        GroceryItem soda = new GroceryItem("Soda", "Tasty", "https://cdn.diffords.com/contrib/bws/2019/05/5cc9b8261f976.jpg","Drink", 0.99, 15);
        soda.setPopularityPoint(8);
        soda.setUserPoint(5);
        allItems.add(soda);

        GroceryItem gummyBear = new GroceryItem("GummyBear", "Disgusting", "https://i.ytimg.com/vi/uElajoX3HoI/maxresdefault.jpg", "Food", 0.5, 100);
        gummyBear.setPopularityPoint(100);
        gummyBear.setUserPoint(100);
        allItems.add(gummyBear);

        GroceryItem iceCream = new GroceryItem("IceCream", "Delicious", "https://bigseventravel.com/wp-content/uploads/2019/09/Screenshot-2019-09-25-at-13.53.22.png","Food", 5.4, 10);
        iceCream.setUserPoint(10);
        iceCream.setPopularityPoint(9);
        allItems.add(iceCream);


        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(ALL_ITEMS_KEY, gson.toJson(allItems));
        editor.commit();
    }
    public static ArrayList<GroceryItem> getAllItem(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);
        return allItems;
    }

    public static void changeRate(Context context, int itemId, int newRate){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();

        ArrayList<GroceryItem> allItem = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);
        if (null!=allItem) {
            ArrayList<GroceryItem> newItem= new ArrayList<>();
            for(GroceryItem i :allItem){
                if(i.getId()==itemId){
                    i.setRate(newRate);
                    newItem.add(i);
                }else{
                    newItem.add(i);
                }
            }
            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY, gson.toJson(newItem));
            editor.commit();
        }
    }

    public static int getID() {
        ID++;
        return ID;
    }

    public static ArrayList<Review> getReviewsById(Context context, int itemId){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<GroceryItem>allItems= getAllItem(context);
        if(null!=allItems){
            for(GroceryItem i:allItems){
                if(i.getId()==itemId){
                    ArrayList<Review> reviews=i.getReviews();
                    return reviews;
                }
            }
        }
        return null;
    }

    public static void adReview(Context context, Review review){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<GroceryItem>allIten=getAllItem(context);
        if (null!=allIten) {
            ArrayList<GroceryItem>newItem=new ArrayList<>();
            for (GroceryItem i:allIten
                 ) {
                if(i.getId()==review.getGroceryItemId()){
                    ArrayList<Review> reviews = i.getReviews();
                    reviews.add(review);
                    i.setReviews(reviews);
                    newItem.add(i);
                }else{
                    newItem.add(i);
                }
            }

            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY, gson.toJson(newItem));
            editor.commit();
        }
    }
}
