package pl.it.camp.sklep.db;

import pl.it.camp.sklep.model.user.Admin;
import pl.it.camp.sklep.model.user.Customer;
import pl.it.camp.sklep.model.user.User;

import java.util.Arrays;

public class UserRepository {
    private User[] users = new User[5];

    public UserRepository() {
        this.users[0] = new Admin("admin", "6bdbb8e3b90a36d5b3245d2d9a1b4a9f"); //12345
        this.users[1] = new Admin("Kuba", "1f56201436ad0377399bdfe0ee2b1279"); //456
        this.users[2] = new Customer("Tomek", "cf9fac80c86b9714c697d4204829e9af"); //23456
        this.users[3] = new Customer("Olek", "1e7cc5f664c541e5a4ee57eab6b73abb"); //34567
        this.users[4] = new Customer("Wojtek", "44cf3a7e1def7261e426cbda3f936ec4"); //45678
    }

    public boolean addCustomer(String login, String password) {
        User user = findUserByLogin(login);
        if (user == null) {
            User[] newList = Arrays.copyOf(this.users, this.users.length + 1);
            this.users = Arrays.copyOf(newList, newList.length);
            this.users[users.length - 1] = new Customer(login, password);
            return true;
        } else {
            return false;
        }
    }

    public boolean changeUserFunction(String login, String function) {
        User user = findUserByLogin(login);
        if (user == null || user == this.users[0]) {  // deleting first default User - >admin< account - is prohibited
            return false;
        }
        user.setFunction(function);
        return true;
    }

    public User findUserByLogin(String login) {
        for (User element : users) {
            if (element.getLogin().equals(login)) {
                return element;
            }
        }
        return null;
    }

    public User[] getUsers() {
        return users;
    }
}
