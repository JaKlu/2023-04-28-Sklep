package pl.it.camp.sklep.db;

import pl.it.camp.sklep.model.product.*;

public class ProductRepository {
    private Product[] products = new Product[5];

    public ProductRepository() {
        products[0] = new Computer("100", "Dell XPS", 4500.00, 5);
        products[1] = new Monitor("101", "Iyama 27", 1200.00, 3);
        products[2] = new Keyboard("102", "Logitech Z", 150.00, 8);
        products[3] = new Mouse("103", "Microsoft X", 90.00, 7);
        products[4] = new Printer("104", "Brother 375", 650.00, 4);
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
    public Product[] getProducts() {
        return products;
    }
    public Product findProductByCode(String code) {
        for (Product element : products) {
            if (element.getCode().equals(code)) {
                return element;
            }
        }
        return null;
    }
}
