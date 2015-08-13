package com.team8.setgame;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/game")
@Produces(MediaType.APPLICATION_JSON)
public class GameResource {

    @Inject
    private GameRepository repository;

    @GET
    public Response get() {
        Set<String> gids = repository.getGames();
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (String s : gids) {
            builder.add(s);
        }
        return (Response.ok(builder.build()).build());
    }

    @GET
    @Path("/{gid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getTableCards(@PathParam("gid") String gid) {
        Optional<Game> g = repository.getGame(gid);
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        System.out.println(gid);
        if (g.get() != null) {
            List<Card> table = g.get().getTable();
            for (Card z : table) {
                System.out.println(z.getuID());
                arrBuilder.add(z.getuID());
            }
            return (Response.ok(arrBuilder.build()).build());
        } else {
            return Response.noContent().build();
        }
    }
    
//    @GET
//    @Path("/status")
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response gameStatus(HttpServletRequest req){
//        String cmd = req.getParameter("cmd");
//        System.out.println(cmd);
//        if ("newGame".equals(cmd)) {
//            Game g = new Game();
//            repository.add(g);
//            System.out.println(g.gameId());
//            return (Response.ok(g.gameId()).build());
//
//    }
//        return (Response.noContent().build());
//    }
}
