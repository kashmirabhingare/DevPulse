package com.devpulse.ai.prompt;

import java.util.List;

import com.devpulse.dto.DashboardResponse;
import com.devpulse.entity.LogEntry;

public class PromptBuilder {

    private PromptBuilder() {
    }

    public static String buildPrompt(DashboardResponse dashboard,
                                     List<LogEntry> recentLogs) {

        StringBuilder prompt = new StringBuilder();

        prompt.append("You are a Senior Java Backend Engineer.\n\n");

        prompt.append("Analyze the following application logs.\n\n");

        prompt.append("Dashboard Summary:\n");
        prompt.append("------------------------------\n");
        prompt.append("Total Logs: ")
              .append(dashboard.getTotalLogs()).append("\n");

        prompt.append("Error Logs: ")
              .append(dashboard.getErrorLogs()).append("\n");

        prompt.append("Warning Logs: ")
              .append(dashboard.getWarnLogs()).append("\n");

        prompt.append("Info Logs: ")
              .append(dashboard.getInfoLogs()).append("\n");

        prompt.append("Debug Logs: ")
              .append(dashboard.getDebugLogs()).append("\n");

        prompt.append("Health Status: ")
              .append(dashboard.getHealthStatus()).append("\n");

        prompt.append("Most Active Service: ")
              .append(dashboard.getMostActiveService()).append("\n\n");

        prompt.append("Recent Logs:\n");
        prompt.append("------------------------------\n");

        int count = 1;

        for (LogEntry log : recentLogs) {

            prompt.append(count++)
                  .append(". ");

            prompt.append("[")
                  .append(log.getLevel())
                  .append("] ");

            prompt.append(log.getService())
                  .append(" - ");

            prompt.append(log.getMessage())
                  .append("\n");

            if (log.getStackTrace() != null &&
                    !log.getStackTrace().isBlank()) {

                prompt.append("StackTrace: ")
                      .append(log.getStackTrace())
                      .append("\n");
            }

            prompt.append("\n");
        }

        prompt.append("""
Analyze the application logs as an experienced Senior Java Backend Engineer.

Return ONLY a valid JSON object.

Do NOT return markdown.

Do NOT wrap the JSON inside ```json.

Do NOT add explanations.

Return EXACTLY this JSON structure.

{
  "summary": "Brief executive summary of the application's health.",

  "rootCause": "Main root cause of the issue.",

  "severity": "LOW",

  "healthScore": 95,

  "suggestedFixes": [
    "Fix 1",
    "Fix 2",
    "Fix 3"
  ],

  "preventionTips": [
    "Tip 1",
    "Tip 2",
    "Tip 3"
  ]
}

Rules:

1. summary must be a string.

2. rootCause must be a string.

3. severity must be one of:

LOW

MEDIUM

HIGH

CRITICAL

4. healthScore must be an integer between 0 and 100.

5. suggestedFixes MUST be a JSON array of strings.

Never return a single string.

6. preventionTips MUST be a JSON array of strings.

Never return a single string.

Use camelCase exactly.

Do NOT use snake_case.

Use:

summary

rootCause

severity

healthScore

suggestedFixes

preventionTips

Return ONLY the JSON object.

""");

        return prompt.toString();
    }
}