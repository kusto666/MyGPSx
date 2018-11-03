package mygpsx;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.firebase.database.FirebaseDatabase;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CPriorityAddController implements Initializable{

	@FXML
	private TextField fxTxtNumberPriority;
	@FXML
	private TextField fxTxtNamePriority;
	
	 // Добавляем приоритет!!!
    @FXML
    private void AddPriority(ActionEvent event) 
    {
    	try 
    	{
    		CLPSMain.mDatabase = FirebaseDatabase.getInstance()
    				.getReference()
    				.child(CMAINCONSTANTS.FB_my_owner_settings)
    				.child(CMAINCONSTANTS.FB_my_priority);
    		
    		String uploadId = CLPSMain.mDatabase.push().getKey();
    		CLPSMain.mDatabase.child(uploadId).child("fxTxtNumberPriority").
			setValue(fxTxtNumberPriority.getText());
    		CLPSMain.mDatabase.child(uploadId).child("fxTxtNamePriority").
			setValue(fxTxtNamePriority.getText());
    		
    		CPriorityEditController.m_stagePriorityAdd.close();
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		// TODO Auto-generated method stub
		
	}

}
