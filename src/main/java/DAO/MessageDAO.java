package DAO;

import java.sql.*;

import Util.ConnectionUtil;
import Model.Account;
import Model.Message;

public class MessageDAO {
    
    public Message insertMessage(Message msg){
        Connection connection = ConnectionUtil.getConnection();

        try{
            // SQL
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";
            
            // insert values to prep statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, msg.getPosted_by());
            preparedStatement.setString(2, msg.getMessage_text());
            preparedStatement.setLong(3, msg.getTime_posted_epoch());

            // execute statement
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();

            // get and return Message created
            if(pkeyResultSet.next()){
                int generated_id = pkeyResultSet.getInt(1);
                return new Message(generated_id, msg.getPosted_by(), msg.getMessage_text(), msg.getTime_posted_epoch());
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
