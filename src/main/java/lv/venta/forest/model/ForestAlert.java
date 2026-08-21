package lv.venta.forest.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ForestAlert {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String alertType;    // FIRE_RISK, DROUGHT, PEST, TEMPERATURE, WIND
    private String severity;     // INFO, WARNING, CRITICAL
    private String message;
    private LocalDateTime timestamp;
    private boolean acknowledged;
    @ManyToOne @JoinColumn(name = "zone_id")
    private ForestZone zone;
    @ManyToOne @JoinColumn(name = "sensor_id")
    private ForestSensor sensor;

    public ForestAlert() {}
    public ForestAlert(String alertType, String severity, String message, ForestZone zone, ForestSensor sensor) {
        this.alertType = alertType; this.severity = severity; this.message = message;
        this.zone = zone; this.sensor = sensor;
        this.timestamp = LocalDateTime.now(); this.acknowledged = false;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAlertType() { return alertType; }
    public void setAlertType(String v) { this.alertType = v; }
    public String getSeverity() { return severity; }
    public void setSeverity(String v) { this.severity = v; }
    public String getMessage() { return message; }
    public void setMessage(String v) { this.message = v; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime v) { this.timestamp = v; }
    public boolean isAcknowledged() { return acknowledged; }
    public void setAcknowledged(boolean v) { this.acknowledged = v; }
    public ForestZone getZone() { return zone; }
    public void setZone(ForestZone v) { this.zone = v; }
    public ForestSensor getSensor() { return sensor; }
    public void setSensor(ForestSensor v) { this.sensor = v; }
}
