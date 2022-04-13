package dev.aquashdw.community.controller.dto;

import dev.aquashdw.community.entity.AreaEntity;

public class AreaDto implements Comparable<AreaDto>{
    private Long id;
    private String regionMajor;
    private String regionMinor;
    private String regionPatch;
    private Double latitude;
    private Double longitude;
    private Double distance;

    public AreaDto() {
    }

    public AreaDto(AreaEntity areaEntity) {
        this.id = areaEntity.getId();
        this.regionMajor = areaEntity.getRegionMajor();
        this.regionMinor = areaEntity.getRegionMinor();
        this.regionPatch = areaEntity.getRegionPatch();
        this.latitude = areaEntity.getLatitude();
        this.longitude = areaEntity.getLongitude();
        this.distance = areaEntity.getDistance();
    }

    public AreaDto(Long id, String regionMajor, String regionMinor, String regionPatch, Double latitude, Double longitude) {
        this.id = id;
        this.regionMajor = regionMajor;
        this.regionMinor = regionMinor;
        this.regionPatch = regionPatch;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = 0.0;
    }

    public AreaDto(Long id, String regionMajor, String regionMinor, String regionPatch, Double latitude, Double longitude, Double distance) {
        this.id = id;
        this.regionMajor = regionMajor;
        this.regionMinor = regionMinor;
        this.regionPatch = regionPatch;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "AreaDto{" +
                "id=" + id +
                ", regionMajor='" + regionMajor + '\'' +
                ", regionMinor='" + regionMinor + '\'' +
                ", regionPatch='" + regionPatch + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", distance=" + distance +
                '}';
    }

    @Override
    public int compareTo(AreaDto o) {
        if (this.distance < o.getDistance()) {
            return -1;
        } else if (this.distance > o.getDistance()) {
            return 1;
        }
        return 0;
    }
}