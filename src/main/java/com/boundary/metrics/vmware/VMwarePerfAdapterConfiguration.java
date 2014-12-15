// Copyright 2014 Boundary, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.boundary.metrics.vmware;

import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.boundary.metrics.vmware.client.client.meter.manager.MeterManagerClient;
import com.boundary.metrics.vmware.client.metrics.MetricsClient;
import com.boundary.metrics.vmware.poller.MonitoredEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.jersey.api.client.Client;

/**
 * Handles configuration of the VMware Adapter Configuration
 */
public class VMwarePerfAdapterConfiguration extends Configuration {

    static class MeterManagerConfiguration {
        @NotNull
        @JsonProperty
        private URI baseUri = URI.create("https://api.boundary.com");

        @NotEmpty
        @JsonProperty
        private String apiKey;

        public URI getBaseUri() { return baseUri; }

        public String getApiKey() {
            return apiKey;
        }

        public MeterManagerClient build(Client httpClient) {
            return new MeterManagerClient(httpClient, getBaseUri(), getApiKey());
        }
    }

    static class MetricClientConfiguration {
        @NotNull
        @JsonProperty
        private URI baseUri = URI.create("https://metrics-api.boundary.com");

        public URI getBaseUri() { return baseUri; }

        @JsonProperty
        @NotEmpty
        private String apiKey;

        public String getApiKey() {
            return apiKey;
        }

        public MetricsClient build(Client httpClient) {
            return new MetricsClient(httpClient, getBaseUri(), apiKey);
        }
    }

    @JsonProperty
    @Valid
    @NotNull
    private List<MonitoredEntity> monitoredEntities;

    /**
     * Returns a list of monitoried entities
     * @return {@link MonitoredEntity}
     */
    public List<MonitoredEntity> getMonitoredEntities() {
        return monitoredEntities;
    }

    @Valid
    @NotNull
    @JsonProperty
    private JerseyClientConfiguration client = new JerseyClientConfiguration();

    /**
     * Returns the Jesery client configuration
     * @return {@link JerseyClientConfiguration}
     */
    public JerseyClientConfiguration getClient() { return client; }

    @JsonProperty
    @Valid
    @NotNull
    private MeterManagerConfiguration meterManagerClient = new MeterManagerConfiguration();

    /**
     * Returns the meter manager configuration
     * 
     * @return {@link MeterManagerConfiguration}
     */
    public MeterManagerConfiguration getMeterManagerClient() {
        return meterManagerClient;
    }

    @JsonProperty
    @Valid
    @NotNull
    private MetricClientConfiguration metricsClient = new MetricClientConfiguration();
    
    /**
     * Returns the {@link MetricClientConfiguration} instances associated from the configuration
     * @return {@link MetricClientConfiguration}
     */
    public MetricClientConfiguration getMetricsClient() {
        return metricsClient;
    }

    @JsonProperty
    @NotEmpty
    private String orgId;

    /**
     * Returns the Boundary organization id associated with the metric client
     * @return {@link String}
     */
    public String getOrgId() {
        return orgId;
    }
}
