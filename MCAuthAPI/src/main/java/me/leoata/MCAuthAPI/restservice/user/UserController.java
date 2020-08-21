package me.leoata.MCAuthAPI.restservice.user;

import me.leoata.MCAuthAPI.McAuthApiApplication;
import me.leoata.MCAuthAPI.restservice.IController;
import me.leoata.MCAuthAPI.restservice.exception.MalformedAttemptException;
import me.leoata.MCAuthAPI.restservice.user.exception.UserNotFoundException;
import me.leoata.MCAuthAPI.types.Server;
import me.leoata.MCAuthAPI.types.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController implements IController {

    @GetMapping("/user/{uuid}")
    public User user(@PathVariable String uuid, @RequestBody Server requested){
        if (requested == null || requested.getKey() == null || requested.getName() == null)
            throw new MalformedAttemptException();
        Server retrieved = McAuthApiApplication.getInstance().getAuthenticator().getStorage().getServer(requested.getName());
        if (!retrieved.getKey().equals(requested.getKey()))
            throw new UserNotFoundException();
        User user = McAuthApiApplication.getInstance().getAuthenticator().getStorage().getUser(uuid);
        if (user == null)
            throw new UserNotFoundException();
        return user;
    }
}
