package mygpsx;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CSysUserAddController implements Initializable{

	@FXML 
	private AnchorPane fxApaneMain;
	// [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
	@FXML
	private TextField fxTxtEmail;
	@FXML
	private PasswordField fxTxtPass;
	@FXML
	private PasswordField fxTxtPassToo;
	@FXML
	private Button fxBtnAddSysUser;
	 // Добавляем приоритет!!!
    @FXML
    private void AddSysUser(ActionEvent event) 
    {
    	try 
    	{
    		if(!fxTxtPass.getText().equals(fxTxtPassToo.getText()))
    		{
    			System.out.println("Пароли не совпадают!");
    			CMyToast.makeText(CLPSMain.stage,
    					"Пароли не совпадают!",
    					CMyToast.TOAST_SHORT, CMyToast.TOAST_ERROR);
    			return;
    		}
    		// [START create_user_with_email]
    		CreateRequest request = new CreateRequest()
    			    .setEmail(fxTxtEmail.getText())
    			    .setPassword(fxTxtPass.getText());
    		UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
    		System.out.println("Successfully created new user: " + userRecord.getUid());
    		
    		// Здесь добавим запись в my_sys_users_binding
    		CStructSysUser tempSysUser = new CStructSysUser(userRecord.getUid(),
    														userRecord.getEmail(), 
    														fxTxtPass.getText(),
    														CCONSTANTS_EVENTS_JOB.MYNONE,
    														CCONSTANTS_EVENTS_JOB.MYNONE);
    		
    		FirebaseDatabase.getInstance()
			.getReference()
			.child(CMAINCONSTANTS.FB_my_sys_users_binding).child(userRecord.getUid()).setValueAsync(tempSysUser);
    		///////////////////////////////////////////////////////////
    		//Parent root = FXMLLoader.load(getClass().getResource("FXSysUserEdit.fxml"));
    		CMyToast.makeText(CLPSMain.m_stageFXSysUserEdit,
    				"Успешное создание sysuser: " + userRecord.getUid(),
					CMyToast.TOAST_SHORT, CMyToast.TOAST_SUCCESS);
            // [END create_user_with_email]
    		((Stage)fxBtnAddSysUser.getScene().getWindow()).close();// И успешно закрываем окно!!!
        }
		catch(Exception e) 
		{
			CMyToast.makeText(CLPSMain.stage,
					"Ошибка создания пользователя: " + e.getMessage(),
					CMyToast.TOAST_SHORT, CMyToast.TOAST_ERROR);
           e.printStackTrace();
        }
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		fxTxtPass.setText("111111");
		fxTxtPassToo.setText("111111");
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
		/*fxTxtNumberStatus.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{

			@Override
			public void handle(KeyEvent event) 
			{
				//System.out.println("OnKeyInterPressed");
				if (event.getCode().equals(KeyCode.ENTER))
	            {
					//AddStatusInBase();
	            }
			}
		});*/
		
		/*fxTxtNameStatus.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{

			@Override
			public void handle(KeyEvent event) 
			{
				//System.out.println("OnKeyInterPressed");
				if (event.getCode().equals(KeyCode.ENTER))
	            {
					//AddStatusInBase();
	            }
			}
		});*/
		
	}
	/*void AddStatusInBase()
	{
		CLPSMain.mDatabase = FirebaseDatabase.getInstance()
				.getReference()
				.child(CMAINCONSTANTS.FB_my_owner_settings)
				.child(CMAINCONSTANTS.FB_my_status);
		
		String uploadId = CLPSMain.mDatabase.push().getKey();
		CLPSMain.mDatabase.child(uploadId).child("MyIDUnique").
		setValueAsync(uploadId);
		CLPSMain.mDatabase.child(uploadId).child("MyCLassStatus").
		setValueAsync(fxTxtNumberStatus.getText());
		CLPSMain.mDatabase.child(uploadId).child("MyNameStatus").
		setValueAsync(fxTxtNameStatus.getText());
		
		CStatusEditController.m_stageStatusAdd.close();
	}*/
}
