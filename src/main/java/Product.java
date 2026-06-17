public class Product {
    private final String id;
    private String name;
    private double price;
    private int quantity;

    public Product(String id, String name, double price, int quantity) {
        if (isBlank(id)) {
            throw new IllegalArgumentException("Mã sản phẩm không được để trống");
        }
        this.id = id.trim().toUpperCase();
        setName(name);
        setPrice(price);
        setQuantity(quantity);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public void reduceQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Số lượng mua phải lớn hơn 0");
        }
        if (amount > quantity) {
            throw new IllegalArgumentException("Không đủ hàng trong kho");
        }
        quantity -= amount;
    }

    public void increaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Số lượng nhập phải lớn hơn 0");
        }
        quantity += amount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (isBlank(name)) {
            throw new IllegalArgumentException("Tên sản phẩm không được để trống");
        }
        this.name = name.trim();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Giá sản phẩm phải lớn hơn 0");
        }
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Số lượng tồn kho không hợp lệ");
        }
        this.quantity = quantity;
    }

    public boolean matches(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return true;
        }
        String q = keyword.toLowerCase();
        return id.toLowerCase().contains(q) || name.toLowerCase().contains(q);
    }
}
