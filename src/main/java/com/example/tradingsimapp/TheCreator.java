package com.example.tradingsimapp;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class for globally needed things
 */
public class TheCreator {
    private static int bearBull = 5;
    private static Market<? extends Asset> clickedMarket;
    private static Asset clickedAsset;
    private static Index clickedIndex;
    private static String typeOfAsset;
    private static String nameForIndex;
    private static List<Fund> funds = new ArrayList<>();
    private static List<Index> indices = new ArrayList<>();
    private static List<? extends Investor> investors = new ArrayList<>();
    private static List<Market<? extends Asset>> markets = new ArrayList<>();
    private static List<String> investorNames = new ArrayList<>();
    private static List<String> cities = new ArrayList<>();
    private static List<String> countries = new ArrayList<>();
    private static List<String> companyNames1 = new ArrayList<>();
    private static List<String> companyNames2 = new ArrayList<>();
    private static List<String> marketStreets = new ArrayList<>();

    /**
     * loads text files to arrays
     * @throws IOException
     */
    public static void loading() throws IOException {
        readToArray("/investorNames.txt", investorNames);
        readToArray("/cities.txt", cities);
        readToArray("/countries.txt", countries);
        readToArray("/companyNames1.txt", companyNames1);
        readToArray("/companyNames2.txt", companyNames2);
        readToArray("/marketStreets.txt", marketStreets);
    }

    /**
     * Technical reading line by line from the given file to the given array
     * @param txt
     * @param array
     */
    public static void readToArray(String txt, List<String> array){
        try (InputStream inputStream = TheCreator.class.getResourceAsStream(txt);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String contents = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            String lines[] = contents.split("\\r?\\n");
            array.addAll(List.of(lines));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getRandomString(List<String> array) {
        int rnd = new Random().nextInt(array.size());
        return array.get(rnd);
    }

    public static Fund getRandomFund() {
        int rnd = new Random().nextInt(funds.size());
        return funds.get(rnd);
    }

    public static Market<? extends Asset> getRandomMarket(){
        int rnd = new Random().nextInt(markets.size());
        return markets.get(rnd);
    }

    public static Asset getRandomAsset(Market<? extends Asset> market){
        int rnd = new Random().nextInt(market.getAssets().size());
        return market.getAssets().get(rnd);
    }

    /**
     * Handles the Market creation with its initial Assets, threads and investors
     * @param type
     */
    public static void MarketTheCreator(String type) {
        Market<? extends Asset> market;
        if (type.equals("Company")) {
            market = new Market<Company>();
            List<Company> comps = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                Company c = new Company();
                Thread thread = new Thread(c);
                thread.start();
                comps.add(c);
            }
            market.setAssets(comps);
            addInvestors();
        }
        if (type.equals("Commodity")) {
            market = new Market<Commodity>();
            List<Commodity> comms = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                comms.add(new Commodity());
            }
            market.setAssets(comms);
            addInvestors();
        }
        if (type.equals("Currency")) {
            market = new Market<Currency>();
            List<Currency> currs = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                currs.add(new Currency());
            }
            market.setAssets(currs);
            addInvestors();
        }
    }

    /**
     * Handles adding an investors
     */
    public static void addInvestors() {
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Fund());
            thread.start();
        }
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Everyman());
            thread.start();
        }
    }

    /**
     * @return all Market names
     */
    public synchronized static List<String> allMarketNames(){
        List<String> names = new ArrayList<>();
        for(Market<? extends Asset> market: markets){
            names.add(market.getName());
        }
        return names;
    }

    /**
     * @return all Investor names
     */
    public synchronized static List<String> allInvestorNames(){
        List<String> names = new ArrayList<>();
        for(Investor investor: investors){
            names.add(investor.getfName() + " " + investor.getlName());
        }
        return names;
    }

    public synchronized static List<String> allIndicesNames(){
        List<String> names = new ArrayList<>();
        for(Index index: indices){
            names.add(index.getName());
        }
        return names;
    }

    /**
     * @param name
     * @return Market object found by its name
     */
    public synchronized static Market<? extends Asset> searchMarketsByName(String name){
        for(Market<? extends Asset> market: markets){
            if (name.equals(market.getName())){
                return market;
            }
        }
        return markets.get(0);
    }

    public synchronized static Index searchIndicesByName(String name){
        for(Index index: indices){
            if (name.equals(index.getName())){
                return index;
            }
        }
        return indices.get(0);
    }

    /**
     * @param name
     * @return Investor object found by its name
     */
    public synchronized static Investor searchInvestorsByName(String name){
        for(Investor investor: investors){
            if (name.equals(investor.getfName() + " " + investor.getlName())){
                return investor;
            }
        }
        return investors.get(0);
    }

    public static Index getClickedIndex() {
        return clickedIndex;
    }

    public static void setClickedIndex(Index clickedIndex) {
        TheCreator.clickedIndex = clickedIndex;
    }

    public static String getNameForIndex() {
        return nameForIndex;
    }

    public static void setNameForIndex(String nameForIndex) {
        TheCreator.nameForIndex = nameForIndex;
    }

    public static List<Index> getIndices() {
        return indices;
    }

    public static void setIndices(List<Index> indices) {
        TheCreator.indices = indices;
    }

    public static List<? extends Investor> getInvestors() {
        return investors;
    }

    public static void setInvestors(List<? extends Investor> investors) {
        TheCreator.investors = investors;
    }

    public static String getTypeOfAsset() {
        return typeOfAsset;
    }

    public static void setTypeOfAsset(String typeOfAsset) {
        TheCreator.typeOfAsset = typeOfAsset;
    }

    public static Market<? extends Asset> getClickedMarket() {
        return clickedMarket;
    }

    public static void setClickedMarket(Market<? extends Asset> clickedMarket) {
        TheCreator.clickedMarket = clickedMarket;
    }

    public static Asset getClickedAsset() {
        return clickedAsset;
    }

    public static void setClickedAsset(Asset clickedAsset) {
        TheCreator.clickedAsset = clickedAsset;
    }

    public static List<String> getCities() {
        return cities;
    }

    public static void setCities(List<String> cities) {
        TheCreator.cities = cities;
    }

    public static List<String> getCountries() {
        return countries;
    }

    public static void setCountries(List<String> countries) {
        TheCreator.countries = countries;
    }

    public static void setInvestorNames(List<String> investorNames) {
        TheCreator.investorNames = investorNames;
    }

    public static List<Fund> getFunds() {
        return funds;
    }

    public static void setFunds(List<Fund> funds) {
        TheCreator.funds = funds;
    }

    public static List<Market<? extends Asset>> getMarkets() {
        return markets;
    }

    public static void setMarkets(List<Market<? extends Asset>> markets) {
        TheCreator.markets = markets;
    }

    public static List<String> getCompanyNames1() {
        return companyNames1;
    }

    public static void setCompanyNames1(List<String> companyNames1) {
        TheCreator.companyNames1 = companyNames1;
    }

    public static List<String> getCompanyNames2() {
        return companyNames2;
    }

    public static void setCompanyNames2(List<String> companyNames2) {
        TheCreator.companyNames2 = companyNames2;
    }

    public static List<String> getMarketStreets() {
        return marketStreets;
    }

    public static void setMarketStreets(List<String> marketStreets) {
        TheCreator.marketStreets = marketStreets;
    }

    public static List<String> getInvestorNames() {
        return investorNames;
    }

    public static int getBearBull() {
        return bearBull;
    }

    public static void setBearBull(int bearBull) {
        TheCreator.bearBull = bearBull;
    }
}