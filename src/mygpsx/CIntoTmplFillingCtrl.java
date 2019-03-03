package mygpsx;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CIntoTmplFillingCtrl implements Initializable
{
	@FXML
	private TextField fxTxtNumberAttrjob;
	@FXML
	private TextField fxTxtName;
	@FXML
	private Button fxBtnDeleteAttrjob;
	@FXML
	private Label fxLbUniqueID;
	
	@FXML
    private void BtnDeleteAttrjob(ActionEvent event)// ќткрываем окно дл¤ добавлени¤ атрибутов!!!
    {
		System.out.println("btnOpenFrameWithAttributes!!!");
		System.out.println("fxLbUniqueID.getText() = " + fxLbUniqueID.getText());
//    	CCONSTANTS_EVENTS_JOB.SAMPLE_JOBING = "ADD_SHIP";
		try 
		{
    		FirebaseDatabase.getInstance()
    				.getReference()
    				.child(CMAINCONSTANTS.FB_my_owner_settings)
    				.child(CMAINCONSTANTS.FB_my_templates)
    				.child(CMAINCONSTANTS.m_UniqueTempIDTempate)
    				.child(CMAINCONSTANTS.FB_my_adding_attr)
    				.child(fxLbUniqueID.getText()).setValueAsync(null);
    		System.out.println("Click - BtnDeleteAttrjob!!!");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
    	
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		System.out.println("initialize - CIntoTmplCtrl.");
		
	}
	

}
