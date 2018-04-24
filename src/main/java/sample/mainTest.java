package sample;

import sample.app.Entities.Clients;
import sample.app.Transactions.ClientDao.clientDao;

/**
 * Created by ahmed mar3y on 24/04/2018.
 */
public class mainTest {
    public static void main(String[] args) {
        Clients clients = clientDao.SaveClients(new Clients("ahmed", "123", "Asdress"));


    }


}
