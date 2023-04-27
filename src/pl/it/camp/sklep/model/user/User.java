package pl.it.camp.sklep.model.user;

public class User {
    private String function;
    private final String login;
    private final String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
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
}
