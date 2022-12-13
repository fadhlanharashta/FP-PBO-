package com.example.snakegame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Snake extends Circle{
    private List<Circle> tails;
    private int length = 0;
    private Controller controllerDirection;
    private static final int MOVE = 10;


    public Snake(double d, double d1, double d2){
        super(d, d1, d2);
        tails = new ArrayList<>();
        controllerDirection = Controller.UP;
    }
    //automatic movement
    public void movement(){
        for(int i = length-1; i>=0; i--){
            if(i==0){
                tails.get(i).setCenterX(getCenterX());
                tails.get(i).setCenterY(getCenterY());
            }
            else{
                tails.get(i).setCenterX(tails.get(i-1).getCenterX());
                tails.get(i).setCenterY(tails.get(i-1).getCenterY());
            }
        }
        if(controllerDirection == Controller.UP){
            setCenterY(getCenterY()- MOVE);
        } else if (controllerDirection == Controller.DOWN) {
            setCenterY(getCenterY()+ MOVE);
        }else if (controllerDirection == Controller.LEFT) {
            setCenterX(getCenterX()- MOVE);
        }else if (controllerDirection == Controller.RIGHT) {
            setCenterX(getCenterX()+ MOVE);
        }
    }

    public boolean suicide(){
        for(int i = 0; i < length; i++){
            if(this.getCenterX()==tails.get(i).getCenterX() && this.getCenterY()==tails.get(i).getCenterY()){
                return true;
            }
        }return false;
    }

    public Controller getControllerDirection(){
        return controllerDirection;
    }
    public void setControllerDirection(Controller controllerDirection){
        this.controllerDirection = controllerDirection;
    }
    private Circle endTail(){
        if(length == 0){
            return this;
        }return tails.get(length-1);
    }
    public void eat(Circle food){
        Circle tail = endTail();
        food.setCenterX(tail.getCenterX());
        food.setCenterY(tail.getCenterY());
        food.setFill(Color.BLACK);
        tails.add(length++, food);
    }
    public int getLength (){
        return length;
    }
}
