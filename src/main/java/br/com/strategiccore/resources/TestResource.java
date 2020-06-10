package br.com.strategiccore.resources;

import br.com.strategiccore.entities.TestEntity;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/test")
@Tag(name = "Test")
public class TestResource {

    @POST
    @Transactional
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(TestEntity testEntity) {
        TestEntity.persist(testEntity);
        return Response.ok(testEntity).build();
    }
}
