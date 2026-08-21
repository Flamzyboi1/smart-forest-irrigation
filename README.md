# Smart Forest Monitoring Project

Spring Boot 3.2.5 / Java 17+ / H2 / Thymeleaf / Spring Security / JWT / Leaflet.

## Run in Eclipse
1. Import this folder as an Existing Maven Project.
2. Maven > Update Project.
3. Run `lv.venta.forest.ForestApplication`.
4. Open http://localhost:8080/

## Demo accounts
- SUPERADMIN: ForestAdmin / Forest123#
- ADMIN: ranger1 / Ranger123#
- USER: observer1 / Observer123#

## Permissions
- SUPERADMIN can view, create, edit and delete all users and approve/reject profile-change requests.
- ADMIN/USER cannot view or delete other users.
- ADMIN/USER can edit their own profile through a request that requires SUPERADMIN approval.

## H2 Console
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:forestdb
User: sa
Password: empty

## Important
The database is in-memory, so demo data is recreated when the application restarts.
