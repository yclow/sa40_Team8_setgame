package com.team8.setgame;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseFeature;

@RequestScoped
@Path("/gameevent/{gid}")
public class GameEventResource {
    
    @Inject private GameRepository repository;
    
    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public Response get(@PathParam("gid") String gid) {
        System.out.println(">>> gid request = " + gid);
        Optional<Game> opt = repository.getGame(gid);
        if (!opt.isPresent())
            return (Response.status(Response.Status.NOT_FOUND).entity("Game not found: " + gid).build());
        Game g = opt.get();
        EventOutput eo = new EventOutput();
        g.add(eo);
        return (Response.ok(eo).build());
    }
}
