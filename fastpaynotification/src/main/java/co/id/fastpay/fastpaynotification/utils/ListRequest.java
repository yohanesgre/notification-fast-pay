package co.id.fastpay.fastpaynotification.utils;

public class ListRequest {
    private String type;
    private int offset;
    private int limit;

    public ListRequest(String type, int offset, int limit){
        this.type = type;
        this.offset = offset;
        this.limit = limit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
