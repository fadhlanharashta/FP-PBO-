package com.example.snakegame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.Random;

public class snakeGame extends Application {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int SIZERADIUS = 5;
    private static final int MOVE = 8;
    private Pane root;
    private Circle food;
    private Circle player;
    private Random random;
    private Controller controllerDirection;

    //create snake
    private void addPlayer(){
        //To make the snake appear in the middle of the panel
        player = new Circle(HEIGHT/2, WIDTH/2, SIZERADIUS +1);
        root.getChildren().add(player);
    }

    //create food
    private void addFood(){
        //the food will not go beyond the window yet still appear randomly
        food = new Circle(random.nextInt(WIDTH), random.nextInt(HEIGHT), SIZERADIUS);
        //set food color
        food.setFill(Color.GREEN);
        root.getChildren().add(food);

    }

    private void movement(){
        if(controllerDirection == Controller.UP){
            player.setCenterY(player.getCenterY()-MOVE);
        } else if (controllerDirection == Controller.DOWN) {
            player.setCenterY(player.getCenterY()+MOVE);
        }else if (controllerDirection == Controller.LEFT) {
            player.setCenterX(player.getCenterX()-MOVE);
        }else if (controllerDirection == Controller.RIGHT) {
            player.setCenterX(player.getCenterX()+MOVE);
        }
    }
    //to keep the snake moving
    private void move(){
        Platform.runLater(()->{
            movement();
        });
    }

    @Override
    public void start(Stage stage){
        //create panel
        root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);
        random = new Random();
        controllerDirection = Controller.UP;

        addFood();
        addPlayer();

        Runnable r = () -> {
            try {
                for (; ;){
                    move();
                    Thread.sleep(500);
                }
            } catch (InterruptedException ie) {

            }
        };

        Scene scene = new Scene(root);

        //movement key
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            KeyCode order = event.getCode();
            if(order == KeyCode.UP){
                controllerDirection = Controller.UP;
            } else if (order == KeyCode.DOWN) {
                controllerDirection = Controller.DOWN;
            }else if (order == KeyCode.LEFT) {
                controllerDirection = Controller.LEFT;
            }else if (order == KeyCode.RIGHT) {
                controllerDirection = Controller.RIGHT;
            }
        });

        //show panel
        stage.setTitle("Ular Sanca Perkasa!!!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        Thread th = new Thread(r);
        th.setDaemon(true);
        th.start();
    }

    public static void main(String[] args)
    {
        launch();
    }
}