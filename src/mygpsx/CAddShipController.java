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

public class CAddShipController implements Initializable{
	 
	DatabaseReference m_DatabaseRef;
	
	 @FXML
	 TextField fxTxtFreeNameFirst;
	 @FXML
	 TextField fxTxtEmail;
	 @FXML
	 PasswordField fxTxtPassFirst;
	 @FXML
	 PasswordField fxTxtPassSecond;
	 @FXML
	 private ComboBox<CStructSysUser> fxCbSelectSysUser;// Системный пользователь - выбор по мылу!!!
	 private ArrayList<CStructSysUser> m_alSysUser = null;
	 private ObservableList<CStructSysUser> m_ObservableList;
	// private String m_stTempIDSysUser;
	// private String m_stTempEMailSysUser;
	 CStructSysUser TempSP;
	 
	 @FXML
	 TextField fxLbNameShip;
	 @FXML
	 TextField fxLbDirectorShip;
	 @FXML
	 TextArea fxTaShortDescriptionShip;
	 @FXML
	 Label flLbInfoSaveErrors;
	 @FXML
	 Label fxLbLatitudeText;
	 @FXML
	 Label fxLbLongitudeText;
	 @FXML
	 private CMainController objectController;
	 @FXML
	 Label fxLbSuccessPass;
	
