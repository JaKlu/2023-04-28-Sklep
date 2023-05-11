package pl.it.camp.sklep.db;

import pl.it.camp.sklep.model.product.*;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private static final ProductRepository instance = new ProductRepository();

    private final List<Product> products = new ArrayList<>();

    private ProductRepository() {
    }
//    public ProductRepository() {
////        products.add(new Computer("100", "Dell XPS", 4500.00, 5));
////        products.add(new Monitor("101", "Iyama 27", 1200.00, 3));
////        products.add(new Keyboard("102", "Logitech Z", 150.00, 8));
////        products.add(new Mouse("103", "Microsoft X", 90.00, 7));
////        products.add(new Printer("104", "Brother 375", 650.00, 4));
//    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public boolean supplyProduct(String code, int quantity) {
        Product product = findProductByCode(code);
        if (product != null && quantity > 0) {
            product.setQuantity(product.getQuantity() + quantity);
            return true;
        }
        return false;
    }

    public boolean buyProduct(String code, int quantity) {
        Product product = findProductByCode(code);
        if (product != null && product.getQuantity() >= quantity) {
            product.setQuantity(product.getQuantity() - quantity);
            return true;
        }
        return false;
    }

    public Product findProductByCode(String code) {
        for (Product element : products) {
            if (element.getCode().equals(code)) {
                return element;
            }
        }
        return null;
    }

    public List<Product> getProducts() {
        return products;
    }

    public static ProductRepository getInstance() {
        return instance;
    }
}
