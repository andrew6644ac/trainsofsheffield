package com.sheffieldtrains.service;

import com.sheffieldtrains.db.ProductRepository;
import com.sheffieldtrains.domain.product.*;

import java.util.List;

public class ProductService {

    public static void main(String[] args) {
        List<Locomotive> locomotiveList=ProductService.getAllLocomotives();
        List<Controller> controllerList=ProductService.getAllControllers();
        List<RollingStock> rollingStockList=ProductService.getAllRollingStocks();
        List<Track> trackList=ProductService.getAllTracks();
        List<TrackPack> trackPackList=ProductService.getAllTrackPacks();
        List<TrainSet> trainSetList=ProductService.getAllTrainSets();
        System.out.println("test done");
    }

    public static float getProductPrice(String productId) {
        //ToDo: to implement
        return 100;
    }


    public static void getProduct(String productCode, ProductType productType) {
        ProductRepository.getProduct(productCode, productType);
    }

    public static List<Locomotive> getAllLocomotives(){
        return ProductRepository.getAllLocomotives();
    }

    public static List<Controller> getAllControllers(){
        return ProductRepository.getAllControllers();
    }

    public static List<RollingStock> getAllRollingStocks(){
        return ProductRepository.getAllRollingStocks();
    }

    public static List<Track> getAllTracks(){
        return ProductRepository.getAllTracks();
    }

    public static List<TrackPack> getAllTrackPacks(){
        return ProductRepository.getAllTrackPacks();
    }

    public static List<TrainSet> getAllTrainSets(){
        return ProductRepository.getAllTrainSets();
    }

}
