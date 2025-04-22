package commands;


import commands.exceptions.WrongArgumentsAmountException;
import managers.ProductManager;
import models.IdGenerator;
import models.IncorrectStringValueException;
import models.Product;
import models.creators.ProductCreator;
import server.RequestHandler;
import server.ResponseHandler;

public class AddIfMax extends Add {
    private ProductManager<Product> productCollection;

    public AddIfMax(ProductManager<Product> manager, ResponseHandler response, RequestHandler request) {
        super(manager, response, request);
        this.productCollection = manager;
    }

    @Override
    public void execute(String[] arguments) throws WrongArgumentsAmountException, IncorrectStringValueException {
        throw new WrongArgumentsAmountException();
    }

    @Override
    public String getDescription() {
        return "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента в этой коллекции";
    }

    @Override
    public String toString() {
        return "add_if_max";
    }

    @Override
    public void execute() throws WrongArgumentsAmountException {
        Product product = new ProductCreator(this.requestHandler, this.responseHandler).createProduct();
        if (this.productCollection.getCollection().last().compareTo(product) < 0) {
            this.productCollection.addProdut(product);
            System.out.println("Продукт успешно добавлен в коллекцию");
            return;
        }
        IdGenerator.updateCounter(this.productCollection.getCollection());
        System.out.println("Элемент невозможно добавить в коллекцию, так как он меньше наибольшего элемента ^^");
    }
}
