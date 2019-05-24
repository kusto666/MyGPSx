package mygpsx;

import java.io.IOException;

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
import javafx.util.Callback;

public class CUserCellPriority  extends ListCell<CStructPriority>
{
	
	@FXML
	Label fxLbUniqueID;
	@FXML
	TextField fxTxtNumberPriority;
	@FXML
	public TextField fxTxtNamePriority;
	@FXML
	Button fxBtnDeletePriority;
	@FXML
	FXMLLoader mLLoader;
	@FXML
	AnchorPane m_Pane;
	
	@Override
	public void updateItem(CStructPriority item, boolean empty) 
	{
		super.updateItem(item, empty);
		
        if(empty || item == null) 
        {
            setText(null);
            setGraphic(null);
        } 
        else
        {
            mLLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXListCellPriority));
            try 
            {
                mLLoader.load();
                fxLbUniqueID = (Label)mLLoader.getNamespace().get("fxLbUniqueID");
                fxTxtNumberPriority = (TextField)mLLoader.getNamespace().get("fxTxtNumberPriority");
                fxTxtNumberPriority.setEditable(false);
                fxTxtNamePriority = (TextField)mLLoader.getNamespace().get("fxTxtNamePriority");
                fxBtnDeletePriority = (Button)mLLoader.getNamespace().get("fxBtnDeletePriority");
        		m_Pane = (AnchorPane)mLLoader.getNamespace().get("fxCellPane");
        		
        		fxLbUniqueID.setText(String.valueOf(item.getMyIDUnique()));
                fxTxtNumberPriority.setText(String.valueOf(item.getMyCLassPriority()));
                fxTxtNamePriority.setText(String.valueOf(item.getMyNamePriority()));
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }

            System.out.println(fxTxtNamePriority.getText());
            setText(null);
            setGraphic(m_Pane);
        }
	}
}