	 private void CreateBinding(String stMyPhoneID_, String stSysUserID) throws FirebaseAuthException
	 {
		// Работаем с firebase - привязываем!!!
		FirebaseDatabase.getInstance().getReference()
				 .child(CMAINCONSTANTS.FB_my_sys_users_binding).child(stSysUserID)
				 .child("myPhoneBinding").setValueAsync(stMyPhoneID_);
	 }
	 @FXML
	 private void btnAddShip(ActionEvent event) 
	 {
		 CStructUser tempUser = null;
		// Текущая дата для создания судна(объекта - пока не знаю для чего))))
		Date date = new Date();
		long lUnixTimeCreate = date.getTime();
		System.out.println("lUnixTimeCreate = " + Long.toString(lUnixTimeCreate));
		System.out.println("btnAddShip!!!");
		
		// Случайный уникальный ID устройства - к примуру!!!
		String symbols = "abcdefghijklmnopqrstuvwxyz";
		String randomIDPhoneTest= new Random().ints(11, 0, symbols.length())
		    .mapToObj(symbols::charAt)
		    .map(Object::toString)
		    .collect(Collectors.joining());
		System.out.println("String random = " + randomIDPhoneTest);
		try 
   	 	{
			// Работаем с firebase - записываем!!!
			m_DatabaseRef = FirebaseDatabase.getInstance().getReference().child(CMAINCONSTANTS.FB_users);
		

			// Потом добавим проверку на пустоту полей!!!
			if(fxLbNameShip.getText().replaceAll("\\s","").length() == 0 ||
			   fxLbDirectorShip.getText().replaceAll("\\s","").length() == 0 ||
			   fxTaShortDescriptionShip.getText().replaceAll("\\s","").length() == 0)
			{
				flLbInfoSaveErrors.setText("ЗАПОЛНИТЕ ВСЕ ПОЛЯ!");
				return;
			}
			
			String stTempSpaces = fxTxtFreeNameFirst.getText().replace(" ", "");
			if(stTempSpaces.equals(""))
			{
				fxTxtFreeNameFirst.setText("EMPTY FREE NAME!!! - Не может быть пустым!!!");
				fxTxtFreeNameFirst.setStyle("-fx-text-inner-color: red;");
				return;
			}
			
			// Проверка на координаты для создоваемого объекта!!!
			if(CMainController.m_LocationTempForCAddShipController == null)
			{
				flLbInfoSaveErrors.setText("ОТСУТСТВУЮТ КООРДИНАТЫ! ВЫБЕРЕТЕ НА КАРТЕ ТОЧКУ ПОЗИЦИИ.");
				return;
			}
			
			if(fxCbSelectSysUser.getValue().getMyIDSysUser() == null)
			{
				tempUser = new CStructUser(
						fxTxtFreeNameFirst.getText(),
						randomIDPhoneTest, 
						Double.toString(CMainController.m_LocationTempForCAddShipController.getLatitude()),
						Double.toString(CMainController.m_LocationTempForCAddShipController.getLongitude()),
						"none", 
						fxLbNameShip.getText(),
						fxLbDirectorShip.getText(), 
						fxTaShortDescriptionShip.getText(),
						"false",
						"MyPass",
						"none");
			}
			else
			{
				tempUser = new CStructUser(
						fxTxtFreeNameFirst.getText(),
						randomIDPhoneTest, 
						Double.toString(CMainController.m_LocationTempForCAddShipController.getLatitude()),
						Double.toString(CMainController.m_LocationTempForCAddShipController.getLongitude()),
						fxCbSelectSysUser.getValue().getMyEmail(), 
						fxLbNameShip.getText(),
						fxLbDirectorShip.getText(), 
						fxTaShortDescriptionShip.getText(),
						"false",
						"MyPass",
						fxCbSelectSysUser.getValue().getMyIDSysUser());
			}

			// Создаем корабль-телефон(MyPhoneID_*****************)		
			m_DatabaseRef.child(CMAINCONSTANTS.MyPhoneID_ + randomIDPhoneTest).setValueAsync(tempUser);
			////////////////// - Здесь привязывание авторизации в firebase - //////////////////////////////
			// Привязываем корабль-телефон к SysUser:
			if(fxCbSelectSysUser.getValue().getMyIDSysUser() != null)// Был ли тронут ComboBox с SysUsers вообще или даже не заходили в него)))
			{
				CreateBinding(CMAINCONSTANTS.MyPhoneID_ + randomIDPhoneTest, fxCbSelectSysUser.getValue().getMyIDSysUser());
			}
			//////////////////- Здесь привязывание авторизации в firebase ENDING !!!- //////////////////////////////
			
			
			flLbInfoSaveErrors.setText("");
			CMainController.m_LocationTempForCAddShipController = null;
			System.out.println("Типа создали кораблик!!!");
			
			CMyToast.makeText(CLPSMain.stage,
    				"Типа создали кораблик!!!",
					CMyToast.TOAST_SHORT, CMyToast.TOAST_SUCCESS);
			
            CLPSMain.m_stageAddShip.close();
        } 
   	 	catch (Exception e) 
   	 	{
            e.printStackTrace();
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
		//m_stTempIDSysUser = null;
		DatabaseReference mDBRefSysUser;
		//CStructSysUser tempSysUser = null;
		m_alSysUser = new ArrayList<CStructSysUser>();

		TempSP = new CStructSysUser();
		TempSP.setMyEmail(CStrings.m_EMPTY_SEL_USER);
		
		mDBRefSysUser = FirebaseDatabase.getInstance().getReference()
				.child(CMAINCONSTANTS.FB_my_sys_users_binding);
		mDBRefSysUser.addValueEventListener(new ValueEventListener()
		 {
			@Override
			public void onDataChange(DataSnapshot arg0)
			{
			//	Platform.runLater(
			//  			  () -> {
				try 
				{
					Iterable<DataSnapshot> contactChildren = arg0.getChildren();
					
					m_alSysUser = new ArrayList<CStructSysUser>();
					m_alSysUser.add(TempSP);
					for (DataSnapshot structCStructSysUser : contactChildren)
	                {
						TempSP = structCStructSysUser.getValue(CStructSysUser.class);
						// Проверим свободен ли для биндинга:
						if(TempSP.getMyPhoneBinding().equals("none"))
						{
		                 	System.out.println( "CStructSysUser = "  + TempSP.getMyEmail());
		                 	m_alSysUser.add(TempSP);// Заполнили массив!!!
						}
                	}
		            m_ObservableList = FXCollections.observableArrayList (m_alSysUser);
					            
					            Platform.runLater(
					        	() -> {
					        		fxCbSelectSysUser.setItems(m_ObservableList);
					        		fxCbSelectSysUser.setValue(m_alSysUser.get(0));
					        	});
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}

				fxCbSelectSysUser.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CStructSysUser>() 
			    {
					@Override
					public void changed(ObservableValue<? extends CStructSysUser> observable, CStructSysUser oldValue,
							CStructSysUser newValue)
					{
						if(newValue != oldValue)
						{
							//Platform.setImplicitExit(false);
							/*Platform.runLater(
						        	() -> {
						        		try 
						        		{
						        			m_stTempIDSysUser = newValue.getMyIDSysUser();
							        		m_stTempEMailSysUser = newValue.getMyEmail();
										} catch (Exception e) 
						        		{
											// TODO: handle exception
										}
						        		
						        	});
							
							System.out.println("fxCbSelectSysUser.getSelectionModel() newValue = " + m_stTempIDSysUser);*/
						}
					}
				});
			//});
			}

			@Override
			public void onCancelled(DatabaseError arg0) {
			}
		 });
	}
	

}
