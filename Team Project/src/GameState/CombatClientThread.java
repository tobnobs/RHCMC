package GameState;
 
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;
 
import javax.swing.SwingWorker;
 
import outworld.AppendingObjectOutputStream;
import outworld.WorldServer;
import serialized.Data;
import GameState.CombatScreenPanel2v2;
import GameState.Command;
import GameState.CommandComparator;
import GameState.DamageSkill;
import GameState.Player;
import GameState.SkillsList;
import MovementMap.TheMap;
 
/**
 * @author Liam Howe
 * Class to run a 2v2 combat screen over the network
 * 
 */
public class CombatClientThread extends SwingWorker<Void, Player[]>{
       
        public CombatScreenPanel2v2 csp2;
        public TheMap map;
        public Player main;
        public Player player1;
        public Player player2;
        public Player player3;
        public SkillsList skillList = new SkillsList();
        public ArrayList<Command> commandList = new ArrayList<Command>();
        public Stack<Command> commandStack = new Stack<Command>();
        public Stack<Command> commandStackDefense = new Stack<Command>();
        public ArrayList<Player> combatants = new ArrayList<Player>();
        private boolean gameOverCheck = false;
       
        /**
         * Takes in the players and the map
         * @param map The map
         * @param main The main player
         * @param player1
         * @param player2
         * @param player3
         */
        public CombatClientThread(TheMap map, Player main, Player player1, Player player2, Player player3){
                this.map = map;
                this.main = main;
                this.player1 = player1;
                this.player2 = player2;
                this.player3 = player3;
                this.csp2 = map.csp2;
        }
        /**
         * Runs the combat loop for the client side
         */
        protected Void doInBackground() throws Exception {
                System.out.println("Combat loop entered");
                while(!gameOverCheck){
                		map.playersUpdated = false;
                        //send command
                        Thread.sleep(250);
                        Command newCommand = null;
                        Player deathCheck = null;
                        if(csp2.mainPlayer.getName().equals(csp2.p1.getName())){
                                System.out.println("deathCheck = p1");
                                deathCheck = csp2.p1;
                        }
                        else if(csp2.mainPlayer.getName().equals(csp2.p2.getName())){
                                System.out.println("deathCheck = p2");
                                deathCheck = csp2.p2;
                        }
                        else if(csp2.mainPlayer.getName().equals(csp2.p3.getName())){
                                System.out.println("deathCheck = p3");
                                deathCheck = csp2.p3;
                        }
                        else if(csp2.mainPlayer.getName().equals(csp2.p4.getName())){
                                System.out.println("deathCheck = p4");
                                deathCheck = csp2.p4;
                        }
                        else {
                                System.out.println("Death check not set");
                        }
                        System.out.println("Player " + deathCheck.getName() + " death status is " + deathCheck.isDead());
                        if(deathCheck.isDead()){
               //               Thread.sleep(50 * (deathCheck.getPlayerID() + 1));
                                Command dummyCommand = new Command(99, 99, null, 99, 99, 99);
                                map.client.sendCommandBYTES(dummyCommand);
                                System.out.println("************");
                        }
                        else {
                                while(newCommand == null){
                                        Thread.sleep(250);
                                        newCommand = map.csp2.command;
                                }
                       
                                System.out.println("***");
                                if(newCommand != null){
                                        newCommand = csp2.command;
      //                                  Thread.sleep(deathCheck.getPlayerID()*200);
                                        map.send(newCommand);
                                        System.out.println("command sent to server");
                                        newCommand = null;
                                }
                        }
                        System.out.println("Waiting for reply...");
                        System.out.println(" ");
                        while(!map.playersUpdated){
                                try {
                                        Thread.sleep(250);
                                } catch (InterruptedException e1) {
                                        e1.printStackTrace();
                                }
                        }
                        System.out.println("Reply received from server");
                        //update players from server
                        Player tempP1;
                        Player tempP2 = null;
                        Player tempP3 = null;
                        Player tempP4 = null;
                        tempP1 = map.others.get(map.mainPos);
                        if(map.mainPos == 0){
                                tempP2 = map.others.get(1);
                                tempP3 = map.others.get(2);
                                tempP4 = map.others.get(3);
                                System.out.println("Your player was in position: "+map.mainPos);
                        }
                        else if(map.mainPos == 1){
                                tempP2 = map.others.get(0);
                                tempP3 = map.others.get(2);
                                tempP4 = map.others.get(3);
                                System.out.println("Your player was in position: "+map.mainPos);
                        }      
                        else if(map.mainPos == 2){
                                tempP2 = map.others.get(0);
                                tempP3 = map.others.get(1);
                                tempP4 = map.others.get(3);
                                System.out.println("Your player was in position: "+map.mainPos);
                        }
                        else if(map.mainPos == 3){
                                tempP2 = map.others.get(1);
                                tempP3 = map.others.get(2);
                                tempP4 = map.others.get(3);
                                System.out.println("Your player was in position: "+map.mainPos);
                        }
                        csp2.commandInt = 10;
                        csp2.command = null;
                        csp2.targetSelected = false;
                        System.out.println("Player 1 (" + tempP1.getName() + ") dead? " + tempP1.isDead());
                        System.out.println("Player 2 (" + tempP2.getName() + ") dead? " + tempP2.isDead());
                        System.out.println("Player 3 (" + tempP3.getName() + ") dead? " + tempP3.isDead());
                        System.out.println("Player 4 (" + tempP4.getName() + ") dead? " + tempP4.isDead());
                        ArrayList<Player> teamZero = new ArrayList<Player>();
                        ArrayList<Player> teamOne = new ArrayList<Player>();
                        for(Player player : map.others){
                        	if(player.getTeam() == 0) teamZero.add(player);
                        	else if(player.getTeam() == 1) teamOne.add(player);
                        }
  
                        if(teamZero.get(0).isDead() && teamZero.get(1).isDead() || teamOne.get(0).isDead() && teamOne.get(1).isDead()){
                                gameOverCheck = true;
                        }
                       
                        Player[] newPlayers = {tempP1, tempP2, tempP3, tempP4};
                        publish(newPlayers);

                }
                Thread.sleep(1000);
                System.out.println("Game has ended, gameOver set to true");
                map.gameOver = true;
                return null;
        }
 
