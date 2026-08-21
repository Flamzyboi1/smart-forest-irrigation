package lv.venta.forest;

import lv.venta.forest.model.*;
import lv.venta.forest.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {
    private final AppUserRepository users; private final ForestZoneRepository zones; private final ForestSensorRepository sensors; private final ForestAlertRepository alerts; private final SensorReadingRepository readings; private final PasswordEncoder encoder;
    public DataLoader(AppUserRepository u,ForestZoneRepository z,ForestSensorRepository s,ForestAlertRepository a,SensorReadingRepository r,PasswordEncoder e){users=u;zones=z;sensors=s;alerts=a;readings=r;encoder=e;}
    private AppUser user(String username,String password,String full,String email,String role){AppUser u=new AppUser();u.setUsername(username);u.setPassword(encoder.encode(password));u.setFullName(full);u.setEmail(email);u.setRole(role);u.setActive(true);return users.save(u);}
    @Override public void run(String...args){
        if(users.count()==0){user("ForestAdmin","Forest123#","Forest Administrator","admin@forest.lv","SUPERADMIN");user("ranger1","Ranger123#","Forest Ranger","ranger1@forest.lv","ADMIN");user("observer1","Observer123#","Forest Observer","observer1@forest.lv","USER");}
        if(zones.count()==0){
            ForestZone z1=new ForestZone("Northern Pine Forest","Pine",70,450.5,57.3845,21.5608,700,"LOW","HEALTHY","NORMAL");zones.save(z1);
            ForestZone z2=new ForestZone("Eastern Mixed Forest","Mixed",55,320,57.1200,22.0100,650,"CRITICAL","AT_RISK","EMERGENCY");zones.save(z2);
            ForestZone z3=new ForestZone("Southern Oak Reserve","Oak",90,180,56.9800,21.7500,500,"MEDIUM","HEALTHY","ALERT");zones.save(z3);
            ForestSensor s1=new ForestSensor("FR-NP-001","Multi-Environmental",57.3845,21.5608,"Northern Pine Sensor",true,z1);sensors.save(s1);
            ForestSensor s2=new ForestSensor("FR-EM-001","Fire-Risk",57.1200,22.0100,"Eastern Mixed Sensor",true,z2);sensors.save(s2);
            ForestSensor s3=new ForestSensor("FR-SO-001","Multi-Environmental",56.9800,21.7500,"Southern Oak Sensor",true,z3);sensors.save(s3);
            reading(s1,24.5,65,45,12.3,410,15,3.8,"LOW_RISK");
            reading(s2,31.2,28,15,22,430,78,3.6,"EVACUATE");
            reading(s3,27.0,44,30,18,420,48,3.7,"MONITOR");
            ForestAlert a=new ForestAlert("FIRE_RISK","CRITICAL","Critical fire risk detected. FRI >= 75. Immediate action required.",z2,s2);alerts.save(a);
        }
    }
    private void reading(ForestSensor s,double t,double h,double soil,double wind,double co2,double fri,double batt,String rec){SensorReading r=new SensorReading();r.setSensor(s);r.setTimestamp(LocalDateTime.now().minusMinutes((long)(Math.random()*60)));r.setTemperature(t);r.setHumidity(h);r.setSoilMoisture(soil);r.setWindSpeed(wind);r.setCo2Level(co2);r.setFireRiskIndex(fri);r.setBatteryVoltage(batt);r.setRecommendation(rec);readings.save(r);}
}
