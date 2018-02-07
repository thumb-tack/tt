package org.jboss.unimbus.jms.artemis;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.unimbus.condition.RequiredClassPresent;

/**
 * Created by bob on 2/7/18.
 */
@ApplicationScoped
@RequiredClassPresent("org.apache.artemis.client.cdi.configuration.ArtemisClientConfiguration")
public class ArtemisClientConfiguration implements org.apache.artemis.client.cdi.configuration.ArtemisClientConfiguration {

    @Override
    public String getUsername() {
        return this.username.orElse(null);
    }

    @Override
    public String getPassword() {
        return this.password.orElse(null);
    }

    @Override
    public String getUrl() {
        return this.url.orElse(null);
    }

    @Override
    public String getHost() {
        return this.host.orElse(null);
    }

    @Override
    public Integer getPort() {
        return this.port.orElse(null);
    }

    @Override
    public String getConnectorFactory() {
        return REMOTE_CONNECTOR;
    }

    @Override
    public boolean startEmbeddedBroker() {
        return false;
    }

    @Override
    public boolean isHa() {
        return false;
    }

    @Override
    public boolean hasAuthentication() {
        return (this.username.isPresent());
    }

    @Inject
    @ConfigProperty(name = "artemis.username")
    Optional<String> username;

    @Inject
    @ConfigProperty(name = "artemis.password")
    Optional<String> password;

    @Inject
    @ConfigProperty(name = "artemis.url")
    Optional<String> url;

    @Inject
    @ConfigProperty(name = "artemis.host")
    Optional<String> host;

    @Inject
    @ConfigProperty(name = "artemis.port")
    Optional<Integer> port;
}
