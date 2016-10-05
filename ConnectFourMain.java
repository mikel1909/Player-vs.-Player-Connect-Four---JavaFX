package connectFour;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ConnectFourMain extends Application {

	int widthCanvas = 630;
	int heightCanvas = 270;
	boolean redWin = false;
	boolean blackWin = false;
	int[][] board = new int[7][6];
	boolean redTurn = true;
	
	public static void main(String[] args){
		launch(args);
	}//main

	public void start(Stage primaryStage) {
		initialize(primaryStage);
	}//start
	
	private void initialize(Stage primaryStage){
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 0, 0, 25));
		//grid.setGridLinesVisible(true);
		
		HBox titleReset = new HBox();
		titleReset.setSpacing(422);
		Label title = new Label("Connect Four");
		title.setFont(Font.font("Tahoma", 20));
		Button reset = new Button("Restart Game");
		reset.setPrefSize(90, 20);
		reset.setOnAction(
		new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				redTurn = true;
				redWin = false;
				blackWin = false;
				for(int x = 0; x < 7; x ++){
					for(int y = 0; y < 6; y ++){
						board[x][y] = 0;
					}
				}
		  	}//handle
		}//EventHandler class
		);
		titleReset.getChildren().addAll(title, reset);
		grid.add(titleReset, 0, 0);
		
		HBox buttonHolder = new HBox();
		//buttonHolder.setSpacing(10);
		Button[] buttons = new Button[7];
		for(int i = 0; i < 7; i ++){
			buttons[i] = new Button("" + (i-3));
			buttons[i].setPrefSize(90, 40);
			buttonHolder.getChildren().add(buttons[i]);
		}
		grid.add(buttonHolder, 0, 1);
		
		for(int i = 0; i < 7; i ++){
			final int x = i;
			buttons[x].setOnAction(
			new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
			        for(int y = 5; y >= 0; y --){
			        	if(board[x][y] == 0){
			        		if(redTurn){
			        			board[x][y] = 1;
			        		}else{
			        			board[x][y] = 2;
			        		}
			        		redTurn = !redTurn;
			        		break;
			        	}
			        }
			  	}//handle
			}//EventHandler class
			);
		}
		
		Canvas canvas = new Canvas(widthCanvas, heightCanvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
	        
	    AnimationTimer timer = new AnimationTimer() {
	        public void handle(long now) {
	        	if(!blackWin && !redWin){
		            drawBackground(gc);
		            drawBoard(gc);
		            checkForFinishedGame(gc);
	        	}
	        }//handle
	    };//AnimationTimer
		grid.add(canvas, 0, 2);
		timer.start();
		
		Scene primaryScene = new Scene(grid, 680, 380);
		
		primaryStage.setScene(primaryScene);
        primaryStage.show();
	}//initialize
	
	private void drawBackground(GraphicsContext gc){
		gc.setFill(Color.AQUAMARINE);
		gc.fillRect(0, 0, widthCanvas, heightCanvas);//draw blue background
		
		gc.setFill(Color.BLACK);
		for(int i = 0; i <= 7; i ++){
			gc.strokeLine(90*i, 0, 90*i, heightCanvas);
		}//draw vertical lines
		for(int i = 0; i <= 6; i ++){
			gc.strokeLine(0, 45*i, widthCanvas, 45*i);
		}//draw horizontal lines
	}//drawBackground
	
	private void drawBoard(GraphicsContext gc){
		for(int x = 0; x < 7; x ++){
			for(int y = 0; y < 6; y ++){
				if(board[x][y] == 0){
					gc.setFill(Color.AQUAMARINE);
				}else if(board[x][y] == 1){
					gc.setFill(Color.RED);
				}else if(board[x][y] == 2){
					gc.setFill(Color.BLACK);
				}
				gc.fillOval(90*x+30, 45*y+15, 22.5, 22.5);
			}//for y
		}//for x
	}//drawBoard
	
	private void checkForFinishedGame(GraphicsContext gc){
		for(int y = 0; y < 6; y ++){
			for(int x = 0; x <= 3; x ++){
				if((board[x][y] > 0) && (board[x][y] == board[x+1][y]) && (board[x][y] == board[x+2][y]) && (board[x][y] == board[x+3][y])){
					if(board[x][y] == 1){
						redWin = true;
					}else{
						blackWin = true;
					}
				}
			}
		}//Horizontal Wins
		
		for(int x = 0; x < 7; x ++){
			for(int y = 0; y <= 2; y ++){
				if((board[x][y] > 0) && (board[x][y] == board[x][y+1]) && (board[x][y] == board[x][y+2]) && (board[x][y] == board[x][y+3])){
					if(board[x][y] == 1){
						redWin = true;
					}else{
						blackWin = true;
					}
				}
			}
		}//Vertical Wins
		
		for(int x = 0; x <= 3; x ++){
			for(int y = 0; y <= 2; y ++){
				if((board[x][y] > 0) && (board[x][y] == board[x+1][y+1]) && (board[x][y] == board[x+2][y+2]) && (board[x][y] == board[x+3][y+3])){
					if(board[x][y] == 1){
						redWin = true;
					}else{
						blackWin = true;
					}
				}
			}
		}//Diagonal Top Left to Bottom Right
		
		for(int x = 3; x <= 6; x ++){
			for(int y = 0; y <= 2; y ++){
				if((board[x][y] > 0) && (board[x][y] == board[x-1][y+1]) && (board[x][y] == board[x-2][y+2]) && (board[x][y] == board[x-3][y+3])){
					if(board[x][y] == 1){
						redWin = true;
					}else{
						blackWin = true;
					}
				}
			}
		}
		
		if(redWin){
			gc.setFill(Color.DARKVIOLET);
			gc.setFont(Font.font("Tahoma", 20));
			gc.fillText("Red Wins! Press 'Restart Game' to Play Again", 125, 50);
		}
		if(blackWin){
			gc.setFill(Color.DARKVIOLET);
			gc.setFont(Font.font("Tahoma", 20));
			gc.fillText("Black Wins! Press 'Restart Game' to Play Again", 125, 50);
		}
	}//checkForWin
	
}//class
