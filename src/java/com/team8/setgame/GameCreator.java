/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team8.setgame;

import java.io.IOException;
import java.io.PrintWriter;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/create")
public class GameCreator extends HttpServlet {

    @Inject
    private GameRepository repository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        System.out.println(cmd);
        if ("newGame".equals(cmd)) {
            Game g = new Game();
            repository.add(g);
            System.out.println(g.gameId());
            resp.setStatus(HttpServletResponse.SC_OK);
            try (PrintWriter pw = resp.getWriter()) {
                pw.println(g.gameId());
            }
        }
    }
}
