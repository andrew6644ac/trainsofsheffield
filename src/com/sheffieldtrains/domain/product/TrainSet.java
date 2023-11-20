package com.sheffieldtrains.domain.product;

import java.util.List;

public class TrainSet extends ProductDuringEra{
    private String contents;
    private List<LocomotiveInTrainSet> locomotiveList;
    private List<RollingStockInTrainSet> rollingStockList;
    private List<TrackPackInTrainSet> trackPackList;
    private Controller controller;

    public TrainSet(){}

    public TrainSet(String productCode,
                    String brand,
                    String productName,
                    float price,
                    Gauge gauge,
                    int quantity,
                    String eraCode,
                    String contents) {
        super(productCode, brand, productName, price, gauge, quantity, eraCode);
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public List<LocomotiveInTrainSet> getLocomotiveList() {
        return locomotiveList;
    }

    public void setLocomotiveList(List<LocomotiveInTrainSet> locomotiveList) {
        this.locomotiveList = locomotiveList;
    }

    public List<RollingStockInTrainSet> getRollingStockList() {
        return rollingStockList;
    }

    public void setRollingStockList(List<RollingStockInTrainSet> rollingStockList) {
        this.rollingStockList = rollingStockList;
    }

    public List<TrackPackInTrainSet> getTrackPackList() {
        return trackPackList;
    }

    public void setTrackPackList(List<TrackPackInTrainSet> trackPackList) {
        this.trackPackList = trackPackList;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }



}
