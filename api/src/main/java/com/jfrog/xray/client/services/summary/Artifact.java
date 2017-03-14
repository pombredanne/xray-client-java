package com.jfrog.xray.client.services.summary;

import java.util.List;

/**
 * Created by romang on 2/27/17.
 */
public interface Artifact {
    
    public General getGeneral();

    public List<? extends Issue> getIssues();

    public List<? extends License> getLicenses();
}

