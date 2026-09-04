package lv.venta.forest.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class WaterBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "forest_zone_id")
    private ForestZone forestZone;
    
    private LocalDate measurementDate;
    
    // Water inputs (mm or liters/m2)
    private Double precipitation; // rainfall
    private Double irrigation; // artificial irrigation
    private Double groundwaterContribution; // capillary rise
    
    // Water outputs
    private Double evapotranspiration; // ET
    private Double runoff; // surface runoff
    private Double deepPercolation; // drainage below root zone
    
    // Soil water status
    private Double soilMoistureContent; // current soil moisture %
    private Double fieldCapacity; // maximum water holding capacity
    private Double wiltingPoint; // minimum moisture before wilting
    
    // Calculated values
    private Double waterBalance; // inputs - outputs
    private Double availableWater; // soilMoistureContent - wiltingPoint
    private Double waterDeficit; // fieldCapacity - soilMoistureContent
    
    // Constructors, getters, setters
    public WaterBalance() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ForestZone getForestZone() { return forestZone; }
    public void setForestZone(ForestZone forestZone) { this.forestZone = forestZone; }
    public LocalDate getMeasurementDate() { return measurementDate; }
    public void setMeasurementDate(LocalDate measurementDate) { this.measurementDate = measurementDate; }
    public Double getPrecipitation() { return precipitation; }
    public void setPrecipitation(Double precipitation) { this.precipitation = precipitation; }
    public Double getIrrigation() { return irrigation; }
    public void setIrrigation(Double irrigation) { this.irrigation = irrigation; }
    public Double getGroundwaterContribution() { return groundwaterContribution; }
    public void setGroundwaterContribution(Double groundwaterContribution) { this.groundwaterContribution = groundwaterContribution; }
    public Double getEvapotranspiration() { return evapotranspiration; }
    public void setEvapotranspiration(Double evapotranspiration) { this.evapotranspiration = evapotranspiration; }
    public Double getRunoff() { return runoff; }
    public void setRunoff(Double runoff) { this.runoff = runoff; }
    public Double getDeepPercolation() { return deepPercolation; }
    public void setDeepPercolation(Double deepPercolation) { this.deepPercolation = deepPercolation; }
    public Double getSoilMoistureContent() { return soilMoistureContent; }
    public void setSoilMoistureContent(Double soilMoistureContent) { this.soilMoistureContent = soilMoistureContent; }
    public Double getFieldCapacity() { return fieldCapacity; }
    public void setFieldCapacity(Double fieldCapacity) { this.fieldCapacity = fieldCapacity; }
    public Double getWiltingPoint() { return wiltingPoint; }
    public void setWiltingPoint(Double wiltingPoint) { this.wiltingPoint = wiltingPoint; }
    public Double getWaterBalance() { return waterBalance; }
    public void setWaterBalance(Double waterBalance) { this.waterBalance = waterBalance; }
    public Double getAvailableWater() { return availableWater; }
    public void setAvailableWater(Double availableWater) { this.availableWater = availableWater; }
    public Double getWaterDeficit() { return waterDeficit; }
    public void setWaterDeficit(Double waterDeficit) { this.waterDeficit = waterDeficit; }
}
