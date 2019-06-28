package nutan.tech.listmodel;

import nutan.tech.models.PurchaseOrderModel;

import java.util.List;

public class PurchaseOrdersListModel {

    private List<PurchaseOrderModel> purchaseOrdersList;

    public List<PurchaseOrderModel> getPurchaseOrdersList() {
        return purchaseOrdersList;
    }

    public void setPurchaseOrdersList(List<PurchaseOrderModel> purchaseOrdersList) {
        this.purchaseOrdersList = purchaseOrdersList;
    }
}
