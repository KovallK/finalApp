package pl.sda.ownApp.user;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Role {

    public Long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setId(Long id){
        this.id=id;
    }
    public void setRoleName(String roleName){
        this.roleName=roleName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;


    private String roleName;

    @ManyToMany
    private Set<User> users;

    public void addUser(User user) {
        if (users == null) {
            users = new HashSet<>();
        }
        users.add(user);
    }
}


