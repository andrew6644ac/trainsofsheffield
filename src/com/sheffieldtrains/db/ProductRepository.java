package com.sheffieldtrains.db;

import com.sheffieldtrains.domain.product.*;
import com.sheffieldtrains.service.ProductNotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ProductRepository extends Repository {
    public static void main(String[] args) {
        ProductRepository repository=new ProductRepository();
        System.out.println("Products Loaded");
    }

    private static Map<String, Locomotive> LOCOMOTIVE_MAP = new HashMap<>();
    private static Map<String, Controller> CONTROLLER_MAP = new HashMap<>();
    private static Map<String, Track> TRACK_MAP = new HashMap<>();
    private static Map<String, RollingStock> ROLLING_STOCK_MAP = new HashMap<>();
    private static Map<String, TrackPack> TRACK_PACK_MAP = new HashMap<>();
    private static Map<String, TrainSet> TRAIN_SET_MAP = new HashMap<>();

    private static Map<String, List<TrackPackInTrainSet>> TRACK_PACK_IN_TRAIN_SET_MAP= new HashMap<>();

    private static Map<String, List<LocomotiveInTrainSet>> LOCOMOTIVE_IN_TRAIN_SET_MAP= new HashMap<>();

    private static String LOCOMOTIVE_SQL = """
                SELECT distinct
                    p.productCode,
                    p.brand,
                    p.productName,
                    p.price,
                    p.gauge,
                    lo.dccCode,
                    lo.eraCode,
                    p.quantity
                FROM
                    locomotive lo 
                JOIN  product p ON lo.productCode = p.productCode
                WHERE upper(p.productType)="LOCOMOTIVE"
            """;

    private static String CONTROLLER_SQL = """
                SELECT distinct
                    p.productCode,
                    p.brand,
                    p.productName,
                    p.price,
                    p.gauge,
                    p.quantity,
                    c.isDigital
                FROM
                    controller c
                JOIN  product p ON c.productCode = p.productCode
                WHERE upper(p.productType)="CONTROLLER"
            """;

    private static final String ROLLING_STOCK_SQL = """
            SELECT distinct
                p.productCode,
                p.brand,
                p.productName,
                p.price,
                p.gauge,
                p.quantity,
                r.eraCode,
                r.type
            FROM
                rollingstock r
            JOIN  product p ON r.productCode = p.productCode
            WHERE upper(p.productType)="ROLLING_STOCK"
            """;

    private static final String TRACK_SQL = """
                SELECT distinct
                    p.productCode,
                    p.brand,
                    p.productName,
                    p.price,
                    p.gauge,
                    p.quantity
                FROM
                    product p
                WHERE upper(p.productType)="TRACK"
            """;

    private static final String TRACK_PACK_SQL = """
                SELECT
                    p.productCode,
                    p.brand,
                    p.productName,
                    p.price,
                    p.gauge,
                    p.quantity,
                    tp.packContents,
                    tp.packType,
                    tk.productCode AS trackProductCode,
                    tip.quantity   AS trackQuantity
                FROM
                    trackpack tp
                        JOIN    product p       ON tp.productCode=p.productCode
                        JOIN    trackinpack tip ON tp.productCode=tip.tpProductCode
                        JOIN    track tk        ON tk.productCode=tip.tkProductCode
                WHERE upper(p.productType)="TRACK_PACK"
                ORDER BY p.productCode
            """;

    private static final String TRACK_PACK_IN_TRAIN_SET_SQL = """
                SELECT 
                    ts.productCode AS trainSetCode,
                    tp.tpProductCode AS trackPackCode,
                    tp.quantity
                FROM
                    trainset ts
                JOIN    trackpackintrainset tp ON ts.productCode=tp.tsProductCode
                ORDER BY ts.productCode
            """;

    private static final String LOCOMOTIVE_IN_TRAIN_SET_SQL = """
                SELECT
                    ts.productCode  AS trainSetCode,
                    lts.locProductCode AS locomotiveCode,
                    lts.quantity
                FROM
                    trainset ts
                JOIN    locomointrainset lts ON lts.tsProductCode=ts.productCode
                ORDER BY ts.productCode
            """;

    private static final String TRAIN_SET_WITH_CONTROLLER_AND_ROLLING_STOCKS_SQL = """
                SELECT
                    p.productCode,
                    p.brand,
                    p.productName,
                    p.price,
                    p.gauge,
                    p.quantity,
                    ts.setContents,
                    ts.eraCode,
                    c.productCode AS controllerCode,
                    rsts.rsProductCode AS rollingStockProductCode,
                    rsts.quantity    AS rollingStockQuantitiy
                FROM
                        trainset ts
                JOIN    product p       ON ts.productCode=p.productCode
                JOIN    controller c    ON c.productCode=ts.controllerProductCode
                JOIN    rollingstockintrainset rsts ON ts.productCode=rsts.tsProductCode
                WHERE upper(p.productType)="TRAIN_SET"
                ORDER BY p.productCode, c.productCode, rsts.rsProductCode;
            """;

    //load all products from the database
    static {
        getConnection();
        loadAllProducts();
    }

   /* public ProductRepository(){
        getConnection();
        loadAllProducts();
    }*/

    public static Product getProduct(String productCode, ProductType productType) {

        if (ProductType.LOCOMOTIVE.equals(productType)) {
            return LOCOMOTIVE_MAP.get(productCode);
        }
        if (ProductType.CONTROLLER.equals(productType)) {
            return CONTROLLER_MAP.get(productCode);
        }
        if (ProductType.TRACK.equals(productType)) {
            return TRACK_MAP.get(productCode);
        }
        if (ProductType.ROLLING_STOCK.equals(productType)) {
            return ROLLING_STOCK_MAP.get(productCode);
        }
        if (ProductType.TRACK_PACK.equals(productType)) {
            return TRACK_PACK_MAP.get(productCode);
        }
        if (ProductType.TRAIN_SET.equals(productType)) {
            return TRAIN_SET_MAP.get(productCode);
        }
        throw new ProductNotFoundException();
    }

    public static List<Controller> getAllControllers() {
        return new ArrayList<Controller>(CONTROLLER_MAP.values());
    }

    public static List<Locomotive> getAllLocomotives() {
        return new ArrayList<Locomotive>(LOCOMOTIVE_MAP.values());
    }

    public static List<RollingStock> getAllRollingStocks() {
        return new ArrayList<RollingStock>(ROLLING_STOCK_MAP.values());
    }

    public static List<Track> getAllTracks() {
        return new ArrayList<Track>(TRACK_MAP.values());
    }

    public static List<TrackPack> getAllTrackPacks() {
        return new ArrayList<TrackPack>(TRACK_PACK_MAP.values());
    }

    public static List<TrainSet> getAllTrainSets() {
        return new ArrayList<TrainSet>(TRAIN_SET_MAP.values());
    }

    public static void  loadAllProducts() {
        try {
            loadLocomotives();
            loadControllers();
            loadTracks();
            loadRollingStocks();
            loadTrackPacks();
            loadLocomotiveInTrainSet();
            loadTrackPacksInTrainSet();
            loadTrainSets();
        }
        catch (SQLException ex){
            throw new RuntimeException("Error encountered in loading products");
        }

    }

    private static void loadLocomotives() throws SQLException {
        ResultSet resultSet= connection.prepareStatement(LOCOMOTIVE_SQL).executeQuery();
        while(resultSet.next()){
            Locomotive locomotive=buildLocomotive(resultSet);
            if (locomotive!=null) {
                LOCOMOTIVE_MAP.put(locomotive.getProductCode(), locomotive);
            }
        }
    }

    private static Locomotive buildLocomotive(ResultSet resultSet) throws SQLException {
        /*p.productCode,
                p.brand,
                p.productName,
                p.price,
                p.gauge
        lo.dccCode,
                lo.errCode*/
        Locomotive locomotive=new Locomotive();
        populateProductInfo(locomotive,  resultSet);
        DCCCode dccCode=DCCCode.valueOf((resultSet.getString("dccCode").toUpperCase()));
        String eraCode=resultSet.getString("eraCode");
        locomotive.setDccCode(dccCode);
        locomotive.setEraCode(eraCode);
        return locomotive;
    }

    private static void populateProductInfo(Product product, ResultSet resultSet) throws SQLException{
        String productCode=resultSet.getString("productCode");
        String brand=resultSet.getString("brand");
        String productName=resultSet.getString("productName");
        float  price=resultSet.getFloat("price");
        Gauge gauge =Gauge.valueOf(resultSet.getString("gauge"));
        int quantity= resultSet.getInt("quantity");
        product.setProductCode(productCode);
        product.setBrand(brand);
        product.setProductName(productName);
        product.setPrice(price);
        product.setGauge(gauge);
        product.setQuantity(quantity);
    }

    private static void loadControllers() throws SQLException {
        ResultSet resultSet= connection.prepareStatement(CONTROLLER_SQL).executeQuery();
        while(resultSet.next()){
            Controller controller=buildController(resultSet);
            if (controller!=null) {
                CONTROLLER_MAP.put(controller.getProductCode(), controller);
            }
        }
    }

    private static Controller buildController(ResultSet resultSet) throws SQLException{
        /*isDigital*/
        /*String productCode=resultSet.getString("productCode");
        String brand=resultSet.getString("brand");
        String productName=resultSet.getString("productName");
        float  price=resultSet.getFloat("price");
        Gauge gauge =Gauge.valueOf(resultSet.getString("gauge"));*/
        Controller controller=new Controller();
        populateProductInfo(controller, resultSet);
        boolean isDigital=resultSet.getBoolean("isDigital");
        controller.setDigital(isDigital);
        return controller;
    }

    private static void loadRollingStocks() throws SQLException {
        ResultSet resultSet= connection.prepareStatement(ROLLING_STOCK_SQL).executeQuery();
        while(resultSet.next()){
            RollingStock rollingStock=buildRollingStock(resultSet);
            if (rollingStock!=null) {
                ROLLING_STOCK_MAP.put(rollingStock.getProductCode(), rollingStock);
            }
        }
    }

    private static RollingStock buildRollingStock(ResultSet resultSet) throws SQLException {
        /*String productCode=resultSet.getString("productCode");
        String brand=resultSet.getString("brand");
        String productName=resultSet.getString("productName");
        float  price=resultSet.getFloat("price");
        Gauge gauge =Gauge.valueOf(resultSet.getString("gauge"));*/
        RollingStock rollingStock=new RollingStock();
        populateProductInfo(rollingStock, resultSet);
        RollingStockType rollingStockType=RollingStockType.valueOf(resultSet.getString("type").toUpperCase(Locale.ROOT));
        rollingStock.setRollingStockType(rollingStockType);
        String eraCode=resultSet.getString("eraCode");
        rollingStock.setEraCode(eraCode);
        return rollingStock;
    }

    private static  void loadTracks() throws SQLException{
        ResultSet resultSet= connection.prepareStatement(TRACK_SQL).executeQuery();
        while(resultSet.next()){
            Track track=buildTrack(resultSet);
            if (track!=null) {
                TRACK_MAP.put(track.getProductCode(), track);
            }
        }
    }

    private static Track buildTrack(ResultSet resultSet) throws SQLException{
        Track track= new Track();
        populateProductInfo(track, resultSet);
        return track;
    }

    private static void loadTrackPacks() throws SQLException{
        ResultSet resultSet= connection.prepareStatement(TRACK_PACK_SQL).executeQuery();
        while(resultSet.next()){
            TrackPack trackPack=buildTrackPack(resultSet);
            if (trackPack!=null) {
                TRACK_PACK_MAP.put(trackPack.getProductCode(), trackPack);
            }
        }
    }

    private static TrackPack buildTrackPack(ResultSet resultSet) throws SQLException {
        String productCode=resultSet.getString("productCode");
        TrackPack trackPack=new TrackPack();
        populateProductInfo(trackPack, resultSet);
        String packContents=resultSet.getString("tp.packContents");
        String packType=resultSet.getString("tp.packType");
        trackPack.setContents(packContents);
        trackPack.setPackType(packType);
        List<TrackInPack> containedTrackList=new ArrayList<>();
        do {
            String trackProductCode=resultSet.getString("trackProductCode");
            int trackQuantity=resultSet.getInt("trackQuantity");
            Track track=TRACK_MAP.get(trackProductCode);
            TrackInPack trackInPack=new TrackInPack(track, trackQuantity);
            containedTrackList.add(trackInPack);
        }
        while (resultSet.next() && resultSet.getString("productCode").equals(productCode));
        trackPack.setTrackList(containedTrackList);
        return trackPack;
    }

    private static void loadTrackPacksInTrainSet() throws SQLException {
        ResultSet resultSet= connection.prepareStatement(TRACK_PACK_IN_TRAIN_SET_SQL).executeQuery();
        while(resultSet.next()){
            String trainSetCode=resultSet.getString("trainSetCode");
            List<TrackPackInTrainSet> trackPackList=buildTrackPackList(resultSet, trainSetCode);
            if (trackPackList!=null) {
                TRACK_PACK_IN_TRAIN_SET_MAP.put(trainSetCode, trackPackList);
            }
        }
    }

    private static List<TrackPackInTrainSet> buildTrackPackList(ResultSet resultSet, String trainSetCode) throws SQLException  {
        List<TrackPackInTrainSet> trackPackList=new ArrayList<>();
        do {
            String trackPackCode=resultSet.getString("trackPackCode");
            int quantity=resultSet.getInt("quantity");
            if (trackPackCode!=null){
                TrackPack trackPack=TRACK_PACK_MAP.get(trackPackCode);
                trackPackList.add(new TrackPackInTrainSet(trackPack, quantity));
            }
        }
        while (resultSet.next() && resultSet.getString("trainSetCode").equals(trainSetCode));
        return trackPackList;
    }



    private static void loadLocomotiveInTrainSet() throws SQLException {
        ResultSet resultSet= connection.prepareStatement(LOCOMOTIVE_IN_TRAIN_SET_SQL).executeQuery();
        while(resultSet.next()){
            String trainSetCode=resultSet.getString("trainSetCode");
            List<LocomotiveInTrainSet> locomotiveList=buildLocomotiveList(resultSet, trainSetCode);
            if (locomotiveList!=null) {
                LOCOMOTIVE_IN_TRAIN_SET_MAP.put(trainSetCode, locomotiveList);
            }
        }
    }

    private static List<LocomotiveInTrainSet> buildLocomotiveList(ResultSet resultSet, String trainSetCode) throws SQLException{
        List<LocomotiveInTrainSet> locomotiveList=new ArrayList<>();
        do {
            /*SELECT
            ts.productCode  AS trainSetCode,
                    lts.locProductCode AS locomotiveCode,
            lts.quantity
                    FROM
            trainset ts
            JOIN    locomointrainset lts ON lts.tsProductCode=ts.productCode
            ORDER BY ts.productCode*/
            String locomotiveCode=resultSet.getString("locomotiveCode");
            int quantity=resultSet.getInt("quantity");
            if (locomotiveCode!=null){
                Locomotive locomotive=LOCOMOTIVE_MAP.get(locomotiveCode);
                locomotiveList.add(new LocomotiveInTrainSet(locomotive, quantity));
            }
        }
        while (resultSet.next() && resultSet.getString("trainSetCode").equals(trainSetCode));
        return locomotiveList;
    }

    private static void loadTrainSets() throws SQLException {
        ResultSet resultSet= connection.prepareStatement(TRAIN_SET_WITH_CONTROLLER_AND_ROLLING_STOCKS_SQL).executeQuery();
        while(resultSet.next()){
            TrainSet trainSet=buildTrainSetWithoutTrackPacks(resultSet);
            List<TrackPackInTrainSet> trackPackInTrainSetsList=TRACK_PACK_IN_TRAIN_SET_MAP.get(trainSet.getProductCode());
            trainSet.setTrackPackList(trackPackInTrainSetsList);
            List<LocomotiveInTrainSet>  locomotiveInTrainSet= LOCOMOTIVE_IN_TRAIN_SET_MAP.get(trainSet.getProductCode());
            trainSet.setLocomotiveList(locomotiveInTrainSet);
            TRAIN_SET_MAP.put(trainSet.getProductCode(), trainSet);
        }
    }

    private static TrainSet buildTrainSetWithoutTrackPacks(ResultSet resultSet) throws SQLException{
        /*SELECT
        p.productCode,
                p.brand,
                p.productName,
                p.price,
                p.gauge,
                ts.setContents,
                ts.eraCode,
                c.productCode AS controllerCode,
        rsts.rsProductCode AS rollingStockProductCode,
                rsts.quantity    AS rollingStockQuantity
        FROM
                trainset ts
        JOIN    product p       ON ts.productCode=p.productCode
        JOIN    controller c    ON c.productCode=ts.controllerProductCode
        JOIN    rollingstockintrainset rsts ON ts.productCode=rsts.tsProductCode
        WHERE upper(p.productType)="TRAIN_SET"
        ORDER BY p.productCode, c.productCode, rsts.rsProductCode;*/
        TrainSet trainSet=new TrainSet();
        populateProductInfo(trainSet,  resultSet);
        trainSet.setEraCode(resultSet.getString("eraCode"));
        trainSet.setContents(resultSet.getString("setContents"));
        Controller controller=CONTROLLER_MAP.get(resultSet.getString("controllerCode"));
        trainSet.setController(controller);
        List<RollingStockInTrainSet> rollingStockInTrainSetList=new ArrayList<>();
        do {
            String rollingStockProductCode = resultSet.getString("rollingStockProductCode");
            RollingStock rollingStock = ROLLING_STOCK_MAP.get(rollingStockProductCode);
            int quantity = resultSet.getInt("rollingStockQuantitiy");
            rollingStockInTrainSetList.add(new RollingStockInTrainSet(rollingStock, quantity));
        }
            while (resultSet.next() && trainSet.getProductCode().equals(resultSet.getString("productCode"))) ;
        trainSet.setRollingStockList(rollingStockInTrainSetList);
        return trainSet;
    }
    // Create OrderLines
   /* List<OrderLine> orderLines = new ArrayList<>();
                do {
        OrderLine orderLine = new OrderLine();
        orderLine.setLineNumber(resultSet.getInt("lineNumber"));
        Product product =buildProduct(resultSet);
        orderLine.setProduct(product);
        orderLine.setQuantity(resultSet.getInt("quantity"));
        orderLines.add(orderLine);
    } while (resultSet.next() && resultSet.getInt("orderNumber") == orderNumber);*/
}
