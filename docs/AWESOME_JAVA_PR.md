# Pull Request: Add boot-usage-spring-boot-starter to Awesome Java

## PR Title
Add boot-usage-spring-boot-starter to Monitoring section

---

## PR Description

### What is this?
[boot-usage-spring-boot-starter](https://github.com/dhruv-15-03/boot-usage) - Spring Boot Actuator extension that provides application startup and runtime metrics via `/actuator/bootusage` endpoint.

### What makes it unique?
This starter provides detailed boot-time and runtime metrics that are not available in standard Spring Boot Actuator, including:
- JVM uptime and start time
- Heap and non-heap memory usage with percentages
- Active thread count and CPU load
- System load average
- All exposed through a simple REST endpoint

### Entry to add

Add to the **Monitoring** section (in alphabetical order after "Automon"):

```markdown
- [boot-usage-spring-boot-starter](https://github.com/dhruv-15-03/boot-usage) - Spring Boot Actuator extension providing application startup and runtime metrics including JVM uptime, memory usage, and CPU load.
```

---

## Checklist

- [x] Licensed under Apache License 2.0 (open source, not GPL/AGPL)
- [x] Has English documentation (README.md)
- [x] Sorted alphabetically in the Monitoring section
- [x] Description ends with a full stop
- [x] Short, simple, and unbiased description
- [x] Not a duplicate (searched existing entries)
- [x] Individual PR for single suggestion

---

## How to Submit

1. **Fork** the repository: https://github.com/akullpp/awesome-java

2. **Edit** `README.md` - Find the **Monitoring** section and add after "Automon":

```markdown
### Monitoring

_Tools that observe/monitor applications in production by providing telemetry._

- [Apitally](https://github.com/apitally/apitally-java) - Simple, privacy-focused API monitoring, analytics and request logging for Spring Boot apps.
- [Automon](https://github.com/stevensouza/automon) - Combines the power of AOP with monitoring and/or logging tools.
- [boot-usage-spring-boot-starter](https://github.com/dhruv-15-03/boot-usage) - Spring Boot Actuator extension providing application startup and runtime metrics including JVM uptime, memory usage, and CPU load.
- [Datadog ![c]](https://github.com/DataDog/dd-trace-java) - Modern monitoring & analytics.
```

3. **Create PR** with title: `Add boot-usage-spring-boot-starter`

4. **PR Body** (copy this):

```
## Description

Adding [boot-usage-spring-boot-starter](https://github.com/dhruv-15-03/boot-usage) to the Monitoring section.

**What it does:**
- Provides a Spring Boot Actuator endpoint (`/actuator/bootusage`) that exposes application startup and runtime metrics
- Includes JVM uptime, heap/non-heap memory usage percentages, thread count, CPU load, and system load average
- Auto-configures with Spring Boot 3.x

**Why it's noteworthy:**
- Fills a gap in standard Spring Boot Actuator by providing boot-time metrics in a single, easy-to-consume endpoint
- Zero configuration required - just add the dependency
- Lightweight with no external dependencies beyond Spring Boot

**License:** Apache License 2.0

**Documentation:** Available in English at https://github.com/dhruv-15-03/boot-usage#readme
```

---

## Quick Links

- **Your Repository:** https://github.com/dhruv-15-03/boot-usage
- **Awesome Java Fork URL:** https://github.com/akullpp/awesome-java/fork
- **Awesome Java README:** https://github.com/akullpp/awesome-java/blob/master/README.md
- **Awesome Java Contributing:** https://github.com/akullpp/awesome-java/blob/master/CONTRIBUTING.md

---

## Alternative: Direct GitHub PR Link

After forking, you can create a PR directly using this URL format:
```
https://github.com/akullpp/awesome-java/compare/master...YOUR_GITHUB_USERNAME:awesome-java:master?expand=1
```

Replace `YOUR_GITHUB_USERNAME` with `dhruv-15-03`.
