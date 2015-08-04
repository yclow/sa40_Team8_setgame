package com.team8.setgame;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@WebServlet("/game")
public class GameServlet extends HttpServlet {

//    @Resource(mappedName = "concurrent/mypool")
//    private ManagedScheduledExecutorService svc;
    @Inject
    private GameRepository repository;

//    private class MakeMove implements Runnable {
//
//        private final int p0;
//        private final int p1;
//        private final int p2;
//        private final Game game;
//
//        
//        public MakeMove(int p0, int p1, int p2,Game g) {
//            this.p0=p0;
//            this.p1=p1;
//            this.p2=p2;
//            game = g;
//        }
//
//        @Override
//        public void run() {
//            game.checkSelection(p0, p1, p2);
//        }
//
//    }
 

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String cmd = req.getParameter("cmd");
//        System.out.println(cmd);
//        String redirect = "";
//        if ("newGame".equals(cmd)) {
//            Game g = new Game();
//            repository.add(g);
//            System.out.println(g.gameId());
//            resp.setStatus(HttpServletResponse.SC_OK);
//            try (PrintWriter pw = resp.getWriter()) {
//                pw.println(g.gameId());
//            }
//            redirect = "client.html#game" + g.gameId();
//        } else {
//            Game g = new Game();
//            redirect = "client.html#game" + g.gameId();
//        }
//
//        resp.sendRedirect(redirect);
//    }
    

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String p0 = req.getParameter("p0");
//        String p1 = req.getParameter("p1");
//        String p2 = req.getParameter("p2");
//        System.out.println(p0);
//        System.out.println(p1);
//        System.out.println(p2);
//        String gid = req.getParameter("gid");
//        Optional<Game> opt = repository.getGame(gid);
//        if (!opt.isPresent()) {
//            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            return;
//        }
////        
////        svc.submit(new MakeMove(
////                Integer.parseInt(p0), Integer.parseInt(p1),Integer.parseInt(p2), opt.get()));
//
//    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String gid = req.getParameter("gid");
        String p0 = req.getParameter("p0");
        String p1 = req.getParameter("p1");
        String p2 = req.getParameter("p2");
        Optional<Game> g = repository.getGame(gid);
        g.get().checkSelection(Integer.parseInt(p0), Integer.parseInt(p1), Integer.parseInt(p2));
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        List<Card> table = g.get().getTable();
        for (Card z : table) {
            System.out.println(z.toString());
            arrBuilder.add(z.toString());
        }

        resp.setContentType(MediaType.APPLICATION_JSON);
        resp.setStatus(HttpServletResponse.SC_OK);
        try (PrintWriter pw = resp.getWriter()) {
            pw.println(arrBuilder.build().toString());
        }

    }

}

//    @GET
//    @Path("/{gid}")
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response getTableCards(HttpServletRequest req) {
//        String gid = req.getParameter("gid");
//        String p0 = req.getParameter("p0");
//        String p1 = req.getParameter("p1");
//        String p2 = req.getParameter("p2");
//        Optional<Game> g=repository.getGame(gid);
//        g.get().checkSelection(Integer.parseInt(p0), Integer.parseInt(p1), Integer.parseInt(p2));
//        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
//        System.out.println(gid);
//        List<Card> table = g.get().getTable();
//           for (Card z : table)
//           {
//               System.out.println(z.toString());
//               arrBuilder.add(z.toString());
//           } 
//        return (Response.ok(arrBuilder.build()).build());
//    }

