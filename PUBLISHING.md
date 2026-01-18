# Publishing to Maven Central

This guide explains how to publish boot-usage-spring-boot-starter to Maven Central.

## Prerequisites

### 1. Create a Sonatype OSSRH Account

1. Go to https://central.sonatype.org/register/central-portal/
2. Sign in with GitHub (recommended) or create account
3. Verify your namespace: `io.github.dhruv1503`
   - Since you're using `io.github.dhruv1503`, verification is automatic via GitHub

### 2. Generate GPG Keys

```bash
# Install GPG (Windows: use Gpg4win)
# Generate a key pair
gpg --full-generate-key

# Choose: RSA and RSA, 4096 bits, no expiration
# Enter: Dhruv Rastogi <dhruvrastogi2004@gmail.com>

# List your keys
gpg --list-secret-keys --keyid-format LONG

# Export your public key to a keyserver
gpg --keyserver keyserver.ubuntu.com --send-keys YOUR_KEY_ID
gpg --keyserver keys.openpgp.org --send-keys YOUR_KEY_ID

# Export private key for CI (base64 encoded)
gpg --export-secret-keys YOUR_KEY_ID | base64 > private-key.txt
```

### 3. Configure GitHub Secrets

Go to https://github.com/dhruv-15-03/boot-usage/settings/secrets/actions

Add these secrets:

| Secret Name | Value |
|-------------|-------|
| `OSSRH_USERNAME` | Your Sonatype Central username |
| `OSSRH_PASSWORD` | Your Sonatype Central token |
| `GPG_PRIVATE_KEY` | Base64 encoded private key |
| `GPG_PASSPHRASE` | Your GPG key passphrase |

### 4. Publish a Release

```bash
# Update version in build.gradle (remove -SNAPSHOT for release)
# Commit and tag
git add .
git commit -m "Release v1.0.0"
git tag v1.0.0
git push origin main --tags
```

The GitHub Actions release workflow will:
1. Build the project
2. Sign artifacts with GPG
3. Publish to Maven Central

### 5. Manual Publishing (Alternative)

```bash
# Set environment variables
export OSSRH_USERNAME=your-username
export OSSRH_PASSWORD=your-token
export GPG_SIGNING_KEY=your-key-id
export GPG_PASSPHRASE=your-passphrase

# Publish
./gradlew publishToMavenCentral
```

## After Publishing

1. Log into https://central.sonatype.com
2. Go to Deployments
3. Find your staging repository
4. Click "Publish" to release to Maven Central

It takes about 30 minutes to appear on Maven Central, and up to 4 hours to sync to search.maven.org.

## Verification

```bash
# Check if published
curl "https://repo1.maven.org/maven2/io/github/dhruv1503/boot-usage-spring-boot-starter/maven-metadata.xml"
```
