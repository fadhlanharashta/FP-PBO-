package com.example.snakegame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.util.Random;

public class snakeGame extends Application {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private static final int SIZERADIUS = 8;


    private Pane root;
    private Circle food;
    private Snake player;
    private Random random;
    private Text score;

    //create snake
    private void addPlayer(){
        //To make the snake appear in the middle of the panel
        player = new Snake(HEIGHT/2, WIDTH/2, SIZERADIUS +1);
        root.getChildren().add(player);
    }

    //create food
    private void addFood(){
        //the food will not go beyond the window yet still appear randomly
        food = new Circle(random.nextInt(WIDTH), random.nextInt(HEIGHT), SIZERADIUS);
        //set food color
        food.setFill(Color.RED);
        root.getChildren().add(food);

    }

    private boolean hit(){
        return food.intersects(player.getBoundsInLocal());
    }

    private boolean gameOver(){
        return player.suicide();
    }

    //to keep the snake moving
    private void move(){
        Platform.runLater(()->{
            player.movement();
            movementcorrection();
            if(hit()){
                player.eat(food);
                score.setText("Your score is "+player.getLength());
                addFood();
            } else if (gameOver()) {
                root.getChildren().clear();
                Image bg = new Image(getClass().getResource("ImageBackground/BackgroundForGame.jpg").toExternalForm());
                ImageView imgv = new ImageView(bg);
                root.getChildren().add(imgv);
                BackgroundImage myBI = new BackgroundImage(bg, BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,
                        BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
                score.setText("Game Over, Thanks for playing \nScore: " + player.getLength());
                score.setFont(Font.font ("Verdana", 16));
                score.setFill(Color.WHITE);
                root.getChildren().add(score);
                addPlayer();
                addFood();
            }
        });
    }

    private void movementcorrection(){
        if (player.getCenterX() < 0){
            player.setCenterX(WIDTH);
        } else if (player.getCenterX() > WIDTH) {
            player.setCenterX(0);
        }
        if (player.getCenterY() < 0){
            player.setCenterY(HEIGHT);
        } else if (player.getCenterY() > HEIGHT){
            player.setCenterY(0);
        }
    }

    @Override
    public void start(Stage stage){
        //create panel
        root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);
        random = new Random();
        score = new Text(0,32,"0");
        Canvas canvas = new Canvas(WIDTH,HEIGHT);
        root.getChildren().add(canvas);
        Image bg = new Image(getClass().getResource("ImageBackground/BackgroundForGame.jpg").toExternalForm());
        ImageView imgv = new ImageView(bg);
        root.getChildren().add(imgv);
        addFood();
        addPlayer();

        //snake move interval
        Runnable runsnake = () -> {
            try {
                for (; ;){
                    move();
                    Thread.sleep(100);
                }
            } catch (InterruptedException ie) {

            }
        };

        Scene scene = new Scene(root, Color.GREEN);

        //movement key
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            KeyCode order = event.getCode();
            if(order == KeyCode.W){
                player.setControllerDirection(Controller.UP);
            } else if (order == KeyCode.S) {
                player.setControllerDirection(Controller.DOWN);
            }else if (order == KeyCode.A) {
                player.setControllerDirection(Controller.LEFT);
            }else if (order == KeyCode.D) {
                player.setControllerDirection(Controller.RIGHT);
            }
        });

        //show panel
        stage.setTitle("Ular Sanca Perkasa!!!");
        stage.setScene(scene);

        BackgroundImage myBI = new BackgroundImage(bg, BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        stage.setResizable(false);
        stage.show();


        Thread th = new Thread(runsnake);
        th.setDaemon(true);
        th.start();

    }

    public static void main(String[] args)
    {
        launch();
    }
}