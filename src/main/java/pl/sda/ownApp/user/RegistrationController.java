package pl.sda.ownApp.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.ownApp.service.UserExistException;
import pl.sda.ownApp.service.UserService;


import javax.validation.Valid;

@Slf4j
@Controller
//@RequiredArgsConstructor
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        RegisterForm registerForm = new RegisterForm();
        registerForm.setUsername("");
        model.addAttribute("registerForm", registerForm);

        return "user/registerForm";
    }

    @PostMapping("/register")
    public String handleRegisterForm(@ModelAttribute @Valid RegisterForm registerForm,
                                     BindingResult bindingResult,
                                     Model model) throws RuntimeException {
        System.out.println(registerForm);

        if (bindingResult.hasErrors()) {
//            model.addAttribute("registerForm", registerForm);
            return "user/registerForm";
        }

        try {
            userService.registerUser(registerForm);
        } catch (UserExistException e) {
            bindingResult.rejectValue("username", "username.exists", "Użytkownik o takim mailu jest już zarejestrowany w bazie");
            return "user/registerForm";
        }
        return "redirect:/thank-you-for-registering";

    }

    @GetMapping("/thank-you-for-registering")
    public String showRegisterThankYouPage() {
        return "user/registerFormSuccess";
    }
}

