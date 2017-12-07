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
public class Square extends Expression implements Numeral{
    private Expression expression;

    public Square(Expression expression) {
        this.expression = expression;
    }
    
    @Override
    public String toString() {
        return "Square{" + "expression=" + expression + '}';
    }
    

    @Override
    public int evaluate() {
        int tmp=expression.evaluate();
        return tmp*tmp;
    }
    
    
}
