/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monsterproject;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ImageIcon;

/**
 *
 * @author Gabriel Cerdas
 */
public class Game {
    private int currentPlayer;
    private String[] playerIds;
    
    private Deck deck;
    private ArrayList<ArrayList<Card>> playerHand;
    private ArrayList<Card> stockpile;
    
    private Card.Type validType;
    private Card.Value validValue;
    
    boolean gameDirection;
    public Game(String[] pids){
        deck = new Deck();
        deck.shuffle();
        stockpile = new ArrayList<Card>();
        
        playerIds = pids;
        currentPlayer = 0;
        gameDirection= false;
        
        playerHand = new ArrayList<ArrayList<Card>>();
        for (int i = 0; i <pids.length; i ++) {
        ArrayList<Card> hand = new ArrayList<Card>(Arrays.asList(deck.drawCard(16)));
        playerHand.add(hand);
        }
    }
    
    public void satart(Game game){
        Card card = deck.drawCard();
        validType = card.getType();
        validValue = card.getValue();
        
        stockpile.add(card);
    }
    
    public Card getTopCard(){
        return new Card(validType, validValue);
    }
    
    public ImageIcon getTopsCardImage(){
        return new ImageIcon(validType + "-" + validValue + ".png");
    }
    
    public String getCurentPlayer(){
        return this.playerIds[this.currentPlayer];
    }
    
    public String getPreviusPLayer(int i) {
        int index = this.currentPlayer - i;
        if (index == -1){
            index = playerIds.length - 1;
        }
        return this.playerIds[index];
    }
    
    public String[] getPlayers(){
        return playerIds;
    }
    
    public ArrayList<Card> getPlayerHand(String pid){
        int index =Arrays.asList(playerIds).indexOf(pid);
        return playerHand.get(index);
    }
    
    public int getPlayerHandSize(String pid){
        return getPlayerHand(pid).size();
    }
    
    public Card getPlayerCard(String pid, int choice){
        ArrayList<Card> hand = getPlayerHand(pid);
        return hand.get(choice);
    }
    
    public boolean hasEmptyHand(String pid){
        return getPlayerHand(pid).isEmpty();
    }
    
    public boolean validCardPlay(Card card){
        return card.getType() == validType || card.getValue() == validValue;
    }
    public void checkPlayerTurn(String pid) throws InvalidPlayerTurnException{
        if(this.playerIds[this.currentPlayer] != pid){
           throw new InvalidPlayerTurnException("it is not" + pid + " 's turn", pid);
        }
    }
    public void submitDraw(String pid) throws InvalidPlayerTurnException{
        checkPlayerTurn(pid);
        
        if (deck.isEmpty()){
            deck.replaceDeckWith(stockpile);
            deck.shuffle();
        }
        
        getPlayerHand(pid).add(deck.drawCard());
        if (gameDirection == false) {
            currentPlayer = (currentPlayer + 1) % playerIds.length;
        }
        else if(gameDirection == true){
            currentPlayer = (currentPlayer - 1) % playerIds.length;
            if ( currentPlayer == -1) {
                currentPlayer = playerIds.length - 1;
            }
        }
    }
    
    public void setCardType(Card.Type type){
        validType = type;
    }
}

class InvalidPlayerTurnException extends Exception{
    String playerId;
    
    public InvalidPlayerTurnException (String message, String pid){
        super(message);
        playerId = pid;
    }
    
    public String getPid(){
        return playerId;
    }
}

class InvalidTypeSubmissionException extends Exception{
    private Card.Type expected;
    private Card.Type actual;
    
    public InvalidTypeSubmissionException(String message, Card.Type actual, Card.Type expected){
        this.actual = actual;
        this.expected = expected;
    }
}

class InvalidValueSubmissionException extends Exception{
    private Card.Value expected;
    private Card.Value actual;
    
    public InvalidValueSubmissionException(String message, Card.Value actual, Card.Value expected){
        this.actual = actual;
        this.expected = expected;
    }
}