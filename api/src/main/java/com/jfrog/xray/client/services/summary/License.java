package com.jfrog.xray.client.services.summary;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by romang on 2/27/17.
 */
public interface License {
    @JsonProperty("name")
    String getName();

    @JsonProperty("full_name")
    String getFullName();

    @JsonProperty("components")
    List<String> getComponents();
}
