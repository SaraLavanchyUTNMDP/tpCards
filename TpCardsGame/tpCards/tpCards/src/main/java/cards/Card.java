/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cards;

/**
 *
 * @author Lavanchy
 */
public class Card {
    int number;
    TypeOfCard type;

    public Card(int number, TypeOfCard type) {
        this.number = number;
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public TypeOfCard getType() {
        return type;
    }

    public void setType(TypeOfCard type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.number+"|"+this.type;
    }
    
    
    
    
}
