package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationDto {

    private String username;
    private String password;

    public static AuthorizationDto from(User user) {
        return new AuthorizationDto(user.getLogin(), user.getPassword());
    }

}
