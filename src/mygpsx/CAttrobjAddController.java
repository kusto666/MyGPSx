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

public class CAttrobjAddController implements Initializable{

	@FXML
	private TextField fxTxtNumberAttrobj;
	@FXML
	private TextField fxTxtNameAttrobj;
	
	 // Добавляем приоритет!!!
    @FXML
    private void AddAttrobj(ActionEvent event) 
    {
    	try 
    	{
    		AddAttrobjInBase();
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		fxTxtNumberAttrobj.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{

			@Override
			public void handle(KeyEvent event) 
			{
				//System.out.println("OnKeyInterPressed");
				if (event.getCode().equals(KeyCode.ENTER))
	            {
					AddAttrobjInBase();
	            }
			}
		});
		
		fxTxtNameAttrobj.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{

			@Override
			public void handle(KeyEvent event) 
			{
				//System.out.println("OnKeyInterPressed");
				if (event.getCode().equals(KeyCode.ENTER))
	            {
					AddAttrobjInBase();
	            }
			}
		});
		
	}
	void AddAttrobjInBase()
	{
		CLPSMain.mDatabase = FirebaseDatabase.getInstance()
				.getReference()
				.child(CMAINCONSTANTS.FB_my_owner_settings)
				.child(CMAINCONSTANTS.FB_my_attrobj);
		
		String uploadId = CLPSMain.mDatabase.push().getKey();
		CLPSMain.mDatabase.child(uploadId).child("MyIDUnique").
		setValueAsync(uploadId);
		CLPSMain.mDatabase.child(uploadId).child("MyCLassAttrobj").
		setValueAsync(fxTxtNumberAttrobj.getText());
		CLPSMain.mDatabase.child(uploadId).child("MyNameAttrobj").
		setValueAsync(fxTxtNameAttrobj.getText());
		CLPSMain.mDatabase.child(uploadId).child("MyHeight").
		setValueAsync("55");
		
		CAttrobjEditController.m_stageAttrobjAdd.close();
	}
}
