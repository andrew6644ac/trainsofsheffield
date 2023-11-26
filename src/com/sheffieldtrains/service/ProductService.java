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

    public static void restockProduct(String productCode, String productType){
        //todo:
    }

    public static void createNewLocomotive(Locomotive locomotive){
        //todo:
    }

    public static void createNewLocomotive(Controller controller){
        //todo:
    }

    public static void createNewRollingStock(RollingStock rollingStock){
        //todo:
    }

    public static void createTrack(Track track){
        //todo:
    }

    public static void createTrackPack(TrackPack trackPack){
        //todo:
    }

    public static float getProductPrice(String productId, ProductType productType) {
       return getProduct(productId, productType).getPrice();
    }


    public static Product getProduct(String productCode, ProductType productType) {
        return ProductRepository.getProduct(productCode, productType);
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
