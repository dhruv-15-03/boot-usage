# Boot Usage Spring Boot Starter

[![Build Status](https://github.com/dhruv-15-03/boot-usage/actions/workflows/build.yml/badge.svg)](https://github.com/dhruv-15-03/boot-usage/actions/workflows/build.yml)
[![JitPack](https://jitpack.io/v/dhruv-15-03/boot-usage.svg)](https://jitpack.io/#dhruv-15-03/boot-usage)
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
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.dhruv-15-03</groupId>
    <artifactId>boot-usage</artifactId>
    <version>v1.0.3</version>
</dependency>
```

### Gradle

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.dhruv-15-03:boot-usage:v1.0.3'
}
```

## Usage

The feature is **disabled by default**. To turn it on, set `spring.boot.usage.report.enabled=true`
and expose the `bootusage` actuator endpoint over the web:

```yaml
spring:
  boot:
    usage:
      report:
        enabled: true

management:
  endpoints:
    web:
      exposure:
        include: bootusage
```

### Accessing the Endpoint

```bash
# Get usage analysis
curl http://localhost:8080/actuator/bootusage

# Force cache refresh
curl http://localhost:8080/actuator/bootusage?force=true
```

### Sample Response

> The structure below reflects the actual endpoint output. The `beanOrigins` section is
> only present when `spring.boot.usage.report.include-origins=true`.

```json
{
  "schemaVersion": "1.0.0",
  "metadata": {
    "generatedAt": "2025-01-15T12:34:56.789Z",
    "schemaVersion": "1.0.0",
    "springBootVersion": "3.3.5",
    "javaVersion": "21.0.5",
    "applicationName": "my-app",
    "activeProfiles": ["default"],
    "enabledFeatures": ["bean-origins", "unused-jar-detection"]
  },
  "configuration": {
    "enabled": true,
    "includeOrigins": true,
    "includeConfidence": false,
    "detectUnusedJars": true,
    "markdownSummary": false,
    "outputDir": "build/boot-usage",
    "policiesFailOnViolation": false,
    "cacheTtlMs": 0
  },
  "autoConfiguration": {
    "applied": ["org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration"],
    "appliedCount": 45,
    "skipped": ["org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration"],
    "skippedCount": 12,
    "skippedDetails": [
      {
        "className": "org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration",
        "reasons": ["@ConditionalOnClass did not find required class 'org.springframework.data.redis.core.RedisOperations'"]
      }
    ],
    "exclusions": []
  },
  "starters": {
    "used": [
      {
        "name": "spring-boot-starter-web",
        "coordinate": "org.springframework.boot:spring-boot-starter-web:3.3.5",
        "groupId": "org.springframework.boot",
        "artifactId": "spring-boot-starter-web",
        "version": "3.3.5",
        "category": "web",
        "location": "spring-boot-starter-web-3.3.5.jar",
        "status": "USED"
      }
    ],
    "usedCount": 1,
    "unused": [],
    "unusedCount": 0,
    "indeterminate": [],
    "indeterminateCount": 0,
    "totalDetected": 1,
    "analysisMetadata": {
      "matchedAutoConfigurations": 45,
      "excludedAutoConfigurations": 12
    }
  },
  "beanOrigins": [
    {
      "bean": "myController",
      "definitionLocation": "com/example/MyController.class",
      "codeSource": "/app/classes",
      "type": "com.example.MyController",
      "scope": "singleton"
    }
  ],
  "suggestions": {
    "unusedStarters": [],
    "optimizationTips": []
  },
  "summary": {
    "totalBeans": 180,
    "singletonBeans": 178,
    "prototypeBeans": 2,
    "appliedAutoConfigurations": 45,
    "skippedAutoConfigurations": 12,
    "totalStarters": 1,
    "usedStarters": 1,
    "unusedStarters": 0,
    "indeterminateStarters": 0,
    "starterEfficiencyPercent": 100
  },
  "policyViolations": []
}
```

## Configuration

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `spring.boot.usage.report.enabled` | boolean | `false` | Master switch. Enables report generation and the `bootusage` endpoint. |
| `spring.boot.usage.report.cache-ttl` | long (ms) | `0` | Endpoint cache TTL in milliseconds. `0` regenerates the report on every request. |
| `spring.boot.usage.report.include-origins` | boolean | `false` | Include sanitized bean origin locations in the report. |
| `spring.boot.usage.report.include-confidence` | boolean | `false` | Include heuristic confidence scores in suggestions. |
| `spring.boot.usage.report.detect-unused-jars` | boolean | `false` | Best-effort detection of unused JARs on the classpath. |
| `spring.boot.usage.report.markdown-summary` | boolean | `false` | Also write a Markdown summary to the output directory. |
| `spring.boot.usage.report.output-dir` | String | `build/boot-usage` | Output directory for persisted reports. |
| `spring.boot.usage.report.policies-fail-on-violation` | boolean | `false` | Fail startup if any usage policy returns violations. |

> The `bootusage` endpoint must also be exposed via
> `management.endpoints.web.exposure.include=bootusage` to be reachable over HTTP.

### Example Configuration

```yaml
spring:
  boot:
    usage:
      report:
        enabled: true                 # required - off by default
        cache-ttl: 5000               # cache the report for 5s (ms); 0 = no cache
        include-origins: true
        detect-unused-jars: true
        markdown-summary: true
        output-dir: build/boot-usage
        policies-fail-on-violation: false

management:
  endpoints:
    web:
      exposure:
        include: bootusage            # expose the endpoint over HTTP
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

Register your policy as a Spring bean:

```java
@Bean
public UsagePolicy noUnusedStartersPolicy() {
    return new NoUnusedStartersPolicy();
}
```

When `spring.boot.usage.report.policies-fail-on-violation=true`, any policy violation will cause application startup to fail.

## Requirements

- Java 21 or later
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
