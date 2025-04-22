package commands;


import commands.exceptions.WrongArgumentsAmountException;
import managers.ProductManager;
import models.IdGenerator;
import models.IncorrectIntegerValueException;
import models.IncorrectStringValueException;
import models.Product;

public class RemoveByID extends Command {
    private ProductManager<Product> productManager;

    public RemoveByID(ProductManager<Product> manager) {
        super();
        this.productManager = manager;
        this.isNeedArguments = true;
        this.commandArguments = 1;
    }

    @Override
    public void execute(String[] arguments)
            throws WrongArgumentsAmountException, IncorrectStringValueException, IncorrectIntegerValueException {
        if (arguments.length != this.commandArguments) {
            throw new WrongArgumentsAmountException();
        }
        if (arguments[arguments.length - 1].matches("^-?\\d+$") == false) {
            throw new IncorrectIntegerValueException();
        }
        long id = Long.parseLong(arguments[arguments.length - 1]);
        for (Product product : this.productManager.getCollection()) {
            if (product.getId() == id) {
                this.productManager.getCollection().remove(product);
                IdGenerator.updateCounter(this.productManager.getCollection());
                System.out.println("Элемент успешно удален из коллекции");
                return;
            }
        }
        System.out.println("Элемента с таким id не существует в коллекции :(");
    }

    @Override
    public void execute() throws WrongArgumentsAmountException {
        throw new WrongArgumentsAmountException();
    }

    @Override
    public String getDescription() {
        return "remove_by_id id : удалить элемент из коллекции по его id";
    }

    @Override
    public String toString() {
        return "remove_by_id";
    }
}
