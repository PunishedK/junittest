public class CartItem {
    private final Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Sản phẩm không hợp lệ");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Số lượng mua không hợp lệ");
        }
        this.product = product;
        this.quantity = quantity;
    }

    public void addQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Số lượng mua không hợp lệ");
        }
        this.quantity += amount;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSubTotal() {
        return product.getPrice() * quantity;
    }
}
