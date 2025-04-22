package utils;

import managers.ProductManager;
import models.Product;

public class IDreceive {
    private ProductManager<Product> productManager;

    public IDreceive(ProductManager<Product> productManager) {
        this.productManager = productManager;
    }

    public void updateID() {
        long id = 1;
        for(Product product: productManager.getCollection()) {
            product.setID(id);
            id++;
        }
    }
}
