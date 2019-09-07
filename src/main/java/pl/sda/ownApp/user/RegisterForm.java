package pl.sda.ownApp.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.sda.ownApp.config.SecurityBeansConfig;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Getter
@Setter
@ToString(exclude = "password")
public class RegisterForm {
    //    @NotNull
    @NotEmpty(message = "Pole nie może być puste.")
//    @NotBlank
//    @Pattern(regexp = ".+@.+", message = "Pole musi spełniać .+@.+")
    @Email
    private String username;

    @Size(min = 8,max = 30,message = "Hasło musi mieć min 8 znaków, max 30 znaków")
    @NotEmpty
    private String password;
    @NotBlank
    @Size(max = 50,message = "Nick nie może być pustym polem i być dłuższy niż 50 znaków")
    private String nickname;
}
