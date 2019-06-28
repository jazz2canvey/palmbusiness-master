package nutan.tech.listmodel;

import nutan.tech.models.PurchasedItemModel;

import java.util.List;

public class PurchasedItemsListModel {

    private List<PurchasedItemModel> purchasedItemsList;

    public List<PurchasedItemModel> getPurchasedItemsList() {
        return purchasedItemsList;
    }

    public void setPurchasedItemsList(List<PurchasedItemModel> purchasedItemsList) {
        this.purchasedItemsList = purchasedItemsList;
    }
}
