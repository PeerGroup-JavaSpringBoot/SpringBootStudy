package dev.aquashdw.community.entity;

import dev.aquashdw.community.controller.dto.AreaDto;

import javax.persistence.*;

@Entity
@Table(name = "area")
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
    private Double distance;
    public AreaEntity() {
    }

    public AreaEntity(Long id, String regionMajor, String regionMinor, String regionPatch, Double latitude, Double longitude) {
        this.id = id;
        this.regionMajor = regionMajor;
        this.regionMinor = regionMinor;
        this.regionPatch = regionPatch;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public AreaEntity(Long id, String regionMajor, String regionMinor, String regionPatch, Double latitude, Double longitude, Double distance) {
        this.id = id;
        this.regionMajor = regionMajor;
        this.regionMinor = regionMinor;
        this.regionPatch = regionPatch;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegionMajor() {
        return regionMajor;
    }

    public void setRegionMajor(String regionMajor) {
        this.regionMajor = regionMajor;
    }

    public String getRegionMinor() {
        return regionMinor;
    }

    public void setRegionMinor(String regionMinor) {
        this.regionMinor = regionMinor;
    }

    public String getRegionPatch() {
        return regionPatch;
    }

    public void setRegionPatch(String regionPatch) {
        this.regionPatch = regionPatch;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
