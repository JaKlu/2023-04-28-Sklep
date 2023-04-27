package pl.it.camp.sklep.gui;

import pl.it.camp.sklep.db.ProductRepository;
import pl.it.camp.sklep.db.UserRepository;
import pl.it.camp.sklep.model.product.Product;
import pl.it.camp.sklep.model.user.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GUI {
    private static final Scanner scanner = new Scanner(System.in);

    public static String printInitialMenu() {
        System.out.println("""
                1. Zaloguj
                2. Zarejestruj
                3. Wyjście""");
        return scanner.nextLine();
    }

    public static String printInternalMenu(User user) {
        System.out.println("1. Wyświetl listę produktów");
        System.out.println("2. Kup produkt");
        System.out.println("3. Wyloguj");
        if (user.getFunction().equals("Administrator")) {
            System.out.println("4. Uzupełnij produkt");
            System.out.println("5. Zmień rolę");
        }
        return scanner.nextLine();
    }

    public static void printProductList(Product[] products, User user) {
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

    public static void buyProduct(ProductRepository products, String code, int quantity) {
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

    public static String readProductCode() {
        System.out.println("Podaj kod produktu: ");
        return scanner.nextLine();
    }

    public static void supplyProduct(ProductRepository products, String code, int quantity) {
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

    public static void printUserList(UserRepository users) {
        for (User element : users.getUsers()) {
            System.out.println(element);
        }
    }

    public static void changeFunction(UserRepository users) {
        printUserList(users);
        String login = readLogin();
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

    public static User readLoginAndPassword() {
        System.out.println("Login:");
        String login = scanner.nextLine();

        System.out.println("Hasło:");
        return new User(login, scanner.nextLine());
    }

    public static String readLogin() {
        System.out.println("Login:");
        return scanner.nextLine();
    }

    public static String readPassword() {
        System.out.println("Hasło:");
        return scanner.nextLine();
    }

    public static String readFunction() {
        String function;
        do {
            System.out.println("Nowa funkcja [Administrator/Customer]:");
            function = scanner.nextLine();
        } while (!(function.equals("Administrator")) && !(function.equals("Customer")));

        return function;
    }

    public static int readProductQuantity() {
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
}
