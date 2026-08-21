package lv.venta.forest.model;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "sensorId"))
public class ForestSensor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sensorId;
    private String type; // temperature, humidity, soil_moisture, fire_risk, wind_speed, co2
    private double latitude;
    private double longitude;
    private String locationLabel;
    private boolean active;
    @ManyToOne @JoinColumn(name = "zone_id")
    private ForestZone zone;

    public ForestSensor() {}
    public ForestSensor(String sensorId, String type, double lat, double lng,
                        String label, boolean active, ForestZone zone) {
        this.sensorId = sensorId; this.type = type; this.latitude = lat;
        this.longitude = lng; this.locationLabel = label; this.active = active; this.zone = zone;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSensorId() { return sensorId; }
    public void setSensorId(String v) { this.sensorId = v; }
    public String getType() { return type; }
    public void setType(String v) { this.type = v; }
    public double getLatitude() { return latitude; }
    public void setLatitude(double v) { this.latitude = v; }
    public double getLongitude() { return longitude; }
    public void setLongitude(double v) { this.longitude = v; }
    public String getLocationLabel() { return locationLabel; }
    public void setLocationLabel(String v) { this.locationLabel = v; }
    public boolean isActive() { return active; }
    public void setActive(boolean v) { this.active = v; }
    public ForestZone getZone() { return zone; }
    public void setZone(ForestZone v) { this.zone = v; }
}
