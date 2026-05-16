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

## Deploiement Cloud

L'application a ete deployee sur Google Cloud Platform avec Cloud Run et Cloud SQL.

Liens de demonstration :

```text
Frontend Cloud Run:
https://flotte-frontend-686322940226.europe-west1.run.app

Backend Swagger Cloud Run:
https://flotte-backend-686322940226.europe-west1.run.app/swagger-ui.html
```

Services utilises :

```text
Cloud Run        : backend Spring Boot et frontend Angular
Cloud SQL        : base de donnees MySQL
Artifact Registry: stockage des images Docker
Cloud Build      : build et push des images Docker
```

## Notes metier

### Maintenance preventive

Le suivi de maintenance preventive est base sur le kilometrage du vehicule. Les vehicules avec un kilometrage superieur a `100000 km` sont affiches dans la liste de maintenance preventive.

Endpoint :

```text
GET /vehicules/maintenance
```

### Couts carburant

Les consommations representent des donnees reelles de ravitaillement. L'utilisateur saisit la quantite de carburant et le cout total. Le tableau de bord agrege ensuite les couts par vehicule.

Endpoint :

```text
GET /dashboard/fuel-costs
```

### Utilisation de la flotte

L'utilisation de la flotte est calculee selon le nombre de missions affectees a chaque vehicule. Les vehicules les plus actifs sont ceux qui possedent le plus de missions.

Endpoint :

```text
GET /dashboard/fleet-usage
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
