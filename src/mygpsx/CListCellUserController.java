package mygpsx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/*import com.google.firebase.tasks.Task;*/

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CListCellUserController implements Initializable{

	//private ComboBox<CStructUser> fxCbSelectUser2;
	
	
	@FXML
	private Label fxLbUniqueID;
	@FXML
	private Button fxBtnViewJobs;
	@FXML
	private Label lbMyNameShip;
	@FXML
	private Label lbMyDirectorShip;
	@FXML
	private AnchorPane fxCellPane;
	//String m_stBindingIDSysUser = null;
	CStructUser m_tempStructUser = null;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	// Удаление объекта.
	@FXML
    private void eventDeleteObject() 
    {
		// Ищем сразу привязку к сист. пользователю

		DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
		 .child(CMAINCONSTANTS.FB_users).child(CMAINCONSTANTS.MyPhoneID_ + fxLbUniqueID.getText()).getRef();
		ref.addListenerForSingleValueEvent(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot arg0)
			{
				m_tempStructUser = arg0.getValue(CStructUser.class);
				System.out.println("m_tempStructUser.getmySysUserBinding() = " + m_tempStructUser.getMySysUserBinding());
				System.out.println("m_tempStructUser.getMyEmail() = " + m_tempStructUser.getMyEmail());
				Platform.runLater(
			  			  () -> {
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Удаление судна!");
						
						if(m_tempStructUser.getMySysUserBinding() == null)
						{
							m_tempStructUser.setMySysUserBinding("none");// Это для старых тестируемых пользователей!!!
						}
						if(m_tempStructUser.getMySysUserBinding().equals("none"))
						{
							alert.setHeaderText("Вы действительно хотите удалить судно ?");
						}
						else
						{
							alert.setHeaderText("Вы действительно хотите удалить судно,\nпривязанное к пользователю:\n" + 
									m_tempStructUser.getMyEmail() + " ?");
						}
						
						alert.setContentText(CStrings.m_APP_NAME_CHOOSE);
						ButtonType buttonTypeYes = new ButtonType(CStrings.m_APP_NAME_CHOOSE_YES);
						ButtonType buttonTypeNo = new ButtonType(CStrings.m_APP_NAME_CHOOSE_NO);
						
						alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
						
						java.util.Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == buttonTypeYes)
						{
							try
							{
								// Первое: удаление судна из "users"
								FirebaseDatabase.getInstance().getReference()
								 .child(CMAINCONSTANTS.FB_users)
								 .child(CMAINCONSTANTS.MyPhoneID_ + fxLbUniqueID.getText())
								 .setValueAsync(null);
								
								// Второе: unbinding from my_sys_users_binding id of ship
								if(!m_tempStructUser.getMySysUserBinding().equals("none"))
								{
									FirebaseDatabase.getInstance().getReference()
									 .child(CMAINCONSTANTS.FB_my_sys_users_binding)
									 .child(m_tempStructUser.getMySysUserBinding()).child("myPhoneBinding")
									 .setValueAsync("none");
								}
								
								System.out.println("Удаление судна.");
								CMyToast.makeText(CLPSMain.stage,
										"Судно успешно удалено!",
										CMyToast.TOAST_SHORT, CMyToast.TOAST_SUCCESS);
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
						} 
						else
						{
				
						}
						System.out.println("eventDeleteObject()");
			  		});
			}
			
			@Override
			public void onCancelled(DatabaseError arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		

    }
	// Редактирование объекта.
	@FXML
    private void eventEditObject() 
    {
		CCONSTANTS_EVENTS_JOB.EDITING_SHIP_ID = CMAINCONSTANTS.MyPhoneID_ + fxLbUniqueID.getText();
		System.out.println("eventEditObject() = " + CCONSTANTS_EVENTS_JOB.EDITING_SHIP_ID);
    	CCONSTANTS_EVENTS_JOB.SAMPLE_JOBING = "EDIT_SHIP";
    	try 
    	{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXEditShipFxml));
            CLPSMain.m_rootEditShip = (Parent)fxmlLoader.load();
            CLPSMain.m_stageEditShip = new Stage();
            CLPSMain.m_stageEditShip.setTitle(CStrings.m_APP_NAME + "->Редактирование ship");
            CLPSMain.m_stageEditShip.setScene(new Scene(CLPSMain.m_rootEditShip));  
            CLPSMain.m_stageEditShip.setResizable(false);
            CLPSMain.m_stageEditShip.setAlwaysOnTop(true); // А так ВЫБИРАТЬ КООРДИНАТЫ УДОБНЕЙ!!!
            CLPSMain.m_stageEditShip.initOwner(CLPSMain.stage);
            CLPSMain.m_stageEditShip.show();
            
            CMainController.fxLbLatitudeText = (Label)fxmlLoader.getNamespace().get("fxLbLatitudeText");
            CMainController.fxLbLongitudeText = (Label)fxmlLoader.getNamespace().get("fxLbLongitudeText");
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }

	@FXML
    private void BtnViewJobs(ActionEvent event) 
    {
		CCONSTANTS_EVENTS_JOB.MY_CHANGE_TAB_TO_MY_TAB_1_NAME = true;
		CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_SHIP = fxLbUniqueID.getText();
		System.out.println("BtnViewJobs - CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_SHIP = " + CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_SHIP);
		CCONSTANTS_EVENTS_JOB.MY_TABPANE_MAIN.getSelectionModel().select(1);
    }

}
