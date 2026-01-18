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

package io.github.dhruv1503.bootusage.endpoint;

import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.lang.Nullable;

import io.github.dhruv1503.bootusage.autoconfigure.UsageReportService;

/**
 * {@link Endpoint @Endpoint} to expose application usage information.
 * <p>
 * This endpoint provides insights into starter usage, auto-configuration status,
 * bean origins, and optimization suggestions beyond what {@code /actuator/conditions}
 * offers.
 *
 * @author Dhruv Rastogi
 * @since 1.0.0
 * @see UsageReportService
 */
@Endpoint(id = "bootusage")
public class UsageEndpoint {

	private final UsageReportService reportService;

	/**
	 * Create a new {@code UsageEndpoint}.
	 * @param reportService the usage report service
	 */
	public UsageEndpoint(UsageReportService reportService) {
		this.reportService = reportService;
	}

	/**
	 * Return the usage report.
	 * @param force whether to bypass the cache and regenerate the report
	 * @return the usage report
	 */
	@ReadOperation
	public Map<String, Object> usage(@Nullable Boolean force) {
		boolean bypass = (force != null) && force;
		return this.reportService.generateReport(bypass);
	}

}
