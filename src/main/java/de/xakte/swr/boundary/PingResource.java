package de.xakte.swr.boundary;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import de.xakte.swr.control.PingControl;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static de.xakte.swr.boundary.Util.json;

@Path("/ping")
@Api(value = "/ping", description = "Ping service with echo and echo reverse")
@Produces(MediaType.APPLICATION_JSON)
public class PingResource {

    @Inject
    private PingControl pingControl;

    @GET
    @Path("/reverse/{message}")
    @ApiOperation(value = "reverse", notes = "Reverse echo")
    public Response reverse(@ApiParam(value = "Reverse echo message") @PathParam("message") String message) {
        String reverse = pingControl.reverse(message);

        return Response
                .ok(json("Reverse echo message", reverse))
                .build();
    }

    @GET
    @Path("/echo/{message}")
    @ApiOperation(value = "echo", notes = "Echo")
    public Response echo(@ApiParam(value = "Echo message") @PathParam("message") String message) {
        String echo = pingControl.echo(message);

        JsonObject value = Json.createObjectBuilder().add("Echo message", echo).build();

        return Response
                .ok(json("Echo message", echo))
                .build();
    }
}
