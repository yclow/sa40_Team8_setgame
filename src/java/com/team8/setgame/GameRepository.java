package com.team8.setgame;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GameRepository {

    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    private final Map<String, Game> games = new HashMap<>();

    public Set<String> getGames() {
        Lock rLock = rwLock.readLock();
        rLock.lock();
        try {
            return (Collections.unmodifiableSet(games.keySet()));
        } finally {
            rLock.unlock();
        }
    }

    public Optional<Game> getGame(String id) {
        Lock rLock = rwLock.readLock();
        rLock.lock();
        try {
            return (Optional.ofNullable(games.get(id)));
        } finally {
            rLock.unlock();
        }
    }

    public void add(final Game game) {
        Lock wLock = rwLock.writeLock();
        wLock.lock();
        try {
            games.put(game.gameId(), game);
        } finally {
            wLock.unlock();
        }
    }

}
