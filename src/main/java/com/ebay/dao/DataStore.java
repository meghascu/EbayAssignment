package com.ebay.dao;

import com.google.common.util.concurrent.AtomicDouble;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Just to duplicate database with atomic updates and fetch.
 */
@Component
public class DataStore {

    private static AtomicReference<Set<Integer>> categoryIds = new AtomicReference<>(new HashSet<>());
    private static AtomicReference<Set<String>> enrolledSellers = new AtomicReference<>(new HashSet<>());
    private static AtomicDouble minPrice = new AtomicDouble();


    private DataStore(){}

    private static DataStore INSTANCE;

    public static DataStore getInstance(){
        if(INSTANCE==null)
            INSTANCE = new DataStore();
        return INSTANCE;
   }

   public boolean addCategoryId(int categoryId){
        if(!categoryIds.get().contains(categoryId)){
            categoryIds.get().add(categoryId);
            return true;
        }
        return false;

   }

   public boolean isCategoryAllowed(int catId){
        return categoryIds.get().contains(catId);
   }

   public boolean removeCategory(int catId){
        if(categoryIds.get().contains(catId)) {
            categoryIds.get().remove(catId);
            return true;
        }
        return false;
   }

   public boolean addSeller(String sellerId){
        if(!enrolledSellers.get().contains(sellerId)){
            enrolledSellers.get().add(sellerId);
            return true;
        }
        return false;
   }

   public boolean isSellerAllowed(String sellerId){
        return enrolledSellers.get().contains(sellerId);
   }

   public boolean removeSeller(String sellerId){
        if(enrolledSellers.get().contains(sellerId)){
            enrolledSellers.get().remove(sellerId);
            return true;
        }
        return false;
   }

   public  boolean updateMinPrice(double price){
        minPrice.set(price);
        return true;
   }

   public boolean isPriceValid(double price){
        return price >= minPrice.get();
   }
}
