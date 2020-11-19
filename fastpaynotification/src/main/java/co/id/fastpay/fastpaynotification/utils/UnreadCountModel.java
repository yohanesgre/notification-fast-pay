package co.id.fastpay.fastpaynotification.utils;

import com.google.gson.annotations.SerializedName;

public class UnreadCountModel {
    @SerializedName("unread_inbox")
    private int unreadCount;

    public UnreadCountModel(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public int getUnreadCount() {
        return unreadCount;
    }
}
