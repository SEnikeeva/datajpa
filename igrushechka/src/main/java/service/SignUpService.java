package service;

import context.Component;
import dto.AuthorizationDto;

import java.util.Optional;

public interface SignUpService extends Component {
    void signUp(String login, String password);
}
