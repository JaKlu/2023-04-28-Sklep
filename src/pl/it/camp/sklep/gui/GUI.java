package pl.it.camp.sklep.gui;

import pl.it.camp.sklep.db.ProductRepository;
import pl.it.camp.sklep.db.UserRepository;
import pl.it.camp.sklep.model.product.Product;
import pl.it.camp.sklep.model.user.User;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GUI {
    private static final GUI instance = new GUI();
    private static final UserRepository users = UserRepository.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    private GUI() {
    }

    public String printInitialMenu() {
        System.out.println("""
                1. Zaloguj
                2. Zarejestruj
                3. Wyjście""");
        return scanner.nextLine();
    }

    public String printInternalMenu(User user) {
        System.out.println("1. Wyświetl listę produktów");
        System.out.println("2. Kup produkt");
        System.out.println("3. Wyloguj");
        if (user.getFunction().equals("Administrator")) {
            System.out.println("4. Uzupełnij produkt");
            System.out.println("5. Zmień rolę");
        }
        return scanner.nextLine();
    }

    public void printProductList(List<Product> products, User user) {
        boolean isEmpty = true;
        for (Product element : products) {
            if (user.getFunction().equals("Administrator")) {
                System.out.println(element);
            } else if (element.getQuantity() > 0) {
                System.out.println(element);
                isEmpty = false;
            }
        }
        if (user.getFunction().equals("Customer") && isEmpty) {
            System.out.println("Brak produktów do wyświetlenia");
        }
    }

    public void buyProduct(ProductRepository products, String code, int quantity) {
        if (products.buyProduct(code, quantity)) {
            Product product = products.findProductByCode(code);
            StringBuilder sb = new StringBuilder()
                    .append("Kupiono:\n")
                    .append(product.getName())
                    .append(" | Ilość: ")
                    .append(quantity)
                    .append("\nWartość: ")
                    .append(product.getPrice() * quantity);
            System.out.println(sb);
        } else {
            System.out.println("Produkt niedostępny lub brak wystarczającej ilości produktu");
        }
    }

    public String readProductCode() {
        System.out.println("Podaj kod produktu: ");
        return scanner.nextLine();
    }

    public void supplyProduct(ProductRepository products, String code, int quantity) {
        if (products.supplyProduct(code, quantity)) {
            Product product = products.findProductByCode(code);
            StringBuilder sb = new StringBuilder()
                    .append("Dodano:\n")
                    .append(product.getName())
                    .append(" | Ilość: ")
                    .append(quantity);
            System.out.println(sb);
        } else {
            System.out.println("Produkt niedostępny lub wprowadzono niewłaściwą ilość produktu");
        }
    }

    public void printUserList(UserRepository users) {
        for (Map.Entry<String, User> entry : users.getUsers().entrySet()) {
            System.out.println(entry.getValue().getFunction() + " | " + entry.getValue().getLogin());
        }
    }

    public void changeFunction() {
        printUserList(users);
        String login = readLogin();
        if (users.findUserByLogin(login) == null) {
            System.out.println("Błędna nazwa użytkownika");
            return;
        }

        String function = readFunction();
        if (users.changeUserFunction(login, function)) {
            StringBuilder sb = new StringBuilder()
                    .append("Zmieniono funkcję:\n")
                    .append(login)
                    .append(" --> ")
                    .append(function);
            System.out.println(sb);
        } else {
            System.out.println("Błąd lub nieuprawniona zmiana funkcji");
        }
    }

    public User readLoginAndPassword() {
        System.out.println("Login:");
        String login = scanner.nextLine();

        System.out.println("Hasło:");
        return new User(login, scanner.nextLine());
    }

    public String readLogin() {
        System.out.println("Login:");
        return scanner.nextLine();
    }

    public String readPassword() {
        System.out.println("Hasło:");
        return scanner.nextLine();
    }

    public String readFunction() {
        System.out.println("Nowa funkcja [Administrator/Customer]:");
        while (true) {
            switch (scanner.nextLine()) {
                case "a":
                case "A":
                case "Admin":
                case "admin":
                case "Administrator":
                case "administrator":
                    return "Administrator";
                case "c":
                case "C":
                case "cust":
                case "Cust":
                case "customer":
                case "Customer":
                    return "Customer";
                default:
                    System.out.println("Błędna nazwa funkcji");
                    break;
            }
        }
    }

    public int readProductQuantity() {
        System.out.println("Podaj ilość: ");
        int quantity;

        while (true) {
            try {
                quantity = scanner.nextInt();
                scanner.nextLine();
                if (quantity <= 0) {
                    System.out.println("Podaj liczbę większą niż 0:");
                    continue;
                }
                break;
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Podaj liczbę większą niż 0:");
                scanner.nextLine();
            }
        }
        return quantity;
    }

    public static GUI getInstance() {
        return instance;
    }
}
