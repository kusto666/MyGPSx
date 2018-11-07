package mygpsx;

import java.io.IOException;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;

public class CUserCellIntoTmpl  extends ListCell<CStructAttrTmpl>
{
	
	@FXML
	Label fxLbUniqueID;
	@FXML
	TextField fxTxtNumberAttrjob;
	@FXML
	public TextField fxTxtNameAttrjob;
	@FXML
	Button fxBtnDeleteAttrjob;
	@FXML
	FXMLLoader mLLoader;
	@FXML
	AnchorPane m_Pane;
	
	@Override
	public void updateItem(CStructAttrTmpl  item, boolean empty) 
	{
		super.updateItem(item, empty);
		// Rectangle rect = new Rectangle(100, 20);
        if(empty || item == null) 
        {
        	//rect.setFill(Color.web("blue"));
        	//item.getMyHeight();
            setText(null);
            setGraphic(null);
        }
        else
        {
            mLLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXCellIntoTmpl));
            try 
            {
                mLLoader.load();
                fxLbUniqueID = (Label)mLLoader.getNamespace().get("fxLbUniqueID");
                fxTxtNumberAttrjob = (TextField)mLLoader.getNamespace().get("fxTxtNumberAttrjob");
                fxTxtNumberAttrjob.setEditable(false);
                fxTxtNameAttrjob = (TextField)mLLoader.getNamespace().get("fxTxtNameAttrjob");
                fxBtnDeleteAttrjob = (Button)mLLoader.getNamespace().get("fxBtnDeleteAttrjob");
        		m_Pane = (AnchorPane)mLLoader.getNamespace().get("fxCellPane");
        		// Здесь выставляем разную высоту для ячейки листа шаблона - блять ! Работает!!!
        		// Get all different heigth from realbase firebase!!!!!!!!!!!!!!!!!!!!
        		// It`s JOBING!!!!!
        		m_Pane.setPrefHeight(Double.parseDouble(item.getMyAttrHeight()));

        		fxLbUniqueID.setText(String.valueOf(item.getMyIDUnique()));
        		fxTxtNumberAttrjob.setText(String.valueOf(item.getMyAttrID()));
        		fxTxtNameAttrjob.setText(String.valueOf(item.getMyAttrName()));
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }

           // System.out.println(fxTxtNameAttrobj.getText());
            setText(null);
            setGraphic(m_Pane);
        }
	}
}