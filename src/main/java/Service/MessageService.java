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
     * @return deleted message delete success, else null
     */
    public Message deleteMessageById(int msg_id){
        // Get target message
        Message msg = getMessageById(msg_id);

        // if delete successful, return target message
        if(messageDAO.deleteMessageById(msg_id))
            return msg;
        return null;
    }
}
