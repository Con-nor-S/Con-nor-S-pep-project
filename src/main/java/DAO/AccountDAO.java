package DAO;

import java.sql.*;

import Util.ConnectionUtil;
import Model.Account;

public class AccountDAO{

    /** 
     * Adds account to DB
     * @param acc Account to be added
     * @return Account added if successful, else null
     */ 
    public Account insertAccount(Account acc){
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            // SQL
            String sql = "INSERT INTO Account (username, password) VALUES (?,?)";
            
            // insert values to prep statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, acc.getUsername());
            preparedStatement.setString(2, acc.getPassword());

            // execute statement
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();

            // get and return Account created
            if(pkeyResultSet.next()){
                int generated_id = pkeyResultSet.getInt(1);
                return new Account(generated_id, acc.getUsername(), acc.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return null;
    }

    /** 
     * Gets an account of certain username and password
     * @param acc Account to be found
     * @return Account if found, else null
     */ 
    public Account findAccount(Account acc){
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            // SQL
            String sql = "SELECT * FROM Account WHERE username=? AND password=?";
            
            // insert values to prep statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, acc.getUsername());
            preparedStatement.setString(2, acc.getPassword());

            // execute query
            ResultSet rs = preparedStatement.executeQuery();

            // get and return Account found 
            if(rs.next()){
                return new Account(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return null;
    }

    /**
     * Gets an account of given ID
     * @param acc_id ID of account to find
     * @return Account matching ID if found, else null
     */
    public Account findById(int acc_id){
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            // SQL
            String sql = "SELECT * FROM Account WHERE account_id=?";
            
            // insert values to prep statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, acc_id);

            // execute query
            ResultSet rs = preparedStatement.executeQuery();

            // get and return Account found 
            if(rs.next()){
                return new Account(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return null;
    }

}