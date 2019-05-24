package mygpsx;

import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.http.util.TextUtils;

import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/*import com.google.firebase.tasks.OnCompleteListener;
import com.google.firebase.tasks.Task;*/
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.Marker;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import netscape.javascript.JSObject;

public class CEditShipController implements Initializable{
	 
	// DatabaseReference m_DatabaseRef;
	
	 @FXML
	 private TextField fxTxtEmail;
	 @FXML
	 private PasswordField fxTxtPassFirst;
	 @FXML
	 private PasswordField fxTxtPassSecond;
	 @FXML
	 private ComboBox<CStructSysUser> fxCbSelectSysUser;// Системный пользователь - выбор по мылу!!!
	 private ArrayList<CStructSysUser> m_alSysUser = null;
	 private ObservableList<CStructSysUser> m_ObservableList;
	 private String m_stTempIDSysOldUser;// Эти два значения нужны для того, если редактируемое судно имело
	 private String m_stTempEMailOldSysUser;// binding by email(id) для проверки: если больше не нужно, то тогда освобождаем этот email!!!
	 private CStructSysUser m_TempSP;
	 private CStructUser m_tempUser;
	 boolean m_bRetIsBindingSysUser = false;
	 
	 @FXML
	 private TextField fxTxtFreeNameFirst;
	 @FXML
	 private Label fxLbUniqueID;
	 @FXML
	 private TextField fxLbNameShip;
	 @FXML
	 private TextField fxLbDirectorShip;
	 @FXML
	 private TextArea fxTaShortDescriptionShip;
	 @FXML
	 private Label flLbInfoSaveErrors;
	 @FXML
	 private Label fxLbLatitudeText;
	 @FXML
	 private Label fxLbLongitudeText;
	 @FXML
	 private CMainController objectController;
	 @FXML
	 private Label fxLbSuccessPass;
	
