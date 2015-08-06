
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
    private List<Card> Table=new ArrayList<>();
    private List<Card> Deck=new ArrayList<>();
    private final SseBroadcaster broadcaster = new SseBroadcaster();
    private int unique;
    
    public Game() {   
        Table=new ArrayList<>();
        Deck=new ArrayList<>();
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
            Table.add(a);
            System.out.println(Table.get(i).toString());
            
        }
        if(DoesSetExist(Table)==true)
            {
                System.out.println("There exists a set in the table");
                ShowSet(Table);
            }
//            Table.add(new Card(1,1,1,1));
//            Table.add(new Card(1,1,1,2));
//            Table.add(new Card(1,1,1,3));
//            Table.add(new Card(1,1,2,1));
//            Table.add(new Card(1,1,2,2));
//            Table.add(new Card(1,1,2,3));
//            Table.add(new Card(1,1,3,1));
//            Table.add(new Card(1,1,3,2));
//            Table.add(new Card(1,1,3,3));
//            Table.add(new Card(1,2,1,1));
//            Table.add(new Card(1,2,1,2));
//            Table.add(new Card(1,2,1,3));
    }

    public List<Card> getTable() {
        return Table;
    }
    
    public String gameId() {
        return (gid);
    }
    
    public void add(EventOutput eo) {
        broadcaster.add(eo);
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
       public boolean DoesSetExist(List<Card> card) {

        int size = card.size();
        for (int ai = 0; ai < size; ai++) {
            Card A = card.get(ai);
            for (int bi = ai + 1; bi < size; bi++) {
                Card B = card.get(bi);
                for (int ci = bi + 1; ci < size; ci++) {
                    Card C = card.get(ci);
                    if((isSet(A,B,C))==true)
                        //System.out.println("Card A = "+ A + " Card B = "+ B +" Card C = "+C);
                    return true;
                }
            }
        }
        return false;
    }
       
       public void ShowSet (List<Card> card)
       {
           for (int ai = 0; ai < card.size(); ai++) {
            Card A = card.get(ai);
            for (int bi = ai + 1; bi < card.size(); bi++) {
                Card B = card.get(bi);
                for (int ci = bi + 1; ci < card.size(); ci++) {
                    Card C = card.get(ci);
                    if((isSet(A,B,C))==true)
                        System.out.println("Card A = "+ A + " Card B = "+ B +" Card C = "+C);
                    
                    }
                }
            }
        }
   public void checkSelection(int p0,int p1,int p2)
   {
//       Card a=new Card(p0,p0,p0,p0);
//       Card b=new Card(p1,p1,p1,p1);
//       Card c=new Card(p2,p2,p2,p2);
       Card a = Table.get(p0);
       System.out.println(a.toString());
       Card b = Table.get(p1);
       System.out.println(b.toString());
       Card c = Table.get(p2);
       System.out.println(c.toString());
       
        Lock wLock = lock.writeLock();
        wLock.lock();
        try { 
       
       if(isSet(a,b,c))
       {
           Table.remove(a);
           Table.remove(b);
           Table.remove(c);

//           Iterator<Card> cIter=Table.iterator();
//           while (cIter.hasNext())
//           {
//               Card z=cIter.next();
//               if((z.getuID()==a.getuID() ||
//                       z.getuID()==b.getuID() ||
//                       z.getuID()==c.getuID()))
//                       cIter.remove();
//           }
           
           System.out.println("Table Size After remove: "+Table.size());
             for(int i=0;i<3;i++)
             {
                 Card x= Deck.get(i); 
                 Deck.remove(x);
                 Table.add(x);
             }
           System.out.println("Checking to see if there are sets on the table after removal");
            while(DoesSetExist(Table)==false)
            {
                System.out.println("Number of cards on table: "+Table.size());
                System.out.println("There are no sets on the table");
                System.out.println("Shuffling table cards back into Deck");
                for(int i=0; i<12  ; i++){
                    //System.out.println(Table.size());
                    Card temp = Table.get(i);
                    
                    Deck.add(temp);
                    Table.remove(i);
                   
                    Card x = Deck.get(i);
                    Deck.remove(x);
                    Table.add(x);
                }
            }
            System.out.println("There are sets found on the table");
            ShowSet(Table);
           //System.out.println("Set Found"); 
           System.out.println(Table.size());
//           for (Card z : Table)
//           {
//               System.out.println(z.toString());
//           }
           
//                JsonObject data = Json.createObjectBuilder()
//                    .add("01",01)
//                    .add("02",02)
//                    .add("03",03)
//                    .build();           
//            System.out.println(">>> json = " + data);
//            OutboundEvent ooe = new OutboundEvent.Builder()
//                    .mediaType(MediaType.APPLICATION_JSON_TYPE)
//                    .data(JsonObject.class, data)
//                    .build();
//            broadcaster.broadcast(ooe);        
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
