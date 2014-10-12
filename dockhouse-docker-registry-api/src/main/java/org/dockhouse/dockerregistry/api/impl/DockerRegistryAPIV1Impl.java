package org.dockhouse.dockerregistry.api.impl;

import org.dockhouse.dockerregistry.api.DockerRegistryAPI;

/**
 * Created by BWI on 09/10/2014.
 */
public class DockerRegistryAPIV1Impl extends DockerRegistryAPIBase implements DockerRegistryAPI {

    /**
     * Default constructor
     * @param registryURL
     */
    public DockerRegistryAPIV1Impl(String registryURL) {
        super(registryURL);
    }

    /**
     * Return the API version
     * @return API Version
     */
    @Override
    public String getAPIVersion() {
        return "v1";
    }

    /**
     * Return the status of the private registry
     * @return
     */
    @Override
    public boolean isAlive() {
        String endPoint = this.getVersionedURL() + "_ping";

        try {
            String status = restTemplate.getForObject(endPoint,String.class);
            if ("true".equals(status)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }

    }



}
