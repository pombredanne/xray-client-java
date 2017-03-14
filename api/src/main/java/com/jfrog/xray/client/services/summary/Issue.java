package com.jfrog.xray.client.services.summary;

import java.util.List;

/**
 * Created by romang on 2/27/17.
 */
public interface Issue {

    String getSummary();

    String getDescription();

    String getIssueType();

    String getSeverity();

    String getProvider();

    String getCreated();

    List<String> getImpactPath();
}
