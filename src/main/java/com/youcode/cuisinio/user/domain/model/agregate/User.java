package com.youcode.cuisinio.user.domain.model.agregate;

import com.youcode.cuisinio.user.domain.model.valueObject.Email;
import com.youcode.cuisinio.user.domain.model.valueObject.PasswordHash;
import com.youcode.cuisinio.user.domain.model.valueObject.UserId;
import com.youcode.cuisinio.user.domain.model.valueObject.UserProfile;
import org.springframework.data.annotation.Id;

public class User {
    @Id
    private final UserId id;
    private final Email email;
    private PasswordHash passwordHash;
    private UserProfile profile;


    public User(UserId id, Email email, PasswordHash passwordHash, UserProfile profile) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.profile = profile;
    }

    public void updateProfile(UserProfile newProfile) {
        this.profile = newProfile;
    }
    public void updatePassword(PasswordHash newHash) {
        this.passwordHash = newHash;
    }

    public UserId getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public PasswordHash getPasswordHash() {
        return passwordHash;
    }
}