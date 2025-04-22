package commands;

import commands.exceptions.WrongArgumentsAmountException;
import managers.ProductManager;
import models.IncorrectStringValueException;
import models.Product;

public class Clear extends Command {
    private ProductManager<Product> productManager;

    public Clear(ProductManager<Product> manager) {
        super();
        this.productManager = manager;
    }

    @Override
    public String toString() {
        return "clear";
    }

    @Override
    public void execute() throws WrongArgumentsAmountException {
        if (this.productManager.getCollection().isEmpty()) {
            System.out.println("коллекция пустая :)");
            return;
        }
        this.productManager.getCollection().clear();
        System.out.println("Коллекция успешна очищена >:)");
    }

    @Override
    public void execute(String[] arguments) throws WrongArgumentsAmountException, IncorrectStringValueException {
        throw new WrongArgumentsAmountException();
    }

    @Override
    public String getDescription() {
        return "clear : очистить коллекцию";
    }
}
