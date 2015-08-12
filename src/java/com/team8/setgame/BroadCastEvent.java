/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team8.setgame;

import java.util.Optional;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.media.sse.OutboundEvent;

/**
 *
 * @author a0136322r
 */
public class BroadCastEvent implements Runnable {

    private String gid;
    private Game g;
    @Inject
    private GameRepository repository;

    public BroadCastEvent(String id,Game ga)
    {
        g=ga;
        gid=id;
    }

    @Override
    public void run() {
        System.out.println("Is the code running here?"+g.gameId());
        JsonObject jbuild = null;
        JsonArrayBuilder grrBuilder = Json.createArrayBuilder();
        System.out.println(g.getTable().size());
        
        for (Card z : g.getTable()) {
            jbuild = Json.createObjectBuilder()
                    .add("ID", z.getuID())
                    .build();
            grrBuilder.add(jbuild);
        }
        System.out.println(">>> json = " + jbuild);
        OutboundEvent data = new OutboundEvent.Builder()
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .data(JsonObject.class, grrBuilder.build())
                .build();
        g.getBroadcaster().add(g.getEo());
        g.getBroadcaster().broadcast(data);
        System.out.println("End of Message");
    }
}
