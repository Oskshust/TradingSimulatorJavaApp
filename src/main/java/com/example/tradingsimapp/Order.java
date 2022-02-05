package com.example.tradingsimapp;

/**
 * Utility class to place/handle operations between Everyman-Fund
 */
public class Order {
    private Everyman client;
    private Asset assetTarget;
    private Market<? extends Asset> marketTarget;
    private String operation;
    private float howMuch;

    /**
     * Standard constructor, args are given by the ordering person
     *
     * @param client
     * @param assetTarget
     * @param marketTarget
     * @param operation
     * @param howMuch
     */
    public Order(Everyman client, Asset assetTarget, Market<? extends Asset> marketTarget, String operation, float howMuch) {
        this.client = client;
        this.assetTarget = assetTarget;
        this.marketTarget = marketTarget;
        this.operation = operation;
        this.howMuch = howMuch;
    }

    public Everyman getClient() {
        return client;
    }

    public void setClient(Everyman client) {
        this.client = client;
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

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public float getHowMuch() {
        return howMuch;
    }

    public void setHowMuch(float howMuch) {
        this.howMuch = howMuch;
    }
}
