package mygpsx;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.FirebaseDatabase;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

@SuppressWarnings("unused")
public class CListCellSysUserController implements Initializable{

	//private String stUniqueIDStatus = null;
	@FXML
	Label fxLbUniqueID;
	@FXML
	private TextField fxTxtEmail;
	@FXML
    private void BtnDeleteSysUser(ActionEvent event) 
    {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("�������� ������������!");
		alert.setHeaderText("�� ������������� ������ ������� ������������?");
		alert.setContentText(CStrings.m_APP_NAME_CHOOSE);
		ButtonType buttonTypeYes = new ButtonType(CStrings.m_APP_NAME_CHOOSE_YES);
		ButtonType buttonTypeNo = new ButtonType(CStrings.m_APP_NAME_CHOOSE_NO);
		
		alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
		
		java.util.Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeYes)
		{
    		try 
    		{
    			System.out.println("BtnDeleteSysUser(fxLbUniqueID) = " + fxLbUniqueID.getText());
        		System.out.println("BtnDeleteSysUser(fxTxtEmail) = " + fxTxtEmail.getText());
				FirebaseAuth.getInstance().deleteUser(fxLbUniqueID.getText());
				System.out.println("Successfully deleted user.");
				CMyToast.makeText(CLPSMain.stage,
    					"Successfully deleted user!",
    					CMyToast.TOAST_SHORT, CMyToast.TOAST_SUCCESS);
			} 
    		catch (FirebaseAuthException e)
    		{
    			CMyToast.makeText(CLPSMain.stage,
    					"������ ��������!!!",
    					CMyToast.TOAST_SHORT, CMyToast.TOAST_ERROR);
				e.printStackTrace();
			}

			/*CLPSMain.mDatabase = FirebaseDatabase.getInstance()
					.getReference()
					.child(CMAINCONSTANTS.FB_my_owner_settings)
					.child(CMAINCONSTANTS.FB_my_templates);
			CLPSMain.mDatabase.child(CMAINCONSTANTS.m_UniqueTempIDTempate).removeValueAsync();
			//CCONSTANTS_EVENTS_JOB.TEMPLATE_FILLING_OR_EDIT = 1;// ����� �� �������� � ��������������
			// �������, ������ ����� == 1.
			CCONSTANTS_EVENTS_JOB.TEMP_COUNT_ADDING_CONTROLS_IN_TMPL = 0; // �������� ���-�� ���������.
*/			//break;
		} 
		else
		{

		}
		
		
		
		
	
		
/*    	try 
    	{
    		
    		System.out.println("BtnDeleteStatus");
    		CLPSMain.mDatabase = FirebaseDatabase.getInstance()
    				.getReference()
    				.child(CMAINCONSTANTS.FB_my_owner_settings)
    				.child(CMAINCONSTANTS.FB_my_status);
    		
    		Button btn = (Button)event.getSource();// ����� �������� ������!!!
    		System.out.println("btn = " + btn.toString());
    		AnchorPane ap = (AnchorPane)btn.getParent();// ����� �������� ������������ ������!!!
    		System.out.println("ap = " + ap.toString());
    		
    		ObservableList<Node> listNode = ap.getChildren();// ����� �������� ������ ���� �������� �������� ��������!!! 
    		Label nodeOne = (Label)listNode.get(5);// �������� �� ID(ID - ��� �� 0 � �.�. ����!) ������(��������)
    		stUniqueIDStatus = nodeOne.getText();// ������� ����� ���������� � Scene Biulder
    		System.out.println("stUniqueIDStatus = " + stUniqueIDStatus);
    		CLPSMain.mDatabase = FirebaseDatabase.getInstance()
    				.getReference()
    				.child(CMAINCONSTANTS.FB_my_owner_settings)
    				.child(CMAINCONSTANTS.FB_my_status)
    				.child(stUniqueIDStatus);
    		CLPSMain.mDatabase.setValueAsync(null);// ������� ��������(������) �� ����!!!
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }*/
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
/*		fxTxtNameStatus.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{

			@Override
			public void handle(KeyEvent event) 
			{
				System.out.println("OnKeyInterPressed");
				if (event.getCode().equals(KeyCode.ENTER))
	            {
					AnchorPane ap = (AnchorPane)fxTxtNameStatus.getParent();// ����� �������� ������������ ������!!!
					ObservableList<Node> listNode = ap.getChildren();// ����� �������� ������ ���� �������� �������� ��������!!! 
		    		Label nodeOne = (Label)listNode.get(5);
		    		stUniqueIDStatus = nodeOne.getText();
		    		
		    		CLPSMain.mDatabase = FirebaseDatabase.getInstance()
		    				.getReference()
		    				.child(CMAINCONSTANTS.FB_my_owner_settings)
		    				.child(CMAINCONSTANTS.FB_my_status)
		    				.child(stUniqueIDStatus);
		    		CLPSMain.mDatabase.child("MyNameStatus").setValueAsync(fxTxtNameStatus.getText());
	            }
			}
		});*/
		
	}

}
