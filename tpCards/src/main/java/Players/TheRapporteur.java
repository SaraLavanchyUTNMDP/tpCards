/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Players;

import cards.Card;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Lavanchy
 */
public class TheRapporteur implements Observer {

    Player theWinner;
    int count;
    
    public TheRapporteur() {
        this.theWinner=null;
        this.count=0;
    }
    
    @Override
    public synchronized void update(Observable o, Object arg) {
            if(o instanceof Player){
                if(((Player) o).getTable().getMatching()){
                    this.count++;
                    Card theTakenCard = ((Player) o).getHand().lastCard();
                    System.out.println("El jugador "+((Player) o).getName()
                            +" ha tomado la carta "+ theTakenCard.toString() 
                            +". Van jugadas "+this.count+" cartas" );
                    if(((Player) o).getTable().getWinner()!=null){
                        if(((Player) o).getPoints()>((Player) o).getTable().getWinner().getPoints()){
                            this.theWinner = ((Player) o);
                            ((Player) o).getTable().setWinner(theWinner);
                        }
                    }else{
                        this.theWinner = ((Player) o);
                        ((Player) o).getTable().setWinner(theWinner);
                    }
                    notifyAll();
                }
            }
        
    }
    
    
}
