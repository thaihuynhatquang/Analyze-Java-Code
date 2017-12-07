/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package week8th;

/**
 *
 * @author Tung Duong
 */
public class Numeral extends Expression {
    private int value;

    public Numeral() {
    }

    public Numeral(int value) {
        this.value = value;
    }

    
    @Override
    public String toString() {
        return "Numeral{" + "value=" + value + '}';
    }

    

    @Override
    public int evaluate() {
        return value;
    }
    
    
}
