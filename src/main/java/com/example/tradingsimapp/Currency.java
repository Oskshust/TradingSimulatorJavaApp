package com.example.tradingsimapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Child class of Asset
 */
public class Currency extends Asset {
    private List<String> legalCountries;

    /**
     * Standard constructor
     */
    public Currency() {
        super();
        this.setName(this.getName() + "COIN");
        this.legalCountries = new ArrayList<>();
        Random rand = new Random();
        for (int i=0; i<rand.nextInt(5)+1; i++){
            legalCountries.add(TheCreator.getRandomString(TheCreator.getCountries()));
        }
    }

    public List<String> getLegalCountries() {
        return legalCountries;
    }

    public void setLegalCountries(List<String> legalCountries) {
        this.legalCountries = legalCountries;
    }

    /**
     * Creates basic info about the object
     *
     * @return text with info
     */
    @Override
    public String toString() {
        StringBuilder text = new StringBuilder("Name: " + this.getName() + "\r\n");
        text.append("min price: ").append(Float.toString(this.getMinPrice())).append("\r\n");
        text.append("max price: ").append(Float.toString(this.getMaxPrice())).append("\r\n");
        text.append("how many prices: ").append(Integer.toString(this.getPrices().size())).append("\r\n");
        text.append("legal countries: " + "\r\n");
        for (String country: this.getLegalCountries()){
            text.append(country).append("\r\n");
        }
        return text.toString();
    }
}
