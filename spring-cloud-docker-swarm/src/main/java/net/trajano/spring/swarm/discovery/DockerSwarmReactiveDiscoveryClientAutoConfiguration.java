package net.trajano.spring.swarm.discovery;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.ConditionalOnReactiveDiscoveryEnabled;
import org.springframework.cloud.client.ReactiveCommonsClientAutoConfiguration;
import org.springframework.cloud.client.discovery.composite.reactive.ReactiveCompositeDiscoveryClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
@ConditionalOnReactiveDiscoveryEnabled
@ConditionalOnDockerSwarmDiscoveryEnabled
@AutoConfigureAfter({
    DockerSwarmDiscoveryAutoConfiguration.class,
    ReactiveCompositeDiscoveryClientAutoConfiguration.class
})
@AutoConfigureBefore({
    ReactiveCommonsClientAutoConfiguration.class
})
@Slf4j
public class DockerSwarmReactiveDiscoveryClientAutoConfiguration {

    /**
     * Specifies a comma separated list of networks to look for services that contain the service labels.
     * If not specified it will use all the networks that the container has access to.
     */
    @Value("${docker.discovery.networks:#{null}}")
    private String dockerDiscoveryNetworks;

    @Bean
    @ConditionalOnMissingBean
    public DockerSwarmReactiveDiscoveryClient dockerSwarmReactiveDiscoveryClient(final DockerSwarmDiscovery dockerSwarmDiscovery) {
        log.warn("Building client");
        return new DockerSwarmReactiveDiscoveryClient(dockerSwarmDiscovery);
    }
}
