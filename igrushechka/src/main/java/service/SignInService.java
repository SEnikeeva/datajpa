package service;


import context.Component;
import dto.AuthorizationDto;


import java.util.Optional;

public interface SignInService extends Component {
    AuthorizationDto signIn(String login, String password);
}
