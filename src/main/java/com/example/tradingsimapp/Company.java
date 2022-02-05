package com.example.tradingsimapp;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Child class of Asset, separate thread for each object
 */
public class Company extends Asset implements Runnable{
    private LocalDate ipoDate;
    private float openingPrice;
    private float revenue;
    private float profit;
    private float capital;
    private float tradingVolume;
    private float totalSales;

    /**
     * Standard constructor, semi-random
     */
    public Company() {
        super();
        this.setName(this.getName() + " Stock Comp.");
        Random rand = new Random();
        this.ipoDate = LocalDate.now();
        this.openingPrice = rand.nextFloat() * 100;
        this.revenue = rand.nextFloat() * 10000;
        this.profit = getRevenue() - rand.nextFloat() * 1000;
        this.capital = rand.nextFloat() * 100000;
        this.tradingVolume = rand.nextFloat() * 10000;
        this.totalSales = getRevenue() + getTradingVolume() * getOpeningPrice();
    }

    public LocalDate getIpoDate() {
        return ipoDate;
    }

    public void setIpoDate(LocalDate ipoDate) {
        this.ipoDate = ipoDate;
    }

    public float getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(float openingPrice) {
        this.openingPrice = openingPrice;
    }

    public float getRevenue() {
        return revenue;
    }

    public void setRevenue(float revenue) {
        this.revenue = revenue;
    }

    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }

    public float getCapital() {
        return capital;
    }

    public void setCapital(float capital) {
        this.capital = capital;
    }

    public float getTradingVolume() {
        return tradingVolume;
    }

    public void setTradingVolume(float tradingVolume) {
        this.tradingVolume = tradingVolume;
    }

    public float getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(float totalSales) {
        this.totalSales = totalSales;
    }

    /**
     * Generates more shares
     */
    public void moreShares(){
        float generatedFloat = 1F + new Random().nextFloat() * 100F;
        this.setTradingVolume(generatedFloat + this.getTradingVolume());
    }

    /**
     * Generates more shares
     */
    public void generateProfit(){
        float generatedFloat = 1F + new Random().nextFloat() * 100F;
        this.setProfit(generatedFloat + this.getProfit());
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
        text += "opening price: " + Float.toString(this.getOpeningPrice()) + "\r\n";
        text += "revenue: " + Float.toString(this.getRevenue()) + "\r\n";
        text += "profit: " + Float.toString(this.getProfit()) + "\r\n";
        text += "capital: " + Float.toString(this.getCapital()) + "\r\n";
        text += "trading volume: " + Float.toString(this.getTradingVolume()) + "\r\n";
        text += "total sales: " + Float.toString(this.getTotalSales()) + "\r\n";
        text += "how many prices: " + Integer.toString(this.getPrices().size()) + "\r\n";

        return text;
    }

    /**
     * Handles 'run' procedure. Generate shares and profit.
     */
    @Override
    public void run() {
        while(true){
            this.generateProfit();
            Random rand = new Random();
            if(rand.nextFloat() < 0.5){
                this.moreShares();
            }
            try {
                TimeUnit.SECONDS.sleep(TheCreator.getBearBull());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
