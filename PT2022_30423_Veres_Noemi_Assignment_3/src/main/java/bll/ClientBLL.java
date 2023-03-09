package bll;

import bll.validators.EmailValidator;
import bll.validators.Validator;
import dataAcess.dao.ClientDAO;
import model.Client;
import bll.validators.PhoneNumberValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * ClientBLL is responsible for the business logic involving the clients.
 * @Author: Veres Noemi
 * @Since: Apr 14, 2022
 */
public class ClientBLL {
    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;

    public ClientBLL() {
        validators = new ArrayList<Validator<Client>>();
        validators.add(new PhoneNumberValidator());
        validators.add(new EmailValidator());

        clientDAO = new ClientDAO();
    }

    public void updateClient(int id, String userName, String firstName, String lastName, String address,
                             String phoneNumber, String emailAddress){
        Client client = clientDAO.findById(id);
        if(!userName.isEmpty()){
            client.setUserName(userName);
        }
        if(!firstName.isEmpty()){
            client.setFirstName(firstName);
        }
        if(!lastName.isEmpty()){
            client.setLastName(lastName);
        }
        if(!address.isEmpty()){
            client.setAddress(address);
        }
        if(!phoneNumber.isEmpty()){
            client.setPhoneNumber(phoneNumber);
        }
        if(!emailAddress.isEmpty()){
            client.setEmailAddress(emailAddress);
        }
        updateClient(client);
    }

    public Client findClientById(int id) {
        Client st = clientDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The Client with id = " + id + " was not found!");
        }
        return st;
    }

    public ArrayList<Client> findAllClients(){
        return (ArrayList<Client>) clientDAO.findAll();
    }

    public void insertClient(Client c){
        clientDAO.insert(c);
    }

    public void updateClient(Client c){
        clientDAO.update(c);
    }

    public void deleteClient(Client c){
        clientDAO.delete(c);
    }

    public String[] getColumns(){ String[] columns = clientDAO.getColumnsList();
        return columns;
    }
}
