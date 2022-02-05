package com.example.tradingsimapp;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Abstract class, parent for Everyman and Fund classes
 */
public class Investor{
    private String fName;
    private String lName;
    private Wallet billfold;
    private Asset assetTarget;
    private boolean enoughPaper;
    private Market<? extends Asset> marketTarget;

    /**
     * Standard constructor
     */
    public Investor() {
        this.fName = TheCreator.getRandomString(TheCreator.getInvestorNames());
        this.lName = TheCreator.getRandomString(TheCreator.getInvestorNames());
        this.billfold = new Wallet();
        this.marketTarget = TheCreator.getRandomMarket();
        this.assetTarget = TheCreator.getRandomAsset(this.marketTarget);
        this.enoughPaper = false;
        Stonks.getInvestorList().add(this);
    }

    /**
     * If false - breaks run() procedure
     * @return
     */
    public boolean isEnoughPaper() {return enoughPaper;}

    public void setEnoughPaper(boolean enoughPaper) {this.enoughPaper = enoughPaper;}

    public String getfName() {
        return fName;
    }

    public void setfName(String name) {
        this.fName = name;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String name) {
        this.lName = name;
    }

    public Wallet getBillfold() {
        return billfold;
    }

    public void setBillfold(Wallet billfold) {
        this.billfold = billfold;
    }

    public Asset getAssetTarget() {
        return assetTarget;
    }

    public void setAssetTarget(Asset assetTarget) {
        this.assetTarget = assetTarget;
    }

    public Market<? extends Asset> getMarketTarget() {
        return marketTarget;
    }

    public void setMarketTarget(Market<? extends Asset> marketTarget) {
        this.marketTarget = marketTarget;
    }

    /**
     * It is payday - investor is getting cash. A lot of it, in fact.
     */
    public void payDay(){
        Random rand = new Random();
        this.billfold.setCash(this.billfold.getCash() + rand.nextFloat() * 10000);
    }

    /**
     * The main process of buying/selling Assets
     *
     * @param howMuch
     * @throws InterruptedException
     */
    public void business(float howMuch) throws InterruptedException {
        System.out.println(this.getfName() + " doin business");
        if(stonksOrNot(this.assetTarget)){
            action(howMuch);
        }
        else{
            action(-howMuch);
        }
        TimeUnit.SECONDS.sleep(TheCreator.getBearBull());
    }

    /**
     * Similar to business, but in the mean of handling orders
     *
     * @param howMuch
     * @param buySell
     * @throws InterruptedException
     */
    public void directOperation(float howMuch, String buySell) throws InterruptedException {
        if(buySell.equals("buy")){
            action(howMuch);
        }
        else{
            action(-howMuch);
        }
        TimeUnit.SECONDS.sleep(TheCreator.getBearBull());
    }

    /**
     * Checks whether an asset should be bought or sold
     *
     * @param asset
     * @return false-sell, true-buy
     */
    public boolean stonksOrNot(Asset asset){
        if (asset.getCurrentPrice() == asset.getMaxPrice()) {
            return false;
        }
        return asset.getCurrentPrice() < (asset.getMaxPrice() - asset.getMinPrice()) / 2;
    }

    /**
     *Another step in buy/sell operation, basically 'a middle man'
     *
     * @param buyOrSell
     * @throws InterruptedException
     */
    public synchronized void action(float buyOrSell) throws InterruptedException {
        if (buyOrSell > 0) {
            if (this.billfold.getCash() > buyOrSell * this.assetTarget.getCurrentPrice()) {
                assignToWallet(buyOrSell);
            }
        }
        else{
            deleteFromWallet(buyOrSell);
        }
    }

    /**
     * Another middle man after action()
     *
     * @param buy
     */
    public void assignToWallet(float buy){
        if(this.assetTarget.getClass() == Commodity.class){
            this.buyOperation("Comm", buy);
        }
        if(this.assetTarget.getClass() == Company.class){
            this.buyOperation("Comp", buy);
        }
        if(this.assetTarget.getClass() == Currency.class){
            this.buyOperation("Curr", buy);
        }
    }

    /**
     * Similar to assignToWallet but for selling
     *
     * @param sell
     */
    public void deleteFromWallet(float sell){
        if(this.assetTarget.getClass() == Commodity.class){
            this.sellOperation("Comm", sell);
        }
        if(this.assetTarget.getClass() == Company.class){
            this.sellOperation("Comp", sell);
        }
        if(this.assetTarget.getClass() == Currency.class){
            this.sellOperation("Curr", sell);
        }
    }

    /**
     * Buys the Asset, assigns it to the Billfold, handles cash operation
     *
     * @param typeName
     * @param buy
     */
    public void buyOperation(String typeName, float buy){
        if (this.billfold.getMap(typeName).containsKey(this.assetTarget.getName())){
            this.billfold.setCash(this.billfold.getCash() - this.assetTarget.getCurrentPrice() * buy - this.getMarketTarget().getCost());

            this.billfold.getMap(typeName).put(this.assetTarget.getName(), this.billfold.getMap(typeName).get(this.assetTarget.getName()) + buy);
        }
        else{
            this.billfold.setCash(this.billfold.getCash() - this.assetTarget.getCurrentPrice() * buy);

            this.billfold.getMap(typeName).put(this.assetTarget.getName(), buy);
        }

        this.marketTarget.addMarginRecord(this.fName + " " + this.lName, this.assetTarget, buy, " bought ");

        this.assetTarget.changePrice(buy);
    }

    /**
     * Sells the Asset, deletes it from the Billfold, handles cash operation
     *
     * @param typeName
     * @param sell
     */
    public void sellOperation(String typeName, float sell){
        if (this.billfold.getMap(typeName).containsKey(this.assetTarget.getName())) {
            this.billfold.getMap(typeName).put(this.assetTarget.getName(),
                    this.billfold.getMap(typeName).get(this.assetTarget.getName()) + sell);

            // subtraction, because sell is supposed to be negative
            this.billfold.setCash(this.billfold.getCash() - this.assetTarget.getCurrentPrice() * sell - this.getMarketTarget().getCost());

            if (this.billfold.getMap(typeName).get(this.assetTarget.getName()) <= 0.001){
                this.billfold.getMap(typeName).remove(this.assetTarget.getName());
            }

            this.marketTarget.addMarginRecord(this.fName + " " + this.lName, this.assetTarget, sell, " sold ");

            this.assetTarget.changePrice(sell);
        }
    }

    /**
     * Appends info about the Billfold (Wallet) to the already completed String
     *
     * @param text
     * @return
     */
    public String infoAboutWallet(String text){
        text += "Cash: " + Float.toString(this.getBillfold().getCash()) + "\r\n";
        text += "Assets: " + "\r\n";
        text += "Company shares: " + "\r\n";
        for(String name: this.getBillfold().getComp().keySet()){
            text += name + ": " + this.getBillfold().getComp().get(name) + "\r\n";
        }
        text += "Commodities: " + "\r\n";
        for(String name: this.getBillfold().getComm().keySet()){
            text += name + ": " + this.getBillfold().getComm().get(name) + "\r\n";
        }
        text += "Currencies: " + "\r\n";
        for(String name: this.getBillfold().getCurr().keySet()){
            text += name + ": " + this.getBillfold().getCurr().get(name) + "\r\n";
        }
        return text;
    }

    /**
     * Updates targets for buying/selling operations
     *
     * @param asset
     * @param market
     */
    public synchronized void update(Asset asset, Market<? extends Asset> market){
        this.assetTarget = asset;
        this.marketTarget = market;
    }

}
