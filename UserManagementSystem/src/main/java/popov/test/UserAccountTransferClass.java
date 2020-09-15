package popov.test;

import org.hibernate.annotations.CreationTimestamp;
import popov.test.validation.UniqueAccount;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

// create transfer class for bean validation - copy of Entity
@UniqueAccount // custom annotation for unique account check
public class UserAccountTransferClass {

    private Integer id;

    // each field that must be validated has special annotations
    @NotEmpty(message = "Field cannot be empty!")
    // regexp for password
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])[0-9a-zA-Z]{3,16}$",
            message = "Password must contain at least 1 digit, 1 letter \n - only latin letters allowed")
    @Size(min = 3, max = 16, message = "Password length must be from 3 to 16 characters")
    private String password;

    @NotNull(message = "User name is required")
    @Size(min = 3, max = 16, message = "User name length must be from 3 to 16 characters")
    private String userName;

    @NotNull(message = "First name is required")
    @Size(min = 1, max = 16, message = "First name length must be from 1 to 16 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only latin letters allowed!")
    private String firstName;

    @NotNull(message = "Last name is required")
    @Size(min = 1, max = 16, message = "Last name length must be from 1 to 16 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only latin letters allowed!")
    private String lastName;

    private String role;

    private boolean status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public UserAccountTransferClass() {
    }

    public UserAccountTransferClass
            (String password, String userName, String firstName,
             String lastName, String role, boolean status) {
        this.password = password;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
