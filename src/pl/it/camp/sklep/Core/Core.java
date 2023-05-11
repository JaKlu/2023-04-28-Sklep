package pl.it.camp.sklep.Core;

import pl.it.camp.sklep.db.FileLoader;
import pl.it.camp.sklep.db.ProductRepository;
import pl.it.camp.sklep.db.UserRepository;
import pl.it.camp.sklep.gui.GUI;
import pl.it.camp.sklep.model.user.User;

import java.io.IOException;

public class Core {
    private static final Core instance = new Core();
    private static final GUI gui = GUI.getInstance();
    private static final Authenticator authenticator = Authenticator.getInstance();
    private static final ProductRepository products = ProductRepository.getInstance();
    private static final UserRepository users = UserRepository.getInstance();
    private static final FileLoader fileLoader = FileLoader.getInstance();

    private Core() {
    }

    public void start() {
        try {
            fileLoader.readDataFromFile();
        } catch (IOException e) {
            System.out.println("ERROR while reading from database");
            return;
        }
        programLoop:
        while (true) {
            switch (gui.printInitialMenu()) {
                case "1":
                    User loggedUser = authenticator.logIn();
                    if (loggedUser == null) break programLoop;
                    loggedUserLoop:
                    while (true) {
                        switch (gui.printInternalMenu(loggedUser)) {
                            case "1":
                                gui.printProductList(products.getProducts(), loggedUser);
                                break;
                            case "2":
                                gui.buyProduct(products, gui.readProductCode(), gui.readProductQuantity());
                                break;
                            case "3":
                                break loggedUserLoop;
                            case "4":
                                if (loggedUser.getFunction().equals("Administrator")) {
                                    gui.supplyProduct(products, gui.readProductCode(), gui.readProductQuantity());
                                }
                                break;
                            case "5":
                                if (loggedUser.getFunction().equals("Administrator")) {
                                    gui.changeFunction();
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                case "2":
                    authenticator.signIn();
                    break;
                case "3":
                    try {
                        fileLoader.saveDataToFile();
                    } catch (IOException e) {
                        System.out.println("ERROR while writing to database");
                    }
                    break programLoop;
                default:
                    break;
            }
        }
    }

    public static Core getInstance() {
        return instance;
    }
}
