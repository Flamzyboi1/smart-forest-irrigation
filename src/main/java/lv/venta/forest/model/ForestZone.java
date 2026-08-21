package lv.venta.forest.model;

import jakarta.persistence.*;

@Entity
public class ForestZone {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String treeSpecies;
    private int treeAgeYears;
    private double areaHectares;
    private double centerLat;
    private double centerLng;
    private double radiusMeters;
    private String fireRisk;   // LOW, MEDIUM, HIGH, CRITICAL
    private String healthStatus; // HEALTHY, AT_RISK, CRITICAL
    private String status;       // NORMAL, ALERT, EMERGENCY

    public ForestZone() {}
    public ForestZone(String name, String treeSpecies, int treeAgeYears, double areaHectares,
                      double lat, double lng, double radius, String fireRisk, String healthStatus, String status) {
        this.name = name; this.treeSpecies = treeSpecies; this.treeAgeYears = treeAgeYears;
        this.areaHectares = areaHectares; this.centerLat = lat; this.centerLng = lng;
        this.radiusMeters = radius; this.fireRisk = fireRisk; this.healthStatus = healthStatus;
        this.status = status;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTreeSpecies() { return treeSpecies; }
    public void setTreeSpecies(String v) { this.treeSpecies = v; }
    public int getTreeAgeYears() { return treeAgeYears; }
    public void setTreeAgeYears(int v) { this.treeAgeYears = v; }
    public double getAreaHectares() { return areaHectares; }
    public void setAreaHectares(double v) { this.areaHectares = v; }
    public double getCenterLat() { return centerLat; }
    public void setCenterLat(double v) { this.centerLat = v; }
    public double getCenterLng() { return centerLng; }
    public void setCenterLng(double v) { this.centerLng = v; }
    public double getRadiusMeters() { return radiusMeters; }
    public void setRadiusMeters(double v) { this.radiusMeters = v; }
    public String getFireRisk() { return fireRisk; }
    public void setFireRisk(String v) { this.fireRisk = v; }
    public String getHealthStatus() { return healthStatus; }
    public void setHealthStatus(String v) { this.healthStatus = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }
}
