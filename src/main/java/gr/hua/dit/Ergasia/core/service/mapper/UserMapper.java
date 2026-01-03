package gr.hua.dit.Ergasia.core.service.mapper;

import gr.hua.dit.Ergasia.core.model.User;
import gr.hua.dit.Ergasia.web.dto.UserView;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserView toUserView(User user) {
        if (user == null) return null;

        return new UserView(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }
}
