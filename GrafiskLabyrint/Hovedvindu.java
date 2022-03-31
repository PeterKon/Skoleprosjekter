

import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.FXCollections;
import java.io.File;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Hovedvindu extends Application {
	
	private Stage primaryStage;

	private final static int CHAR_SIZE = 70;

	private BorderPane rootPane;
	private FlowPane centerPane;

	private Label[] enteredChars;
	private TextField[] inputChars;

	private ListView<String> wordListView;
	private Ordliste wordListItems;
	private ObservableList<String> wordListObs;

	private Button openFileButton;
	private Button setNumOfCharsButton;


	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;

		rootPane = new BorderPane();
		Scene scene = new Scene(rootPane, 1000, 400);
		HBox topPane = new HBox();

		// create center gui
		setUpCenterGui(0);

		// create top gui
		openFileButton = new Button("Åpne ordliste");
		openFileButton.setOnAction(new OpenFileHandler());
		setNumOfCharsButton = new Button("Set antall bokstaver");
		setNumOfCharsButton.setDisable(true);
		setNumOfCharsButton.setOnAction(new SetNumOfCharsHandler());

		topPane.getChildren().addAll(openFileButton, setNumOfCharsButton);

		rootPane.setTop(topPane);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Kryssordløser");
		primaryStage.show();
	}


	private void setUpCenterGui(int num) {
		centerPane = new FlowPane();

		enteredChars = new Label[num];
		inputChars = new TextField[num];

		for (int i=0; i<num; i++) {
			enteredChars[i] = new Label();
			enteredChars[i].setPrefHeight(CHAR_SIZE);
			enteredChars[i].setPrefWidth(CHAR_SIZE);
			enteredChars[i].setStyle("-fx-font-size: 30px; -fx-alignment: center; -fx-background-color: white;");

			inputChars[i] = new TextField();
			inputChars[i].setPrefHeight(CHAR_SIZE);
			inputChars[i].setPrefWidth(CHAR_SIZE);
			inputChars[i].setStyle("-fx-font-size: 30px; -fx-alignment: center;");

			inputChars[i].setOnAction(new AddNewLetterHandler(i));
		}

		centerPane.getChildren().addAll(inputChars);
		centerPane.setAlignment(Pos.CENTER);
		rootPane.setCenter(centerPane);
	}


	private void setUpRightGui(File fil) {
		wordListView = new ListView<String>();
		wordListItems = Filleser.lesFil(fil);

		wordListObs = FXCollections.observableList(wordListItems);

		wordListView.setItems(wordListObs);
		rootPane.setRight(wordListView);

	}


	private class OpenFileHandler implements EventHandler<ActionEvent> {


		public void handle(ActionEvent e) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Åpne ordliste");

			File fil = fileChooser.showOpenDialog(primaryStage);

			if (fil == null) {
				return;
			}

			setUpRightGui(fil);
			setUpCenterGui(0);
			setNumOfCharsButton.setDisable(false);
		}

	}


	private class SetNumOfCharsHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Sett antall bokstaver");
			dialog.setHeaderText(null);
			dialog.setContentText("Antall bokstaver:");

			Optional<String> result = dialog.showAndWait();

			if (result.isPresent()) {
				int num = Integer.parseInt(result.get());

				setUpCenterGui(num);

				wordListItems.skrellVekk(num);
				wordListObs = FXCollections.observableList(wordListItems);
				wordListView.setItems(wordListObs);

				setNumOfCharsButton.setDisable(true);
			}
		}
	}


	private class AddNewLetterHandler implements EventHandler<ActionEvent> {
		private int index;

		public AddNewLetterHandler(int index) {
			this.index = index;
		}

		public void handle(ActionEvent e) {
			String inputValue = inputChars[index].getText();

			char inputValueChar = inputValue.charAt(0);

			wordListItems.skrellVekk(inputValueChar, index);
			wordListObs = FXCollections.observableList(wordListItems);
			wordListView.setItems(wordListObs);

			enteredChars[index].setText(inputValue);
			centerPane.getChildren().set(index, enteredChars[index]);
		}
	}

}