package io.thorntail.servlet.impl.undertow;

import java.io.IOException;
import java.net.URL;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.resource.Resource;
import io.undertow.server.handlers.resource.ResourceSupplier;
import io.undertow.server.handlers.resource.URLResource;
import io.thorntail.servlet.impl.ResourceProviders;

/**
 * Created by bob on 1/22/18.
 */
@ApplicationScoped
public class InjectedResourceSupplier implements ResourceSupplier {
    @Override
    public Resource getResource(HttpServerExchange exchange, String path) throws IOException {
        return getResource(exchange, path, false);
    }

    protected Resource getResource(HttpServerExchange exchange, String path, boolean exact) throws IOException {
        URL url = providers.getResource(path);
        if (url != null) {
            URLResource resource = new URLResource(url, path);
            if (!resource.isDirectory()) {
                return resource;
            }

            if ( path.endsWith("/")) {
                path = path + "index.html";
            } else {
                path = path + "/index.html";
            }

            Resource indexResource = getResource(exchange, path, true);
            if ( indexResource != null ) {
                return resource;
            }
        }
        return null;
    }

    @Inject
    @Any
    ResourceProviders providers;
}
