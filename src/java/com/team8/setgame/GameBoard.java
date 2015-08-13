    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team8.setgame;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;


@WebServlet("/board")
public class GameBoard extends HttpServlet{
    
    @Inject private GameRepository repository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String gid = req.getParameter("gid");
        
        Optional<Game> opt = repository.getGame(gid);
        if (!opt.isPresent()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        resp.setContentType(MediaType.APPLICATION_JSON);
        resp.setStatus(HttpServletResponse.SC_OK);
        
        JsonArrayBuilder builder = Json.createArrayBuilder();
        
        for (Card s: opt.get().getTable())
            builder.add(s.getuID());
        
        try (PrintWriter pw = resp.getWriter()) {
            pw.println(builder.build().toString());
        }
    }
    
    
}
