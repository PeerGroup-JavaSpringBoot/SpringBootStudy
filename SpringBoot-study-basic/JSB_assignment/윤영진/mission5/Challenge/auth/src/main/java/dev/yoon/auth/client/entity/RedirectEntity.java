package dev.yoon.auth.client.entity;

import dev.yoon.auth.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "client_redirect_uris")
public class RedirectEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
            targetEntity = OAuthClientEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "client_id", nullable = false)
    private OAuthClientEntity client;

    @Column
    private String redirectUri;

    public RedirectEntity() {
    }

    public RedirectEntity(OAuthClientEntity client, String redirectUri) {
        this.client = client;
        this.redirectUri = redirectUri;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OAuthClientEntity getClient() {
        return client;
    }

    public void setClient(OAuthClientEntity client) {
        this.client = client;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    @Override
    public String toString() {
        return "RedirectEntity{" +
                "redirectUri='" + redirectUri + '\'' +
                '}';
    }
}
