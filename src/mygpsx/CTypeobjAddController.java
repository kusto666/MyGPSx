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

public class CTypeobjAddController implements Initializable{

	@FXML
	private TextField fxTxtNumberTypeobj;
	@FXML
	private TextField fxTxtNameTypeobj;
	
	 // Добавляем приоритет!!!
    @FXML
    private void AddTypeobj(ActionEvent event) 
    {
    	try 
    	{
    		AddTypeobjInBase();
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		fxTxtNumberTypeobj.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{

			@Override
			public void handle(KeyEvent event) 
			{
				//System.out.println("OnKeyInterPressed");
				if (event.getCode().equals(KeyCode.ENTER))
	            {
					AddTypeobjInBase();
	            }
			}
		});
		
		fxTxtNameTypeobj.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{

			@Override
			public void handle(KeyEvent event) 
			{
				//System.out.println("OnKeyInterPressed");
				if (event.getCode().equals(KeyCode.ENTER))
	            {
					AddTypeobjInBase();
	            }
			}
		});
		
	}
	void AddTypeobjInBase()
	{
		CLPSMain.mDatabase = FirebaseDatabase.getInstance()
				.getReference()
				.child(CMAINCONSTANTS.FB_my_owner_settings)
				.child(CMAINCONSTANTS.FB_my_typeobj);
		
		String uploadId = CLPSMain.mDatabase.push().getKey();
		CLPSMain.mDatabase.child(uploadId).child("MyIDUnique").
		setValueAsync(uploadId);
		CLPSMain.mDatabase.child(uploadId).child("MyCLassTypeobj").
		setValueAsync(fxTxtNumberTypeobj.getText());
		CLPSMain.mDatabase.child(uploadId).child("MyNameTypeobj").
		setValueAsync(fxTxtNameTypeobj.getText());
		
		CTypeobjEditController.m_stageTypeobjAdd.close();
	}
}
