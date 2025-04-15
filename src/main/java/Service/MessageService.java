package Service;

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
}
