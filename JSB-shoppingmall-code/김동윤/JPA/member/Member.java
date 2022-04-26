package eci.server.ItemModule.entity.member;

import eci.server.ItemModule.entity.entitycommon.EntityDate;
import eci.server.ItemModule.entity.item.ItemManufacture;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Entity
@Getter
@Table(name="member")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 3
public class Member extends EntityDate { // 5

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCE3")
    @SequenceGenerator(name="SEQUENCE3", sequenceName="SEQUENCE3", allocationSize=1)
    @Column(name = "member_id")
    private Long id;


    @Column(nullable = false, length = 30, unique = true)
    private String email;

    private String password;

    @Column(nullable = false, length = 20)
    private String username;

    @Column(nullable = false, unique = true, length = 20)
    private String department;

    @Column(nullable = false, unique = true, length = 20)
    private String contact;

    @OneToMany(
            mappedBy = "member",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<MemberRole> roles;

    @OneToOne(
            mappedBy = "member",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            optional = false
    )
    @JoinColumn(name = "profile_image", nullable = false)
    private ProfileImage profileImage;

    public Member(
            String email,
            String password,
            String username,
            String department,
            String contact,
            List<Role> roles,
            ProfileImage profileImage
    ) {
        System.out.println("");
        this.email = email;
        this.password = password;
        this.username = username;
        this.department = department;
        this.contact = contact;
        this.roles =
                roles.stream().map(r -> new MemberRole(
                                this, r))
                        .collect(toSet());
        this.profileImage = profileImage;
        addProfileImages(profileImage);
    }

    public void updateDepartment(String department) {
        this.department = department;
    }

    /**
     * 멤버에 새로운 이미지 정보를 등록하는 메소드
     * 해당 Image에 this(Member)를 등록해줍니다.
     * cascade 옵션을 PERSIST로 설정해두었기 때문에,
     * Post가 저장되면서 Image도 함께 저장
     * @param added
     */
    private void addProfileImages(ProfileImage added) {
        added.initMember(this);
    }



}