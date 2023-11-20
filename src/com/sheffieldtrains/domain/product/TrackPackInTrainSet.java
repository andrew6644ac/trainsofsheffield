package com.sheffieldtrains.domain.product;

public class TrackPackInTrainSet {
    private TrackPack trackPack;
    private int quantity;

    public TrackPackInTrainSet(TrackPack trackPack, int quantity) {
        this.trackPack = trackPack;
        this.quantity = quantity;
    }


    public TrackPack getTrackPack() {
        return trackPack;
    }

    public int getQuantity() {
        return quantity;
    }
}
