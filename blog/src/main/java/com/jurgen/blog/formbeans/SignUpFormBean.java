package com.jurgen.blog.formbeans;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class SignUpFormBean {

    @Size(min = 3, max = 30, message = "-length of username must be from 3 to 30 characters")
    private String username;

    @Size(min = 6, max = 50, message = "-length of email must be from 6 to 50 characters")
    @Email(message = "-incorrect email")
    private String email;

    @NotEmpty(message = "-password can't be empty")
    private String password;

    private String confirmPassword;

    private boolean passValid;

    public SignUpFormBean() {
    }

    @AssertTrue(message = "-passwords must be equal")
    public boolean isPassValid() {
        if (password != null) {
            return password.equals(confirmPassword);
        }
        return false;
    }

    public void setPassValid(boolean passValid) {
        this.passValid = passValid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
