package com.example.tradingsimapp;

/**
 * Child class of Asset
 */
public class Commodity extends Asset {
    private String tradingUnit;
    private String currency;

    /**
     * Standard constructor, semi-random
     */
    public Commodity() {
        super();
        this.tradingUnit = "1 thing";
        this.currency = "USD";
    }

    public String getTradingUnit() {
        return tradingUnit;
    }

    public void setTradingUnit(String tradingUnit) {
        this.tradingUnit = tradingUnit;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Creates basic info about the object
     *
     * @return text with info
     */
    @Override
    public String toString(){
        String text = "Name: " + this.getName() + "\r\n";
        text += "min price: " + Float.toString(this.getMinPrice()) + "\r\n";
        text += "max price: " + Float.toString(this.getMaxPrice()) + "\r\n";
        text += "trading unit: " + this.getTradingUnit() + "\r\n";
        text += "currency: " + this.getCurrency() + "\r\n";
        text += "how many prices: " + Integer.toString(this.getPrices().size()) + "\r\n";

        return text;
    }
}
