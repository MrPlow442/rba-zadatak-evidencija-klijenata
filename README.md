# RBA Evaluacija - Matija Lovrekovic - Evidencija karticnih zahtjeva

Projekt se sastoji od 3 modula:
- application - servisni dio, sadrzi REST endpointove
- database - migracije za bazu se nalaze ovdje
- frontend - frontend za korisnicku podrsku

## Kako zapoceti
Za pokretanje ovog projekta potrebni su:
- JDK 21
- Gradle v8.11.1
- Node.js v22.11.0
- docker i docker-compose
- Preferabilno IntelliJ IDEA IDE

Otvaranjem ovog projekta u IntelliJ IDEA nalazit ce se dvije run konfiguracije za podizanje i brisanje dev environmenta:
 - `dockerCompose up` - podize postgreSQL bazu podataka sa shemama za dev i testove, zookeper i kafka broker. Ekvivalent zvanju  
```
./gradlew rbaEnvironmentComposeUp flywayMigrateLocalDb flywayMigrateLocalTestDb --stacktrace
```
 - `dockerCompose down forced` - brise prethodno navedene komponente. Ekvivalent zvanju
```
./gradlew rbaEnvironmentComposeDownForced
```

> Za vise informacija mozete pogledati datoteke unutar `/devtools/environment-setup`

Nakon podizanja environmenta servis se moze pokrenuti pokretanjem `service` run konfiguracije ili
preko main metode u `hr.mlovrekovic.evidencijaklijenata.Application`

Nakon pokretanja servisa moze se pokrenuti frontend sa pokretanjem `frontend` run konfiguracije. Ekvivalent zvanju
```
npm install
npm start
```
unutar `frontend` direktorija

### Rucno testiranje

Unutar `/devtools/testing` direktorija nalazi se `api_requests.http` datoteka koja sadrzi IntelliJ-style HTTP requestove
za sve endpointove Card Request API

Ujedno je moguce i poslati poruku na Kafka topic putem POST requesta
na `/api/v1/operations/card-request:send-message` endpoint primjer cega se nalazi u prethodno navedenoj datoteci

## Da sam imao vise vremena
* Paginacija za getAll
* Spring Security je ukljucen u projekt no nisam ga upotrijebio. Planirao sam zastititi API i uspostaviti komunikaciju sa frontendom preko JWT tokena
* Frontend je barebones, nedostaje validacija i fali truda oko dizajna i prezentacije, isao sam iskljucivo na MVP funkcionalnost
* Frontend testovi ne postoje trenutno. Nemam bas iskustva u testiranju u reactu pa sam to ostavio za kraj i nisam napravio
* Vise testova: ops servis i kafka, integracijski testovi controllera
* Swagger dokumentacija/runner za endpointove
* Zamjena hardkodiranih URL-ova sa environment variablama