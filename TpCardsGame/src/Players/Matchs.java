/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Players;

import JDBC.MatchConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Lavanchy
 */
public class Matchs {
    private  Table tableOnPlaying;
    private MainPlayer dealer;
    private List<Player> players;
    private TheRapporteur theRappporteur;
    private List<Thread> myThreads;

    public Matchs() {
        this.tableOnPlaying = new Table();
        this.dealer = new MainPlayer(this.tableOnPlaying);
        this.players = new ArrayList<>();
        this.theRappporteur = new TheRapporteur();
        this.myThreads = new ArrayList<>();
    }
    
      /** this methods do the game. it charge the players, starts the thread and
     * start the match.
     */
    public void Gamming(){
        this.ChargingMyPlayers();
        //we starts ours threads
        for(int i=0;i<this.myThreads.size();i++){
            this.myThreads.get(i).start();
        }
        //the dealer starts to pull the cards
        this.dealer.startMatch();
        
        //when the game end we show the winner and save the results.
        System.out.println("El ganador del juego fue: "+this.tableOnPlaying.getWinner().getName()
                +" con "+this.tableOnPlaying.getWinner().getPoints()+" puntos");
        this.saveResults();
    }    
    
    /** you give a name to the method and the method 
     * create a new Player of this game
     */
    public void addPlayer(String name){
        Player x = new Player(name, this.tableOnPlaying);
        this.players.add(x);
    }
    
    /** this method let you insert new Players from the console Scanner
     * create a new player of all of them and add them to the list of
     * players of the game.
     * it also add the observer of the game to all the players.
     * besides, it create the threads for all the players and add its to the list
     * of threads.
     */
    public void ChargingMyPlayers(){
        boolean continueTheCharging = true;
        while(continueTheCharging){
            Scanner scan = new Scanner(System.in);
            
            System.out.println("Insert the name of the player");
            String name = scan.nextLine();
            this.addPlayer(name);
            
            System.out.println("Any other player want to play? 1-0");
            int flag = scan.nextInt();
            if(flag == 0){
                continueTheCharging = false;
            }
        }
        
        //to add the observer to my players and create the Threads
        for(int i=0;i<this.players.size();i++){
            this.players.get(i).addObserver(theRappporteur);
            Thread auxiliar = new Thread(this.players.get(i));
            this.myThreads.add(auxiliar);
        } 
    }
    
    /** this method insert my results in the database
     */
    public void saveResults(){
        MatchConnection match = new MatchConnection();
        match.newMatch(this.players, this.tableOnPlaying.getWinner());
    }
    
    
    
}
