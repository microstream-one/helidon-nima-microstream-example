# helidon-nima-microstream-example
Helidon Níma Microstream Example - Loom based webserver with microstream

This example is based upon the Helidon Níma Example from Tomas Langer.
The original example is available on GitHub https://github.com/tomas-langer/helidon-nima-example

This example is built on top of an ALPHA-2 release of Helidon 4. Alpha releases serve as prototypes or technology demonstration.

The code is not yet production quality in all of its aspects, and problems may be expected.

Contrary to the original example this one use a microstream storage as datasource.

## Prerequisites
Java 19 with preview feature "Loom" (now available as RC at https://jdk.java.net/19/)
Maven

## How to

1. Build the project from the repository root
 
        mvn clean package
2. Run the application

        java --enable-preview -jar nima/target/example-nima-microstream-blocking.jar
3. Call the endpoints (default count is 3)

        curl -i http://localhost:8080/one
        curl -i http://localhost:8080/sequence
        curl -i http://localhost:8080/sequence?count=4
        curl -i http://localhost:8080/parallel
        curl -i http://localhost:8080/parallel?count=2
