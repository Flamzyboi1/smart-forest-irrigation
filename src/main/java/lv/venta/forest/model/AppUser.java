package lv.venta.forest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "app_user")
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(min=3,max=30)
    @Pattern(regexp="^[A-Za-z0-9._-]+$", message="Username may contain letters, numbers, dot, underscore and hyphen only")
    @Column(unique=true, nullable=false)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min=8,max=100)
    @Column(nullable=false)
    private String password;

    @NotBlank @Size(min=2,max=100)
    @Column(nullable=false)
    private String fullName;

    @NotBlank @Email @Size(max=120)
    @Column(nullable=false)
    private String email;

    @NotBlank @Pattern(regexp="^(USER|ADMIN|SUPERADMIN)$")
    @Column(nullable=false)
    private String role;

    @Column(nullable=false)
    private boolean active=true;

    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getUsername(){return username;} public void setUsername(String v){username=v;}
    public String getPassword(){return password;} public void setPassword(String v){password=v;}
    public String getFullName(){return fullName;} public void setFullName(String v){fullName=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getRole(){return role;} public void setRole(String v){role=v;}
    public boolean isActive(){return active;} public void setActive(boolean v){active=v;}
}
