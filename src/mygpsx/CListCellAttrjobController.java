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
public class CListCellAttrjobController implements Initializable{

	private String stUniqueIDAttrjob = null;
	@FXML
	private Label fxLbUniqueID;
	@FXML
	private Button fxBtnDeleteAttrjob;
	@FXML
	private Button fxBtnAddAttrjobIntoTmpl;
	@FXML
	private TextField fxTxtNameAttrjob;
	@FXML
    private void BtnDeleteAttrjob(ActionEvent event) 
    {
    	try 
    	{
    		
    		System.out.println("BtnDeleteAttrjob");
    		CLPSMain.mDatabase = FirebaseDatabase.getInstance()
    				.getReference()
    				.child(CMAINCONSTANTS.FB_my_owner_settings)
    				.child(CMAINCONSTANTS.FB_my_attrjob);
    		
    		Button btn = (Button)event.getSource();// Здесь получили кнопку!!!
    		System.out.println("btn = " + btn.toString());
    		AnchorPane ap = (AnchorPane)btn.getParent();// Здесь получили родительскую панель!!!
    		System.out.println("ap = " + ap.toString());
    		
    		ObservableList<Node> listNode = ap.getChildren();// Здесь получаем массив всех дочерних объектов родителя!!! 
    		Label nodeOne = (Label)listNode.get(5);// Выбераем по ID(ID - это от 0 и т.д. выше!) объект(контролл)
    		stUniqueIDAttrjob = nodeOne.getText();// Порядок можно посмотреть в Scene Biulder
    		System.out.println("stUniqueIDAttrjob = " + stUniqueIDAttrjob);
    		CLPSMain.mDatabase = FirebaseDatabase.getInstance()
    				.getReference()
    				.child(CMAINCONSTANTS.FB_my_owner_settings)
    				.child(CMAINCONSTANTS.FB_my_attrjob)
    				.child(stUniqueIDAttrjob);
    		CLPSMain.mDatabase.setValue(null);// Удаляем значение(объект) из базы!!!
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }
	@FXML
    private void BtnAddAttrjobIntoTmpl(ActionEvent event) 
    {
		String stTempUniqueID = CLPSMain.mDatabase.push().getKey();
		CLPSMain.mDatabase = FirebaseDatabase.getInstance()
				.getReference()
				.child(CMAINCONSTANTS.FB_my_owner_settings)
				.child(CMAINCONSTANTS.FB_my_templates)
				.child(CMAINCONSTANTS.m_UniqueTempIDTempate).child(CMAINCONSTANTS.FB_my_adding_attr);
		CLPSMain.mDatabase.child(stTempUniqueID).child("attr_id").setValue(fxLbUniqueID.getText());
		CLPSMain.mDatabase.child(stTempUniqueID).child("attr_name").setValue(fxTxtNameAttrjob.getText());
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		if(CCONSTANTS_EVENTS_JOB.SAMPLE_ANY_OR_ANY.equals("DEL")) 
		{
			fxBtnDeleteAttrjob.setVisible(true);
			fxBtnAddAttrjobIntoTmpl.setVisible(false);
		}
		if(CCONSTANTS_EVENTS_JOB.SAMPLE_ANY_OR_ANY.equals("ADD")) 
		{
			fxBtnDeleteAttrjob.setVisible(false);
			fxBtnAddAttrjobIntoTmpl.setVisible(true);
		}
		fxTxtNameAttrjob.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{
			@Override
			public void handle(KeyEvent event) 
			{
				System.out.println("OnKeyInterPressed");
				if (event.getCode().equals(KeyCode.ENTER))
	            {
					AnchorPane ap = (AnchorPane)fxTxtNameAttrjob.getParent();// Здесь получили родительскую панель!!!
					ObservableList<Node> listNode = ap.getChildren();// Здесь получаем массив всех дочерних объектов родителя!!! 
		    		Label nodeOne = (Label)listNode.get(5);
		    		stUniqueIDAttrjob = nodeOne.getText();
		    		
		    		CLPSMain.mDatabase = FirebaseDatabase.getInstance()
		    				.getReference()
		    				.child(CMAINCONSTANTS.FB_my_owner_settings)
		    				.child(CMAINCONSTANTS.FB_my_attrjob)
		    				.child(stUniqueIDAttrjob);
		    		CLPSMain.mDatabase.child("MyNameAttrjob").setValue(fxTxtNameAttrjob.getText());
	            }
			}
		});
		
	}

}
