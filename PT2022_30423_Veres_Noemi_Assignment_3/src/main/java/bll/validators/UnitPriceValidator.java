package bll.validators;

import model.Product;
/**
 * PhoneNumberValidator is responsible for validating the product quantity.
 * @Author: Veres Noemi
 * @Since: Apr 14, 2022
 */
public class UnitPriceValidator implements Validator<Product> {
    /**
     * Minimum unit price of a product.
     */
    public static final int MIN_VALUE = 0;

    /**
     * Validates the unitPrice field of the entity.
     * @param t is the object whose field is validated.
     * @return the validation result.
     */
    public void validate(Product t) {
        if (t.getUnitPrice() < MIN_VALUE) {
            throw new IllegalArgumentException("Unit price cannot be negative!");
        }
    }
}
