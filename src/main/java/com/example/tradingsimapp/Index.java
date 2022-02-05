package com.example.tradingsimapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class with potential
 */
public class Index {
    private List<? extends Asset> listOfSharks;
    private String name;

    public List<? extends Asset> getListOfSharks() {
        return listOfSharks;
    }

    public void setListOfSharks(List<? extends Asset> listOfSharks) {this.listOfSharks = listOfSharks;}

    public Index() {
        this.name = TheCreator.getNameForIndex();
        this.listOfSharks = new ArrayList<>();
    }

    /**
     * Shows top3 Assets in the clickedMarket
     *
     * @param market
     * @param howMany
     * @return
     */
    public static synchronized String WIG3(Market<? extends Asset> market, int howMany){
        Collections.sort(market.getAssets(), Collections.reverseOrder());
        String text = "";
        for (int i=0; i<howMany; i++){
            text += "Top " + Integer.toString(i+1) + "\r\n";
            text += market.getAssets().get(i).toString() + "\r\n";
        }
        return text;
    }


    public synchronized List<String> allAssetNames(){
        List<String> names = new ArrayList<>();
        for(Asset asset: this.listOfSharks){
            names.add(asset.getName());
        }
        return names;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
