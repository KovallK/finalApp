package pl.sda.ownApp.user;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserContextService {
    public String getLoggedUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // zapisanie obiektu autenykacji z obiektu
        if(authentication instanceof AnonymousAuthenticationToken){
            return null;
        }
        return authentication.getName();
    }
}
