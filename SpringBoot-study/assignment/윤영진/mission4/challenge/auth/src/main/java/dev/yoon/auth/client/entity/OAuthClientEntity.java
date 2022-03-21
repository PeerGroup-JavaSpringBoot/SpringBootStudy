package dev.yoon.auth.client.entity;

import dev.yoon.auth.entity.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "client_registered")
public class OAuthClientEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String uid;

    @Column
    private String clientId;

    @Column
    private String clientSecret;

    @OneToMany(
            targetEntity = RedirectEntity.class,
            fetch = FetchType.EAGER,
            mappedBy = "client",
            cascade = CascadeType.ALL
    )
    private List<RedirectEntity> redirectUris;

    public OAuthClientEntity() {
    }

    public OAuthClientEntity(String uid, String clientId, String clientSecret, List<RedirectEntity> redirectUris) {
        this.uid = uid;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUris = redirectUris;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public List<RedirectEntity> getRedirectUris() {
        return redirectUris;
    }

    public void setRedirectUris(List<RedirectEntity> redirectUris) {
        this.redirectUris = redirectUris;
    }

    @Override
    public String toString() {
        return "OAuthClientEntity{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", redirectUris=" + redirectUris +
                '}';
    }
}
