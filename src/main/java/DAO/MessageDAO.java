package DAO;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import Util.ConnectionUtil;
import Model.Message;

public class MessageDAO {
    
    /**
     * Adds message to DB
     * @param msg message to add to DB
     * @return Message if successful, else null
     */
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

    /**
     * Gets all messages from DB
     * @return List of all messages
     */
    public List<Message> getMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> result = new ArrayList<>();

        try{
            // SQL
            String sql = "SELECT * FROM Message";
            
            // Create statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // execute statement
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                result.add(new Message( rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch") ));
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * Gets message of given ID
     * @param msg_id ID of desired message
     * @return Message matching ID, or null if none present
     */
    public Message getMessageById(int msg_id){
        Connection connection = ConnectionUtil.getConnection();

        try{
            // SQL
            String sql = "SELECT * FROM Message WHERE message_id=?";
            
            // Insert values into prep statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, msg_id);

            // execute statement
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){
                return new Message( rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch") );
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Deletes message of given ID
     * @param msg_id ID of desired message to delete
     * @return true if deleted, else false
     */
    public boolean deleteMessageById(int msg_id){
        Connection connection = ConnectionUtil.getConnection();

        try{
            // SQL
            String sql = "DELETE FROM Message WHERE message_id=?";
            
            // Insert values into prep statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, msg_id);

            // execute statement
            int rowsEffected = preparedStatement.executeUpdate();

            // Return true if row deleted
            return rowsEffected > 0;

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Updates message of given ID to given text
     * @param msg_id ID of message to update
     * @param msg_body new text for message
     * @return true if update success, else false
     */
    public boolean updateMessageById(int msg_id, String msg_body){
        Connection connection = ConnectionUtil.getConnection();

        try{
            // SQL
            String sql = "UPDATE Message SET message_text=? WHERE message_id=?";
            
            // Insert values into prep statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, msg_body);
            preparedStatement.setInt(2, msg_id);

            // execute statement
            int rowsEffected = preparedStatement.executeUpdate();

            // Return true if row deleted
            return rowsEffected > 0;

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}
