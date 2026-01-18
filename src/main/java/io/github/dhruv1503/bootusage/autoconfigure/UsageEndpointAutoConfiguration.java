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

import io.github.dhruv1503.bootusage.endpoint.UsageEndpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * Auto-configuration for the Boot Usage {@link Endpoint @Endpoint}.
 * <p>
 * This configuration is loaded after {@link UsageAnalysisAutoConfiguration}
 * and exposes the {@code /actuator/bootusage} endpoint.
 *
 * @author Dhruv Rastogi
 * @since 1.0.0
 * @see UsageEndpoint
 */
@AutoConfiguration(after = UsageAnalysisAutoConfiguration.class)
@ConditionalOnClass(Endpoint.class)
@ConditionalOnBean(UsageReportService.class)
@ConditionalOnProperty(prefix = "spring.boot.usage.report", name = "enabled", havingValue = "true")
public class UsageEndpointAutoConfiguration {

	/**
	 * Create the {@link UsageEndpoint}.
	 * @param service the usage report service
	 * @return the usage endpoint
	 */
	@Bean
	@ConditionalOnMissingBean
	public UsageEndpoint usageEndpoint(UsageReportService service) {
		return new UsageEndpoint(service);
	}

}
