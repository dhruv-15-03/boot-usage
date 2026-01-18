# Boot Usage Spring Boot Starter

[![Build Status](https://github.com/dhruv-15-03/boot-usage/actions/workflows/build.yml/badge.svg)](https://github.com/dhruv-15-03/boot-usage/actions/workflows/build.yml)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.dhruv1503/boot-usage-spring-boot-starter.svg)](https://search.maven.org/artifact/io.github.dhruv1503/boot-usage-spring-boot-starter)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

A Spring Boot starter that provides runtime analysis of starter usage in your application. It helps you identify:

- **Used starters**: Starters with auto-configurations that are actively contributing beans
- **Unused starters**: Starters included in your dependencies but not contributing any beans
- **Indeterminate starters**: Starters whose usage status cannot be definitively determined

This is invaluable for optimizing your application's dependencies and reducing bloat.

## Features

- `/actuator/bootusage` - Actuator endpoint for runtime usage analysis
- **Starter Detection** - Automatically discovers Spring Boot starters on the classpath
- **Auto-Configuration Analysis** - Tracks which auto-configurations are active and contributing beans
- **Bean Origin Tracking** - Identifies which starters contributed which beans
- **Usage Policies** - Define custom policies to enforce starter usage rules (SPI)
- **Multiple Output Formats** - JSON endpoint response or Markdown summary files
- **Caching** - Configurable result caching for performance

## Quick Start

### Maven

```xml
<dependency>
    <groupId>io.github.dhruv1503</groupId>
    <artifactId>boot-usage-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

```groovy
implementation 'io.github.dhruv1503:boot-usage-spring-boot-starter:1.0.0'
```

## Usage

Once added to your project, the starter automatically registers an actuator endpoint.

### Accessing the Endpoint

```bash
# Get usage analysis
curl http://localhost:8080/actuator/bootusage

# Force cache refresh
curl http://localhost:8080/actuator/bootusage?force=true
```

### Sample Response

```json
{
  "usedStarters": [
    {
      "name": "spring-boot-starter-web",
      "groupId": "org.springframework.boot",
      "artifactId": "spring-boot-starter-web",
      "version": "3.3.5",
      "status": "USED",
      "category": "web"
    }
  ],
  "unusedStarters": [
    {
      "name": "spring-boot-starter-data-redis",
      "groupId": "org.springframework.boot",
      "artifactId": "spring-boot-starter-data-redis",
      "version": "3.3.5",
      "status": "UNUSED",
      "category": "data"
    }
  ],
  "indeterminateStarters": [],
  "matchedAutoConfigCount": 45,
  "excludedAutoConfigCount": 12
}
```

## Configuration

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `management.endpoint.bootusage.enabled` | boolean | `true` | Enable/disable the bootusage endpoint |
| `spring.boot.usage.enabled` | boolean | `true` | Enable/disable usage analysis |
| `spring.boot.usage.cache-ttl` | long | `60000` | Cache TTL in milliseconds |
| `spring.boot.usage.include-origins` | boolean | `false` | Include bean origin information |
| `spring.boot.usage.include-confidence` | boolean | `false` | Include confidence scores |
| `spring.boot.usage.detect-unused-jars` | boolean | `false` | Detect unused JAR files |
| `spring.boot.usage.markdown-summary` | boolean | `false` | Generate Markdown summary file |
| `spring.boot.usage.output-dir` | String | `null` | Directory for output files |
| `spring.boot.usage.policies-fail-on-violation` | boolean | `false` | Fail startup on policy violations |

### Example Configuration

```yaml
management:
  endpoint:
    bootusage:
      enabled: true

spring:
  boot:
    usage:
      enabled: true
      cache-ttl: 120000
      include-origins: true
      detect-unused-jars: true
      policies-fail-on-violation: false
```

## Usage Policies (SPI)

You can define custom policies to enforce starter usage rules. Implement the `UsagePolicy` interface:

```java
import io.github.dhruv1503.bootusage.autoconfigure.UsagePolicy;
import io.github.dhruv1503.bootusage.autoconfigure.StarterUsageAnalyzer.StarterAnalysisResult;

public class NoUnusedStartersPolicy implements UsagePolicy {

    @Override
    public String getName() {
        return "no-unused-starters";
    }

    @Override
    public String getDescription() {
        return "Ensures no unused starters are present in the application";
    }

    @Override
    public PolicyResult evaluate(StarterAnalysisResult result) {
        if (result.unusedStarters().isEmpty()) {
            return PolicyResult.passed("No unused starters detected");
        }
        return PolicyResult.failed("Found " + result.unusedStarters().size() + 
            " unused starters: " + result.unusedStarters());
    }
}
```

Register your policy via Spring's `META-INF/spring.factories` or as a bean:

```java
@Bean
public UsagePolicy noUnusedStartersPolicy() {
    return new NoUnusedStartersPolicy();
}
```

When `spring.boot.usage.policies-fail-on-violation=true`, any policy violation will cause application startup to fail.

## Requirements

- Java 17 or later
- Spring Boot 3.2.0 or later

## Building from Source

```bash
./gradlew clean build
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

This project was inspired by discussions in [Spring Boot PR #47023](https://github.com/spring-projects/spring-boot/pull/47023).

## Author

- **Dhruv Rastogi** - [dhruv-15-03](https://github.com/dhruv-15-03)
