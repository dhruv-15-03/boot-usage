# Publishing & Distribution

`boot-usage` is distributed through two channels. Pick the right one for your use case:

| Channel | Coordinate | Auth | Notes |
|---------|------------|------|-------|
| **JitPack** (recommended for consumers) | `com.github.dhruv-15-03:boot-usage:v1.0.3` | None | Built on demand from the tagged source. Ready-to-paste snippets are in the [README](README.md). |
| **GitHub Packages** | `io.github.dhruv1503:boot-usage-spring-boot-starter:1.0.3` | GitHub PAT (`read:packages`) | Published automatically by CI on every release tag. |

> **Maven Central is not used.** The artifact is **not** published to `repo1.maven.org`. See [Future: Maven Central](#future-maven-central-not-active) below if that ever changes.

## How a release is published (current)

Releases are automated by [`.github/workflows/release.yml`](.github/workflows/release.yml), which triggers on any pushed `v*` tag:

```bash
# 1. Bump `version` in build.gradle, then:
git commit -am "chore: bump version to X.Y.Z"
git tag vX.Y.Z
git push origin main --tags
```

The workflow then:

1. Builds the project with **JDK 21** (Temurin).
2. Runs `./gradlew publish`, pushing the artifact to **GitHub Packages**
   (`https://maven.pkg.github.com/dhruv-15-03/boot-usage`).
3. Creates a GitHub Release with the built JARs attached.

JitPack needs no release step of its own — it builds the same tag on first request.

### Required secret

| Secret | Used for |
|--------|----------|
| `GH_PAT` | A GitHub Personal Access Token with `write:packages`, used by the GitHub Packages publish step. |

## Consuming the library

Full Maven and Gradle snippets live in the [README](README.md). In short:

- **JitPack (no auth):** add `https://jitpack.io` as a repository and depend on
  `com.github.dhruv-15-03:boot-usage:v1.0.3`.
- **GitHub Packages (auth required):** add `https://maven.pkg.github.com/dhruv-15-03/boot-usage`
  with a GitHub PAT, and depend on `io.github.dhruv1503:boot-usage-spring-boot-starter:1.0.3`.

## Verification

```bash
# JitPack (public, no auth) - should return HTTP 200
curl -sI "https://jitpack.io/com/github/dhruv-15-03/boot-usage/v1.0.3/boot-usage-v1.0.3.pom"
```

## Future: Maven Central (not active)

Maven Central is **not** configured today; `build.gradle` has no `publishToMavenCentral`
task and the release workflow does not target it. If the project later publishes to
Central under the `io.github.dhruv1503` namespace, it would require:

1. A [Sonatype Central](https://central.sonatype.org/register/central-portal/) account
   (namespace `io.github.dhruv1503` is GitHub-verifiable).
2. GPG signing — the `signing` plugin is already wired into `build.gradle` and activates
   when a `SIGNING_KEY` is present.
3. A Central Portal publishing step plus the matching CI secrets
   (`OSSRH_USERNAME`, `OSSRH_PASSWORD`, `GPG_PRIVATE_KEY`, `GPG_PASSPHRASE`).

Until that work is done, treat Maven Central references anywhere as aspirational.