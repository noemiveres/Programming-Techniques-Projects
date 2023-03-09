package bll.validators;

import model.Product;
/**
 * PhoneNumberValidator is responsible for validating the product quantity.
 * @Author: Veres Noemi
 * @Since: Apr 14, 2022
 */
public class ProductQuantityValidator implements Validator<Product> {
    /**
     * Minimum stock quantity of a product.
     */
    public static final int MIN_VALUE = 0;

    /**
     * Validates the quantity field of the entity.
     *
     * @param t is the object whose field is validated.
     * @return the validation result.
     */
    public void validate(Product t) {
        if (t.getQuantity() < MIN_VALUE) {
            throw new IllegalArgumentException("Product quantity cannot be negative!");
        }
    }

}
