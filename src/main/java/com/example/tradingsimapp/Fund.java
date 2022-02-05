package com.example.tradingsimapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Child class of an Investor
 */
public class Fund extends Investor implements Runnable{
    private String ceoFName;
    private String ceoLName;
    private float fee;
    private ArrayList<Order> orders;

    /**
     * Standard constructor
     */
    public Fund() {
        super();
        this.setlName(" Global Fund");
        this.ceoFName = TheCreator.getRandomString(TheCreator.getInvestorNames());
        this.ceoLName = TheCreator.getRandomString(TheCreator.getInvestorNames());
        this.fee = new Random().nextFloat();
        this.orders = new ArrayList<>();
        TheCreator.getFunds().add(this);
        List<Fund> temp = (List<Fund>)TheCreator.getInvestors();
        temp.add(this);
    }

    public String getCeoFName() {
        return ceoFName;
    }

    public void setCeoFName(String fName) {
        this.ceoFName = fName;
    }

    public String getCeoLName() {
        return ceoLName;
    }

    public void setceoLName(String lName) {
        this.ceoLName = lName;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    /**
     * Realizes the oldest order from the queue
     *
     * @throws InterruptedException
     */
    public void realizeNextOrder() throws InterruptedException {
        System.out.println(this.getfName() + this.getlName() + " handles the order");
        Order nextOrder = this.getOrders().get(0);
        this.update(nextOrder.getAssetTarget(), nextOrder.getMarketTarget());
        float cost = nextOrder.getAssetTarget().getCurrentPrice() * nextOrder.getHowMuch() + this.getFee();
        directOperation(nextOrder.getHowMuch(), nextOrder.getOperation());
        exchange(nextOrder.getClient(), cost, nextOrder.getHowMuch(), nextOrder.getAssetTarget());
        this.getOrders().remove(0);
    }

    /**
     * Exchanges cash and bought Asset with client
     *
     * @param client
     * @param cost
     * @param howMuch
     * @param assetBought
     */
    public void exchange(Everyman client, float cost, float howMuch, Asset assetBought){
        client.getBillfold().setCash(client.getBillfold().getCash() - cost);
        this.getBillfold().setCash(this.getBillfold().getCash() + cost);
        client.addOrderedAsset(assetBought, howMuch);
        this.removeOrderedAsset(assetBought);
    }

    /**
     * Removes the ordered Asset from its own Billfold
     *
     * @param assetBought
     */
    public void removeOrderedAsset(Asset assetBought){
        String typeName = "Curr";
        if (assetBought.getClass() == Company.class){ typeName = "Comp";}
        if (assetBought.getClass() == Commodity.class){ typeName = "Comm";}

        this.getBillfold().getMap(typeName).remove(assetBought.getName());
    }

    /**
     * Creates basic info about the object
     *
     * @return text with info
     */
    @Override
    public String toString() {
        String text = "Name of fund: " + this.getfName() + " " + this.getlName() + "\r\n";
        text += "Name of CEO: " + this.getCeoFName() + " " + this.getCeoLName() + "\r\n";
        text += "Fee: " + Float.toString(this.getFee()) + "\r\n";
        return this.infoAboutWallet(text);
    }

    /**
     * Handles 'run' procedure. Buys the target Asset and handles orders, sometimes happens payday.
     */
    @Override
    public void run() {
        while(!this.isEnoughPaper()) {
            Random rand = new Random();
            if (rand.nextFloat() < 0.2){
                payDay();
            }
            if (rand.nextFloat() < 0.3 && !this.getOrders().isEmpty()) {
                try {
                    realizeNextOrder();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    business(rand.nextFloat() * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
