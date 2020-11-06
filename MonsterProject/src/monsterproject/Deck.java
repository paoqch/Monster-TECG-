/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monsterproject;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author Gabriel Cerdas
 */
public class Deck {
    private Card[]cards;
    private int cardsindeck;
    
    public Deck(){
        cards = new Card[30];
    }
    
    public void reset(){
        Card.Type[] types = Card.Type.values();
        cardsindeck = 0;
        for(int i = 0; i< types.length-i; i++){
            Card.Type type = types[i];
            
            cards[cardsindeck++] = new Card(type, Card.Value.getValue(0));
            
            for(int j = i; j<10; j++){
                cards[cardsindeck++] = new Card(type, Card.Value.getValue(j));
                cards[cardsindeck++] = new Card(type, Card.Value.getValue(j));
            }
        }
    }
        
        public void replaceDeckWith(ArrayList<Card> cards){
            this.cards=cards.toArray(new Card [cards.size()]);
            this.cardsindeck= this.cards.length;
        }
        
        public boolean isEmpty(){
            return cardsindeck == 0;
        }
        
        public void shuffle(){
            int n = cards.length;
            Random random = new Random();
            for(int i  = 0; i<cards.length; i ++) {  
                int randomValue = i + random.nextInt(n-i);
                Card randomCard = cards[randomValue];
                cards[randomValue] = cards[i];
                cards[i] = randomCard;
                
            }
        }
        public Card  drawCard() throws IllegalArgumentException{
            if(isEmpty()){
                throw new IllegalArgumentException("Cannot");    
            }
            return cards[--cardsindeck];
        }
        
        public ImageIcon drawCardImage() throws IllegalArgumentException{
            if(isEmpty()){
                throw new IllegalArgumentException("Cannot");    
            }
            return new ImageIcon (cards[--cardsindeck].toString()+ ".png");
        }
        
        public Card[] drawCard (int n){
            if(n<0){
               throw new IllegalArgumentException("Cannot");  
            }
            if (n> cardsindeck){
                throw new IllegalArgumentException("Cannot");
            }
            Card[] ret = new Card[n];
            
            for (int i = 0 ; i < n; i++){
                ret[i] = cards[--cardsindeck];
            }
            return ret;            
        }
    }

