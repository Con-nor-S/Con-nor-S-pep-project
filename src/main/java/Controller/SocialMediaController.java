package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/login", this::loginHandler);
        app.post("/register", this::registerHandler);
        app.post("/messages", this::messagesPostHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /**
     * login endpoint handler
     * @param ctx Javalin Context obj
     * @throws JsonProcessingException If cant convert request body JSON to Account obj
     */
    private void loginHandler(Context ctx) throws JsonProcessingException{
        // Get credentials from request body
        ObjectMapper om = new ObjectMapper();
        Account acc = om.readValue(ctx.body(), Account.class);
        
        // Log in attempt
        Account loggedInAccount = accountService.loginAccount(acc);
        
        // Check result
        if(loggedInAccount != null){
            ctx.json(loggedInAccount);
        }
        else {
            ctx.status(401); // unauthorized
        }
    }

    /**
     * register endpoint handler
     * @param ctx Javalin Context obj
     * @throws JsonProcessingException If cant convert request body JSON to Account obj
     */
    private void registerHandler(Context ctx) throws JsonProcessingException{
        // Get credentials from request body
        ObjectMapper om = new ObjectMapper();
        Account acc = om.readValue(ctx.body(), Account.class);
        
        // Register attempt
        Account createdAccount = accountService.createAccount(acc);
        
        // Check result
        if(createdAccount != null){
            ctx.json(createdAccount);
        }
        else {
            ctx.status(400); // client error
        }
    }

    private void messagesPostHandler(Context ctx) throws JsonProcessingException{
        // Get message from request body
        ObjectMapper om = new ObjectMapper();
        Message msg = om.readValue(ctx.body(), Message.class);

        // Check poster is a real account
        if(accountService.findById(msg.getPosted_by()) != null){
            // Create message attempt
            Message createdMessage = messageService.createMessage(msg);
            
            // Check result
            if(createdMessage != null){
                ctx.json(createdMessage);
            }
            else {
                ctx.status(400); // client error 
            }
        } else {
            ctx.status(400); // client error 
        }
    }


}