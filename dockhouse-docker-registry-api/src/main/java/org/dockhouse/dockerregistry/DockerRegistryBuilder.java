package org.dockhouse.dockerregistry;

import org.dockhouse.dockerregistry.api.DockerRegistryAPI;
import org.dockhouse.dockerregistry.api.impl.DockerRegistryAPIV1Impl;
import org.dockhouse.dockerregistry.domain.DockerRegistry;

public class DockerRegistryBuilder {

    private static final String PROTOCOL = "http";
    private int registryVersion;
    private String host;
    private String ip;
    private DockerRegistryAPI dockerRegistryAPI;

    public DockerRegistryBuilder setRegistryVersion(int registryVersion) {
        this.registryVersion = registryVersion;
        return this;
    }

    public DockerRegistryBuilder setHost(String host) {
        this.host = host;
        return this;
    }

    public DockerRegistryBuilder setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public DockerRegistry create() {
        return new DockerRegistry(registryVersion, host, ip, dockerRegistryAPI);
    }

    private void getAPIFromVersion() {
        // for the moment, there exist only the V1 API registryVersion
        this.dockerRegistryAPI = new DockerRegistryAPIV1Impl(PROTOCOL+"://"+host+":"+ip);
    }
}