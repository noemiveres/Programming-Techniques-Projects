package bll;

import bll.validators.ProductQuantityValidator;
import bll.validators.UnitPriceValidator;
import bll.validators.Validator;
import dataAcess.dao.ProductDAO;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * ProductBLL is responsible for the business logic involving products.
 * @Author: Veres Noemi
 * @Since: Apr 14, 2022
 */
public class ProductBLL {
    private List<Validator<Product>> validators;
    private ProductDAO productDAO;

    public ProductBLL() {
        validators = new ArrayList<Validator<Product>>();
        validators.add(new ProductQuantityValidator());
        validators.add(new UnitPriceValidator());

        productDAO = new ProductDAO();
    }

    public Product findProductById(int id) {
        Product st = productDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The Product with id = " + id + " was not found!");
        }
        return st;
    }

    public ArrayList<Product> findAllProducts(){
        return (ArrayList<Product>) productDAO.findAll();
    }

    public void insertProduct(Product p){
        productDAO.insert(p);
    }

    public void updateProduct(Product p){
        productDAO.update(p);
    }

    public void deleteProduct(Product p){
        productDAO.delete(p);
    }

    public void updateProduct(int id, String name, String unitPrice, String quantity){
        Product product = productDAO.findById(id);
        if(!name.isEmpty()){
            product.setName(name);
        }
        if(!unitPrice.isEmpty()){
            product.setUnitPrice(Integer.parseInt(unitPrice));
        }
        if(!quantity.isEmpty()){
            product.setQuantity(Integer.parseInt(quantity));
        }
        updateProduct(product);
    }
}
