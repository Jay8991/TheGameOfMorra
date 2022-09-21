import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;


public class TheGameOfMorra extends Application {

	MorraInfo info = new MorraInfo();
	TextField portnumber, Ipaddress;
	Label each_player_played, each_player_points,who_won_the_game,are_they_playing, total_guessed;
	Button serverChoice, clientChoice, b1,second_gui_Start, winning,winning1, quit1;
	Button play_again;
	Button quit;
	HashMap<String, Scene> sceneMap;
	GridPane grid;
	HBox buttonBox;
	VBox ClientBox;
	Scene startScene;
	BorderPane startPane;
	Client clientConnection;
	Scene scene;
	ListView<String> listItems2;
	int count_client = 0;
	Label specify_win;

	Label for_play ;
	Label label2;
	TextField c1;
	Image zero ;
	ImageView zero_view;
	Image one ;
	ImageView one_view ;
	Image two ;
	ImageView two_view ;
	Image three ;
	ImageView three_view ;
	Image four ;
	ImageView four_view ;
	Image five ;
	ImageView five_view;
	MorraInfo class_1 = new MorraInfo();


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("(Client) Let's Play Morra!!!");
		//////////////////////IMAGES FOR THE GAME //////////

		zero = new Image ("zero.png");
		zero_view = new ImageView();
		zero_view.setImage(zero);
		zero_view.setFitHeight(60);
		zero_view.setFitWidth(60);
		one = new Image("one.jpg");
		one_view = new ImageView();
		one_view.setImage(one);

		one_view.setFitHeight(60);
		one_view.setFitWidth(60);

		two = new Image("two.jpg");
		two_view = new ImageView();
		two_view.setImage(two);
		two_view.setFitHeight(60);
		two_view.setFitWidth(60);

		three = new Image ("three.jpg");
		three_view = new ImageView();
		three_view.setImage(three);
		three_view.setFitHeight(60);
		three_view.setFitWidth(60);

		four = new Image("four.jpg");
		four_view = new ImageView();
		four_view.setImage(four);

		four_view.setFitHeight(60);
		four_view.setFitWidth(60);

		five = new Image("five.jpg");
		five_view = new ImageView();
		five_view.setImage(five);
		five_view.setFitHeight(60);
		five_view.setFitWidth(60);
/////////////////////////Images that we will be using for our client/////////////
		play_again = new Button("Play Again");
		quit = new Button("Quit");

		portnumber = new TextField("Enter IP address");
		Ipaddress = new TextField("Port Number");
		//this is for the serverbox
		ClientBox = new VBox();
		//this here is the button to start our server
		serverChoice = new Button("Start Client");
		Label not_correct = new Label();
		ClientBox.getChildren().addAll(portnumber, Ipaddress,serverChoice, not_correct);


		label2 = new Label("Wrong IP OR PORTNUMBER ");
		clientConnection = new Client();
		each_player_played = new Label ();
		each_player_points= new Label ();

		are_they_playing = new Label();

		second_gui_Start = new Button("Start Game");
		second_gui_Start.setDisable(true);
		second_gui_Start.setOnAction(e->{
			primaryStage.setScene(sceneMap.get("Client"));
			primaryStage.setTitle("This is the Client");
			clientConnection = new Client(data -> {
				try {
					Client.sleep(1500);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}

				Platform.runLater(() -> {
					listItems2.getItems().add(data.toString());
					if(clientConnection.data1.client_size< 2)
					{
						listItems2.getItems().clear();
					}

				});

			});
			clientConnection.start();
		});


//		play_again.setDisable(false);
//		quit.setDisable(false);
		serverChoice.setOnAction(e->{
			if(!portnumber.getText().isEmpty() && !Ipaddress.getText().isEmpty() ) {
				if (portnumber.getText().equals("127.0.0.1") && Ipaddress.getText().equals("5555")) {
					primaryStage.setScene(sceneMap.get("Client2"));
					primaryStage.setTitle("RULES");

				}
				else
				{
					not_correct.setText("ip address or portnumber is wrong");
				}
			}
		});

		listItems2 = new ListView<>();
		total_guessed = new Label();
		sceneMap = new HashMap<String, Scene>();
		c1 = new TextField();
		b1 = new Button("Make total Guess here");


		b1.setDisable(false);
		/////doing this so that client can't make multiple choices"
		who_won_the_game = new Label();
		zero_view.setDisable(true);
		one_view.setDisable(true);
		two_view.setDisable(true);
		three_view.setDisable(true);
		four_view.setDisable(true);
		five_view.setDisable(true);



		play_again.setDisable(true);
		quit.setDisable(false);
		b1.setOnAction(e->{
			Platform.runLater(()->{
				total_guessed.setText(c1.getText());
				play_again.setDisable(false);
				b1.setDisable(true);
				zero_view.setDisable(false);
				one_view.setDisable(false);
				two_view.setDisable(false);
				three_view.setDisable(false);
				four_view.setDisable(false);
				five_view.setDisable(false);

			});
		});


