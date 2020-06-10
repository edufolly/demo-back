package br.com.strategiccore.resources;

import br.com.strategiccore.entities.Endpoint;
import br.com.strategiccore.repositories.EndpointRepository;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.Path;

/**
 * @author Eduardo Folly
 */
@Path("/endpoints")
@Tag(name = "Endpoints", description = "Endpoints Resource.")
public class EndpointResource
        extends AbstractResource<EndpointRepository, Endpoint> {
}
