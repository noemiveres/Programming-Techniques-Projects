package bll.validators;

import model.Client;

import java.util.regex.Pattern;

/**
 * PhoneNumberValidator is responsible for validating the phone number field of a client.
 * @Author: Veres Noemi
 * @Since: Apr 14, 2022
 */
public class PhoneNumberValidator implements Validator<Client> {
    private static final String PHONE_PATTERN = "[\\d]{10}";

    public void validate(Client t) {
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        if (!pattern.matcher(t.getPhoneNumber()).matches()) {
            throw new IllegalArgumentException("Phone number is not valid!");
        }
    }

}
