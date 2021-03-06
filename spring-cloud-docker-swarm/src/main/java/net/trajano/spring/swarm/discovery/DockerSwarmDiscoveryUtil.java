package net.trajano.spring.swarm.discovery;

import com.github.dockerjava.api.model.Service;
import com.github.dockerjava.api.model.ServiceSpec;

public class DockerSwarmDiscoveryUtil {

    public static String computeDiscoveryServiceId( final Service service) {
        final ServiceSpec serviceSpec = service.getSpec();
        return serviceSpec.getLabels().computeIfAbsent("spring.service.id",
            k -> {
                final String namespace = serviceSpec.getLabels().get("com.docker.stack.namespace");
                final String name = serviceSpec.getName();
                if (namespace != null && name.startsWith(namespace + "_")) {
                    return name.substring(namespace.length() + 1);
                } else {
                    return name;
                }
            });
    }

    private DockerSwarmDiscoveryUtil() {

    }
}
