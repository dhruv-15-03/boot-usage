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

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

/**
 * SPI for defining usage policies that validate the generated report.
 * <p>
 * Implementations can inspect the usage report and return a {@link PolicyResult}
 * containing violations and/or warnings. When
 * {@code spring.boot.usage.report.policies-fail-on-violation=true}, any violations
 * will cause the application to fail at startup.
 *
 * <h2>Example Implementation</h2>
 * <pre>{@code
 * @Component
 * public class NoDevToolsInProductionPolicy implements UsagePolicy {
 *     @Override
 *     public PolicyResult evaluate(Map<String, Object> report,
 *             ApplicationContext context, Environment env) {
 *         if ("prod".equals(env.getProperty("spring.profiles.active"))) {
 *             Map<String, Object> starters = (Map<String, Object>) report.get("starters");
 *             List<Map<String, Object>> used = (List<Map<String, Object>>) starters.get("used");
 *             boolean hasDevTools = used.stream()
 *                     .anyMatch(s -> "spring-boot-devtools".equals(s.get("artifactId")));
 *             if (hasDevTools) {
 *                 return PolicyResult.violation("DevTools starter is not allowed in production");
 *             }
 *         }
 *         return PolicyResult.ok();
 *     }
 * }
 * }</pre>
 *
 * @author Dhruv Rastogi
 * @since 1.0.0
 * @see PolicyResult
 * @see UsagePolicyEnforcer
 */
@FunctionalInterface
public interface UsagePolicy {

	/**
	 * Evaluates the usage report and returns any policy issues.
	 * @param report the generated usage report
	 * @param context the Spring application context
	 * @param environment the Spring environment
	 * @return the policy result containing violations and/or warnings
	 */
	PolicyResult evaluate(Map<String, Object> report, ApplicationContext context, Environment environment);

	/**
	 * Result of a policy evaluation.
	 *
	 * @param violations list of violation messages that will fail startup
	 * @param warnings list of warning messages that are logged but don't fail startup
	 */
	record PolicyResult(List<String> violations, List<String> warnings) {

		/**
		 * Creates a result indicating no issues.
		 * @return an empty policy result
		 */
		public static PolicyResult ok() {
			return new PolicyResult(Collections.emptyList(), Collections.emptyList());
		}

		/**
		 * Creates a result with a single violation.
		 * @param message the violation message
		 * @return a policy result with one violation
		 */
		public static PolicyResult violation(String message) {
			return new PolicyResult(List.of(message), Collections.emptyList());
		}

		/**
		 * Creates a result with multiple violations.
		 * @param messages the violation messages
		 * @return a policy result with violations
		 */
		public static PolicyResult violations(List<String> messages) {
			return new PolicyResult(messages, Collections.emptyList());
		}

		/**
		 * Creates a result with a single warning.
		 * @param message the warning message
		 * @return a policy result with one warning
		 */
		public static PolicyResult warning(String message) {
			return new PolicyResult(Collections.emptyList(), List.of(message));
		}

		/**
		 * Creates a result with multiple warnings.
		 * @param messages the warning messages
		 * @return a policy result with warnings
		 */
		public static PolicyResult warnings(List<String> messages) {
			return new PolicyResult(Collections.emptyList(), messages);
		}

		/**
		 * Check if this result has any violations.
		 * @return true if there are violations
		 */
		public boolean hasViolations() {
			return !this.violations.isEmpty();
		}

		/**
		 * Check if this result has any warnings.
		 * @return true if there are warnings
		 */
		public boolean hasWarnings() {
			return !this.warnings.isEmpty();
		}

	}

}
