package pl.sda.ownApp.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.ownApp.user.*;

import java.util.Optional;

@Service
public class UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public void registerUser(RegisterForm registerForm) {

        Optional<User> user = userRepository.findByUsername(registerForm.getUsername());
        if (user.isPresent()) {

            throw new UserExistException();
        }
        User newUser = new User();
        newUser.setUsername(registerForm.getUsername());
        newUser.setPasswordHash(passwordEncoder.encode(registerForm.getPassword()));
        newUser.setNickname(registerForm.getNickname());

        String roleName = "ROLE_USER";
        String roleAdmin = "ROLE_ADMIN";
        Optional<Role> role = roleRepository.findByRoleName(roleName); //przypisanie wyniku pobrania roli z bazy danych i przypisanie do optionala
        if (role.isPresent()) {
            newUser.addRole(role.get()); // przypisanie roli do uzytkownika.Rola pobrana z optionala.
        }else {
            Role newRole = new Role();
            newRole.setRoleName(roleName);
            roleRepository.save(newRole);
            newUser.addRole(newRole);
        }
        userRepository.save(newUser);
    }
}
