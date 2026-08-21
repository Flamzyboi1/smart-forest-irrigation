package lv.venta.forest.controller;

import lv.venta.forest.model.*;
import lv.venta.forest.service.ForestService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/forest")
public class ForestController {
    private final ForestService service;
    public ForestController(ForestService service){this.service=service;}
    @GetMapping("/zones") public List<ForestZone> zones(){return service.getAllZones();}
    @GetMapping("/zones/{id}") public ResponseEntity<ForestZone> zone(@PathVariable Long id){return service.getZoneById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}
    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')") @PostMapping("/zones") public ForestZone addZone(@RequestBody ForestZone z){return service.saveZone(z);}
    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')") @PutMapping("/zones/{id}") public ResponseEntity<ForestZone> updateZone(@PathVariable Long id,@RequestBody ForestZone z){return service.getZoneById(id).map(x->{x.setName(z.getName());x.setTreeSpecies(z.getTreeSpecies());x.setTreeAgeYears(z.getTreeAgeYears());x.setAreaHectares(z.getAreaHectares());x.setCenterLat(z.getCenterLat());x.setCenterLng(z.getCenterLng());x.setRadiusMeters(z.getRadiusMeters());x.setFireRisk(z.getFireRisk());x.setHealthStatus(z.getHealthStatus());x.setStatus(z.getStatus());return ResponseEntity.ok(service.saveZone(x));}).orElse(ResponseEntity.notFound().build());}
    @PreAuthorize("hasRole('SUPERADMIN')") @DeleteMapping("/zones/{id}") public ResponseEntity<Void> deleteZone(@PathVariable Long id){service.deleteZone(id);return ResponseEntity.noContent().build();}
    @GetMapping("/sensors") public List<ForestSensor> sensors(){return service.getAllSensors();}
    @GetMapping("/sensors/{id}") public ResponseEntity<ForestSensor> sensor(@PathVariable Long id){return service.getSensorById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}
    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')") @PostMapping("/sensors") public ForestSensor addSensor(@RequestBody ForestSensor s){return service.saveSensor(s);}
    @PreAuthorize("hasRole('SUPERADMIN')") @DeleteMapping("/sensors/{id}") public ResponseEntity<Void> deleteSensor(@PathVariable Long id){service.deleteSensor(id);return ResponseEntity.noContent().build();}
    @GetMapping("/readings") public List<SensorReading> readings(){return service.getAllReadings();}
    @GetMapping("/readings/sensor/{sensorId}") public List<SensorReading> sensorReadings(@PathVariable Long sensorId){return service.getReadingsBySensorId(sensorId);}
    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')") @PostMapping("/readings") public SensorReading saveReading(@RequestBody Map<String,Object> body){String sensorId=String.valueOf(body.get("sensorId"));Map<String,Double> p=new HashMap<>();for(String k:List.of("temperature","humidity","soilMoisture","windSpeed","co2Level","fireRiskIndex","batteryVoltage")){Object v=body.get(k);if(v!=null)p.put(k,Double.parseDouble(v.toString()));}return service.submitReading(sensorId,p);}
    @GetMapping("/alerts") public List<ForestAlert> alerts(){return service.getAllAlerts();}
    @GetMapping("/alerts/unresolved") public List<ForestAlert> unresolved(){return service.getUnresolvedAlerts();}
    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')") @PutMapping("/alerts/{id}/resolve") public ResponseEntity<ForestAlert> resolve(@PathVariable Long id){try{return ResponseEntity.ok(service.resolveAlert(id));}catch(Exception e){return ResponseEntity.notFound().build();}}
    @PreAuthorize("hasRole('SUPERADMIN')") @DeleteMapping("/alerts/{id}") public ResponseEntity<Void> deleteAlert(@PathVariable Long id){service.deleteAlert(id);return ResponseEntity.noContent().build();}
}
