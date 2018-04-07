package Players;
import cards.*;
import static java.lang.Thread.sleep;
import java.util.*;

public class Player extends Observable implements Runnable{
    private Mazo hand;
    private String nombre;
    private int points;
    private Table table;
    
    public Player(String nombre, Table tableOnPlaying) {
        this.hand = new Mazo();
        this.nombre = nombre;
        this.points=0;
        this.table = tableOnPlaying;
    }
    
    /** this method takes a Given Card
     */
    public void takeaCard(Card x){
        this.hand.addCard(x);
    }
    
    public void run(){
        while(this.table.getMatching() == true){
            this.table.Match(this);
            this.sumPoints();
            try{
                sleep((int) (Math.random() * 100));
            }catch (InterruptedException e){}
        }
    }   
    
    /** this method sum the points of the Player and set the atributte points.
     */
    public void sumPoints(){
        if(this.hand.lastCard() != null && this.table.getMatching() == true){
            this.points=this.points + this.hand.lastCard().getNumber();
            setChanged();
            notifyObservers();
            clearChanged();
        }
    }
    
    /** return the total points of the hand.
     */
    public int totalPoints(){
        return this.hand.totalPointsOfTheMazo();
    }

    /*getters and setters*/
    public Mazo getHand() {
        return this.hand;
    }

    public void setHand(Mazo hand) {
        this.hand = hand;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }  
    
    public int getPoints(){
        return this.points;
    }
    
    public String getName(){
        return this.nombre;
    }
     public void setName(String name){
         this.nombre = name;
     }

    @Override
    public String toString() {
        return "Datos del jugador " + nombre + ". Su mano es=" + hand.toString()  + "| puntos obtenidos:" + points ;
    }
    
     
    
}