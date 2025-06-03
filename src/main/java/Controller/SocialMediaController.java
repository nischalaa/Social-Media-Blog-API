package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import java.util.List;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

public class SocialMediaController {
    


    private  AccountService accountService = new AccountService();
    private  MessageService messageService = new MessageService();
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::handleRegister);
        app.post("/login", this::handleLogin);
        app.post("/messages", this::handleCreateMessage);
        app.get("/messages", this::handleGetAllMessages);
        app.get("/messages/{message_id}", this::handleGetMessagesById);
        app.delete("/messages/{message_id}", this::handleDeleteMessageById);
        app.patch("/messages/{message_id}", this::handleUpdateMessageById);
        app.get("/accounts/{account_id}/messages", this::handleGetMessagesByUser);
        
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }
    private void handleRegister(Context context) {
        Account account = context.bodyAsClass(Account.class);
        Account created = accountService.register(account);
        if(created != null) {
            context.json(created);
            context.status(200);
        } 
        else {
            context.status(400);
        }
    }

    private void handleLogin(Context context) {
        Account account = context.bodyAsClass(Account.class);
        Account result = accountService.login(account);
        if(result != null) {
            context.json(result);
            context.status(200);
        }
        else {
            context.status(401);
        } 
    }

    private void handleCreateMessage(Context context) {
        Message message = context.bodyAsClass(Message.class);
        Message created = messageService.createMessage(message);
        if(created != null) {
            context.json(created);
            context.status(200);
        }
        else {
            context.status(400);
        }
    }

    private void handleGetAllMessages(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    private void handleGetMessagesById(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if(message != null) {
            context.json(message);
            context.status(200);
        }
        else {
            context.status(200).json("");
        }
    }
    
    private void handleDeleteMessageById(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message deleted = messageService.deleteMessageById(messageId);
        if (deleted != null) {
            context.json(deleted);
            context.status(200);
        }else {            
            context.status(200).json("");
        }
        
    }

    private void handleUpdateMessageById(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        String newData = context.bodyAsClass(Message.class).getMessage_text();;
        Message updated = messageService.updateMessage(messageId, newData);
        if(updated != null) {
            context.json(updated);
            context.status(200);
        }
        else {
            context.status(400).json("");
        }
    }

    private void handleGetMessagesByUser(Context context) {
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByAccountId(accountId);
        context.json(messages);
    }
}