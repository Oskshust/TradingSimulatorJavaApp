package com.example.tradingsimapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generic class, 3 types of Assets
 *
 * @param <T>
 */
public class Market<T extends Asset> {
    private String name;
    private String country;
    private String currency;
    private String city;
    private String address;
    private Index indexes;
    private float cost;
    private List<? extends Asset> assets;
    private List<String> margin;

    /**
     * Standard constructor
     */
    public Market() {
        System.out.println("New Market is being created");
        Random rand = new Random();
        this.city = TheCreator.getRandomString(TheCreator.getCities());
        this.country = TheCreator.getRandomString(TheCreator.getCountries());
        this.address = TheCreator.getRandomString(TheCreator.getMarketStreets()) + " Street " + Integer.toString(rand.nextInt());
        this.cost = rand.nextFloat();
        this.assets = new ArrayList<>();
        this.margin = new ArrayList<>();
        this.currency = "USD";
        this.name = getCity() + " Exchange";
        TheCreator.getMarkets().add(this);
    }

    public void setMargin(List<String> margin){this.margin = margin;}

    public void setAssets(List<? extends Asset> assets) {this.assets = assets;}

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public void setIndexes(Index indexes){this.indexes = indexes;}

    public List<String> getMargin(){return margin;}

    public List<? extends Asset> getAssets() {
        return assets;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public float getCost() {
        return cost;
    }

    public Index getIndexes(){return indexes;}

    /**
     * Adds a record to the margin of operations
     *
     * @param name
     * @param assetTarget
     * @param buy
     * @param boughtSold
     */
    public void addMarginRecord(String name, Asset assetTarget, float buy, String boughtSold){
        this.getMargin().add(name + boughtSold + Float.toString(buy) + " of " +
                assetTarget.getName() + " for " + Float.toString(buy * assetTarget.getCurrentPrice()));

    }

    /**
     * Adds names of Assets to the List
     *
     * @return list of names
     */
    public synchronized List<String> allAssetNames(){
        List<String> names = new ArrayList<>();
        for(Asset asset: this.assets){
            names.add(asset.getName());
        }
        return names;
    }

    /**
     * Searches for the Asset by its name
     *
     * @param name
     * @return found Asset or the first one in the list
     */
    public synchronized Asset searchAssetsByName(String name){
        for(Asset asset: assets){
            if (name.equals(asset.getName())){
                return asset;
            }
        }
        return assets.get(0);
    }

    /**
     * Provides basic info about the Market
     *
     * @return text with info
     */
    @Override
    public String toString(){
        String text = "Name : " + this.getName() + "\r\n";
        text += "Country: " + this.getCountry() + "\r\n";
        text += "City" + this.getCity() + "\r\n";
        text += "Address: " + this.getAddress() + "\r\n";
        text += "Currency: " + this.getCurrency() + "\r\n";
        text += "Cost: " + Float.toString(this.getCost()) + "\r\n";

        return text;
    }

}
