package mygpsx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class ÑFXPreviewTemplateCtrl implements Initializable{

	@FXML
	private AnchorPane fxAPaneTmplMain;// Îíà ãëàâíàÿ - íà íåé âñå è êðåïèòñÿ(ðèñóåòñÿ)!!!
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//fxAPaneTmplMain.//
		Button addNode = new Button("Add");
		
		//addNode.setOnAction(e -> fxAPaneTmplMain.getChildren().add(new Circle(10));
		AnchorPane.setTopAnchor(addNode, 10.0);
		AnchorPane.setLeftAnchor(addNode, 10.0);
		fxAPaneTmplMain.getChildren().add(addNode);
		
	}

}
