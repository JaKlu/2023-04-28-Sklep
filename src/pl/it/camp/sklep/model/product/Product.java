package pl.it.camp.sklep.model.product;

public class Product {
    private final String code;
    private final String name;
    private final double price;
    private int quantity;

    public Product(String code, String name, double price, int quantity) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Kod: ")
                .append(this.getCode())
                .append(" | ")
                .append(this.getName())
                .append(" | Cena: ")
                .append(this.getPrice())
                .append(" | Ilość: ")
                .append(this.getQuantity())
                .append(" szt.")
                .toString();
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
