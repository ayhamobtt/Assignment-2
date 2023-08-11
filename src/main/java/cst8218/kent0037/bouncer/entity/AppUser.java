package cst8218.kent0037.bouncer.entity;

import java.util.HashMap;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "appuser")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "userid")
    @NotNull
    private String userid;
    
    @Column(name = "password")
    @NotNull
    private String password;

    @NotNull
    @Column(name = "groupname")
    private String groupname;

    public AppUser() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return "";
    }

public void setPassword(String password) {
    if (password.length() == 0) {
        return;
    }
    // Initialize a PasswordHash object which will generate password hashes
    Instance<? extends PasswordHash> instance = CDI.current().select(PasswordHash.class);
    PasswordHash passwordHash = instance.get();
    passwordHash.initialize(new HashMap<>());  // You can pass additional parameters to the initialize() method if needed
    // Check if the provided password is empty
    if (password.isEmpty()) {
        // Password should not be changed
        return;
    }

    // Generate a hashed/salted password entry for the given password
    this.password = passwordHash.generate(password.toCharArray());
}

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
}