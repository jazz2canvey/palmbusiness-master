package nutan.tech.listmodel;

import java.util.List;

import nutan.tech.models.PurchaseInvoiceModel;

public class PurchaseInvoicesListModel {

	private List<PurchaseInvoiceModel> purchaseInvoicesList;

	public List<PurchaseInvoiceModel> getPurchaseInvoicesList() {
		return purchaseInvoicesList;
	}

	public void setPurchaseInvoicesList(List<PurchaseInvoiceModel> purchaseInvoicesList) {
		this.purchaseInvoicesList = purchaseInvoicesList;
	}
}
