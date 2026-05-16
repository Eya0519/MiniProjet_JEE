# Plateforme de Gestion de Flotte de Vehicules

Application web de gestion de flotte developpee avec Spring Boot pour le back-end et Angular pour le front-end.

## Fonctionnalites

- Gestion CRUD des vehicules.
- Gestion CRUD des chauffeurs.
- Gestion CRUD des missions.
- Gestion CRUD des consommations de carburant.
- Affectation d'un vehicule et d'un chauffeur a une mission.
- Suivi de la maintenance preventive selon le kilometrage.
- Tableau de bord des couts de carburant par vehicule.
- Reporting des vehicules les plus actifs selon le nombre de missions.

## Technologies

- Java 17
- Spring Boot 4
- Spring Data JPA
- MySQL
- ModelMapper
- Spring Validation
- Swagger / OpenAPI
- Angular
- Docker et Docker Compose

## Structure

```text
src/main/java/tn/itbs/flotte
  controllers
  convertors
  dto
  entities
  repositories
  services

frontend
  src/app
```

## Lancement local sans Docker

### Back-end

Verifier que MySQL est lance avec la base `flotte_db`.

Configuration par defaut :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/flotte_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
server.port=8081
```

Lancer l'application Spring Boot depuis Eclipse avec `Run As > Spring Boot App`.

API :

```text
http://localhost:8081
```

Swagger :

```text
http://localhost:8081/swagger-ui.html
```

### Front-end

```bash
cd frontend
npm.cmd install
npm.cmd start
```

Interface Angular :

```text
http://127.0.0.1:4200
```

## Lancement avec Docker

Depuis la racine du projet :

```bash
docker compose up --build
```

Services :

```text
Frontend: http://localhost:4200
Backend:  http://localhost:8081
Swagger:  http://localhost:8081/swagger-ui.html
MySQL:    localhost:3306
```

Pour arreter :

```bash
docker compose down
```

Pour supprimer aussi les donnees MySQL Docker :

```bash
docker compose down -v
```

## Endpoints principaux

```text
GET    /vehicules/getAll
POST   /vehicules/add
PUT    /vehicules/update/{id}
DELETE /vehicules/delete/{id}
GET    /vehicules/maintenance

GET    /chauffeurs/getAll
POST   /chauffeurs/add
PUT    /chauffeurs/update/{id}
DELETE /chauffeurs/delete/{id}

GET    /missions/getAll
POST   /missions/add
PUT    /missions/update/{id}
DELETE /missions/delete/{id}
PUT    /missions/affecter/{idMission}/{idVehicule}/{idChauffeur}

GET    /consommations/getAll
POST   /consommations/add
PUT    /consommations/update/{id}
DELETE /consommations/delete/{id}

GET    /dashboard/summary
GET    /dashboard/fuel-costs
GET    /dashboard/fleet-usage
```

## Validation

Les DTOs utilisent Spring Validation :

- `@NotBlank`
- `@NotNull`
- `@Min`
- `@Positive`
- `@PositiveOrZero`

## Auteur

Projet mini-projet Spring Boot / Angular.
