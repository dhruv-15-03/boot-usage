package io.github.dhruv1503.bootusage.docs;

import java.util.List;
import java.util.Map;

import io.github.dhruv1503.bootusage.autoconfigure.UsagePolicy;
import io.github.dhruv1503.bootusage.autoconfigure.UsagePolicy.PolicyResult;

import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

/**
 * Compile-time copy of the UsagePolicy example in README.md.
 */
public class NoUnusedStartersPolicy implements UsagePolicy {

	@Override
	@SuppressWarnings("unchecked")
	public PolicyResult evaluate(Map<String, Object> report,
			ApplicationContext context, Environment environment) {
		Map<String, Object> starters = (Map<String, Object>) report.get("starters");
		List<Map<String, Object>> unused = (List<Map<String, Object>>) starters.get("unused");
		if (unused.isEmpty()) {
			return PolicyResult.ok();
		}
		return PolicyResult.violation("Found " + unused.size() + " unused starters: "
				+ unused.stream().map(starter -> starter.get("artifactId")).toList());
	}

}
