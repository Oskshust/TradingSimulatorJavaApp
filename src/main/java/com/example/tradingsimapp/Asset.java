package com.example.tradingsimapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Abstract class for Assets
 */
public class Asset implements Comparable<Asset>{
    private String name;
    private float currentPrice;
    private float minPrice;
    private float maxPrice;
    private List<Float> prices;

    /**
     * Constructor for Asset class
     */
    public Asset() {
        this.name = TheCreator.getRandomString(TheCreator.getCompanyNames1()) + TheCreator.getRandomString(TheCreator.getCompanyNames2());
        float[] prices = new float[3];
        Random rand = new Random();
        prices[0] = rand.nextFloat()*10;
        prices[1] = rand.nextFloat()*10;
        prices[2] = rand.nextFloat()*10;
        Arrays.sort(prices);
        this.currentPrice = prices[1];
        this.minPrice = prices[0];
        this.maxPrice = prices[2];
        this.prices = new ArrayList<>();
    }

    public List<Float> getPrices() {
        return prices;
    }

    public void setPrices(List<Float> prices) {
        this.prices = prices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }

    /**
     * Changes price and min/max price if needed
     *
     * @param change
     */
    public void changePrice(float change){
        this.setCurrentPrice(this.getCurrentPrice() + change);
        if (this.getCurrentPrice() > this.getMaxPrice()){
            this.setMaxPrice(this.getCurrentPrice());
        }
        if (this.getCurrentPrice() < this.getMinPrice()){
            this.setMinPrice((this.getCurrentPrice()));
        }
    }

    /**
     * Compares objects (from 'implements Comparable')
     *
     * @param o
     */
    @Override
    public int compareTo(Asset o) {
        return Float.compare(this.getCurrentPrice(), o.getCurrentPrice());
    }
}
