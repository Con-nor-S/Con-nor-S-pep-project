package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    /**
     * Creates a new MessageService with new MessageDAO
     */
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    /**
     * Creates a new MessageService with given MessageDAO
     * For dependancy tests
     * @param messageDAO
     */
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    /**
     * Inserts message into DB
     * @param msg msg to insert
     * @return persisted Message inserted if successful, else null 
     */
    public Message createMessage(Message msg){
        if(msg.getMessage_text().isEmpty() || msg.getMessage_text().length() > 255)
            return null;
        return messageDAO.insertMessage(msg);
    }

    /**
     * Gets all messages from the DB
     * @return a list of all messages
     */
    public List<Message> getMessages(){
        return messageDAO.getMessages();
    }

    /**
     * Gets message matching given ID
     * @param msg_id ID of message to match
     * @return a message with given ID, or null if none found
     */
    public Message getMessageById(int msg_id){
        return messageDAO.getMessageById(msg_id);
    }

    /**
     * Deletes message matching given ID
     * @param msg_id ID of message to delete
     * @return deleted message if delete success, else null
     */
    public Message deleteMessageById(int msg_id){
        // Get target message before delete
        Message msg = getMessageById(msg_id);

        // if delete successful, return target message
        if(messageDAO.deleteMessageById(msg_id))
            return msg;
        return null;
    }

    /**
     * Updates message matching given ID
     * @param msg_id ID of message to update
     * @param msg_body new text for message
     * @return updated message on success, else null
     */
    public Message updateMessageById(int msg_id, String msg_body){
        // Check for proper msg_body
        if(msg_body.isEmpty() || msg_body.length() > 255)
            return null;

        // if update successful, return new message
        if(messageDAO.updateMessageById(msg_id, msg_body))
            return getMessageById(msg_id);
            
        return null;
    }

    /**
     * Gets messages assosiated with given account ID
     * @param acc_id ID to find messages for
     * @return List of messages posted by acc_id
     */
    public List<Message> getMessagesByAccount(int acc_id){
        return messageDAO.getMessagesByAccount(acc_id);
    }
}
