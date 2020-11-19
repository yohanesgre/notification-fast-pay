package co.id.fastpay.fastpaynotification.utils;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InboxModel {
    @SerializedName("idmailbox_inbox")
    private int id;
    private String title;
    private String content;
    @SerializedName("url_image")
    private String urlImage;
    @SerializedName("url_web")
    private String urlWeb;
    @SerializedName("create_time")
    private String createTime;
    @SerializedName("publish_time")
    private String publishTime;
    @SerializedName("nav_to")
    private String navTo;
    @Nullable
    private String badge;
    private String type;
    @Nullable
    private String slug;
    @SerializedName("idmailbox_to")
    @Nullable
    private int idMailBoxTo;
    @SerializedName("id_outlet")
    @Nullable
    private String idOutlet;
    @SerializedName("is_deleted")
    @Nullable
    private int isDeleted;
    @SerializedName("is_read")
    private int isRead;
    @SerializedName("read_time")
    @Nullable
    private String readTime;
    @SerializedName("customer_name")
    @Nullable
    private String customerName;
    @SerializedName("customer_phone")
    @Nullable
    private String customerPhone;
    @SerializedName("bill_date")
    @Nullable
    private String billDate;
    @SerializedName("admin_fee")
    @Nullable
    private Double adminFee;
    @SerializedName("cashback")
    @Nullable
    private Double cashBack;
    @SerializedName("additional_data")
    @Nullable
    private List<AdditionalDataModel> additionalDataModel;

    public InboxModel(
            int id,
            String title,
            String content,
            String urlImage,
            String urlWeb,
            String createTime,
            String publishTime,
            String navTo,
            String badge,
            String type,
            @Nullable String slug, int idMailBoxTo, @Nullable String idOutlet, int isDeleted, int isRead,
            @Nullable String readTime,
            @Nullable String customerName1, @Nullable String customerPhone1, @Nullable String billDate1, @Nullable Double adminFee, @Nullable Double cashBack, @Nullable List<AdditionalDataModel> additionalDataModel1){
        this.id = id;
        this.title = title;
        this.content = content;
        this.urlImage = urlImage;
        this.urlWeb = urlWeb;
        this.createTime = createTime;
        this.publishTime = publishTime;
        this.navTo = navTo;
        this.badge = badge;
        this.type = type;
        this.slug = slug;
        this.idMailBoxTo = idMailBoxTo;
        this.idOutlet = idOutlet;
        this.isDeleted = isDeleted;
        this.isRead = isRead;
        this.readTime = readTime;
        this.customerName = customerName1;
        this.customerPhone = customerPhone1;
        this.billDate = billDate1;
        this.adminFee = adminFee;
        this.cashBack = cashBack;
        this.additionalDataModel = additionalDataModel1;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public String getUrlWeb() {
        return urlWeb;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public String getNavTo() {
        return navTo;
    }

    @Nullable
    public String getBadge() {
        return badge;
    }

    public String getType() {
        return type;
    }

    public int getIsRead() {
        return isRead;
    }

    public String getContent() {
        return content;
    }

    @Nullable
    public String getReadTime() {
        return readTime;
    }

    @Nullable
    public String getCustomerName() {
        return customerName;
    }

    @Nullable
    public String getCustomerPhone() {
        return customerPhone;
    }

    @Nullable
    public String getBillDate() {
        return billDate;
    }

    @Nullable
    public String getSlug() {
        return slug;
    }

    public int getIdMailBoxTo() {
        return idMailBoxTo;
    }

    @Nullable
    public String getIdOutlet() {
        return idOutlet;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    @Nullable
    public List<AdditionalDataModel> getAdditionalDataModel() {
        return additionalDataModel;
    }

    @Nullable
    public Double getAdminFee() {
        return adminFee;
    }

    @Nullable
    public Double getCashBack() {
        return cashBack;
    }
}
