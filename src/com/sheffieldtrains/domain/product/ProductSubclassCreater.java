package com.sheffieldtrains.domain.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductSubclassCreater {
    private Map<String, Locomotive> locomotiveMap = new HashMap<>();
    private List<String> locomotiveIds = new ArrayList<>();

    private Map<String, Controller> controllerMap = new HashMap<>();
    private List<String> controllerIds = new ArrayList<>();

    private Map<String, RollingStock> rollingStockMap = new HashMap<>();
    private List<String> rollingStockIds = new ArrayList<>();

    private Map<String, Track> trackMap = new HashMap<>();
    private List<String> trackIds = new ArrayList<>();

    private Map<String, TrainSet> trainSetMap = new HashMap<>();
    private List<String> trainSetIds = new ArrayList<>();

    Map<String, TrackPack> trackPackMap=new HashMap<>();
    List<String> trackPackIds=new ArrayList<>();

    public void saveProductCodeForLater(String productCode, ProductType productType) {

        if (ProductType.LOCOMOTIVE.equals(productType)) {
            locomotiveIds.add(productCode);
        } else {
            if (ProductType.CONTROLLER.equals(productType)) {
                controllerIds.add(productCode);
            } else {
                if (ProductType.ROLLING_STOCK.equals(productType)) {
                    rollingStockIds.add(productCode);
                } else {
                    if (ProductType.TRACK.equals(productType)) {
                        trackIds.add(productCode);
                    } else {
                        if (ProductType.TRACK_PACK.equals(productType)) {
                            trackPackIds.add(productCode);
                        }
                    }
                }
            }
        }
    }
}
