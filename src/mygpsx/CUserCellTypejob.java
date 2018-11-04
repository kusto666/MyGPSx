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

public class CUserCellTypejob  extends ListCell<CStructTypejob>
{
	
	@FXML
	Label fxLbUniqueID;
	@FXML
	TextField fxTxtNumberTypejob;
	@FXML
	public TextField fxTxtNameTypejob;
	@FXML
	Button fxBtnDeleteTypejob;
	@FXML
	FXMLLoader mLLoader;
	@FXML
	AnchorPane m_Pane;
	
	@Override
	public void updateItem(CStructTypejob item, boolean empty) 
	{
		super.updateItem(item, empty);
		
        if(empty || item == null) 
        {
            setText(null);
            setGraphic(null);
        } 
        else
        {
            mLLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXListCellTypejob));
            try 
            {
                mLLoader.load();
                fxLbUniqueID = (Label)mLLoader.getNamespace().get("fxLbUniqueID");
                fxTxtNumberTypejob = (TextField)mLLoader.getNamespace().get("fxTxtNumberTypejob");
                fxTxtNumberTypejob.setEditable(false);
                fxTxtNameTypejob = (TextField)mLLoader.getNamespace().get("fxTxtNameTypejob");
                fxBtnDeleteTypejob = (Button)mLLoader.getNamespace().get("fxBtnDeleteTypejob");
        		m_Pane = (AnchorPane)mLLoader.getNamespace().get("fxCellPane");
        		
        		fxLbUniqueID.setText(String.valueOf(item.getMyIDUnique()));
        		fxTxtNumberTypejob.setText(String.valueOf(item.getMyCLassTypejob()));
        		fxTxtNameTypejob.setText(String.valueOf(item.getMyNameTypejob()));
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }

            System.out.println(fxTxtNameTypejob.getText());
            setText(null);
            setGraphic(m_Pane);
        }
	}
}