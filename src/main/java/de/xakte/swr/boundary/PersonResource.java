package de.xakte.swr.boundary;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import de.xakte.swr.beans.Person;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static de.xakte.swr.beans.Gender.FEMALE;
import static de.xakte.swr.boundary.Util.json;

/**
 * Created by Ernst Bunders on 30-6-15.
 */
@Path("/people")
@Api(value = "/people", description = "People service with many exciting possibilities")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    @POST
    @Path("/person/weigh")
    @ApiOperation(value = "weigh", notes = "Weigh a person's value")
    public Response weighPerson(@NotNull @ApiParam(value = "Person to weigh", required = true) Person person) {
        String message = person.getGender() == FEMALE ?
                "I like you, " + person.getName() :
                "Go away, " + person.getName();

        return Response
                .ok(json("weighing", message))
                .build();
    }

    @GET
    @Path("/person")
    @ApiOperation(value = "person", notes = "get a person object in json representation")
    public Response getPerson() {
        Person p = new Person();
        p.setGender(FEMALE);
        p.setName("Ernst");
        return Response
                .ok(p)
                .build();
    }
}
