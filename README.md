<p align="center">
  <h2 align=center>Currencies client-server application</h2>

  <p align="center">
  <b><a href="#installation">Installation</a></b>
  |
  <b><a href="#contributing">Contributing</a></b>
</p>

## Features

* Small Spring/Rest training exercise
* Simple shell user interface
* RESTful API returning currencies exchange rates
* Return exchange rates for every currencies

## Installation

You must have [Java](https://www.java.com/en/download) 8+ and maven installed on your system.

### Mac OS X / Linux

* In Client:
> Run `mvn clean; mvn spring-boot:run` 
> Or run `mvn clean; mvn package; java -jar ./target/ds-client-0.1.0.jar`

* In Server API:
> Run `mvn clean; mvn spring-boot:run`, keep folder `data` at root.
> Or run `mvn clean; mvn package; java -jar ./target/currencies-rest-service-1.0.jar` and move folder `data` in `target`.
