package io.debezium.configserver.rest;

import io.debezium.configserver.model.FrontendConfig;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.net.URI;

@Path("")
public class FrontendConfigResource {

    public static final String PROPERTY_BASE_URI = "ui.base.uri";
    public static final String PROPERTY_UI_MODE = "ui.mode";
    public static final String FRONTEND_CONFIG_ENDPOINT = "/config.js";
    public static final String DEFAULT_BASE_URI = "http://localhost:8080/api";
    public static final String DEFAULT_UI_MODE = "dev";

    @ConfigProperty(name = PROPERTY_BASE_URI, defaultValue = DEFAULT_BASE_URI)
    URI UIBaseURI;

    @ConfigProperty(name = PROPERTY_UI_MODE, defaultValue = DEFAULT_UI_MODE)
    String UIMode;

    @Path(FRONTEND_CONFIG_ENDPOINT)
    @GET
    @Produces("application/javascript")
    public String getFrontendConfig() {
        Jsonb jsonb = JsonbBuilder.create();
        var config = new FrontendConfig(UIBaseURI.toString(), FrontendConfig.UIMode.valueOf(UIMode));
        var jsonConfig = jsonb.toJson(config);
        return "window.UI_CONFIG=" + jsonConfig + ";";
    }

}
