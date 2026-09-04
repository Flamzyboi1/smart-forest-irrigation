# Smart Forest Management System

A Spring Boot application for smart forest management practices, tree species recommendations, and water balance monitoring.

## Features

### Tree Species Management
- **Dominant Tree Analysis**: Identifies dominant tree species in a forest area
- **Tree Recommendations**: Recommends suitable tree species based on soil type
- **Species Database**: Complete information on growth rate, mature height, lifespan, water requirements

### Forest Management Practices
- Track planting, thinning, pruning, harvesting operations
- Cost estimation and tracking
- Water usage and savings monitoring
- Scheduling and status tracking

### Water Balance in Soil
- Precipitation, irrigation, groundwater contribution tracking
- Evapotranspiration, runoff, deep percolation monitoring
- Soil moisture content, field capacity, wilting point analysis
- Automatic water balance calculation

### Water Saving Algorithm
- **Formula**: 1L per 1m2 per 1 minute
- Predict total water needed for irrigation duration
- Cost estimation for water usage

## API Endpoints

### Tree Species
- `GET /api/forest/treespecies` - Get all tree species
- `GET /api/forest/treespecies/dominant` - Get dominant tree species
- `GET /api/forest/treespecies/recommend?soilType=loamy` - Recommend trees by soil type

### Forest Management Practices
- `GET /api/forest/practices` - Get all management practices
- `POST /api/forest/practices` - Create new practice

### Water Balance
- `GET /api/forest/waterbalance/zone/{zoneId}` - Get water balance by zone
- `POST /api/forest/waterbalance` - Calculate and save water balance
- `GET /api/forest/water/calculate?area=1000&duration=10` - Calculate water needs

## Running in Eclipse

1. Import as Maven project
2. Right-click project → Run As → Spring Boot App
3. Access H2 Console: http://localhost:8080/h2-console
   - JDBC URL: `jdbc:h2:mem:forestdb`
   - Username: `sa`
   - Password: (empty)

## Default Credentials
- Username: `admin`
- Password: `admin123`

## Technologies
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- H2 Database
- Java 17+
