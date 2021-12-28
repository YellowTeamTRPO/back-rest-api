package trpo.yellow.restapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import trpo.yellow.restapi.services.LoginService;

import java.util.concurrent.Callable;

@RestController()
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("login")
    public Callable<ResponseEntity> login(@RequestBody String login, @RequestBody String password) {
        return () -> {
            loginService.loginUser(
                    login,
                    password
            );
            return ResponseEntity.ok().build();
        };
    }


}
