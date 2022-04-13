package dev.yoon.challenge_community.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CookieMsg {

    @Id
    private String id;


}