	 private void CreateBinding(String stMyPhoneID_, String stSysUserID) throws FirebaseAuthException
	 {
		// Работаем с firebase - привязываем!!!
		/*FirebaseDatabase.getInstance().getReference()
				 .child(CMAINCONSTANTS.FB_my_sys_users_binding).child(stSysUserID)
				 .child("myPhoneBinding").setValueAsync(stMyPhoneID_);*/
	 }
	 @SuppressWarnings("unlikely-arg-type")
	@FXML
	 private void btnRefreshShip(ActionEvent event) 
	 {
		 try
		 {
			 System.out.println("btnRefreshShip(ActionEvent event)!!!");
			 
			 String stTempSpaces = fxTxtFreeNameFirst.getText().replace(" ", "");
				if(stTempSpaces.equals(""))
				{
					fxTxtFreeNameFirst.setText("EMPTY FREE NAME!!! - Не может быть пустым!!!");
					fxTxtFreeNameFirst.setStyle("-fx-text-inner-color: red;");
					return;
				}
			 
			 // Потом добавим проверку на пустоту полей!!!
			 if(fxLbNameShip.getText().replaceAll("\\s","").length() == 0 ||
			   fxLbDirectorShip.getText().replaceAll("\\s","").length() == 0 ||
			   fxTaShortDescriptionShip.getText().replaceAll("\\s","").length() == 0)
			 {
				 flLbInfoSaveErrors.setText("ЗАПОЛНИТЕ ВСЕ ПОЛЯ!");
				 return;
			 }
				
			 // Обновляем данные в структуре!!!
			 m_tempUser.setMyFreeNameFirst(fxTxtFreeNameFirst.getText());
			 m_tempUser.setMyNameShip(fxLbNameShip.getText());
			 m_tempUser.setMyDirectorShip(fxLbDirectorShip.getText());
			 m_tempUser.setMyShortDescriptionShip(fxTaShortDescriptionShip.getText());
			 m_tempUser.setMyLatitude(fxLbLatitudeText.getText());
			 m_tempUser.setMyLongitude(fxLbLongitudeText.getText());
			 
			 System.out.println("fxCbSelectSysUser.getValue() = " + fxCbSelectSysUser.getValue().toString());
			 System.out.println("CStrings.m_EMPTY_SEL_USER = " + CStrings.m_EMPTY_SEL_USER);
			 if(fxCbSelectSysUser.getValue().toString().equals(CStrings.m_EMPTY_SEL_USER))// Пользователь(пустой) не выбран!!!
			 {
				 System.out.println("Пользователь(пустой) не выбран!!!");
				 if(m_stTempEMailOldSysUser == null)// Значит привязанного пользователя не было, тогда привязываем(оставляем) пустого!!!
				 {
					 m_tempUser.setMyEmail("none");
					 m_tempUser.setMySysUserBinding("none");
				 }
				 else// Пользователь был!!!
				 {
					 // Обновим сист. пользователя!!!
					 FirebaseDatabase.getInstance().getReference()
					 .child(CMAINCONSTANTS.FB_my_sys_users_binding)
					 .child(m_tempUser.getMySysUserBinding())
					 .child("myPhoneBinding")
					 .setValueAsync("none");
					 
					 m_tempUser.setMyEmail("none");
					 m_tempUser.setMySysUserBinding("none");
				 }
				
			 }
			 else
			 {
				 System.out.println("SYS-Пользователь(не пустой) ВЫБРАН!!!");
				 // Простой случай - ничего не изменили!!!
				 if(fxCbSelectSysUser.getValue().toString().equals(m_stTempEMailOldSysUser))
				 {
					 //m_tempUser.setMyEmail(fxCbSelectSysUser.getValue().getMyEmail());
					 //m_tempUser.setMySysUserBinding(fxCbSelectSysUser.getValue().getMyIDSysUser());
				 }
				 else// Старого сист. польз. сделаем привязку "none"
				 {
					// Обновим старого сист. пользователя!!!
					 if(!m_tempUser.getMySysUserBinding().equals("none"))
					 {
						 FirebaseDatabase.getInstance().getReference()
						 .child(CMAINCONSTANTS.FB_my_sys_users_binding)
						 .child(m_tempUser.getMySysUserBinding())
						 .child("myPhoneBinding")
						 .setValueAsync("none");
					 }

					 
					 m_tempUser.setMyEmail(fxCbSelectSysUser.getValue().getMyEmail());
					 m_tempUser.setMySysUserBinding(fxCbSelectSysUser.getValue().getMyIDSysUser());
					 
					// Обновим нового сист. пользователя!!!
					 FirebaseDatabase.getInstance().getReference()
					 .child(CMAINCONSTANTS.FB_my_sys_users_binding)
					 .child(fxCbSelectSysUser.getValue().getMyIDSysUser().toString())
					 .child("myPhoneBinding")
					 .setValueAsync(fxLbUniqueID.getText());
				 }

			 }
			 
			// Работаем с firebase - update user!!!
			FirebaseDatabase.getInstance().getReference()
						 .child(CMAINCONSTANTS.FB_users).child(fxLbUniqueID.getText()).setValueAsync(m_tempUser);

			CLPSMain.m_stageEditShip.close();
			 CMyToast.makeText(CLPSMain.stage,
	    				"Типа обновили кораблик :=))",
						CMyToast.TOAST_SHORT, CMyToast.TOAST_SUCCESS);
		 } 
		 catch (Exception e) 
		 {
			 e.printStackTrace();
			 CMyToast.makeText(CLPSMain.stage,
	    				e.getMessage(),
						CMyToast.TOAST_SHORT, CMyToast.TOAST_ERROR);
		 }
	 }
	 
	 @FXML
	 private void btnInsertStartCoords(ActionEvent event) 
	 {
		 System.out.println("btnInsertStartCoords!!!");
	 }
	 
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		m_bRetIsBindingSysUser = false;
		// Инициализация:
		m_stTempIDSysOldUser = null;
		m_stTempEMailOldSysUser = null;
		
