package dev.aquashdw.community.entity;

import dev.aquashdw.community.controller.dto.AreaDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "area")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AreaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "region_1")
    private String regionMajor;
    @Column(name = "region_2")
    private String regionMinor;
    @Column(name = "region_3")
    private String regionPatch;

    private Double latitude;
    private Double longitude;




}
