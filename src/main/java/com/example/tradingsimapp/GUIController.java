package com.example.tradingsimapp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class for GUI
 */
public class GUIController implements Initializable {

    @FXML
    private Label bbLabel;

    @FXML
    private Label startLabel;

    @FXML
    private Button startButton;

    @FXML
    private Circle startCircle;

    @FXML
    private AnchorPane anchorOfCreatePanel;

    @FXML
    private ComboBox<String> assetsCombo;

    @FXML
    private HBox bbPanel;

    @FXML
    private HBox indexHbox;

    @FXML
    private Slider bbSlider;

    @FXML
    private Label bearLabel;

    @FXML
    private VBox boxfListChart;

    @FXML
    private Label bullLabel;

    @FXML
    private VBox buttonsPanel;

    @FXML
    private VBox indexButtonsVbox;

    @FXML
    private Button indexButton;

    @FXML
    private TitledPane createPanel;

    @FXML
    private Button drawButton;

    @FXML
    private VBox drawEraseBox;

    @FXML
    private Button eraseButton;

    @FXML
    private TextArea infoTextBox;

    @FXML
    private TextField indexNameField;

    @FXML
    private Text instructionBox;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private ListView<String> listWithAssets;
    ObservableList<String> observableAssets = FXCollections.observableArrayList();

    @FXML
    private ListView<String> listWithInvestors;
    ObservableList<String> observableInvestors = FXCollections.observableArrayList();

    @FXML
    private ListView<String> listWithMarkets;
    ObservableList<String> observableMarkets = FXCollections.observableArrayList();

    @FXML
    private ListView<String> indicesList;
    ObservableList<String> observableIndices = FXCollections.observableArrayList();

    @FXML
    private ListView<String> assetsOfIndex;
    ObservableList<String> observableIndexAssets = FXCollections.observableArrayList();

    @FXML
    private HBox listsAboveGraph;

    @FXML
    private AnchorPane mainWindow;

    @FXML
    private Button newAssetButton;

    @FXML
    private Button newMarketButton;

    @FXML
    private Button newIndexButton;

    @FXML
    private Button addToIndex;

    @FXML
    private SplitPane splitlaneOfRadio;

    @FXML
    private Text titleBox;

    @FXML
    private VBox vboxOfAnchor;

    ObservableList<String> comboList = FXCollections.observableArrayList("Company", "Commodity", "Currency");

    /**
     * Creates new Index
     *
     * @param event
     */
    @FXML
    void newIndexButtonClicked(ActionEvent event) {
        TheCreator.setNameForIndex(indexNameField.getText());
        TheCreator.getIndices().add(new Index());
        observableIndices.setAll(TheCreator.allIndicesNames());
        indicesList.setItems(observableIndices);
    }

    /**
     * Adds clicked Asset to the clicked Index
     */
    @FXML
    void addAssetToIndex(ActionEvent event) {
        List<Asset> temp = (List<Asset>) TheCreator.getClickedIndex().getListOfSharks();
        temp.add(TheCreator.getClickedAsset());
        observableIndexAssets.setAll(TheCreator.getClickedIndex().allAssetNames());
        assetsOfIndex.setItems(observableIndexAssets);
    }

    /**
     * Changes typeOfAsset when user changes value in the comboBox
     *
     * @param event
     */
    @FXML
    void comboChangeAsset(ActionEvent event) {
        assetsCombo.setValue(assetsCombo.getValue());
        System.out.println(assetsCombo.getValue());
        TheCreator.setTypeOfAsset(assetsCombo.getValue());
    }

