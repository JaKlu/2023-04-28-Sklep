package pl.it.camp.sklep.model.user;

public class Customer extends User {
    public Customer(String login, String password) {
        super(login, password);
        this.setFunction("Customer");
    }
}
