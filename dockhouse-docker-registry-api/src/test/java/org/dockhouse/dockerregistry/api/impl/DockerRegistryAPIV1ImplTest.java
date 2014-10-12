package org.dockhouse.dockerregistry.api.impl;


import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.DefaultResponseCreator;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;


public class DockerRegistryAPIV1ImplTest {

    private RestTemplate restTemplate;
    @Before
    public void setUp() {
        this.restTemplate = new RestTemplate();
    }

    @Test
    public void testVersionedURL() throws Exception {
        DockerRegistryAPIV1Impl dockerRegistryAPIV1 = new DockerRegistryAPIV1Impl("http://localhost:5000");
        assertEquals("http://localhost:5000/v1/",dockerRegistryAPIV1.getVersionedURL());

        dockerRegistryAPIV1 = new DockerRegistryAPIV1Impl("http://localhost:5000/");
        assertEquals("http://localhost:5000/v1/",dockerRegistryAPIV1.getVersionedURL());

    }

    @Test
    public void testIsAliveSuccessfull() throws Exception {
        String registryURL1 = "http://localhost:5000";
        DockerRegistryAPIV1Impl dockerRegistryAPIV1 = new DockerRegistryAPIV1Impl(registryURL1);

        // Mocking service
        MockRestServiceServer mockServer  = MockRestServiceServer.createServer(this.restTemplate);
        dockerRegistryAPIV1.setRestTemplate(this.restTemplate);

        // Mock build
        mockServer
                .expect(requestTo(dockerRegistryAPIV1.getVersionedURL() + "_ping"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("true", MediaType.APPLICATION_JSON));

        // Call service
        boolean isAlive = dockerRegistryAPIV1.isAlive();
        mockServer.verify();

        // Assert
        assertTrue(isAlive);

    }

    @Test
    public void testIsAliveFailed() throws Exception {
        String registryURL1 = "http://localhost:5000";
        DockerRegistryAPIV1Impl dockerRegistryAPIV1 = new DockerRegistryAPIV1Impl(registryURL1);

        // Mocking service
        MockRestServiceServer mockServer  = MockRestServiceServer.createServer(this.restTemplate);
        dockerRegistryAPIV1.setRestTemplate(this.restTemplate);

        // Mock build
        mockServer
                .expect(requestTo(dockerRegistryAPIV1.getVersionedURL() + "_ping"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withNoContent());

        // Call service
        boolean isAlive = dockerRegistryAPIV1.isAlive();
        mockServer.verify();

        // Assert
        assertFalse(isAlive);

    }

    @Test
    public void testGetVersionSuccessful() throws Exception {

        String registryURL1 = "http://localhost:5000";
        String expectedVersion = "0.8.1";
        DockerRegistryAPIV1Impl dockerRegistryAPIV1 = new DockerRegistryAPIV1Impl(registryURL1);

        // Mocking service
        MockRestServiceServer mockServer  = MockRestServiceServer.createServer(this.restTemplate);
        dockerRegistryAPIV1.setRestTemplate(this.restTemplate);

        // Mock build
        DefaultResponseCreator responseCreator = withSuccess("true", MediaType.APPLICATION_JSON);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Docker-Registry-Version", expectedVersion);
        responseCreator.headers(headers);
        mockServer
                .expect(requestTo(dockerRegistryAPIV1.getVersionedURL() + "_ping"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(responseCreator);

        // Call service
        Optional<String> version = dockerRegistryAPIV1.getVersion();
        mockServer.verify();

        // Assert
        assertTrue(version.isPresent());
        assertEquals(expectedVersion, version.get());
    }

    @Test
    public void testGetVersionFailed_withError() throws Exception {
        String registryURL1 = "http://localhost:5000";
        DockerRegistryAPIV1Impl dockerRegistryAPIV1 = new DockerRegistryAPIV1Impl(registryURL1);

        // Mocking service
        MockRestServiceServer mockServer  = MockRestServiceServer.createServer(this.restTemplate);
        dockerRegistryAPIV1.setRestTemplate(this.restTemplate);

        // Mock build
        mockServer
                .expect(requestTo(dockerRegistryAPIV1.getVersionedURL() + "_ping"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withServerError());

        // Call service
        Optional<String> version = dockerRegistryAPIV1.getVersion();
        mockServer.verify();

        // Assert
        assertFalse(version.isPresent());
    }

    @Test
    public void testGetVersionFailed_withNoInfoInHeader() throws Exception {
        String registryURL1 = "http://localhost:5000";
        DockerRegistryAPIV1Impl dockerRegistryAPIV1 = new DockerRegistryAPIV1Impl(registryURL1);

        // Mocking service
        MockRestServiceServer mockServer  = MockRestServiceServer.createServer(this.restTemplate);
        dockerRegistryAPIV1.setRestTemplate(this.restTemplate);

        // Mock build
        mockServer
                .expect(requestTo(dockerRegistryAPIV1.getVersionedURL() + "_ping"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("true", MediaType.APPLICATION_JSON));

        // Call service
        Optional<String> version = dockerRegistryAPIV1.getVersion();
        mockServer.verify();

        // Assert
        assertFalse(version.isPresent());
    }
}