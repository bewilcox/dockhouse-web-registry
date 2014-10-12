package org.dockhouse.dockerregistry;

import org.dockhouse.dockerregistry.api.DockerRegistryAPI;
import org.dockhouse.dockerregistry.api.impl.DockerRegistryAPIV1Impl;
import org.dockhouse.dockerregistry.domain.DockerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DockerRegistryBuilder {

    private final Logger log = LoggerFactory.getLogger(DockerRegistryBuilder.class);

    private static final String PROTOCOL = "http";
    private String registryVersion;
    private String host;
    private String ip;
    private DockerRegistryAPI dockerRegistryAPI;


    public DockerRegistryBuilder setHost(String host) {
        this.host = host;
        return this;
    }

    public DockerRegistryBuilder setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public DockerRegistry create() {
        log.info("Build a DockerRegistry : ");
        log.info(" -- Host : {}", host);
        log.info(" -- IP : {}", ip);
        // Search version and choose the correct API version
        this.getAPIFromVersion();
        log.info(" -- Version : {}", registryVersion);
        log.info(" -- API Version : {}", this.dockerRegistryAPI.getAPIVersion());
        return new DockerRegistry(registryVersion, host, ip, dockerRegistryAPI);
    }

    private void getAPIFromVersion() {

        // Get the default api
        DockerRegistryAPIV1Impl dockerRegistryAPIV1 = new DockerRegistryAPIV1Impl(PROTOCOL+"://"+host+":"+ip);
        Optional<String> version = dockerRegistryAPIV1.getVersion();
        if (version.isPresent()) {
            this.registryVersion = version.get();
        } else {
            log.warn("Enabled to retrieve the registry version. Use the API V1 by default");
            this.registryVersion = "Not identified";
            this.dockerRegistryAPI = dockerRegistryAPIV1;
        }

        // for the moment, there exist only the V1 API registryVersion
        this.dockerRegistryAPI = dockerRegistryAPIV1;
    }
}