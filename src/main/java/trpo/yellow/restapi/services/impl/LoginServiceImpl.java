package trpo.yellow.restapi.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import trpo.yellow.restapi.services.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

    private final RestTemplate restTemplate;
    private final String loginServiceURL;

    @Autowired
    public LoginServiceImpl(
            RestTemplate restTemplate,
            @Value("${app.loginService.url}") String loginServiceURL) {
        this.restTemplate = restTemplate;
        this.loginServiceURL = loginServiceURL;
    }

    @Override
    public void loginUser(String login, String password) {
        try {
            restTemplate.getForObject(
                    loginServiceURL,
                    Object.class
            );

        } catch (HttpStatusCodeException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Ошибка авторизации пользователя"
            );
        } catch (ResourceAccessException exception) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Сервис авторизации пользователя недоступен"
            );
        }
    }
}
