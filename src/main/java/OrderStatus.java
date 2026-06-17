public enum OrderStatus {
    CHO_XU_LY("Chờ xử lý"),
    DANG_GIAO("Đang giao"),
    HOAN_THANH("Hoàn thành"),
    DA_HUY("Đã hủy");

    private final String text;

    OrderStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static OrderStatus fromText(String text) {
        for (OrderStatus status : values()) {
            if (status.getText().equals(text)) {
                return status;
            }
        }
        return CHO_XU_LY;
    }
}
