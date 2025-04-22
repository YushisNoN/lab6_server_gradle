package commands;


import commands.exceptions.WrongArgumentsAmountException;
import managers.ProductManager;
import models.IncorrectStringValueException;
import models.Product;

public class Info extends Command {
    private ProductManager<Product> productManager;

    public Info(ProductManager<Product> manager) {
        super();
        this.productManager = manager;

    }

    @Override
    public void execute() throws WrongArgumentsAmountException {
        System.out.println("Информация о коллекции:\n" +
                "Количество элементов: " + this.productManager.getCollection().size() + "\n" +
                "Тип: " + this.productManager.getCollection().getClass().getSimpleName() + "\n" +
                "Время инициализации: " + this.productManager.getInitTime());
    }

    @Override
    public void execute(String[] arguments) throws WrongArgumentsAmountException, IncorrectStringValueException {
        throw new WrongArgumentsAmountException();
    }

    @Override
    public String getDescription() {
        return "info : Вывести в стандартный поток вывода информацию о коллекции";
    }

    @Override
    public String toString() {
        return "info";
    }

}
