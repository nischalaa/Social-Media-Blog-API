package Service;
import DAO.MessageDAO;
import Model.Message;

import java.util.List;


public class MessageService {
    private MessageDAO messageDAO = new MessageDAO();

    public Message createMessage(Message message) {
        if(message.getMessage_text().isBlank() || message.getMessage_text().length() > 255) {
            return null;
        }
        if(!messageDAO.isValidUser(message.getPosted_by())) {
            return null;
        }
        return messageDAO.insertMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
    
    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    public Message deleteMessageById(int messageId) {
        Message message = messageDAO.getMessageById(messageId);
        if(message != null) {
            messageDAO.deleteMessage(messageId);
        }
        return message;
    }

    public Message updateMessage(int messageId, String newText) {
        if (newText.isBlank() || newText.length() > 255) {
            return null;
        }

        Message existingMessage = messageDAO.getMessageById(messageId);
        if(existingMessage != null) {
            existingMessage.setMessage_text(newText);
            messageDAO.updateMessage(existingMessage);
            return existingMessage;
        }
        return null;
    }

    public List<Message> getMessagesByAccountId(int accountId) {
        return messageDAO.getMessagesByAccountId(accountId);
    }
}
