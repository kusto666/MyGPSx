package mygpsx;

import java.io.IOException;
import java.util.Date;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

public class CUserCellMsg  extends ListCell<CStructUser>
{
	//fxLbUniqueID
	@FXML
	Label fxLbUniqueID;
	@FXML
	Label lbMyNameShip;
	@FXML
	Label lbMyDirectorShip;
	@FXML
	Label fxLbAlarmNewMsg;
	//@FXML
	//Button fxBtnViewJobs;
	//@FXML
	//Label fxLbBindingEmail;
	@FXML
	FXMLLoader mLLoader;
	@FXML
	AnchorPane m_Pane;
	
	DatabaseReference mDatabaseRef;
	
	
	@Override
	public void updateItem(CStructUser item, boolean empty) 
	{
		super.updateItem(item, empty);
		
        if(empty || item == null) 
        {
            setText(null);
            setGraphic(null);
        } 
        else
        {
        	System.out.println("mLLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXListCellUserMsgFxml));");
            mLLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXListCellUserMsgFxml));
            try 
            {
                mLLoader.load();
                fxLbUniqueID = (Label)mLLoader.getNamespace().get("fxLbUniqueID");
                lbMyNameShip = (Label)mLLoader.getNamespace().get("lbMyNameShip");
                //fxBtnViewJobs = (Button)mLLoader.getNamespace().get("fxBtnViewJobs");
                
                // Здесь проверим есть ли текущая задача, если есть, то пишем "Текущая задача" или "Задачи нет".
                // .......................
                lbMyDirectorShip = (Label)mLLoader.getNamespace().get("lbMyDirectorShip");
                fxLbAlarmNewMsg  = (Label)mLLoader.getNamespace().get("fxLbAlarmNewMsg");
                //fxLbBindingEmail = (Label)mLLoader.getNamespace().get("fxLbBindingEmail");
        		m_Pane = (AnchorPane)mLLoader.getNamespace().get("fxCellPane");
        		if(lbMyNameShip == null)
        		{
        			System.out.println("lbMyNameShip == null!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        		}
        		if(lbMyDirectorShip == null)
        		{
        			System.out.println("label2 == null!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        		}
        		if(m_Pane == null)
        		{
        			System.out.println("m_Pane == null!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        		}
        		fxLbUniqueID.setText(String.valueOf(item.getMyPhoneID()));
        		lbMyNameShip.setText(String.valueOf(item.getMyNameShip()));
        		lbMyDirectorShip.setText(String.valueOf(item.getMyDirectorShip()));
        		
        		GetIsNotReadingMsg();

        		
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }

            
            setText(null);
            setGraphic(m_Pane);
        }
	}
	@FXML
	void OnMouseClicked() throws IOException
	{
		fxLbAlarmNewMsg.setVisible(false);
		System.out.println("void OnMouseClicked()");
	}
	
	
	void GetIsNotReadingMsg()
	{
		 mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("message_to_android");

		// Attach a listener to read the data at our posts reference
		 mDatabaseRef.addValueEventListener(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot arg0) {
				
				if(CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG_FIREBASES != null)
				{
					if(CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG_FIREBASES.equals(CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_PREFIX + fxLbUniqueID.getText()))
					{
						System.out.println( "Проверка на то, что мы получили с андройда новое сообщение!!!");
						Platform.runLater(
			           			  () -> {
			           				fxLbAlarmNewMsg.setVisible(true);
			           			 CLPSMain.m_MyTrayIcon.displayMessage(
			                             CStrings.m_APP_NAME,
			                             "ПОЛУЧЕНО\nНОВОЕ\nСООБЩЕНИЕ!!!\n" + CLPSMain.m_MyTimeFormat.format(new Date()),
			                             java.awt.TrayIcon.MessageType.INFO
			                     );
			           			  });
						
						System.out.println("<<<<<<<<<<<<<<<  " + lbMyNameShip.getText() + "  >>>>>>>>>>>>>");
					}
				}

				
			}
			
			@Override
			public void onCancelled(DatabaseError arg0) {
				System.out.println( "onCancelled" + arg0.getMessage());
			}
		});

	}
}
