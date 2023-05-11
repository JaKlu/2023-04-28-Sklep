package pl.it.camp.sklep.model.user;

import pl.it.camp.sklep.model.Writable;

public class User implements Writable {
    private String function;
    private final String login;
    private final String password;

//    public User(String login, String password) {
//        this.login = login;
//        this.password = password;
//    }

    public User(String function, String login, String password) {
        this(login, password);
        this.function = function;

    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }


    public User(String[] vars) {
        this(vars[0], vars[1], vars[3]);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Funkcja: ")
                .append(getFunction())
                .append(" | Login: ")
                .append(getLogin())
                .toString();
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toCSV() {
        return new StringBuilder()
                .append(this.getFunction())
                .append(";")
                .append(this.getLogin())
                .append(";")
                .append(this.getPassword())
                .toString();
    }
}
