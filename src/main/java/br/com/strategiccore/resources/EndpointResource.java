package br.com.strategiccore.resources;

import br.com.strategiccore.entities.Endpoint;
import br.com.strategiccore.repositories.EndpointRepository;

import javax.ws.rs.Path;

/**
 * @author Eduardo Folly
 */
@Path("/endpoint")
public class EndpointResource
        extends AbstractResource<EndpointRepository, Endpoint> {

}
