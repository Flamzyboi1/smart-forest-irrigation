package lv.venta.forest.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class ForestManagementPractice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String practiceType; // planting, thinning, pruning, harvesting, fertilization, pest_control
    private String description;
    private LocalDate scheduledDate;
    private LocalDate completedDate;
    private String status; // planned, in_progress, completed
    
    // Cost tracking
    private Double estimatedCost; // euros
    private Double actualCost; // euros
    
    // Water impact
    private Double waterSaved; // liters saved by this practice
    private Double waterUsed; // liters used by this practice
    
    // Area coverage
    private Double areaCovered; // square meters
    
    @ManyToOne
    @JoinColumn(name = "tree_species_id")
    private TreeSpecies treeSpecies;
    
    @ManyToOne
    @JoinColumn(name = "forest_zone_id")
    private ForestZone forestZone;
    
    // Constructors, getters, setters
    public ForestManagementPractice() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPracticeType() { return practiceType; }
    public void setPracticeType(String practiceType) { this.practiceType = practiceType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getScheduledDate() { return scheduledDate; }
    public void setScheduledDate(LocalDate scheduledDate) { this.scheduledDate = scheduledDate; }
    public LocalDate getCompletedDate() { return completedDate; }
    public void setCompletedDate(LocalDate completedDate) { this.completedDate = completedDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Double getEstimatedCost() { return estimatedCost; }
    public void setEstimatedCost(Double estimatedCost) { this.estimatedCost = estimatedCost; }
    public Double getActualCost() { return actualCost; }
    public void setActualCost(Double actualCost) { this.actualCost = actualCost; }
    public Double getWaterSaved() { return waterSaved; }
    public void setWaterSaved(Double waterSaved) { this.waterSaved = waterSaved; }
    public Double getWaterUsed() { return waterUsed; }
    public void setWaterUsed(Double waterUsed) { this.waterUsed = waterUsed; }
    public Double getAreaCovered() { return areaCovered; }
    public void setAreaCovered(Double areaCovered) { this.areaCovered = areaCovered; }
    public TreeSpecies getTreeSpecies() { return treeSpecies; }
    public void setTreeSpecies(TreeSpecies treeSpecies) { this.treeSpecies = treeSpecies; }
    public ForestZone getForestZone() { return forestZone; }
    public void setForestZone(ForestZone forestZone) { this.forestZone = forestZone; }
}
