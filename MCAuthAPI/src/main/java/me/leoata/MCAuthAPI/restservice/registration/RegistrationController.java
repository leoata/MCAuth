package me.leoata.MCAuthAPI.restservice.registration;

import me.leoata.MCAuthAPI.McAuthApiApplication;
import me.leoata.MCAuthAPI.restservice.IController;
import me.leoata.MCAuthAPI.restservice.exception.ForbiddenRequestException;
import me.leoata.MCAuthAPI.restservice.exception.MalformedAttemptException;
import me.leoata.MCAuthAPI.restservice.exception.UserDoesNotExistException;
import me.leoata.MCAuthAPI.restservice.registration.attempt.ServerRegisterAttempt;
import me.leoata.MCAuthAPI.restservice.registration.attempt.UserRegisterAttempt;
import me.leoata.MCAuthAPI.restservice.registration.exception.*;
import me.leoata.MCAuthAPI.types.RegisteredUser;
import me.leoata.MCAuthAPI.types.Server;
import me.leoata.MCAuthAPI.types.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController implements IController {

    @PostMapping("/register_user")
    public RegisteredUser registerUser(@RequestBody UserRegisterAttempt attempt){
        if (attempt.getKey() == null || attempt.getName() == null || attempt.getUuid() == null || attempt.getIp() == null)
            throw new MalformedAttemptException();
        Server server = McAuthApiApplication.getInstance().getAuthenticator().getStorage().getServer(attempt.getName());
        if (server == null || !server.getKey().equals(attempt.getKey()))
            throw new ForbiddenRequestException();
        if (McAuthApiApplication.getInstance().getAuthenticator().getStorage().getUser(attempt.getUuid()) != null)
            throw new UserAlreadyExistsException();
        User user = new User(attempt);
        McAuthApiApplication.getInstance().getAuthenticator().getStorage().storeUser(user);
        return new RegisteredUser(user);
    }

    /*@PostMapping("/register_server")
    public Server registerServer(@RequestBody ServerRegisterAttempt attempt){
        if (attempt.getKey() == null || attempt.getName() == null || attempt.getMasterKey() == null)
            throw new MalformedAttemptException();
        if (!attempt.getMasterKey().equals("vZghcwrMhF54Bx75cXr6BY79HdcpVKsybC7B4Wt77K3K6KAEAMSNrTdaBgs7"))
            throw new ForbiddenRequestException();
        if (McAuthApiApplication.getInstance().getAuthenticator().getStorage().getUser(attempt.getName()) != null)
            throw new ServerAlreadyExistsException();
        Server server = new Server(attempt);
        McAuthApiApplication.getInstance().getAuthenticator().getStorage().storeServer(server);
        return server;
    }

    @DeleteMapping("/delete_server")
    public Server deleteServer(@RequestBody ServerRegisterAttempt attempt){
        if (attempt.getKey() == null || attempt.getName() == null || attempt.getMasterKey() == null)
            throw new MalformedAttemptException();
        if (!attempt.getMasterKey().equals("vZghcwrMhF54Bx75cXr6BY79HdcpVKsybC7B4Wt77K3K6KAEAMSNrTdaBgs7"))
            throw new ForbiddenRequestException();
        Server server = new Server(attempt);
        if (McAuthApiApplication.getInstance().getAuthenticator().getStorage().getServer(attempt.getName()) == null)
            throw new ServerDoesNotExistException();
        McAuthApiApplication.getInstance().getAuthenticator().getStorage().deleteServer(server);
        return server;
    }*/

    @DeleteMapping("/delete_user")
    public User deleteUser(@RequestBody UserRegisterAttempt attempt){
        if (attempt.getKey() == null || attempt.getName() == null || attempt.getUuid() == null || attempt.getIp() == null)
            throw new MalformedAttemptException();
        Server server = McAuthApiApplication.getInstance().getAuthenticator().getStorage().getServer(attempt.getName());
        if (server == null || !server.getKey().equals(attempt.getKey()))
            throw new ForbiddenRequestException();
        User user = new User(attempt);
        if (McAuthApiApplication.getInstance().getAuthenticator().getStorage().getUser(attempt.getUuid()) == null)
            throw new UserDoesNotExistException();
        McAuthApiApplication.getInstance().getAuthenticator().getStorage().deleteUser(user);
        return user;
    }
}
