package mang.io.springsecurity.controller;

import lombok.extern.slf4j.Slf4j;
import mang.io.springsecurity.entity.User;
import mang.io.springsecurity.entity.VerificationToken;
import mang.io.springsecurity.event.RegistrationCompleteEvent;
import mang.io.springsecurity.model.UserModel;
import mang.io.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Slf4j
@RestController

public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request){
        User user = userService.registerUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(
                user,
                applicationUrl(request)
        ));
        return "success";
    }

        @GetMapping("/verifyRegistration")
        public String verifyRegistration(@RequestParam("token") String token){
            String result = userService.validateVerificationToken(token);

            if(result.equalsIgnoreCase("valid")){
                return "User verified Successfully";
            }
            return "Bad User";
        }

        @GetMapping("resendVerifyToken")
        public String resendVerificationToken(@RequestParam("token") String oldToken,
                                              HttpServletRequest request){
            VerificationToken verificationToken
                    = userService.generateNewVerificationToken(oldToken);
            User user = verificationToken.getUser();
            resendVerificationTokenMail(user, applicationUrl(request), verificationToken);
            return "Verification Link sent";
        }

    private void resendVerificationTokenMail(User user, String applicationUrl,
                                             VerificationToken verificationToken) {
        String url =
                applicationUrl
                    + "/verifyRegistration?token="
                +verificationToken.getToken();
        // send VerificationEmail
        log.info("Click the link to verify your account: {}", url);
    }

    private String applicationUrl(HttpServletRequest request){
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();

    }
}
