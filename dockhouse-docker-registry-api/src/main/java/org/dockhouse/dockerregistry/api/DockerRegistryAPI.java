package org.dockhouse.dockerregistry.api;

/**
 * Interface for the Remote Docker registry  REST API
 * Created by BWI on 09/10/2014.
 */
public interface DockerRegistryAPI {

    /**
     * Return the API version
     * @return API Version
     */
    public String getAPIVersion();

    /**
     * Return the status of the private registry
     * @return
     */
    boolean isAlive();

}
