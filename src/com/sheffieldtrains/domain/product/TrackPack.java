package com.sheffieldtrains.domain.product;

import java.util.ArrayList;
import java.util.List;

public class TrackPack extends Product{
    private String contents;
    private String packType;
    private List<TrackInPack> trackList=new ArrayList<>();

    public TrackPack(){}

    public TrackPack(String productCode,
                     String brand,
                     String productName,
                     float price,
                     Gauge gauge,
                     int quantity,
                     String contents) {
        super(productCode, brand, productName, price, gauge, quantity);
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public List<TrackInPack> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<TrackInPack> trackList) {
        this.trackList = trackList;
    }

    public void setPackType(String packType) {
        this.packType=packType;
    }

    public String getPackType() {
        return packType;
    }

    public void addTrackInPack(TrackInPack trackInPack){
        trackList.add(trackInPack);
    }
}
