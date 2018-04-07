/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Players;

import cards.*;

/**
 *
 * @author Lavanchy
 */
public class Table{
    private Card card;
    private Player winner;
    private Boolean matching;

    public Table() {
        this.matching=true;
        this.card=null;
    }
    
    public void throwAcardOnTheTable(Card x){
       synchronized(this){
            this.card = x;
           notifyAll();
        }
    }
    
    public Card theCardOnTheTable(){
        synchronized(this){
            return this.card;
        }
    }
    
    /**we pass by parameter a Player. while the table is playing 
     *if there ist a card on the table, the player will wait
     */
    public void Match(Player thePlayer){ 
        synchronized(this){
              
                while(this.card==null && this.matching == true){
                    try{
                        wait();
                    } catch (InterruptedException e){ }
                }
            if(this.matching == true){
                thePlayer.takeaCard(this.card);
                this.card= null;
                notifyAll();
            }                
        }
    } 
    
     /** return true if the MainPlayer have a card or 
     * false if he don't have more Cards.
     */
    public boolean getMatching(){
       synchronized(this){
            return this.matching;
       }
    }
    
    /** set the value of Matching
     */
    public void theTableIsMatching(boolean isMatching){    
        this.matching=isMatching;
        
    }
    
    public Player getWinner(){
            return this.winner;
        
    }
    public void setWinner(Player x){
        synchronized(this){
            this.winner = x;
        }
    }
    
}
