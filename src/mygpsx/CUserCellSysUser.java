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

public class CUserCellSysUser  extends ListCell<CStructSysUser>
{
	
	@FXML
	Label fxLbUniqueID;
	@FXML
	TextField fxTxtNumberStatus;
	@FXML
	public TextField fxTxtEmail;
	@FXML
	Button fxBtnDeleteStatus;
	@FXML
	FXMLLoader mLLoader;
	@FXML
	AnchorPane m_Pane;
	
	@Override
	public void updateItem(CStructSysUser item, boolean empty) 
	{
		super.updateItem(item, empty);
		
        if(empty || item == null) 
        {
            setText(null);
            setGraphic(null);
        } 
        else
        {
            mLLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXListCellSysUser));
            try 
            {
                mLLoader.load();
                fxLbUniqueID = (Label)mLLoader.getNamespace().get("fxLbUniqueID");
                fxTxtNumberStatus = (TextField)mLLoader.getNamespace().get("fxTxtNumberStatus");
                fxTxtNumberStatus.setEditable(false);
                fxTxtEmail = (TextField)mLLoader.getNamespace().get("fxTxtEmail");
                fxBtnDeleteStatus = (Button)mLLoader.getNamespace().get("fxBtnDeleteStatus");
        		m_Pane = (AnchorPane)mLLoader.getNamespace().get("fxCellPane");
        		
        		fxLbUniqueID.setText(String.valueOf(item.getMyIDSysUser()));
        		//fxTxtNumberStatus.setText(String.valueOf(item.getMyCLassStatus()));
        		fxTxtEmail.setText(String.valueOf(item.getMyEmail()));
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }

            System.out.println(fxTxtEmail.getText());
            setText(null);
            setGraphic(m_Pane);
        }
	}
}