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

public class CUserCellTypedescjob  extends ListCell<CStructTypedescjob>
{
	
	@FXML
	Label fxLbUniqueID;
	@FXML
	TextField fxTxtNumberTypedescjob;
	@FXML
	public TextField fxTxtNameTypedescjob;
	@FXML
	Button fxBtnDeleteTypedescjob;
	@FXML
	FXMLLoader mLLoader;
	@FXML
	AnchorPane m_Pane;
	
	@Override
	public void updateItem(CStructTypedescjob item, boolean empty) 
	{
		super.updateItem(item, empty);
		
        if(empty || item == null) 
        {
            setText(null);
            setGraphic(null);
        } 
        else
        {
            mLLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXListCellTypedescjob));
            try 
            {
                mLLoader.load();
                fxLbUniqueID = (Label)mLLoader.getNamespace().get("fxLbUniqueID");
                fxTxtNumberTypedescjob = (TextField)mLLoader.getNamespace().get("fxTxtNumberTypedescjob");
                fxTxtNumberTypedescjob.setEditable(false);
                fxTxtNameTypedescjob = (TextField)mLLoader.getNamespace().get("fxTxtNameTypedescjob");
                fxBtnDeleteTypedescjob = (Button)mLLoader.getNamespace().get("fxBtnDeleteTypedescjob");
        		m_Pane = (AnchorPane)mLLoader.getNamespace().get("fxCellPane");
        		
        		fxLbUniqueID.setText(String.valueOf(item.getMyIDUnique()));
        		fxTxtNumberTypedescjob.setText(String.valueOf(item.getMyCLassTypedescjob()));
        		fxTxtNameTypedescjob.setText(String.valueOf(item.getMyNameTypedescjob()));
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }

            System.out.println(fxTxtNameTypedescjob.getText());
            setText(null);
            setGraphic(m_Pane);
        }
	}
}