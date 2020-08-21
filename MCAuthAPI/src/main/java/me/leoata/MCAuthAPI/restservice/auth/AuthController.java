package me.leoata.MCAuthAPI.restservice.auth;

import me.leoata.MCAuthAPI.McAuthApiApplication;
import me.leoata.MCAuthAPI.restservice.IController;
import me.leoata.MCAuthAPI.restservice.auth.attempt.AuthAttempt;
import me.leoata.MCAuthAPI.restservice.auth.exception.AuthenticationFailureException;
import me.leoata.MCAuthAPI.restservice.exception.MalformedAttemptException;
import me.leoata.MCAuthAPI.restservice.exception.UserDoesNotExistException;
import me.leoata.MCAuthAPI.types.User;
import me.leoata.MCAuthAPI.util.TwoFAUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthController implements IController {
    @PostMapping("/auth")
    public void authenticate(@RequestBody AuthAttempt attempt, HttpServletRequest request) {
        if (attempt.getAuth() == null || attempt.getIp() == null || attempt.getUuid() == null)
            throw new MalformedAttemptException();
        McAuthApiApplication.getInstance().getLogger().info("------Request Start------");

        User user = McAuthApiApplication.getInstance().getAuthenticator().getStorage().getUser(attempt.getUuid());
        if (user == null)
            throw new UserDoesNotExistException();
        String expected = TwoFAUtil.getTOTPCode(user.getTwoFaSecret());
        if (!expected.equals(attempt.getAuth())) {
            McAuthApiApplication.getInstance().getLogger().info("Someone tried to authenticate " + attempt.getUuid() + " but failed (Given: " +
                    attempt.getAuth() + ", expected: " + expected +")");
            McAuthApiApplication.getInstance().getLogger().info("------Request End------");
            throw new AuthenticationFailureException();
        }
        McAuthApiApplication.getInstance().getAuthenticator().getStorage().newUserLogin(user, request.getRemoteAddr());
        McAuthApiApplication.getInstance().getLogger().info("------Request End------");

    }
}
