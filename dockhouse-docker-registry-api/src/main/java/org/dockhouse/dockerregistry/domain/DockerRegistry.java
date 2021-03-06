package org.dockhouse.dockerregistry.domain;

import org.dockhouse.dockerregistry.api.DockerRegistryAPI;

/**
 * Created by BWI on 11/10/2014.
 */
public class DockerRegistry {

    private String version;
    private String host;
    private String ip;

    // API
    private DockerRegistryAPI dockerRegistryAPI;

    public DockerRegistry(String version, String host, String ip, DockerRegistryAPI dockerRegistryAPI) {
        this.version = version;
        this.host = host;
        this.ip = ip;
        this.dockerRegistryAPI = dockerRegistryAPI;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public DockerRegistryAPI getDockerRegistryAPI() {
        return dockerRegistryAPI;
    }

    public DockerRegistryAPI getAPI() {
        return this.dockerRegistryAPI;
    }
}
