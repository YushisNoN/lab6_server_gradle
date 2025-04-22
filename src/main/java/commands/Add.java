package commands;


import commands.exceptions.WrongArgumentsAmountException;
import managers.ProductManager;
import models.IncorrectStringValueException;
import models.Product;
import models.creators.ProductCreator;
import server.RequestHandler;
import server.ResponseHandler;

public class Add extends Command {

    private ProductManager<Product> productCollection;

    protected ResponseHandler responseHandler;
    protected RequestHandler requestHandler;
    public Add(ProductManager<Product> manager, ResponseHandler response, RequestHandler request) {
        super();
        this.productCollection = manager;
        this.requestHandler = request;
        this.responseHandler = response;
    }

    @Override
    public void execute(String[] arguments) throws WrongArgumentsAmountException, IncorrectStringValueException {
        throw new WrongArgumentsAmountException();
    }

    @Override
    public String getDescription() {
        return "add {element} : добавить новый элемент в коллекцию";
    }

    @Override
    public String toString() {
        return "add";
    }

    @Override
    public void execute() throws WrongArgumentsAmountException {
        ProductCreator creator = new ProductCreator(this.requestHandler, this.responseHandler);
        Product product = creator.createProduct();
        this.productCollection.addProdut(product);
        System.out.println("Продукт добавлен");
    }

}
