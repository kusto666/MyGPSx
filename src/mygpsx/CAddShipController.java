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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
/*import com.google.firebase.tasks.OnCompleteListener;
import com.google.firebase.tasks.Task;*/
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.Marker;

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
	 
	DatabaseReference mDatabaseRef;
	
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
	
	 private void CreateAccount(String email, String password) throws FirebaseAuthException {
		
		/* CreateRequest request = new CreateRequest()
				    .setEmail("user@example.com")
				    .setEmailVerified(false)
				    .setPassword("secretPassword")
				    .setPhoneNumber("+11234567890")
				    .setDisplayName("John Doe")
				    .setPhotoUrl("http://www.example.com/12345678/photo.png")
				    .setDisabled(false);
		 
		 UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
		 System.out.println("Successfully created new user: " + userRecord.getUid());*/
		}
	 @FXML
	 private void btnAddShip(ActionEvent event) 
	 {
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
			mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users");
		

			// Потом добавим проверку на пустоту полей!!!
			if(fxLbNameShip.getText().replaceAll("\\s","").length() == 0 ||
			   fxLbDirectorShip.getText().replaceAll("\\s","").length() == 0 ||
			   fxTaShortDescriptionShip.getText().replaceAll("\\s","").length() == 0)
			{
				flLbInfoSaveErrors.setText("ЗАПОЛНИТЕ ВСЕ ПОЛЯ!");
				return;
			}
		
			
			// Проверка на координаты для создоваемого объекта!!!
			if(CMainController.m_LocationTempForCAddShipController == null)
			{
				flLbInfoSaveErrors.setText("ОТСУТСТВУЮТ КООРДИНАТЫ! ВЫБЕРЕТЕ НА КАРТЕ ТОЧКУ ПОЗИЦИИ.");
				return;
			}
			mDatabaseRef.child(CMAINCONSTANTS.MyPhoneID_ + randomIDPhoneTest).child("MyPhoneID").
			setValueAsync(randomIDPhoneTest);
			mDatabaseRef.child(CMAINCONSTANTS.MyPhoneID_ + randomIDPhoneTest).child("MyLatitude").
			setValueAsync(Double.toString(CMainController.m_LocationTempForCAddShipController.getLatitude()));
			mDatabaseRef.child(CMAINCONSTANTS.MyPhoneID_ + randomIDPhoneTest).child("MyLongitude").
			setValueAsync(Double.toString(CMainController.m_LocationTempForCAddShipController.getLongitude()));
			mDatabaseRef.child(CMAINCONSTANTS.MyPhoneID_ + randomIDPhoneTest).child("MyNameShip").
			setValueAsync(fxLbNameShip.getText());
			mDatabaseRef.child(CMAINCONSTANTS.MyPhoneID_ + randomIDPhoneTest).child("MyDirectorShip").
			setValueAsync(fxLbDirectorShip.getText());
			mDatabaseRef.child(CMAINCONSTANTS.MyPhoneID_ + randomIDPhoneTest).child("MyShortDescriptionShip").
			setValueAsync(fxTaShortDescriptionShip.getText());
			mDatabaseRef.child(CMAINCONSTANTS.MyPhoneID_ + randomIDPhoneTest).child("MyIsUserSelected").
			setValueAsync("false");
			////////////////// - Здесь создание авторизации в firebase - //////////////////////////////
			CreateAccount(fxTxtEmail.getText(), fxTxtPassFirst.getText());
			//////////////////- Здесь создание авторизации в firebase ENDING !!!- //////////////////////////////
			flLbInfoSaveErrors.setText("");
			CMainController.m_LocationTempForCAddShipController = null;
			System.out.println("Типа создали кораблик!!!");
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
		// Start listing users from the beginning, 1000 at a time.
				CStructSysUser SysUser = null;
				m_alSysUser = new ArrayList<CStructSysUser>();
				
				ListUsersPage page = null;
				try 
				{
					page = FirebaseAuth.getInstance().listUsers(null);
				} 
				catch (FirebaseAuthException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while (page != null)
				{
				  for (ExportedUserRecord user : page.getValues())
				  {
					  SysUser = new CStructSysUser(user.getUid(), user.getEmail(), user.getPasswordHash());
					  m_alSysUser.add(SysUser);
					  System.out.println("SysUser: " + user.getUid());
					  System.out.println("user.getEmail(): " + user.getEmail());
					  System.out.println("user.getPasswordHash(): " + user.getPasswordHash());
				  }
				  page = page.getNextPage();
				}
				m_ObservableList = FXCollections.observableArrayList (m_alSysUser);
				fxCbSelectSysUser.setItems(m_ObservableList);
/*				fxCbSelectSysUser.valueProperty().addListener(new ChangeListener<CStructSysUser>() {

					@Override
					public void changed(ObservableValue<? extends CStructSysUser> observable, CStructSysUser oldValue,
							CStructSysUser newValue) 
					{
						
						
					}
				});
				fxCbSelectSysUser.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CStructSysUser>() 
			    {
					@Override
					public void changed(ObservableValue<? extends CStructSysUser> observable, CStructSysUser oldValue,
							CStructSysUser newValue)
					{
						if(newValue != oldValue)
						{

						}
((CStructSysUser)newValue).getMyEmail();

					}
				});*/
		//UserRecord
		/*mAFirebaseAuth.createUserWithEmailAndPassword("", "")
        .addOnCompleteListener(this, new OnCompleteListener() {

			@Override
			public void onComplete(Task arg0) {
				// TODO Auto-generated method stub
				
			}
		});*/
	}
	

}
