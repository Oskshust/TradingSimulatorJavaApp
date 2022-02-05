package com.example.tradingsimapp;

import java.util.*;

/**
 * Class for storing Assets. Used by Investors
 */
public class Wallet {
    private float cash;
    private Map<String, Float> Comm;
    private Map<String, Float> Comp;
    private Map<String, Float> Curr;

    /**
     * Standard constructor
     */
    public Wallet() {
        this.cash = new Random().nextFloat() * 10000;
        this.Comm = new HashMap<String, Float>();
        this.Comp = new HashMap<String, Float>();
        this.Curr = new HashMap<String, Float>();
    }

    public float getCash() {return cash;}

    public void setCash(float cash) {this.cash = cash;}

    public Map<String, Float> getComm() {
        return Comm;
    }

    public void setComm(Map<String, Float> comm) {
        Comm = comm;
    }

    public Map<String, Float> getComp() {
        return Comp;
    }

    public void setComp(Map<String, Float> comp) {
        Comp = comp;
    }

    public Map<String, Float> getCurr() {
        return Curr;
    }

    public void setCurr(Map<String, Float> curr) {
        Curr = curr;
    }

    public Map<String, Float> getMap(String nameOfMap){
        if (Objects.equals(nameOfMap, "Comm")) {
            return getComm();
        }
        if (Objects.equals(nameOfMap, "Comp")) {
            return getComp();
        }
        else{
            return getCurr();
        }
    }
}
