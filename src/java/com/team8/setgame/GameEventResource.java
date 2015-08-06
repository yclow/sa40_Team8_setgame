package com.team8.setgame;

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

@RequestScoped
@Path("/gameevent/{gid}")
public class GameEventResource {

    @Inject
    private GameRepository repository;

    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public Response get(@PathParam("gid") String gid) {
        Optional<Game> opt = repository.getGame(gid);
        JsonObject jbuild=null;
        if (!opt.isPresent()) { 
            return (Response.status(Response.Status.NOT_FOUND).entity("Game not found: " + gid).build());
        }
        Game g = opt.get();
        EventOutput eo = new EventOutput();
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        List<Card> table = g.getTable();
        for (Card z : table) {
            jbuild = Json.createObjectBuilder()
                    .add("Number",z.getNumber())
                    .build();
            arrBuilder.add(jbuild);
        }
            OutboundEvent ooe = new OutboundEvent.Builder()
                    .mediaType(MediaType.APPLICATION_JSON_TYPE)
                    .data(JsonObject.class, arrBuilder.build())
                    .build();
            
            g.add(eo,ooe);
            return (Response.ok(eo).build());
        }
    }
