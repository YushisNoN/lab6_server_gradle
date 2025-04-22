package commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import commands.exceptions.WrongArgumentsAmountException;
import managers.ProductManager;
import models.IncorrectStringValueException;
import models.Product;


public class Show extends Command {
    private ProductManager<Product> productManager;

    public Show(ProductManager<Product> manager) {
        super();
        this.productManager = manager;
    }

    @Override
    public String toString() {
        return "show";
    }

    @Override
    public void execute() throws WrongArgumentsAmountException {
        if (this.productManager.getCollection().isEmpty()) {
            System.out.println("коллекция пустая :(");
            return;
        }
        for (Product product : this.productManager.getCollection()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                mapper.enable(SerializationFeature.INDENT_OUTPUT); // Красивый JSON с отступами
                String json = mapper.writeValueAsString(product);
                System.out.println(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void execute(String[] arguments) throws WrongArgumentsAmountException, IncorrectStringValueException {
        throw new WrongArgumentsAmountException();
    }

    @Override
    public String getDescription() {
        return "show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

}
