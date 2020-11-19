package co.id.fastpay.fastpaynotification.utils;

import com.google.gson.annotations.SerializedName;

public class AdditionalDataModel {
    @SerializedName("idmailbox_inbox_add")
    private int id;
    @SerializedName("group_product")
    private String groupProduct;
    @SerializedName("product_id")
    private String productId;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("customer_id")
    private String customerId;
    @SerializedName("share_to")
    private String shareTo;
    private Double nominal;
    @SerializedName("customer_name")
    private String customerName;
    @SerializedName("mailbox_to_idmailbox_to")
    private String mailboxToIdMailBoxTo;

    public AdditionalDataModel(
            int id,
            String groupProduct, String productId,
            String productName, String customerId,
            String shareTo,
            Double nominal,
            String customerName,
            String mailboxToIdMailBoxTo
    ){
        this.id = id;
        this.groupProduct = groupProduct;
        this.productId = productId;
        this.productName = productName;
        this.customerId = customerId;
        this.shareTo = shareTo;
        this.nominal = nominal;
        this.customerName = customerName;
        this.mailboxToIdMailBoxTo = mailboxToIdMailBoxTo;
    }

    public int getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getShareTo() {
        return shareTo;
    }

    public Double getNominal() {
        return nominal;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getMailboxToIdMailBoxTo() {
        return mailboxToIdMailBoxTo;
    }

    public String getGroupProduct() {
        return groupProduct;
    }

    public String getProductName() {
        return productName;
    }
}
