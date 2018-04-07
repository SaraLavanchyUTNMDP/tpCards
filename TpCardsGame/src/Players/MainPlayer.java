/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Players;

import JDBC.MatchConnection;
import cards.*;
import java.util.*;

/**
 *
 * @author Lavanchy
 */
public class MainPlayer {
    private Mazo mazo;
    private Table table;
    
    public MainPlayer(Table tableOnPlaying) {
        this.mazo = new Mazo();
        this.mazo.chargeMazo();
        MatchConnection persistence = new MatchConnection();
        persistence.addCardsOfMazo(this.mazo);
        this.table = tableOnPlaying;
    }
    
    public void startMatch(){
        synchronized(this){
            while(this.mazo.SizeOf()!=0){
                if(this.table.theCardOnTheTable() == null){
                    this.table.throwAcardOnTheTable(this.entregarCarta());
                    this.table.theTableIsMatching(true);
                }
            }
            this.table.theTableIsMatching(false);
            notifyAll();
        }
    }
    
    /** If the Mazo have a Card, this method will return the last card. 
     * Else, will return null.
    */    
    public Card entregarCarta(){
        Card x = null;
        synchronized(this){
           if(this.mazo.SizeOf()!=0){
                x= this.mazo.dischargeMazo();
           }
        }
        return x;
    }
    
    
    public Mazo getMazo(){
        return this.mazo;
    }
}
