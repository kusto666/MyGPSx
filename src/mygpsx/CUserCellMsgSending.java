package mygpsx;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class CUserCellMsgSending  extends ListCell<CMessages>
{
	@FXML
	Label fxLbUniqueID;
	@FXML
	Label fxLbTimeSending;
	@FXML
	TextArea fxTxtAreaMsg;
	@FXML
	Hyperlink fxHLUploadFile;
	@FXML
	FXMLLoader mLLoader;
	@FXML
	AnchorPane m_Pane;
	
	
	@Override
	public void updateItem(CMessages item, boolean empty) 
	{
		super.updateItem(item, empty);
		
        if(empty || item == null) 
        {
            setText(null);
            setGraphic(null);
        } 
        else
        {
        	//System.out.println("mLLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXListCellUserMsgSendingFxml));");
            mLLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXListCellUserMsgSendingFxml));
            try 
            {
                mLLoader.load();
                fxLbUniqueID = (Label)mLLoader.getNamespace().get("fxLbUniqueID");
                fxLbTimeSending = (Label)mLLoader.getNamespace().get("fxLbTimeSending");
                fxTxtAreaMsg = (TextArea)mLLoader.getNamespace().get("fxTxtAreaMsg");
                fxHLUploadFile = (Hyperlink)mLLoader.getNamespace().get("fxHLUploadFile");
        		m_Pane = (AnchorPane)mLLoader.getNamespace().get("fxCellPane");
        		
        		//fxLbUniqueID.setText(String.valueOf(item.getMyPhoneID()));
        		fxLbTimeSending.setText(String.valueOf(item.msg_time));
        		fxTxtAreaMsg.setText(String.valueOf(item.msg_body));
        		
                setText(null);
                setGraphic(m_Pane);
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }

        }
	}
}
