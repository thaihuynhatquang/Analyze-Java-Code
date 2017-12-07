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
public class Multiplication extends BinaryExpression implements Expression{
    private Expression left;
    private Expression right;

    @Override
    public Expression left() {
        return left;
    }

    @Override
    public Expression right() {
        return right;    
    }

    @Override
    public String toString() {
        return "Multiplication{" + "left=" + left + ", right=" + right + '}';
    }

    

    public Multiplication(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    

    @Override
    public int evaluate() {
        int tmpLeft=this.left.evaluate();
        int tmpRight=this.right.evaluate();
        return tmpLeft*tmpRight;
    }
}
