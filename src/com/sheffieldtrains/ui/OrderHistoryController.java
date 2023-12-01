package com.sheffieldtrains.ui;

public class OrderHistoryController {
    private OrderHisotryPanel view;

    public OrderHistoryController(OrderHisotryPanel view) {
        this.view = view;
    }


    public void notifyChange() {
        view.populateTableWithOrderHistoryData();
    }
}
