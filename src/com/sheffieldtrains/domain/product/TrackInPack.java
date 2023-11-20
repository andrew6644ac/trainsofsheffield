package com.sheffieldtrains.domain.product;

public class TrackInPack {
    private Track track;
    private int quantity;

    public TrackInPack(Track track, int quantity) {
        this.track = track;
        this.quantity = quantity;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
