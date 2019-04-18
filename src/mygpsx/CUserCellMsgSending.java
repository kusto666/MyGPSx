package mygpsx;

import java.io.IOException;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
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
	//@FXML
	String mbIsText;
	
	
	@SuppressWarnings("unused")
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
        	
            mLLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXListCellUserMsgSendingFxml));
            try 
            {
            	
                mLLoader.load();
                fxLbUniqueID = (Label)mLLoader.getNamespace().get("fxLbUniqueID");
                fxLbTimeSending = (Label)mLLoader.getNamespace().get("fxLbTimeSending");
                fxTxtAreaMsg = (TextArea)mLLoader.getNamespace().get("fxTxtAreaMsg");
                fxHLUploadFile = (Hyperlink)mLLoader.getNamespace().get("fxHLUploadFile");
        		
                
        		// Здесь определям какое сообщение входящее или исходящее!!!
                if(item.msg_to_user.equals("disp777"))
                {
                	fxLbUniqueID.setText("ВХОДЯЩЕЕ <<<<<");
                }
                else
                {
                	fxLbUniqueID.setText("ИСХОДЯЩЕЕ >>>>>");
                }
        		//fxLbUniqueID.setText(String.valueOf(item.msg_status));
        		fxLbTimeSending.setText(String.valueOf(item.msg_time));

        		m_Pane = (AnchorPane)mLLoader.getNamespace().get("fxCellPane");
        		mbIsText = String.valueOf(item.msg_is_text);
        		if(mbIsText.equals("true"))
        		{
        			fxTxtAreaMsg.setWrapText(true);
        			fxTxtAreaMsg.setText(String.valueOf(item.msg_body));
        			// Здесь делаем высоту по тексту!!!
        			int iCountLineOfText = fxTxtAreaMsg.getText().split("\n").length;
        			//int iCountLineOfText = fxTxtAreaMsg.get
        			// Здесь отладка высоты TextView!!!
                    System.out.println("Здесь отладка высоты TextView!!! кол-во сторок = " + Integer.toString(iCountLineOfText));
                    fxTxtAreaMsg.setPrefHeight( iCountLineOfText * 21 );
        			
        			fxHLUploadFile.setVisible(false);
        		}
        		else
        		{
        			fxTxtAreaMsg.setPrefHeight(0.0d);
        			fxHLUploadFile.setText("СКАЧАТЬ ФАЙЛ...");
        			fxHLUploadFile.setOnAction(new EventHandler<ActionEvent>() 
        			{
   					 
			            @Override
			            public void handle(ActionEvent event) {
			            	CLPSMain.m_myHostServicesLinks.showDocument(String.valueOf(item.msg_body));
			            }
			        });
        		}
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
            setText(null);
            setGraphic(m_Pane);
        }
        	
	}
}