        /**
         * Receives the updated players and updates the GUI with the new players
         */
        protected void process(List<Player[]> NewPlayers){
        		System.out.println("Process called");
                csp2.showSkills();
                csp2.p1 = NewPlayers.get(NewPlayers.size()-1)[0];
                csp2.p2 = NewPlayers.get(NewPlayers.size()-1)[1];
                csp2.p3 = NewPlayers.get(NewPlayers.size()-1)[2];
                csp2.p4 = NewPlayers.get(NewPlayers.size()-1)[3];
                csp2.p1.playersUpdated = false;
                csp2.p2.playersUpdated = false;
                csp2.p3.playersUpdated = false;
                csp2.p4.playersUpdated = false;
                System.out.println("player 1 hp: " + NewPlayers.get(NewPlayers.size()-1)[0].getActualHP());
                System.out.println("player 2 hp: " + NewPlayers.get(NewPlayers.size()-1)[1].getActualHP());
                System.out.println("player 3 hp: " + NewPlayers.get(NewPlayers.size()-1)[2].getActualHP());
                System.out.println("player 4 hp: " + NewPlayers.get(NewPlayers.size()-1)[3].getActualHP());
                if(csp2.mainPlayer.getName().equals(csp2.p1.getName())){
                        if(csp2.p1.isDead()){
                                csp2.hideSkills();
                        }
                }
                else if(csp2.mainPlayer.getName().equals(csp2.p2.getName())){
                        if(csp2.p2.isDead()){
                                csp2.hideSkills();
                        }
                }
                else if(csp2.mainPlayer.getName().equals(csp2.p3.getName())){
                        if(csp2.p3.isDead()){
                                csp2.hideSkills();
                        }
                }
                else if(csp2.mainPlayer.getName().equals(csp2.p4.getName())){
                        if(csp2.p4.isDead()){
                                csp2.hideSkills();
                        }
                }
                csp2.repaint();
                System.out.println("screen repainted");
        }      
       
}