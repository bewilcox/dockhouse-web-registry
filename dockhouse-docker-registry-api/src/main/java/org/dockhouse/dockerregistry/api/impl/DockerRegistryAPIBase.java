package org.dockhouse.dockerregistry.api.impl;

import org.springframework.web.client.RestTemplate;

/**
 * Created by BWI on 11/10/2014.
 */
public abstract class DockerRegistryAPIBase {

    protected RestTemplate restTemplate;
    protected String registryURL;

    public DockerRegistryAPIBase(String registryURL) {
        if (!registryURL.endsWith("/")) {
            registryURL += "/";
        }
        this.registryURL = registryURL;
    }


    /**
     * Return the API version
     * @return
     */
    public abstract String getAPIVersion();

    /**
     * Formatted url form host and port
     * @return http://host:port/
     */
    protected String getVersionedURL() {
        return this.registryURL + this.getAPIVersion() + "/";
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }
        return restTemplate;
    }
}
