package mygpsx;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.firebase.database.FirebaseDatabase;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CAttrjobAddController implements Initializable{

	@FXML
	private TextField fxTxtNumberAttrjob;
	@FXML
	private TextField fxTxtNameAttrjob;
	
	 // Добавляем приоритет!!!
    @FXML
    private void AddAttrjob(ActionEvent event) 
    {
    	try 
    	{
    		AddAttrjobInBase();
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		fxTxtNumberAttrjob.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{

			@Override
			public void handle(KeyEvent event) 
			{
				//System.out.println("OnKeyInterPressed");
				if (event.getCode().equals(KeyCode.ENTER))
	            {
					AddAttrjobInBase();
	            }
			}
		});
		
		fxTxtNameAttrjob.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{

			@Override
			public void handle(KeyEvent event) 
			{
				//System.out.println("OnKeyInterPressed");
				if (event.getCode().equals(KeyCode.ENTER))
	            {
					AddAttrjobInBase();
	            }
			}
		});
		
	}
	void AddAttrjobInBase()
	{
		CLPSMain.mDatabase = FirebaseDatabase.getInstance()
				.getReference()
				.child(CMAINCONSTANTS.FB_my_owner_settings)
				.child(CMAINCONSTANTS.FB_my_attrjob);
		
		String uploadId = CLPSMain.mDatabase.push().getKey();
		CLPSMain.mDatabase.child(uploadId).child("MyIDUnique").
		setValue(uploadId);
		CLPSMain.mDatabase.child(uploadId).child("MyCLassAttrjob").
		setValue(fxTxtNumberAttrjob.getText());
		CLPSMain.mDatabase.child(uploadId).child("MyNameAttrjob").
		setValue(fxTxtNameAttrjob.getText());
		
		CAttrjobEditController.m_stageAttrjobAdd.close();
	}
}
