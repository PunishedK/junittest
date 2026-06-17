import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private final String orderId;
    private final User customer;
    private final String receiverName;
    private final String phone;
    private final String address;
    private final String paymentMethod;
    private final String date;
    private final List<OrderItem> items;
    private OrderStatus status;

    public Order(String orderId, User customer, String receiverName, String phone, String address, String paymentMethod, List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Giỏ hàng đang trống");
        }
        if (isBlank(receiverName)) {
            throw new IllegalArgumentException("Họ tên người nhận không được để trống");
        }
        if (isBlank(phone)) {
            throw new IllegalArgumentException("Số điện thoại không được để trống");
        }
        if (isBlank(address)) {
            throw new IllegalArgumentException("Địa chỉ không được để trống");
        }
        this.orderId = orderId;
        this.customer = customer;
        this.receiverName = receiverName.trim();
        this.phone = phone.trim();
        this.address = address.trim();
        this.paymentMethod = paymentMethod;
        this.items = new ArrayList<>(items);
        this.status = OrderStatus.CHO_XU_LY;
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public double getTotalBeforeDiscount() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getSubTotal();
        }
        return total;
    }

    public double getDiscount() {
        double total = getTotalBeforeDiscount();
        if (total >= 1000000) {
            return total * 0.1;
        }
        return 0;
    }

    public double getTotal() {
        return getTotalBeforeDiscount() - getDiscount();
    }

    public String getOrderId() {
        return orderId;
    }

    public User getCustomer() {
        return customer;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getDate() {
        return date;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
