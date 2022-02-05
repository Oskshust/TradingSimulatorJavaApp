package com.example.tradingsimapp;

import java.util.List;
import java.util.Random;

/**
 * Child class of Investor, separate thread for each everyman
 */
public class Everyman extends Investor implements Runnable {

    /**
     * Standard constructor
     */
    public Everyman() {
        super();
        List<Everyman> temp = (List<Everyman>) TheCreator.getInvestors();
        temp.add(this);
    }

    /**
     * Places an order at the give Fund's list for orders. The most part of the order is handled by the 'contractor'
     *
     * @param contractor
     */
    public void placeOrder(Fund contractor){
        System.out.println(this.getfName() + " places an order");
        Random rand = new Random();
        float howMuch = rand.nextFloat();
        String operation = "sell";
        if (howMuch < 0.5){
            operation = "buy";
        }

        Order contract = new Order(this, this.getAssetTarget(), this.getMarketTarget(), operation, howMuch*10);
        contractor.getOrders().add(contract);
    }

    /**
     * Adds ordered asset to the Billfold (class Wallet)
     *
     * @param assetBought
     * @param howMuch
     */
    public void addOrderedAsset(Asset assetBought, float howMuch){
        String typeName = "Curr";
        if (assetBought.getClass() == Company.class){ typeName = "Comp";}
        if (assetBought.getClass() == Commodity.class){ typeName = "Comm";}

        if (this.getBillfold().getMap(typeName).containsKey(assetBought.getName())){
            this.getBillfold().getMap(typeName).put(assetBought.getName(), this.getBillfold().getMap(typeName).get(assetBought.getName()) + howMuch);
        }
        else{
            this.getBillfold().getMap(typeName).put(assetBought.getName(), howMuch);
        }
    }

    /**
     * Creates basic info about the object
     *
     * @return text with info
     */
    @Override
    public String toString() {
        String text = "Name: " + this.getfName() + " " + this.getlName() + "\r\n";
        text += "Cash: " + Float.toString(this.getBillfold().getCash()) + "\r\n";
        text += "Assets: " + "\r\n";
        text += "Company shares: " + "\r\n";
        return this.infoAboutWallet(text);
    }

    /**
     * Handles 'run' procedure. Buys the target Asset and places orders, sometimes happens payday.
     */
    @Override
    public void run() {
        while(!this.isEnoughPaper()) {
            Random rand = new Random();
            if (rand.nextFloat() < 0.2){
                payDay();
            }
            if (rand.nextFloat() < 0.2) {
                this.placeOrder(TheCreator.getRandomFund());
            } else {
                try {
                    this.business(rand.nextFloat() * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
