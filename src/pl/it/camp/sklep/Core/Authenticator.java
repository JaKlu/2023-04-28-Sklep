package pl.it.camp.sklep.Core;

import org.apache.commons.codec.digest.DigestUtils;
import pl.it.camp.sklep.db.UserRepository;
import pl.it.camp.sklep.gui.GUI;
import pl.it.camp.sklep.model.user.User;

public class Authenticator {
    private static final UserRepository usersDatabase = new UserRepository();
    private static final String seed = "&OY74PZHvIm!Y7K*0!jQWjbzjjIiFY0b";

    public static User logIn() {
        int counter = 0;
        while (counter < 3) {
            User userFromGui = GUI.readLoginAndPassword();
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

    public static void signIn() {
        String login = GUI.readLogin();
        if (usersDatabase.findUserByLogin(login) != null) {
            System.out.println("Użytkownik o podanym loginie już istnieje");
            return;
        }
        if (usersDatabase.addCustomer(login, Authenticator.hashPassword(GUI.readPassword()))) {
            System.out.println("Użytkownik \"" + login + "\" został zarejestrowany i może się zalogować.");
        } else {
            System.out.println("Błąd przy rejestracji nowego użytkownika");
        }
    }

    private static String hashPassword(String password) {
        return DigestUtils.md5Hex(password + seed);
    }

    public static UserRepository getUsersDatabase() {
        return usersDatabase;
    }
}
