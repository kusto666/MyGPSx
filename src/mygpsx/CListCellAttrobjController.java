package mygpsx;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.firebase.database.FirebaseDatabase;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

@SuppressWarnings("unused")
public class CListCellAttrobjController implements Initializable{

	private String stUniqueIDAttrobj = null;
	@FXML
	private TextField fxTxtNameAttrobj;
	@FXML
    private void BtnDeleteAttrobj(ActionEvent event) 
    {
    	try 
    	{
    		
    		System.out.println("BtnDeleteAttrobj");
    		CLPSMain.mDatabase = FirebaseDatabase.getInstance()
    				.getReference()
    				.child(CMAINCONSTANTS.FB_my_owner_settings)
    				.child(CMAINCONSTANTS.FB_my_attrobj);
    		
    		Button btn = (Button)event.getSource();// Здесь получили кнопку!!!
    		System.out.println("btn = " + btn.toString());
    		AnchorPane ap = (AnchorPane)btn.getParent();// Здесь получили родительскую панель!!!
    		System.out.println("ap = " + ap.toString());
    		
    		ObservableList<Node> listNode = ap.getChildren();// Здесь получаем массив всех дочерних объектов родителя!!! 
    		Label nodeOne = (Label)listNode.get(5);// Выбераем по ID(ID - это от 0 и т.д. выше!) объект(контролл)
    		stUniqueIDAttrobj = nodeOne.getText();// Порядок можно посмотреть в Scene Biulder
    		System.out.println("stUniqueIDAttrobj = " + stUniqueIDAttrobj);
    		CLPSMain.mDatabase = FirebaseDatabase.getInstance()
    				.getReference()
    				.child(CMAINCONSTANTS.FB_my_owner_settings)
    				.child(CMAINCONSTANTS.FB_my_attrobj)
    				.child(stUniqueIDAttrobj);
    		CLPSMain.mDatabase.setValueAsync(null);// Удаляем значение(объект) из базы!!!
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		fxTxtNameAttrobj.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{
			@Override
			public void handle(KeyEvent event) 
			{
				System.out.println("OnKeyInterPressed");
				if (event.getCode().equals(KeyCode.ENTER))
	            {
					AnchorPane ap = (AnchorPane)fxTxtNameAttrobj.getParent();// Здесь получили родительскую панель!!!
					ObservableList<Node> listNode = ap.getChildren();// Здесь получаем массив всех дочерних объектов родителя!!! 
		    		Label nodeOne = (Label)listNode.get(5);
		    		stUniqueIDAttrobj = nodeOne.getText();
		    		
		    		CLPSMain.mDatabase = FirebaseDatabase.getInstance()
		    				.getReference()
		    				.child(CMAINCONSTANTS.FB_my_owner_settings)
		    				.child(CMAINCONSTANTS.FB_my_attrobj)
		    				.child(stUniqueIDAttrobj);
		    		CLPSMain.mDatabase.child("MyNameAttrobj").setValueAsync(fxTxtNameAttrobj.getText());
	            }
			}
		});
		
	}

}
