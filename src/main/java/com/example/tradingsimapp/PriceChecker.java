package com.example.tradingsimapp;

/**
 * Class that monitors current prices of all assets
 */
public class PriceChecker implements Runnable{

    public PriceChecker() {}

    /**
     * Updates list of prices for every Asset
     */
    public synchronized void allAssetsUpdate(){
        for (Market<? extends Asset> market: TheCreator.getMarkets()){
            for(Asset asset: market.getAssets()){
                addCurrentPrice(asset);
            }
        }
    }

    /**
     * Add operation, used in allAssetsUpdate()
     * @param asset
     */
    public void addCurrentPrice(Asset asset){
        asset.getPrices().add(asset.getCurrentPrice());
    }

    /**
     * Standard process running
     */
    @Override
    public void run() {
        while(true){
            allAssetsUpdate();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
