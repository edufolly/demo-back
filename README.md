# demo-back project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .


## Goals
- [X] Docker - https://www.docker.com/products/docker-desktop
- [X] GraalVM instalation - https://github.com/graalvm/graalvm-ce-builds/releases .
- [X] Database configuration.
- [X] First CRUD.
  - [X] ~~PanacheEntity~~ or AbstractEntity.
  - [X] PanacheRepository and AbstractRepository.
    - [X] POST.
    - [X] PUT.
    - [X] *Soft*DELETE.
    - [X] GET by ID.
    - [X] GET ALL.
    - [X] Pagination.
    - [X] SYNC With ~~date~~ timestamp.
      - [X] /sync/count.
      - [X] Pagination.
  - [X] AbstractResource.
- [X] OpenAPI Docs.
  - http://localhost:8080/openapi
  - http://localhost:8080/swagger-ui
- [ ] Security
  - [ ] Database Users / Roles
  - [ ] Interceptors
  - [ ] JWT
- [ ] Relationship Tests
  - [ ] One-to-one.
  - [ ] Many-to-one,
  - [ ] Many-to-many.
- [X] import.sql
- [ ] Hibernate Envers.
  - [X] Basic use.
  - [ ] Add user information on revision.
- [X] Basic Test Automation.
- [ ] Migrations with Flyaway.
- [ ] Multiples data sources.
- [ ] Native execution.

## Docker Utils


```shell script
docker network create demo-back-network
```

##### PostgreSQl
```shell script
docker run --ulimit memlock=-1:-1 --memory-swappiness=0 \
           --net demo-back-network \
           --name postgres-quarkus-hibernate -e POSTGRES_USER=hibernate \
           -e POSTGRES_PASSWORD=hibernate -e POSTGRES_DB=hibernate_db \
           -v ~/github/demo-back/postgresql:/var/lib/postgresql/data \
           -p 5432:5432 postgres:12
```

#### Keycloak
```shell script
docker run --name keycloak-quarkus-hibernate \
           --net demo-back-network \
           -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin \
           -e DB_VENDOR=postgres \
           -e DB_ADDR=postgres-quarkus-hibernate -e DB_PORT=5432 \
           -e DB_USER=hibernate -e DB_PASSWORD=hibernate \
           -p 8180:8080 jboss/keycloak
```

## Read

#### Quarkus
- https://developers.redhat.com/courses/quarkus/
- https://quarkus.io/quarkus-workshops/super-heroes/
- Tests => https://github.com/geoand/quarkus-test-demo/ 
- https://quarkus.io/blog/insights-1/

#### JSON-B
- https://javaee.github.io/jsonb-spec/users-guide.html

## Initial Tests

#### Endpoint

##### POST
```shell script
curl -vvv -H 'Content-Type: application/json' \
     -X POST http://localhost:8080/endpoints \
     -d '{"name":"test","url":"test"}'
```

##### PUT
```shell script
curl -vvv -H 'Content-Type: application/json' \
     -X PUT http://localhost:8080/endpoints/1 \
     -d '{"name":"new_test","url":"new_test","web":false}'
```

##### GET by ID
```shell script
curl -vvv http://localhost:8080/endpoints/1
```

##### GET ALL
```shell script
curl -vvv http://localhost:8080/endpoints

curl -vvv http://localhost:8080/endpoints?per_page\=10

curl -vvv http://localhost:8080/endpoints?page\=5\&per_page\=10
```

##### DELETE
```shell script
curl -vvv -X DELETE http://localhost:8080/endpoints/1
```

##### SYNC COUNT
```shell script
curl -vvv http://localhost:8080/endpoints/sync/count?t\=0
```

##### SYNC
```shell script
curl -vvv http://localhost:8080/endpoints/sync\?t\=0\&page\=1\&per_page\=10
```

## Issues
- OpenJDK 64-Bit Server VM warning: forcing TieredStopAtLevel to full optimization because JVMCI is enabled.
  - https://github.com/quarkusio/quarkus/issues/6175
  - Quarkus has a warning to Java 8 version. Installing Java 11.
  - Try to use GraalVM < 20 with Java 11

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./gradlew quarkusDev
```

## Packaging and running the application

The application can be packaged using `./gradlew quarkusBuild`.
It produces the `demo-back-0.0.1-runner.jar` file in the `build` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/lib` directory.

The application is now runnable using `java -jar build/demo-back-0.0.1-runner.jar`.

If you want to build an _über-jar_, just add the `--uber-jar` option to the command line:
```
./gradlew quarkusBuild --uber-jar
```

## Creating a native executable

You can create a native executable using: `./gradlew build -Dquarkus.package.type=native`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./build/demo-back-0.0.1-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/gradle-tooling#building-a-native-executable.