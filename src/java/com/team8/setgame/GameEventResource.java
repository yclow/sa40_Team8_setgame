package com.team8.setgame;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
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
    
    @Inject private GameRepository repository;
    
    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public Response get(@PathParam("gid") String gid) {
        System.out.println(">>> Getting EO with this = " + gid);
        Optional<Game> opt = repository.getGame(gid);
        if (!opt.isPresent())
            return (Response.status(Response.Status.NOT_FOUND).entity("Game not found: " + gid).build());
        Game g = opt.get();
        System.out.println("EO has been built");
        return (Response.ok(g.getEo()).build());
        
    }
}
