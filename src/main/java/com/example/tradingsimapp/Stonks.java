package com.example.tradingsimapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer class
 */
public class Stonks implements Runnable {
    private static List<Investor> investorList = new ArrayList<>();
    private Market<? extends Asset> targetMarket;

    public Stonks() {
        this.targetMarket = TheCreator.getRandomMarket();
    }

    public static List<Investor> getInvestorList() {
        return investorList;
    }

    public void setInvestorList(List<Investor> investorList) {
        Stonks.investorList = investorList;
    }

    /**
     * It was supposed to notify Investors about the best deals, however it caused monotonicity, so now it notifies about random Assets.
     */
    public void checkIfStonks(){
            this.targetMarket = TheCreator.getRandomMarket();
//            stonkify();
            notifyInvestors(TheCreator.getRandomAsset(this.targetMarket), this.targetMarket);
    }

    /**
     * Searches for the best deal and informs Investors about it
     */
    public synchronized void stonkify() {
        for (Asset option : this.targetMarket.getAssets()) {
            if (option.getCurrentPrice() == option.getMaxPrice()) {
                notifyInvestors(option, this.targetMarket);
            }
            if (option.getCurrentPrice() < (option.getMaxPrice() - option.getMinPrice()) / 2) {
                notifyInvestors(option, this.targetMarket);
            }
        }
    }

    /**
     * Chenges target fields of Investors
     *
     * @param asset
     * @param market
     */
    public void notifyInvestors(Asset asset, Market<? extends Asset> market){
        for (Investor alCapone : investorList){
            alCapone.update(asset, market);
        }
    }

    /**
     * Searching and notifying
     */
    @Override
    public void run() {
        while(true){
            this.checkIfStonks();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
