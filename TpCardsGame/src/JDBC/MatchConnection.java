/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import java.io.FileInputStream;
import java.util.Properties;
import Players.*;
import cards.Card;
import cards.Mazo;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Lavanchy
 */
public class MatchConnection {
    Connecction myConnection;
    Properties myQueries;
    
    public MatchConnection(){
        try{
            this.myConnection =Connecction.getInstance();
            this.myQueries = new Properties();
            this.myQueries.load(new FileInputStream("config/myQueries.properties"));
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /** this method add a player in the dataBase
     */
    public int addAplayer(Player player){
        int playerId=0;
        try {
            String sql = myQueries.getProperty("query.addPlayer");
            //String sql = "insert into player(name,points) values (?,?)";
            CallableStatement st = this.myConnection.getConexion().prepareCall(sql);
            st.setString(1, player.getName());
            st.setInt(2, playerId);
            st.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return playerId;
    }
    
    public void newMatch(List<Player> players, Player winner){
        int winnerId=0;
        int matchId=0;
        try{
            Mazo auxiliar = (Mazo) winner.getHand().clone();
            String sqlmatch = myQueries.getProperty("query.addMatch"); 
            CallableStatement stMatch = this.myConnection.getConexion().prepareCall(sqlmatch);
            
            stMatch.setString(1,winner.getName());
            stMatch.setInt(2, winner.getPoints());
            stMatch.executeUpdate();
           
            String sqlSelectMatch = myQueries.getProperty("query.selectMatch");
            PreparedStatement stSM = this.myConnection.getConexion().prepareStatement(sqlSelectMatch);
            stSM.setString(1,winner.getName());
            stSM.execute();
            ResultSet rsKeys = stSM.getResultSet();
            if (rsKeys.next()) {
                winnerId= rsKeys.getInt(1);
                matchId=rsKeys.getInt(2);
            }            
            
            String sqlHand = myQueries.getProperty("query.addHand");    
            for(int i=0;i<winner.getHand().SizeOf();i++){
                Card x=auxiliar.dischargeMazo();
                CallableStatement st = this.myConnection.getConexion().prepareCall(sqlHand);
                st.setString(1, x.getType());
                st.setInt(2, winnerId);
                st.setInt(3, matchId);
                st.setInt(4, x.getNumber());
                st.execute();
            }
            
            String sqlMatchPlayer = myQueries.getProperty("query.insertplayeringame");    
            for(int i=0; i<players.size(); i++){
                this.addAplayer(players.get(i));  
                
                CallableStatement st2 = this.myConnection.getConexion().prepareCall(sqlMatchPlayer);
                st2.setString(1, players.get(i).getName());
                st2.setInt(2, matchId);
                st2.setInt(3, players.get(i).getPoints());
                st2.executeUpdate();
                              
            }
                
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public void addCardsOfMazo(Mazo mazo){
        try{
        String sqlCard = myQueries.getProperty("query.addCard");
        Mazo auxiliar = (Mazo) mazo.clone();
        while(!auxiliar.isEmpty()){
            CallableStatement stCard = this.myConnection.getConexion().prepareCall(sqlCard);
            Card x=auxiliar.dischargeMazo();            
            stCard.setInt(1, x.getNumber());
            stCard.setString(2, x.getType());
            stCard.execute();            
        }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
}
