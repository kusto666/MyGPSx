package mygpsx;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.firebase.database.FirebaseDatabase;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class CListCellTmplJobController implements Initializable{

	private String stUniqueIDAttrjob = null;
	@FXML
	private Label fxLbUniqueID;
	@FXML
	private Button fxBtnDeleteTmplJob;
	@FXML
	private Button fxBtnEditTmplJob;
	@FXML
	private TextField fxTxtNameAttrjob;

	@FXML
    private void BtnDeleteTmplJob(ActionEvent event) 
    {
    	try 
    	{
    		
    		System.out.println("BtnDeleteTmplJob");
    		FirebaseDatabase.getInstance()
    				.getReference()
    				.child(CMAINCONSTANTS.FB_my_owner_settings)
    				.child(CMAINCONSTANTS.FB_my_templates)
    				.child(fxLbUniqueID.getText()).setValueAsync(null);
    		
    		
    		//CLPSMain.mDatabase.setValueAsync(null);// Удаляем значение(объект) из базы!!!
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }
	@FXML
	private void BtnEditTmplJob(ActionEvent event) 
    {
    	try 
    	{
    		 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXEditTemplate));
             CLPSMain.m_rootFXEditTemplateJobs = (Parent)fxmlLoader.load();
             CLPSMain.m_stageFXEditTemplateJobs = new Stage();
             CLPSMain.m_stageFXEditTemplateJobs.setTitle(CStrings.m_APP_NAME + "->Редактирование шаблона задачи");
             CLPSMain.m_stageFXEditTemplateJobs.setScene(new Scene(CLPSMain.m_rootFXEditTemplateJobs));  
             CLPSMain.m_stageFXEditTemplateJobs.setResizable(false);
             CLPSMain.m_stageFXEditTemplateJobs.initModality(Modality.WINDOW_MODAL);// Было , кода думал, что так лучше))) Но так не выбрать координаты!!!
             CLPSMain.m_stageFXEditTemplateJobs.initOwner(CLPSMain.stage);
             CLPSMain.m_stageFXEditTemplateJobs.show();
    		 System.out.println("BtnEditTmplJob - open FXEditTemplateJobs!!!");
     		 CMAINCONSTANTS.m_UniqueTempEditIDTempate = fxLbUniqueID.getText();

    		
    		 //((Stage)fxBtnEditTmplJob.getScene().getWindow()).close();
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{

	}

}
