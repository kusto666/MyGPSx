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
    		AddPriorityInBase();
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		fxTxtNumberPriority.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{

			@Override
			public void handle(KeyEvent event) 
			{
				//System.out.println("OnKeyInterPressed");
				if (event.getCode().equals(KeyCode.ENTER))
	            {
					AddPriorityInBase();
	            }
			}
		});
		
		fxTxtNamePriority.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{

			@Override
			public void handle(KeyEvent event) 
			{
				//System.out.println("OnKeyInterPressed");
				if (event.getCode().equals(KeyCode.ENTER))
	            {
					AddPriorityInBase();
	            }
			}
		});
		
	}
	void AddPriorityInBase()
	{
		CLPSMain.mDatabase = FirebaseDatabase.getInstance()
				.getReference()
				.child(CMAINCONSTANTS.FB_my_owner_settings)
				.child(CMAINCONSTANTS.FB_my_priority);
		
		String uploadId = CLPSMain.mDatabase.push().getKey();
		CLPSMain.mDatabase.child(uploadId).child("MyIDUnique").
		setValueAsync(uploadId);
		CLPSMain.mDatabase.child(uploadId).child("MyCLassPriority").
		setValueAsync(fxTxtNumberPriority.getText());
		CLPSMain.mDatabase.child(uploadId).child("MyNamePriority").
		setValueAsync(fxTxtNamePriority.getText());
		
		CPriorityEditController.m_stagePriorityAdd.close();
	}
}