		sceneMap.put("Client2", createClientGui2());


		sceneMap.put("Client", createClientGui());


		Scene scene = new Scene(ClientBox,300,300);
		primaryStage.setScene(scene);
		primaryStage.show();
	}





	public Scene createClientGui2() {
		MorraInfo info = new MorraInfo();
		VBox serverleft = new VBox();
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		pane.setStyle("-fx-background-color: coral");
		winning= new Button("2 points");
		winning1 = new Button("4 points");
		quit1 = new Button("press to quit");
		winning.setOnAction(e->{

			specify_win.setText("2");
			second_gui_Start.setDisable(false);
		});

		winning1.setOnAction(e->{

			specify_win.setText("4");
			second_gui_Start.setDisable(false);
		});

		quit1.setOnAction(e->
		{
			Platform.exit();
		});

		clientChoice = new Button("bet 2 points");
		specify_win = new Label();
		TextArea txtar = new TextArea();
		txtar.appendText("TO START THE GAME PLACE BET FIRST, OR PRESS QUIT TO LEAVE" );

		serverleft.getChildren().addAll( txtar,winning,winning1,quit1);

		serverleft.getChildren().addAll( second_gui_Start);

		pane.setCenter(serverleft);

		return new Scene(pane, 500, 400);


	}






	public Scene createClientGui() {
		clientConnection = new Client();
		//adding clickable image
		winning =  new Button ("Bet 2");
		VBox clientleft = new VBox();
		BorderPane pane = new BorderPane();
		Label lbl = new Label("Specifiy your winning points by: ");


		winning.setOnAction(e->{

			//clientConnection.mi.specified_winning = 2;
			//class_1.specified_winning = 2;
			play_again.setDisable(false);

		});

		for_play = new Label();


		play_again.setOnAction(e->
		{

			for_play.setText("true");

			b1.setDisable(false);
			zero_view.setDisable(false);
			one_view.setDisable(false);
			two_view.setDisable(false);
			three_view.setDisable(false);
			four_view.setDisable(false);
			five_view.setDisable(false);

		});




		quit.setOnAction(e->
		{
			Platform.runLater(()->
			{
				clientConnection.send_morra(0,0,true,0);

				listItems2.getItems().clear();
				Platform.exit();
				System.exit(0);

			});
		});


		pane.setPadding(new Insets(70));
		pane.setStyle("-fx-background-color: white");

		clientleft.getChildren().addAll(each_player_played,each_player_points,who_won_the_game,are_they_playing, total_guessed);
		HBox newh = new HBox();
		newh.getChildren().addAll(c1,b1,play_again,quit,each_player_played);

		pane.setLeft(clientleft);
		newh.setAlignment(Pos.CENTER);
		pane.setTop(newh);
		specify_win = new Label();

		//new vBox for clickable images///
		VBox vb_center = new VBox();
		///top HBOX in Vbox
		HBox h_box = new HBox();
		h_box.getChildren().addAll(zero_view, one_view);
		vb_center.getChildren().add(h_box);
		///second column
		HBox h_box1 = new HBox();
		h_box1.getChildren().addAll(two_view, three_view);
		vb_center.getChildren().add(h_box1);
		HBox h_box2 = new HBox();
		h_box1.getChildren().addAll(four_view, five_view);

		vb_center.getChildren().add(h_box2);
		pane.setCenter(vb_center);
		pane.setBottom(listItems2);



		//you have to send data using MORRA INFO RIGHT NOW WE ARE USING STRING SO MAKE SURE TO CHANGE IT AT THE END
		zero_view.setOnMouseClicked(e->
		{
			clientConnection.send_morra(0 , Integer.parseInt(total_guessed.getText()),  Boolean.parseBoolean(for_play.getText()), Integer.parseInt(specify_win.getText()));
		});

		one_view.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				clientConnection.send_morra(1, (Integer.parseInt(total_guessed.getText())),  Boolean.parseBoolean(for_play.getText()),Integer.parseInt(specify_win.getText()));
				//		clientConnection.send("15");
			}
		});


		three_view.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				clientConnection.send_morra(3 ,Integer.parseInt(total_guessed.getText()), Boolean.parseBoolean(for_play.getText()),Integer.parseInt(specify_win.getText()));
			}
		});
		two_view.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				clientConnection.send_morra(2, Integer.parseInt(total_guessed.getText()), Boolean.parseBoolean(for_play.getText()),Integer.parseInt(specify_win.getText()));
			}
		});


		four_view.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				clientConnection.send_morra(4 ,Integer.parseInt(total_guessed.getText()), Boolean.parseBoolean(for_play.getText()),Integer.parseInt(specify_win.getText()));
			}
		});

		five_view.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				clientConnection.send_morra(5, Integer.parseInt(total_guessed.getText()),  Boolean.parseBoolean(for_play.getText()),Integer.parseInt(specify_win.getText()));
			}
		});

		return new Scene(pane, 700, 400);

	}
}
