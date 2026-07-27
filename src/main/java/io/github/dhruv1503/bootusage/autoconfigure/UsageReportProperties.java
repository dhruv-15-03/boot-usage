/*
 * Copyright 2012-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.dhruv1503.bootusage.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;

/**
 * Configuration properties for the Boot Usage Report feature.
 * <p>
 * All properties are prefixed with {@code spring.boot.usage.report}.
 *
 * @author Dhruv Rastogi
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "spring.boot.usage.report")
public class UsageReportProperties {

	/**
	 * Master switch to enable the usage report feature and endpoint.
	 * When enabled, a usage report will be generated on application startup
	 * and the /actuator/bootusage endpoint will be available.
	 */
	private boolean enabled = false;

	/**
	 * Cache TTL for the actuator endpoint in milliseconds.
	 * Set to 0 to disable caching (report is regenerated on each request).
	 * Default: 0 (no cache).
	 */
	private long cacheTtl = 0L;

	/**
	 * Include sanitized bean origin locations in the report.
	 * When enabled, each bean will include information about where it was defined.
	 * Paths are sanitized to remove user-specific directory information.
	 */
	private boolean includeOrigins = false;

	/**
	 * Include heuristic confidence scoring in suggestions.
	 * Adds notes about the reliability of heuristic-based detections.
	 */
	private boolean includeConfidence = false;

	/**
	 * Compatibility field for a feature that was advertised but never implemented.
	 */
	private boolean detectUnusedJars = false;

	/**
	 * Also write a human-readable Markdown summary to the output directory.
	 * The Markdown file provides a formatted view of the usage report.
	 */
	private boolean markdownSummary = false;

	/**
	 * Output directory for persisted reports (JSON and optional Markdown).
	 * Relative paths are resolved from the application's working directory.
	 */
	private String outputDir = "build/boot-usage";

	/**
	 * Fail startup if any usage policy returns violations.
	 * When enabled, the application context will fail to start if policies detect issues.
	 * Use this to enforce architectural constraints.
	 */
	private boolean policiesFailOnViolation = false;

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public long getCacheTtl() {
		return this.cacheTtl;
	}

	public void setCacheTtl(long cacheTtl) {
		this.cacheTtl = cacheTtl;
	}

	public boolean isIncludeOrigins() {
		return this.includeOrigins;
	}

	public void setIncludeOrigins(boolean includeOrigins) {
		this.includeOrigins = includeOrigins;
	}

	public boolean isIncludeConfidence() {
		return this.includeConfidence;
	}

	public void setIncludeConfidence(boolean includeConfidence) {
		this.includeConfidence = includeConfidence;
	}

	/**
	 * @return the configured legacy value; it has no effect
	 * @deprecated unused JAR detection is not implemented
	 */
	@Deprecated
	@DeprecatedConfigurationProperty(reason = "Unused JAR detection is not implemented; this property has no effect.")
	public boolean isDetectUnusedJars() {
		return this.detectUnusedJars;
	}

	/**
	 * @param detectUnusedJars the legacy value; it has no effect
	 * @deprecated unused JAR detection is not implemented
	 */
	@Deprecated
	public void setDetectUnusedJars(boolean detectUnusedJars) {
		this.detectUnusedJars = detectUnusedJars;
	}

	public boolean isMarkdownSummary() {
		return this.markdownSummary;
	}

	public void setMarkdownSummary(boolean markdownSummary) {
		this.markdownSummary = markdownSummary;
	}

	public String getOutputDir() {
		return this.outputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public boolean isPoliciesFailOnViolation() {
		return this.policiesFailOnViolation;
	}

	public void setPoliciesFailOnViolation(boolean policiesFailOnViolation) {
		this.policiesFailOnViolation = policiesFailOnViolation;
	}

}
