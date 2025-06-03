package Service;
import Model.Account;

/*import java.io.IOException;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;*/


import DAO.AccountDAO;


public class AccountService {
   /*  public HttpResponse<String> login(String request) {
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }*/

    private AccountDAO accountDAO = new AccountDAO();

    public Account register(Account account) {
       
        if(account.getUsername().isBlank() || account.getPassword().length() < 4) {
            return null;
        }
        if(accountDAO.getAccountByUsername(account.getUsername()) != null) {
            return null;
        }
        return accountDAO.insertAccount(account);
    }
    public Account login(Account account) {
        return accountDAO.getAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
    }
}
