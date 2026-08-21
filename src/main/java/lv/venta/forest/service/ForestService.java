package lv.venta.forest.service;

import lv.venta.forest.model.*;
import lv.venta.forest.repo.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ForestService {
    private final ForestSensorRepository sensorRepo; private final SensorReadingRepository readingRepo; private final ForestAlertRepository alertRepo; private final ForestZoneRepository zoneRepo; private final AppUserRepository userRepo;
    public ForestService(ForestSensorRepository s,SensorReadingRepository r,ForestAlertRepository a,ForestZoneRepository z,AppUserRepository u){sensorRepo=s;readingRepo=r;alertRepo=a;zoneRepo=z;userRepo=u;}
    public List<ForestZone> getAllZones(){return (List<ForestZone>)zoneRepo.findAll();}
    public Optional<ForestZone> getZoneById(Long id){return zoneRepo.findById(id);}
    public ForestZone saveZone(ForestZone z){return zoneRepo.save(z);}
    public void deleteZone(Long id){zoneRepo.deleteById(id);}
    public List<ForestSensor> getAllSensors(){return (List<ForestSensor>)sensorRepo.findAll();}
    public Optional<ForestSensor> getSensorById(Long id){return sensorRepo.findById(id);}
    public ForestSensor saveSensor(ForestSensor s){return sensorRepo.save(s);}
    public void deleteSensor(Long id){sensorRepo.deleteById(id);}
    public List<SensorReading> getAllReadings(){return (List<SensorReading>)readingRepo.findAll();}
    public List<SensorReading> getReadingsBySensorId(Long id){List<SensorReading> out=new ArrayList<>();readingRepo.findAll().forEach(r->{if(r.getSensor()!=null&&r.getSensor().getId().equals(id))out.add(r);});out.sort(Comparator.comparing(SensorReading::getTimestamp));return out;}
    public SensorReading saveReading(SensorReading r){return readingRepo.save(r);}
    public List<ForestAlert> getAllAlerts(){return (List<ForestAlert>)alertRepo.findAll();}
    public List<ForestAlert> getUnresolvedAlerts(){List<ForestAlert> out=new ArrayList<>();alertRepo.findAll().forEach(a->{if(!a.isAcknowledged())out.add(a);});return out;}
    public ForestAlert resolveAlert(Long id){ForestAlert a=alertRepo.findById(id).orElseThrow();a.setAcknowledged(true);return alertRepo.save(a);}
    public void deleteAlert(Long id){alertRepo.deleteById(id);}
    public List<AppUser> getAllUsers(){return (List<AppUser>)userRepo.findAll();}
    public Optional<AppUser> getUserById(Long id){return userRepo.findById(id);}
    public Optional<AppUser> getUserByUsername(String username){return userRepo.findByUsername(username);}
    public SensorReading submitReading(String sensorId,Map<String,Double> p){
        ForestSensor sensor=null; for(ForestSensor s:sensorRepo.findAll()) if(s.getSensorId()!=null&&s.getSensorId().equals(sensorId)) sensor=s;
        if(sensor==null) throw new IllegalArgumentException("Sensor not found: "+sensorId);
        SensorReading r=new SensorReading();r.setSensor(sensor);r.setTimestamp(LocalDateTime.now());r.setTemperature(p.getOrDefault("temperature",0d));r.setHumidity(p.getOrDefault("humidity",0d));r.setSoilMoisture(p.getOrDefault("soilMoisture",0d));r.setWindSpeed(p.getOrDefault("windSpeed",0d));r.setCo2Level(p.getOrDefault("co2Level",400d));r.setFireRiskIndex(Math.max(0,Math.min(100,p.getOrDefault("fireRiskIndex",0d))));r.setBatteryVoltage(p.getOrDefault("batteryVoltage",3.7d));double fri=r.getFireRiskIndex();r.setRecommendation(fri>=75?"EVACUATE":fri>=50?"ALERT":fri>=25?"MONITOR":"LOW_RISK");
        if(sensor.getZone()!=null){sensor.getZone().setFireRisk(fri>=75?"CRITICAL":fri>=50?"HIGH":fri>=25?"MEDIUM":"LOW");zoneRepo.save(sensor.getZone());}
        if(fri>=75&&sensor.getZone()!=null) alertRepo.save(new ForestAlert("FIRE_RISK","CRITICAL","Critical fire risk detected. FRI >= 75. Immediate action required.",sensor.getZone(),sensor));
        return readingRepo.save(r);
    }
}
