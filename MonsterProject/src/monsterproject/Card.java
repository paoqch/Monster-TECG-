/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monsterproject;

/**
 *
 * @author Gabriel Cerdas
 */
public class Card {
    enum Type{
        Hechizos, Secretos, Esbirros;
        
        private static final Type[] types=Type.values();
        public static Type getType(int i){
            return Type.types[i];
        }
    }
    
    enum Value{
        Tipo1, Tipo2, Tipo3, Tipo4, Tipo5, Tipo6, Tipo7, Tipo8, Tipo9, Tipo10;
        
        private static final Value[] values=Value.values();
        public static Value getValue(int i){
            return Value.values[i];
        }
    }
    
    private final Type type;
    private final Value value;
    
    public Card(final Type type, final Value value){
        this.type = type;
        this.value = value;
    }
    
    public Type getType(){
        return this.type;
    }
    
    public Value getValue(){
        return this.value;
    }
    
    public String toString(){
        return type + "_" + value;
    }
}
