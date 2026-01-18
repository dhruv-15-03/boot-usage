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

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

/**
 * SPI for customizing the generated usage report before it's exposed or persisted.
 * <p>
 * Implementations can add, modify, or remove entries from the report map.
 * Multiple customizers are applied in order of their bean definition.
 *
 * <h2>Example Implementation</h2>
 * <pre>{@code
 * @Component
 * public class TeamMetadataCustomizer implements UsageReportCustomizer {
 *     @Override
 *     public void customize(Map<String, Object> report,
 *             ApplicationContext context, Environment env) {
 *         Map<String, Object> teamInfo = new LinkedHashMap<>();
 *         teamInfo.put("team", env.getProperty("app.team", "unknown"));
 *         teamInfo.put("environment", env.getProperty("spring.profiles.active", "default"));
 *         report.put("teamMetadata", teamInfo);
 *     }
 * }
 * }</pre>
 *
 * @author Dhruv Rastogi
 * @since 1.0.0
 * @see UsageReportService
 */
@FunctionalInterface
public interface UsageReportCustomizer {

	/**
	 * Customizes the usage report.
	 * @param report the report map to customize (mutable)
	 * @param context the Spring application context
	 * @param environment the Spring environment
	 */
	void customize(Map<String, Object> report, ApplicationContext context, Environment environment);

}
