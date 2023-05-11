package pl.it.camp.sklep.db;

import pl.it.camp.sklep.model.Writable;
import pl.it.camp.sklep.model.product.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class FileLoader {
    private static final FileLoader instance = new FileLoader();
    private static final ProductRepository products = ProductRepository.getInstance();
    private static final UserRepository users = UserRepository.getInstance();

    private FileLoader() {
    }

    public void readDataFromFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("db.csv"));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] objectData = line.split(";");
            String[] vars = Arrays.copyOfRange(objectData, 1, objectData.length);

            switch (objectData[0]) {
                case "Computer":
                    products.addProduct(new Computer(vars));
                    break;
                case "Keyboard":
                    products.addProduct(new Keyboard(vars));
                    break;
                case "Monitor":
                    products.addProduct(new Monitor(vars));
                    break;
                case "Mouse":
                    products.addProduct(new Mouse(vars));
                    break;
                case "Printer":
                    products.addProduct(new Printer(vars));
                    break;
                case "Administrator":
                case "Customer":
                    users.writeUserToDb(objectData);
                    break;
                default:
                    System.out.println("Unexpected type during database loading");
                    break;
            }
        }
        reader.close();
    }

    public void saveDataToFile() throws IOException {
        Collection<Writable> entries = new ArrayList<>();
        entries.addAll(products.getProducts());
        entries.addAll(users.getUsers().values());

        BufferedWriter writer = new BufferedWriter(new FileWriter("db.csv"));

        boolean firstTime = true;
        for (Writable entry : entries) {
            String lineInFIle = entry.toCSV();
            if (!firstTime) {
                writer.newLine();
            }
            firstTime = false;
            writer.write(lineInFIle);
        }
        writer.close();
    }

    public static FileLoader getInstance() {
        return instance;
    }
}
