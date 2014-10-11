package org.dockhouse.dockerregistry.api.impl;


import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withNoContent;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


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
}