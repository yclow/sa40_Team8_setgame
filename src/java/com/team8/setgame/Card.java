
package com.team8.setgame;
import java.io.Serializable;
import static java.lang.Math.random;
import java.util.Comparator;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Card implements Comparable,Serializable {
        @Id
        private int uID;
        private int number;
        private int symbol;
        private int shading;
        private int colour;
        private double sortOrder;

        public Card()
        {
        }
        
        public Card(int nmb, int syl, int shg, int clr) {
            number = nmb;
            symbol = syl;
            shading = shg;
            colour = clr;
            sortOrder = Math.random(); // used when shuffling the deck
        }

    public int getuID() {
        return uID;
    }

    public void setuID(int uID) {
        this.uID = uID;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }

    public int getShading() {
        return shading;
    }

    public void setShading(int shading) {
        this.shading = shading;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public double getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(double sortOrder) {
        this.sortOrder = sortOrder;
    }
        

        
        
        @Override
        public int compareTo(Object other) {
            if (this.sortOrder == ((Card) other).sortOrder)
                 return 0;
             else if ((this.sortOrder) > ((Card) other).sortOrder)
                 return 1;
             else
                 return -1;
        }

        @Override
        public String toString() {
            return number + "," + symbol + "," + shading + "," + colour; // + " - " + sortOrder;
        }

    
    
}
