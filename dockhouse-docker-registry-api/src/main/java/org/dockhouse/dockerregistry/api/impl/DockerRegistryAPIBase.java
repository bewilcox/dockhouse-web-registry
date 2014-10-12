package org.dockhouse.dockerregistry.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * Created by BWI on 11/10/2014.
 */
public abstract class DockerRegistryAPIBase {

    private static final String X_DOCKER_REGISTRY_VERSION = "X-Docker-Registry-Version";

    protected final Logger log = LoggerFactory.getLogger(DockerRegistryAPIBase.class);

    protected RestTemplate restTemplate;
    protected String registryURL;

    /**
     * Constructor.
     * @param registryURL
     */
    public DockerRegistryAPIBase(String registryURL) {
        if (!registryURL.endsWith("/")) {
            registryURL += "/";
        }
        this.registryURL = registryURL;
        this.restTemplate = new RestTemplate();
    }

    /**
     * Get the version of the registry. This information is given in the header of the response of the ping request
     * @return Registry version
     */
    public Optional<String> getVersion() {

        log.info("Get the status for the registry {} to extract version from the header",this.registryURL);
        try {
            ResponseEntity<String> response = restTemplate.exchange(this.getVersionedURL() + "_ping", HttpMethod.GET,null, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                HttpHeaders headers = response.getHeaders();
                String version = headers.get(X_DOCKER_REGISTRY_VERSION).get(0);
                log.info("Ping Ok for the registry. The version is {}",version);
                return Optional.of(version);
            } else {
                log.error("Enabled to ping the registry");
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("An error occured during the registry version identification",e);
            return Optional.empty();
        }
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

    /**
     * This setter is useful for inject mock restTemplate for the units tests.
     * @param restTemplate
     */
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

}
