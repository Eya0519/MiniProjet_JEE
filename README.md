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

## Preparation au deploiement Google Cloud Platform

Architecture recommandee :

```text
Frontend Angular  -> Cloud Run
Backend Spring    -> Cloud Run
Base MySQL        -> Cloud SQL
Images Docker     -> Artifact Registry
```

### Variables importantes

Back-end Cloud Run :

```text
PORT=8080
SPRING_DATASOURCE_URL=jdbc:mysql:///flotte_db?cloudSqlInstance=PROJECT_ID:REGION:INSTANCE_NAME&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=YOUR_PASSWORD
APP_CORS_ALLOWED_ORIGINS=https://FRONTEND_URL.run.app
```

Front-end Cloud Run :

```text
PORT=8080
API_URL=https://BACKEND_URL.run.app
```

### Commandes indicatives

Remplacer :

```text
PROJECT_ID
REGION
REPOSITORY
INSTANCE_NAME
```

Creer un repository Docker Artifact Registry :

```bash
gcloud artifacts repositories create REPOSITORY --repository-format=docker --location=REGION
```

Construire et pousser le back-end :

```bash
gcloud builds submit --tag REGION-docker.pkg.dev/PROJECT_ID/REPOSITORY/flotte-backend:latest .
```

Construire et pousser le front-end :

```bash
gcloud builds submit ./frontend --tag REGION-docker.pkg.dev/PROJECT_ID/REPOSITORY/flotte-frontend:latest
```

Deployer le back-end sur Cloud Run :

```bash
gcloud run deploy flotte-backend ^
  --image REGION-docker.pkg.dev/PROJECT_ID/REPOSITORY/flotte-backend:latest ^
  --region REGION ^
  --allow-unauthenticated ^
  --add-cloudsql-instances PROJECT_ID:REGION:INSTANCE_NAME ^
  --set-env-vars SPRING_DATASOURCE_URL="jdbc:mysql:///flotte_db?cloudSqlInstance=PROJECT_ID:REGION:INSTANCE_NAME&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false",SPRING_DATASOURCE_USERNAME=root,SPRING_DATASOURCE_PASSWORD=YOUR_PASSWORD
```

Apres creation du front-end, mettre a jour le CORS du back-end avec l'URL front-end :

```bash
gcloud run services update flotte-backend ^
  --region REGION ^
  --set-env-vars APP_CORS_ALLOWED_ORIGINS=https://FRONTEND_URL.run.app
```

Deployer le front-end sur Cloud Run :

```bash
gcloud run deploy flotte-frontend ^
  --image REGION-docker.pkg.dev/PROJECT_ID/REPOSITORY/flotte-frontend:latest ^
  --region REGION ^
  --allow-unauthenticated ^
  --set-env-vars API_URL=https://BACKEND_URL.run.app
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
