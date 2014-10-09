package org.dockhouse.dockerregistry.api.impl;

import org.dockhouse.dockerregistry.api.DockerRegistryAPI;
import org.springframework.web.client.RestTemplate;

/**
 * Created by BWI on 09/10/2014.
 */
public class DockerRegistryAPIImpl implements DockerRegistryAPI {

    private RestTemplate restTemplate = new RestTemplate();

    /**
     * Return the status of the private registry
     * @return
     */
    @Override
    public boolean isAlive() {
        String endPoint = REGISTRY_URL + "/" + REGISTRY_API_VERSION + "/_ping";
        String status = restTemplate.getForObject(endPoint,String.class);
        return Boolean.getBoolean(status);
    }

}
