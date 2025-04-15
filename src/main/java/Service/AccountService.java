package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    /**
     * Creates a new AccountService with new AccountDAO
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    /**
     * Creates a new AccountService with given AccountDAO
     * For dependancy tests
     * @param accountDAO
     */
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    /**
     * Creates an account
     * @param username username of account to be added
     * @param password password of account to be added
     * @return if success persisted Account greated, else null  
     */
    public Account createAccount(String username, String password){
        return accountDAO.insertAccount(new Account(username, password));
    }

    /**
     * Logs in an account
     * @param username username of account to log in
     * @param password password of account to log in
     * @return Account matching username and password, or null if none exists
     */
    public Account loginAccount(String username, String password){
        return accountDAO.findAccount(new Account(username, password));
    }

}
