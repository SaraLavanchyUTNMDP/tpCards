/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cards;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Lavanchy
 */
public class Mazo {
    private List<Card> cards;

    public Mazo() {
        this.cards = new Stack();
    }       
    
    /** Add a card on the Mazo
     */
    public void addCard(Card e){
        this.cards.add(e);
    }
    
    /** Return the last card of the mazo and delete it
     */
    public Card dischargeMazo(){
       Card card=(Card)((Stack)cards).pop();
       return card;
    }
    
    /** Charge the mazo with Spanish's cards and then,
     * the method shuffle the mazo.
     */
    public void chargeMazo(){
        for(int i=1;i<13;i++){
            Card x=new Card(i, TypeOfCard.GOLD);
            Card x1=new Card(i, TypeOfCard.CUP);
            Card x2=new Card(i, TypeOfCard.HEART);
            Card x3=new Card(i, TypeOfCard.WOOD);
            this.cards.add(x);
            this.cards.add(x1);
            this.cards.add(x2);
            this.cards.add(x3);           
        }
        Collections.shuffle(cards);
    }
    
    public void showMazo(){
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "Mazo:" + cards.toString();
    }
    
    /** return the size of the Mazo
     */
    public int SizeOf(){
        int size=this.cards.size();
        return size;
    }

    /** return the last card of the Stack but 
     * the method dont delete it from the Stack.
     * if the mazo isnt setted it return null
     */
    public Card lastCard(){
        Card x = null;
        if(this.SizeOf()!=0){
            x= this.cards.get(this.SizeOf()-1);
        }
        return x;
    }
    
    /**returns the sum of the numbers of the cards of the mazo
     */
    public int totalPointsOfTheMazo(){
        int points=0;
        for(int i = 0;i<this.SizeOf();i++){
            points+=this.cards.get(i).number;
        }
        return points;
    }
    
    
    
}
