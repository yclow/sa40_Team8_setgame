
package com.team8.setgame;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;

public class Game {
    
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final String gid = UUID.randomUUID().toString();
    private final List<Card> Table=new ArrayList<>();
    private final List<Card> Deck=new ArrayList<>();
    private final SseBroadcaster broadcaster = new SseBroadcaster();
    private int unique;
    
    public Game() {   
//        Table=new ArrayList<>();
//        Deck=new ArrayList<>();
        for (int number=1; number<4; number++) {
            for (int colour=1; colour<4; colour++) {
                for (int shading=1; shading<4; shading++) {
                    for (int symbol=1; symbol<4; symbol++) {
                        Deck.add(new Card(number, colour, shading, symbol));
                    }
                }
            }
        }
        for (Card k : Deck)
        {
            k.setuID(unique);
            unique++;
        }
        Collections.sort(Deck); 
       
        for (int i=0;i<12;i++)
        {
            Card a= Deck.get(i);
            Deck.remove(a);
//            Table.add(a);
           
        }
        Table.add(new Card(0,0,0,0));
        Table.add(new Card(1,1,1,1));
        Table.add(new Card(2,2,2,2));
        Table.add(new Card(3,3,3,3));
        Table.add(new Card(4,4,4,4));
        Table.add(new Card(5,5,5,5));
        Table.add(new Card(6,6,6,6));
        Table.add(new Card(7,7,7,7));
        Table.add(new Card(8,8,8,8));
        Table.add(new Card(9,9,9,9));
        Table.add(new Card(10,10,10,10));
        Table.add(new Card(11,11,11,11));
    }

    public List<Card> getTable() {
        return Table;
    }
    
    public String gameId() {
        return (gid);
    }
    
    public void add(EventOutput eo,OutboundEvent ooe) {
        broadcaster.add(eo);
        broadcaster.broadcast(ooe);
    }
    
       public boolean isSet(Card a, Card b, Card c) {
	        if (!((a.getNumber() == b.getNumber()) && (b.getNumber() == c.getNumber()) ||
	                (a.getNumber() != b.getNumber()) && (a.getNumber() != c.getNumber()) && (b.getNumber() != c.getNumber()))) {
	            return false;
	        }
	        if (!((a.getSymbol() == b.getSymbol()) && (b.getSymbol() == c.getSymbol()) ||
	                (a.getSymbol() != b.getSymbol()) && (a.getSymbol() != c.getSymbol()) && (b.getSymbol() != c.getSymbol()))) {
	            return false;
	        }
	        if (!((a.getShading() == b.getShading()) && (b.getShading() == c.getShading()) ||
	                (a.getShading() != b.getShading()) && (a.getShading() != c.getShading()) && (b.getShading() != c.getShading()))) {
	            return false;
	        }
	        if (!((a.getColour() == b.getColour()) && (b.getColour() == c.getColour()) ||
	                (a.getColour() != b.getColour()) && (a.getColour() != c.getColour()) && (b.getColour() != c.getColour()))) {
	            return false;
	        }
	        return true;
	    }
   public void checkSelection(int p0,int p1,int p2)
   {
       Card a=new Card(p0,p0,p0,p0);
       Card b=new Card(p1,p1,p1,p1);
       Card c=new Card(p2,p2,p2,p2);
       
        Lock wLock = lock.writeLock();
        wLock.lock();
        try { 
       
       if(isSet(a,b,c))
       {

           Iterator<Card> cIter=Table.iterator();
           while (cIter.hasNext())
           {
               Card z=cIter.next();
               if((z.getNumber()==a.getNumber() ||
                       z.getNumber()==b.getNumber() ||
                       z.getNumber()==c.getNumber()))
                       cIter.remove();
           }
           
           System.out.println("Table Size After remove: "+Table.size());
             for(int i=0;i<3;i++)
             {
                 Card x= Deck.get(i); 
                 this.Deck.remove(x);
                 this.Table.add(x);
             }
           System.out.println("Set Found");     
       }
       else
       {
           System.out.println("Not a Set");
       }
   }
        finally {
            wLock.unlock();
        }
}
}
