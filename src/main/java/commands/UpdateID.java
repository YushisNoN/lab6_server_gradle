package commands;


import commands.exceptions.WrongArgumentsAmountException;
import managers.ProductManager;
import models.IdGenerator;
import models.IncorrectIntegerValueException;
import models.IncorrectStringValueException;
import models.Product;
import models.creators.ProductCreator;
import server.RequestHandler;
import server.ResponseHandler;
import utils.IDreceive;

import java.io.IOException;

public class UpdateID extends Add {
    private ProductManager<Product> productManager;


    public UpdateID(ProductManager<Product> manager, ResponseHandler response, RequestHandler request) {
        super(manager, response, request);
        this.productManager = manager;
        this.isNeedArguments = true;
        this.commandArguments = 1;
    }

    @Override
    public void execute(String[] arguments)
            throws WrongArgumentsAmountException, IncorrectStringValueException{
        if (arguments.length != this.commandArguments) {
            throw new WrongArgumentsAmountException();
        }
        if (arguments[arguments.length - 1].matches("^-?\\d+$") == false) {
            try {
                throw new IncorrectIntegerValueException();
            } catch (IncorrectIntegerValueException e) {
                throw new RuntimeException(e);
            }
        }
        long id = Long.parseLong(arguments[arguments.length - 1]);
        for (Product product : this.productManager.getCollection()) {
            if (product.getId() == id) {
                try {
                    this.responseHandler.sendResponse(this.requestHandler.getKey(), "add");
                    Object request = null;
                    do {
                        request = this.requestHandler.readRequestCommand(this.responseHandler.getKey());
                    } while(this.requestHandler.readRequestCommand(this.responseHandler.getKey()) == null);
                    if(request instanceof Product) {
                        Product productResponse = (Product) request;
                        long idSave = product.getId();
                        this.productManager.getCollection().remove(product);
                        productResponse.setID(idSave);
                        this.productManager.addProdut(productResponse);
                        IDreceive iDreceive = new IDreceive(this.productManager);
                        iDreceive.updateID();
                        System.out.println("Продукт был успешно обновлён в коллекции");
                        this.responseHandler.sendResponse(this.requestHandler.getKey(), "Продукт был успешно обновлён в коллекции");
                    }
                    IdGenerator.updateCounter(this.productManager.getCollection());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

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
        return "update_id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public String toString() {
        return "update_id";
    }
}