    /**
     * Draws clicked Asset
     *
     * @param event
     */
    @FXML
    void drawAsset(ActionEvent event) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(TheCreator.getClickedAsset().getName());
        List<Float> temp = new ArrayList<>(TheCreator.getClickedAsset().getPrices());
        for (int i=0; i<temp.size(); i++){
            series.getData().add(new XYChart.Data<>(Integer.toString(i), temp.get(i) / temp.get(0) * 100));
        }
        System.out.println("Series ready");
        lineChart.getData().add(series);
        System.out.println("Chart ready");
        updateSeries();
    }

    /**
     * Erases the clicked Asset
     *
     * @param event
     */
    @FXML
    void eraseAsset(ActionEvent event) {
        lineChart.getData().removeIf(series -> series.getName().equals(TheCreator.getClickedAsset().getName()));
    }

    /**
     * Updates already drawn Assets when the new one is created
     */
    void updateSeries(){
        for(XYChart.Series<String, Number> old: lineChart.getData()){
            Market<? extends Asset> mk = TheCreator.getRandomMarket();
            for(Market<? extends Asset> market: TheCreator.getMarkets()){
                if(old.getName().equals(market.searchAssetsByName(old.getName()))){
                    mk = market;
                }
            }
            old.getData().remove(0, old.getData().size());
            List<Float> temp = new ArrayList<>(mk.searchAssetsByName(old.getName()).getPrices());
            for (int i=0; i<temp.size(); i++){
                old.getData().add(new XYChart.Data<>(Integer.toString(i), temp.get(i) / temp.get(0) * 100));
            }
        }
    }

    /**
     * Creates new (typeOfAsset)Asset + Investors
     *
     * @param event
     */
    @FXML
    void newAssetClicked(ActionEvent event) {
        if (TheCreator.getTypeOfAsset().equals("Company")) {
            List<Company> temp = (List<Company>) TheCreator.getClickedMarket().getAssets();
            Company comp = new Company();
            Thread thread = new Thread(comp);
            thread.start();
            temp.add(comp);
            TheCreator.addInvestors();
            observableAssets.add(comp.getName());
            listWithAssets.setItems(observableAssets);
            observableInvestors.setAll(TheCreator.allInvestorNames());
            listWithInvestors.setItems(observableInvestors);
        }
        if (TheCreator.getTypeOfAsset().equals("Commodity")) {
            List<Commodity> temp = (List<Commodity>) TheCreator.getClickedMarket().getAssets();
            Commodity comm = new Commodity();
            temp.add(comm);
            TheCreator.addInvestors();
            observableAssets.add(comm.getName());
            listWithAssets.setItems(observableAssets);
            observableInvestors.setAll(TheCreator.allInvestorNames());
            listWithInvestors.setItems(observableInvestors);
        }
        if (TheCreator.getTypeOfAsset().equals("Currency")) {
            List<Currency> temp = (List<Currency>) TheCreator.getClickedMarket().getAssets();
            Currency curr = new Currency();
            temp.add(curr);
            TheCreator.addInvestors();
            observableAssets.add(curr.getName());
            listWithAssets.setItems(observableAssets);
            observableInvestors.setAll(TheCreator.allInvestorNames());
            listWithInvestors.setItems(observableInvestors);
        }
    }

    /**
     * Handles click of newMarket button
     * @param event
     */
    @FXML
    void newMarketClicked(ActionEvent event) {
        TheCreator.MarketTheCreator(TheCreator.getTypeOfAsset());
        observableMarkets.setAll(TheCreator.allMarketNames());
        listWithMarkets.setItems(observableMarkets);
    }

    /**
     * Initializes the application process, sets listeners, some basic values
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        assetsCombo.setItems(comboList);
        assetsCombo.setValue("Company");
        TheCreator.setTypeOfAsset("Company");

        listWithMarkets.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                TheCreator.setClickedMarket(TheCreator.searchMarketsByName(listWithMarkets.getSelectionModel().getSelectedItem()));
                listWithAssets.getItems().clear();
                observableAssets.addAll(TheCreator.getClickedMarket().allAssetNames());
                listWithAssets.setItems(observableAssets);
                infoTextBox.setText(TheCreator.searchMarketsByName(listWithMarkets.getSelectionModel().getSelectedItem()).toString());
            }
        });

        listWithAssets.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                TheCreator.setClickedAsset(TheCreator.getClickedMarket().searchAssetsByName(listWithAssets.getSelectionModel().getSelectedItem()));
                infoTextBox.setText(TheCreator.getClickedMarket().searchAssetsByName(listWithAssets.getSelectionModel().getSelectedItem()).toString());
            }
        });

        listWithInvestors.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                infoTextBox.setText(TheCreator.searchInvestorsByName(listWithInvestors.getSelectionModel().getSelectedItem()).toString());
            }
        });

        indicesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                TheCreator.setClickedIndex(TheCreator.searchIndicesByName(indicesList.getSelectionModel().getSelectedItem()));
                assetsOfIndex.getItems().clear();
                observableIndexAssets.addAll(TheCreator.getClickedIndex().allAssetNames());
                assetsOfIndex.setItems(observableIndexAssets);
            }
        });

        bbSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                TheCreator.setBearBull((int) bbSlider.getValue());
                bbLabel.setText(Integer.toString(TheCreator.getBearBull()));
            }
        });
    }

    /**
     * Gets the business goin'
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void start(ActionEvent event) throws IOException {
        lineChart.setCreateSymbols(false);
        TheCreator.loading();
        TheCreator.MarketTheCreator("Currency");
        observableMarkets.setAll(TheCreator.allMarketNames());
        listWithMarkets.setItems(observableMarkets);
        observableAssets.addAll(TheCreator.getMarkets().get(0).allAssetNames());
        listWithAssets.setItems(observableAssets);
        observableInvestors.setAll(TheCreator.allInvestorNames());
        listWithInvestors.setItems(observableInvestors);
        Thread threadStonks = new Thread(new Stonks());
        threadStonks.start();
        Thread threadChecker = new Thread(new PriceChecker());
        threadChecker.start();
    }
}
