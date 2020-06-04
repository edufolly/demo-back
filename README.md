# demo-back project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .


## Goals
- [X] Docker - https://www.docker.com/products/docker-desktop
- [X] GraalVM instalation - https://github.com/graalvm/graalvm-ce-builds/releases .
- [X] Database configuration.
- [ ] First CRUD.
  - [X] ~~PanacheEntity~~ or AbstractEntity.
  - [ ] PanacheRepository and AbstractRepository.
    - [X] POST.
    - [X] PUT.
    - [X] *Soft*DELETE.
    - [X] GET by ID.
    - [X] GET ALL.
    - [ ] SYNC With date.
    - [ ] Pagination.
  - [ ] AbstractResource.
- [ ] Relationship Tests
  - [ ] One-to-one.
  - [ ] Many-to-one,
  - [ ] Many-to-many.
- [ ] import.sql
- [ ] Hibernate Envers.
- [ ] Basic Test Automation.
- [ ] Migrations with Flyaway.
- [ ] Security
  - [ ] Database Users / Roles
  - [ ] Interceptors
  - [ ] JWT
- [ ] Multiples data sources.
- [ ] OpenAPI Docs.


## Utils

PostgreSQl in Docker

```shell script
docker run --ulimit memlock=-1:-1 --memory-swappiness=0 \
           --name postgres-quarkus-hibernate -e POSTGRES_USER=hibernate \
           -e POSTGRES_PASSWORD=hibernate -e POSTGRES_DB=hibernate_db \
           -v ~/github/demo-back/postgresql:/var/lib/postgresql/data \
           -p 5432:5432 postgres:12
```

## Read

#### Quarkus
- https://developers.redhat.com/courses/quarkus/
- https://quarkus.io/quarkus-workshops/super-heroes/
- http://www.mastertheboss.com/soa-cloud/quarkus/managing-data-persistence-with-quarkus
- https://github.com/geoand/quarkus-test-demo/
- https://quarkus.io/blog/insights-1/
- https://lordofthejars.github.io/quarkus-cheat-sheet/

#### JSON-B
- https://javaee.github.io/jsonb-spec/users-guide.html

## Initial Tests

#### Endpoint

##### POST
```shell script
curl -vvv -H 'Content-Type: application/json' \
     -X POST http://localhost:8080/endpoint \
     -d '{"name":"test","url":"test"}'
```

##### PUT
```shell script
curl -vvv -H 'Content-Type: application/json' \
     -X PUT http://localhost:8080/endpoint/1 \
     -d '{"name":"new_test","url":"new_test","web":false}'
```

##### GET by ID
```shell script
curl -vvv -X GET http://localhost:8080/endpoint/1
```

##### GET ALL
```shell script
curl -vvv -X GET http://localhost:8080/endpoint
```

##### DELETE
```shell script
curl -vvv -X DELETE http://localhost:8080/endpoint/1
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