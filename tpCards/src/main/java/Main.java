
import Players.*;
import cards.*;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Lavanchy
 */
public class Main {
    public static void main(String[] args) {
      
        Table tableOnPlaying = new Table();
        MainPlayer dealer = new MainPlayer(tableOnPlaying);
        
        TheRapporteur theRappporteur = new TheRapporteur();
        
        Player one = new Player("Juan", tableOnPlaying);
        Player two = new Player("Rodri", tableOnPlaying);
        Player three = new Player("Marquito", tableOnPlaying);
        Player four = new Player("Sisi", tableOnPlaying);
        
        one.addObserver(theRappporteur);
        two.addObserver(theRappporteur);
        three.addObserver(theRappporteur);
        four.addObserver(theRappporteur);
        
        Thread first = new Thread(one);
        Thread second = new Thread(two);
        Thread theard= new Thread(three);
        Thread fourth = new Thread(four);
        first.start();
        second.start();
        theard.start();
        fourth.start();
        dealer.startMatch();
        
        System.out.println("the winner is: " + tableOnPlaying.getWinner().getName()
                + " con "+ tableOnPlaying.getWinner().getPoints()+" puntos.");
        int pointsone = one.getPoints();
        int pointstwo = two.getPoints();
        int pointsthree = three.getPoints();
        int pointsfour = four.getPoints();
        System.out.println("Los puntos fueron: " 
                + pointsone + "|" +one.totalPoints());
        System.out.println(pointstwo + "|" + two.totalPoints());
        System.out.println( pointsthree + "|" +three.totalPoints());
        System.out.println(pointsfour+ "|" +four.totalPoints());
        int Cards =one.getHand().SizeOf()+two.getHand().SizeOf()+three.getHand().SizeOf()+four.getHand().SizeOf();
        System.out.println("Han sido Jugadas "+Cards+" cartas");
                
    }
    
}
