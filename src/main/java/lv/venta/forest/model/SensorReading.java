package lv.venta.forest.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SensorReading {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "sensor_id")
    private ForestSensor sensor;
    private double temperature;      // Celsius
    private double humidity;         // %
    private double soilMoisture;     // %
    private double windSpeed;        // km/h
    private double co2Level;         // ppm
    private double fireRiskIndex;    // 0-100
    private double batteryVoltage;   // V
    private LocalDateTime timestamp;
    private String recommendation;   // LOW_RISK, MONITOR, ALERT, EVACUATE

    public SensorReading() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ForestSensor getSensor() { return sensor; }
    public void setSensor(ForestSensor v) { this.sensor = v; }
    public double getTemperature() { return temperature; }
    public void setTemperature(double v) { this.temperature = v; }
    public double getHumidity() { return humidity; }
    public void setHumidity(double v) { this.humidity = v; }
    public double getSoilMoisture() { return soilMoisture; }
    public void setSoilMoisture(double v) { this.soilMoisture = v; }
    public double getWindSpeed() { return windSpeed; }
    public void setWindSpeed(double v) { this.windSpeed = v; }
    public double getCo2Level() { return co2Level; }
    public void setCo2Level(double v) { this.co2Level = v; }
    public double getFireRiskIndex() { return fireRiskIndex; }
    public void setFireRiskIndex(double v) { this.fireRiskIndex = v; }
    public double getBatteryVoltage() { return batteryVoltage; }
    public void setBatteryVoltage(double v) { this.batteryVoltage = v; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime v) { this.timestamp = v; }
    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String v) { this.recommendation = v; }
}
