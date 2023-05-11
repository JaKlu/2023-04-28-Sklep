package pl.it.camp.sklep.Core;

import org.apache.commons.codec.digest.DigestUtils;
import pl.it.camp.sklep.db.UserRepository;
import pl.it.camp.sklep.gui.GUI;
import pl.it.camp.sklep.model.user.User;

public class Authenticator {
    private static final Authenticator instance = new Authenticator();
    private static final UserRepository usersDatabase = UserRepository.getInstance();
    public static final GUI gui = GUI.getInstance();
    private static final String seed = "&OY74PZHvIm!Y7K*0!jQWjbzjjIiFY0b";

    private Authenticator() {
    }

    public User logIn() {
        int counter = 0;
        while (counter < 3) {
            User userFromGui = gui.readLoginAndPassword();
            User userFromDb = usersDatabase.findUserByLogin(userFromGui.getLogin());

            if (userFromDb != null
                    && userFromDb.getPassword().equals(
                    DigestUtils.md5Hex(userFromGui.getPassword() + seed))) {
                System.out.println("-- Zalogowany użytkownik: " + userFromDb.getLogin() + " --");
                return userFromDb;
            }
            System.out.println("Błędny login lub hasło");
            counter++;
        }
        return null;
    }

    public void signIn() {
        String login = gui.readLogin();
        if (usersDatabase.findUserByLogin(login) != null) {
            System.out.println("Użytkownik o podanym loginie już istnieje");
            return;
        }
        if (usersDatabase.addUser("Customer", login, hashPassword(gui.readPassword()))) {
            System.out.println("Użytkownik \"" + login + "\" został zarejestrowany i może się zalogować.");
        } else {
            System.out.println("Błąd przy rejestracji nowego użytkownika");
        }
    }

    private String hashPassword(String password) {
        return DigestUtils.md5Hex(password + seed);
    }

    public static Authenticator getInstance() {
        return instance;
    }
}
