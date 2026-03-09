# Valuereporter-java-SDK

## Purpose
SDK library for sending observations and activities to the Valuereporter server. Provides the core data models and transport layer used by both the Valuereporter-Agent and Valuereporter-java-Client.

## Tech Stack
- Language: Java 8
- Framework: None (pure library)
- Build: Maven
- Key dependencies: Hystrix, SLF4J

## Architecture
Core SDK library containing shared data models, serialization logic, and HTTP transport for communicating with the Valuereporter server. Uses Hystrix commands for resilient HTTP communication. Serves as the foundation layer that both the Agent and Client libraries build upon.

## Key Entry Points
- SDK API and data models in `src/main/java/`
- `pom.xml` - Maven coordinates: `org.valuereporter:valuereporter-java-sdk`

## Development
```bash
# Build
mvn clean install

# Test
mvn test
```

## Domain Context
Core SDK for the Valuereporter observability ecosystem. Shared foundation library providing data models and transport used by Valuereporter-Agent and Valuereporter-java-Client.
