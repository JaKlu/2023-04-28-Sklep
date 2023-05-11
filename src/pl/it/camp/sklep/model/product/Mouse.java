package pl.it.camp.sklep.model.product;

public class Mouse extends Product{

    public Mouse(String code, String name, double price, int quantity) {
        super(code, name, price, quantity);
    }

    public Mouse(String[] vars) {
        super(vars);
    }
}
