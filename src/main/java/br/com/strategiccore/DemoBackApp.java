package br.com.strategiccore;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(info = @Info(
        title = "Demo Back",
        version = "0.0.1",
        contact = @Contact(
                name = "Eduardo Folly",
                email = "edufolly@gmail.com"
        ),
        description = "Quarkus demo backend application."))
public class DemoBackApp extends Application {
}
