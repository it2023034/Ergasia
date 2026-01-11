package gr.hua.dit.Ergasia.web.controller;

import gr.hua.dit.Ergasia.core.model.User;
import gr.hua.dit.Ergasia.core.service.UserService;
import gr.hua.dit.Ergasia.core.service.mapper.UserMapper;
import gr.hua.dit.Ergasia.web.dto.UserView;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/me")
    public UserView me(Authentication auth) {
        User user = userService.getUserProfile(auth.getName());
        return userMapper.toUserView(user);
    }
}
