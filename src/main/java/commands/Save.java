package commands;

import commands.exceptions.WrongArgumentsAmountException;
import managers.ProductManager;
import models.IncorrectStringValueException;
import models.Product;
import utils.files.FileWriter;

import java.io.FileNotFoundException;
import java.io.IOException;



public class Save extends Command {
    private ProductManager<Product> productCollection;

    public Save(ProductManager<Product> productManager) {
        super();
        this.productCollection = productManager;
    }

    @Override
    public void execute(String[] arguments) throws WrongArgumentsAmountException, IncorrectStringValueException {
        throw new WrongArgumentsAmountException();
    }

    @Override
    public String getDescription() {
        return "save : сохранить коллекцию в файл";
    }

    @Override
    public String toString() {
        return "save";
    }

    @Override
    public void execute() throws WrongArgumentsAmountException {
        FileWriter fileWriter = new FileWriter();
        try {
            fileWriter.write(productCollection);
            System.out.println("Коллекция успешно записана в файл");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
