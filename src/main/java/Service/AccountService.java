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
     * @param acc account to be created
     * @return if success persisted Account greated, else null  
     */
    public Account createAccount(Account acc){
        // Checks validity of credentials
        if(acc.getUsername().isEmpty() || acc.getPassword().length() < 4)
            return null;
        return accountDAO.insertAccount(acc);
    }

    /**
     * Logs in an account
     * @param acc account to be logged into
     * @return Account matching username and password, or null if none exists
     */
    public Account loginAccount(Account acc){
        return accountDAO.findAccount(acc);
    }

}
