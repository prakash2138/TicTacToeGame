package com.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    // GUI variables...
    private GridPane gridPane = new GridPane();
    private BorderPane borderPane = new BorderPane();
    private Label title = new Label("Tic Tac Toe Game");
    private Button restartButton = new Button("Restart Game");
    Font font = Font.font("Roboto", FontWeight.BOLD, 20);
    //8 buttons...
    private Button[] btns = new Button[9];

    // Logic variables...
    boolean gameOver = false;
    int activePlayer = 0;
    int gameState[] = {3, 3, 3, 3, 3, 3, 3, 3, 3};
    int winningPosition[][] = {
        {0, 1, 2},
        {3, 4, 5},
        {6, 7, 8},
        {0, 3, 6},
        {1, 4, 7},
        {2, 5, 8},
        {0, 4, 8},
        {2, 4, 6}
    };

    @Override
    public void start(Stage stage) throws IOException {
        this.createGUI();
        this.handleEvent();
        scene = new Scene(borderPane, 550, 650);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    //function to create GUI...
    private void createGUI() {
        //creating title...
        title.setFont(font);
        //creating restart button...
        restartButton.setFont(font);
        restartButton.setDisable(true);
        //setting title and restart button to borderPane...
        borderPane.setTop(title);
        borderPane.setBottom(restartButton);
        //setting borderpane components to center...
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setAlignment(restartButton, Pos.CENTER);
        borderPane.setPadding(new Insets(15, 15, 15, 15));
        //setting 9 game buttons i.e. 0-8 ...
        int label = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button();
                button.setFont(font);
                button.setId(label + "");
                button.setPrefWidth(150);
                button.setPrefHeight(150);
                gridPane.add(button, j, i);
                gridPane.setAlignment(Pos.CENTER);
                btns[label] = button;
                label++;
            }
        }
        borderPane.setCenter(gridPane);
    }

    //for event handling...
    private void handleEvent() {
        //restart button click...
        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                for (int i = 0; i < 9; i++) {
                    gameState[i] = 3;
                    //btns[i].setText("");
                    btns[i].setGraphic(null);
                    btns[i].setBackground(null);
                    btns[i].setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
                    gameOver = false;
                    restartButton.setDisable(true);
                }
            }
        });
        for (Button btn : btns) {
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    //System.out.println("no button");
                    Button currentBtn = (Button) t.getSource();
                    String ids = currentBtn.getId();
                    int idi = Integer.parseInt(ids);
                    //check for game over...
                    if (gameOver) {
                        //game over and print message...
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setContentText("Game Over !!! try to restart game...");
                        alert.show();
                    } else {
                        //game not over and continue...
                        //check for player...
                        if (gameState[idi] == 3) {
                            //proceed....
                            if (activePlayer == 1) {
                                //chance of 1...
                                //currentBtn.setText(activePlayer + "");
                                currentBtn.setGraphic(new ImageView(new Image("file:src/main/resources/images/x1.png")));
                                gameState[idi] = activePlayer;
                                checkForWinner();
                                activePlayer = 0;
                            } else {
                                //chance of 0...
                                //currentBtn.setText(activePlayer + "");
                                currentBtn.setGraphic(new ImageView(new Image("file:src/main/resources/images/zero1.png")));
                                gameState[idi] = activePlayer;
                                checkForWinner();
                                activePlayer = 1;
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Message");
                            alert.setContentText("Already marked this button...");
                            alert.show();
                        }
                    }
                }
            });
        }
    }

    //this method checks for the winner...
    private void checkForWinner() {
        //winner...
        if (!gameOver) {
            for (int wp[] : winningPosition) {
                if (gameState[wp[0]] == gameState[wp[1]] && gameState[wp[1]] == gameState[wp[2]] && gameState[wp[1]] != 3) {
                    //activePalyer wins...
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Success Message");
                    alert.setContentText("Player " + (activePlayer == 1 ? "X" : "0") + " Wins...");
                    btns[wp[0]].setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                    btns[wp[1]].setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                    btns[wp[2]].setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                    alert.show();
                    gameOver = true;
                    restartButton.setDisable(false);
                    break;
                }
            }
        }
    }

}
