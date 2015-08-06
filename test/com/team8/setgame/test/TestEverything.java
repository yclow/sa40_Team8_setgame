package com.team8.setgame.test;

import com.team8.setgame.GameCreator;
import com.team8.setgame.GameRepository;
import java.io.IOException;
import java.util.NoSuchElementException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestEverything {

    public TestEverything() {
    }

    @Test
    (expected = NullPointerException.class)
    public void testGameCreated() throws ServletException, IOException {

        GameCreator servlet = new GameCreator();

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("cmd")).thenReturn("newGame");

        GameRepository repository = mock(GameRepository.class);
        servlet.setGameRepository(repository);
        servlet.doGet(req,resp);
        
        System.out.println(">>>>>>>>>>" + repository.getGames());
        assertNotNull("Not Null Test", (repository.getGames()).iterator().next());

    }
    
        @Test
    (expected = NoSuchElementException.class)
    public void testGFailCreateGame() throws ServletException, IOException {

        GameCreator servlet = new GameCreator();

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("cmd")).thenReturn("11151");

        GameRepository repository = mock(GameRepository.class);
        servlet.setGameRepository(repository);
        servlet.doGet(req,resp);
        
        System.out.println(">>>>>>>>>>" + repository.getGames());
        assertNotNull("Not Null Test", (repository.getGames()).iterator().next());

    }
}
