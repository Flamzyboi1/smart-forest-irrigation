package lv.venta.forest.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

@Entity
@Table(name="user_change_request")
public class UserChangeRequest {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional=false) @JoinColumn(name="user_id")
    private AppUser user;
    private String requestedUsername;
    private String requestedFullName;
    private String requestedEmail;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String requestedPassword;
    @Column(nullable=false)
    private String status="PENDING";
    @Column(nullable=false)
    private LocalDateTime requestedAt=LocalDateTime.now();
    private LocalDateTime reviewedAt;
    private String reviewedBy;

    public Long getId(){return id;} public void setId(Long v){id=v;}
    public AppUser getUser(){return user;} public void setUser(AppUser v){user=v;}
    public String getRequestedUsername(){return requestedUsername;} public void setRequestedUsername(String v){requestedUsername=v;}
    public String getRequestedFullName(){return requestedFullName;} public void setRequestedFullName(String v){requestedFullName=v;}
    public String getRequestedEmail(){return requestedEmail;} public void setRequestedEmail(String v){requestedEmail=v;}
    public String getRequestedPassword(){return requestedPassword;} public void setRequestedPassword(String v){requestedPassword=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
    public LocalDateTime getRequestedAt(){return requestedAt;} public void setRequestedAt(LocalDateTime v){requestedAt=v;}
    public LocalDateTime getReviewedAt(){return reviewedAt;} public void setReviewedAt(LocalDateTime v){reviewedAt=v;}
    public String getReviewedBy(){return reviewedBy;} public void setReviewedBy(String v){reviewedBy=v;}
}
