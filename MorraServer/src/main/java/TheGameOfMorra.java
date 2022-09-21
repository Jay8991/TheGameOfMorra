import java.util.HashMap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TheGameOfMorra extends Application {

	//so till now kind of taking port numbers , storing user input in class gamedecider


	MorraInfo info = new MorraInfo();
	TextField portnumber,specify_bet1;
	Label each_player_played, each_player_points,who_won_the_game,are_they_playing;
	Button serverChoice, clientChoice, b1, specify_bet;
	HashMap<String, Scene> sceneMap;
	GridPane grid;
	HBox buttonBox;
	VBox ServerBox;
	Scene startScene;
	BorderPane startPane;
	Server serverConnection;
	Scene scene;
	ListView<String> listItems, listItems2;
	int count_client = 0;
	Label label2;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("(Server) Let's Play Morra!!!");

		///this takes the portnumber here
		portnumber = new TextField("Enter Port Number");
		//this is for the serverbox
		ServerBox = new VBox();
		//this here is the button to start our server
		serverChoice = new Button("Start Server");
		ServerBox.getChildren().addAll(portnumber, serverChoice);
   label2 = new Label();
        each_player_points= new Label ();
       each_player_played = new Label();
		//Button event handler basically it will check for port and call server function
		serverChoice.setOnAction(e->{
			if(!portnumber.getText().isEmpty()) {
				if (portnumber.getText().equals("5555") ) {
					primaryStage.setScene(sceneMap.get("server"));
					primaryStage.setTitle("This is the Server");
					serverConnection = new Server(data -> {
						Platform.runLater(() -> {
							listItems.getItems().add(data.toString());

							each_player_played.setText(" there are: " + serverConnection.clients.size() + " Clients Available ");
							if (serverConnection.clients.size() == 2) {
								info.have_2_Players = true;
							}


						});

					});
				}
				else
				{
					label2.setText("Wrong Port Number Currently Port 5555 is available only  ");
					ServerBox.getChildren().add(label2);
				}
			}
			else
			{
				label2.setText("Enter Port Number to start");
			}

		});

        listItems  = new ListView<>();




		sceneMap = new HashMap<String, Scene>();


		sceneMap.put("server", createServerGui());

		Scene scene = new Scene(ServerBox, 400, 300);


		primaryStage.setScene(scene);

		primaryStage.show();




	}


	public Scene createServerGui() {
MorraInfo info = new MorraInfo();
		VBox serverleft = new VBox();
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		pane.setStyle("-fx-background-color: coral");

			specify_bet = new Button("Specifiy bet");

		who_won_the_game = new Label();
		are_they_playing = new Label();
		serverleft.getChildren().addAll(each_player_played,each_player_points,who_won_the_game,are_they_playing);
		pane.setLeft(serverleft);
		pane.setCenter(listItems);

		return new Scene(pane, 500, 400);


	}

}