		fxLbUniqueID.setText(CCONSTANTS_EVENTS_JOB.EDITING_SHIP_ID);
		DatabaseReference mDBRefSysUser;
		mDBRefSysUser = FirebaseDatabase.getInstance().getReference()
				.child(CMAINCONSTANTS.FB_users).child(CCONSTANTS_EVENTS_JOB.EDITING_SHIP_ID);
		mDBRefSysUser.addListenerForSingleValueEvent(new ValueEventListener()
		{
			@Override
			public void onDataChange(DataSnapshot arg0) 
			{
				try 
				{
					Platform.runLater(
					() -> {
						m_tempUser = arg0.getValue(CStructUser.class);
						
						fxTxtFreeNameFirst.setText(m_tempUser.getMyFreeNameFirst());
						fxLbNameShip.setText(m_tempUser.getMyNameShip());
						fxLbDirectorShip.setText(m_tempUser.getMyDirectorShip());
						fxTaShortDescriptionShip.setText(m_tempUser.getMyShortDescriptionShip());
						fxLbLatitudeText.setText(m_tempUser.getMyLatitude());
						fxLbLongitudeText.setText(m_tempUser.getMyLongitude());
					});
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
			}
			@Override
			public void onCancelled(DatabaseError arg0) 
			{
				System.out.println(arg0.getMessage());
			}
		});
		// Здесь наполняем ComboBox системн. пользователями!!!
		m_TempSP = new CStructSysUser();
		m_TempSP.setMyEmail(CStrings.m_EMPTY_SEL_USER);

		mDBRefSysUser = FirebaseDatabase.getInstance().getReference()
				.child(CMAINCONSTANTS.FB_my_sys_users_binding);
		mDBRefSysUser.addListenerForSingleValueEvent(new ValueEventListener()
		{
			@Override
			public void onDataChange(DataSnapshot arg0)
			{
				
				Iterable<DataSnapshot> contactChildren = arg0.getChildren();
				 Platform.runLater(
				        	() -> {
				m_alSysUser = new ArrayList<CStructSysUser>();
				m_alSysUser.add(m_TempSP);
				
				if(!m_tempUser.getMySysUserBinding().equals("none"))// Проверим привязан ли к судну системный пользователь!!!
				{
					m_bRetIsBindingSysUser = true;
					m_TempSP = new CStructSysUser();
					m_TempSP.setMyEmail(m_tempUser.getMyEmail());
					m_TempSP.setMyPhoneBinding(m_tempUser.getMySysUserBinding());
					m_alSysUser.add(m_TempSP);
				}
				
				for (DataSnapshot structCStructSysUser : contactChildren)
                {
					m_TempSP = structCStructSysUser.getValue(CStructSysUser.class);
					// Проверим свободен ли для биндинга:
					if(m_TempSP.getMyPhoneBinding().equals("none"))
					{
	                 	System.out.println( "CStructSysUser = "  + m_TempSP.getMyEmail());
	                 	m_alSysUser.add(m_TempSP);// Заполнили массив!!!
					}
            	}
				
				m_ObservableList = FXCollections.observableArrayList (m_alSysUser);
	            
	           
	        		if(!m_bRetIsBindingSysUser)
	        		{
	        			fxCbSelectSysUser.setItems(m_ObservableList);
		        		fxCbSelectSysUser.setValue(m_alSysUser.get(0));
	        		}
	        		else
	        		{
	        			fxCbSelectSysUser.setItems(m_ObservableList);
		        		fxCbSelectSysUser.setValue(m_alSysUser.get(1));
		        		m_stTempIDSysOldUser = fxCbSelectSysUser.getValue().getMyIDSysUser();
		        		m_stTempEMailOldSysUser = fxCbSelectSysUser.getValue().getMyEmail();
	        		}
	        	});
				
			}
			@Override
			public void onCancelled(DatabaseError arg0) {
				arg0.toException();
			}
		});
	}
}
