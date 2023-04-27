package pl.it.camp.sklep.model.user;

public class Admin extends User {
    public Admin(String login, String password) {
        super(login, password);
        this.setFunction("Administrator");
    }
}
