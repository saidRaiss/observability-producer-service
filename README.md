# Producer Service

This repository contains the **Producer Service**, a microservice that publishes messages to a Kafka topic. It is built using Spring Boot and Kafka and is instrumented with OpenTelemetry for observability purposes.

## Description

The **Producer Service** is responsible for sending messages to a Kafka topic. This service is part of a larger observability project and is used to demonstrate how to integrate Kafka-based messaging with observability tools like OpenTelemetry and Grafana. It collects logs, metrics, and traces which are exported via the OpenTelemetry Java Agent for analysis and visualization.

This repository is used as a **Git submodule** in the main [observability-project](https://github.com/saidRaiss/observability-project), which contains the full setup including Grafana, Loki, Tempo, and Prometheus for monitoring and visualization.

## Environment Variables (Docker Compose)

The following environment variables are used in the **Producer Service** configuration within the Docker Compose setup of the main observability project:

| Environment Variable                        | Description                                                          | Example Value                   |
|---------------------------------------------|----------------------------------------------------------------------|---------------------------------|
| `APP_OUTBOUND_REST_CLIENTS_BRIDGE_BASE_URL` | The base URL for the Bridge service, used for making outbound calls. | `http://bridge:8084`            |
| `APP_KAFKA_BOOTSTRAP`                       | Kafka bootstrap server address.                                      | `PLAINTEXT://kafka:9090`        |
| `APP_KAFKA_TOPIC`                           | Kafka topic to which the messages are published.                     | `topic-observability`           |
| `OTEL_SERVICE_NAME`                         | Name of the service for observability purposes.                      | `producer-service`              |
| `OTEL_RESOURCE_ATTRIBUTES`                  | Attributes to identify the service in observability tools.           | `service.name=producer-service` |
| `OTEL_EXPORTER_OTLP_ENDPOINT`               | Endpoint for OpenTelemetry Collector to export telemetry data.       | `http://otel-collector:4317`    |
| `OTEL_EXPORTER_OTLP_PROTOCOL`               | Protocol used for telemetry data export.                             | `grpc`                          |
| `OTEL_LOGS_EXPORTER`                        | Logs exporter protocol.                                              | `otlp`                          |
| `OTEL_TRACES_EXPORTER`                      | Traces exporter protocol.                                            | `otlp`                          |
| `OTEL_METRICS_EXPORTER`                     | Metrics exporter protocol.                                           | `otlp`                          |


## Usage as a Submodule

To clone the entire observability project along with this submodule, run the following command:

```bash
git clone --recursive https://github.com/saidRaiss/observability-project.git
```

To initialize the submodule after cloning the main repository, use:
```bash
git submodule update --init --recursive
```