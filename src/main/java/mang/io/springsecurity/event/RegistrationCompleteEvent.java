package mang.io.springsecurity.event;

import lombok.Getter;
import lombok.Setter;
import mang.io.springsecurity.entity.User;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private User user;
    private String applicationUrl;

    public RegistrationCompleteEvent(User user, String applicationUrl){
        super(user);
    }

}