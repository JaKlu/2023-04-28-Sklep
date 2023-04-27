package pl.it.camp.sklep.Core;

import pl.it.camp.sklep.db.ProductRepository;
import pl.it.camp.sklep.gui.GUI;
import pl.it.camp.sklep.model.user.User;

public class Core {
    private static final ProductRepository products = new ProductRepository();

    public static void start() {
        programLoop:
        while (true) {
            switch (GUI.printInitialMenu()) {
                case "1":
                    User loggedUser = Authenticator.logIn();
                    if (loggedUser == null) break programLoop;
                    loggedUserLoop:
                    while (true) {
                        switch (GUI.printInternalMenu(loggedUser)) {
                            case "1":
                                GUI.printProductList(products.getProducts(), loggedUser);
                                break;
                            case "2":
                                GUI.buyProduct(products, GUI.readProductCode(), GUI.readProductQuantity());
                                break;
                            case "3":
                                break loggedUserLoop;
                            case "4":
                                if (loggedUser.getFunction().equals("Administrator")) {
                                    GUI.supplyProduct(products, GUI.readProductCode(), GUI.readProductQuantity());
                                }
                                break;
                            case "5":
                                if (loggedUser.getFunction().equals("Administrator")) {
                                    GUI.changeFunction(Authenticator.getUsersDatabase());
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                case "2":
                    Authenticator.signIn();
                    break;
                case "3":
                    break programLoop;
                default:
                    break;
            }
        }
    }
}
