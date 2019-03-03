package mygpsx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class CFXPreviewTemplateCtrl implements Initializable{

	@FXML
	private AnchorPane fxAPaneTmplMain;// ќна главна¤ - на ней все и крепитс¤(рисуетс¤)!!!
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXCreateTemplate));
		try 
		{
			fxmlLoader.load();
			fxAPaneTmplMain = (AnchorPane)fxmlLoader.getNamespace().get("fxAPaneMain");
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*//fxAPaneTmplMain.// 
		Button addNode = new Button("Add");
		
		//addNode.setOnAction(e -> fxAPaneTmplMain.getChildren().add(new Circle(10));
		AnchorPane.setTopAnchor(addNode, 10.0);
		AnchorPane.setLeftAnchor(addNode, 10.0);
		fxAPaneTmplMain.getChildren().add(addNode);*/
		
	}

}
