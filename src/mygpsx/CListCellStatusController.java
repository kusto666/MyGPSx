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
public class CListCellStatusController implements Initializable{

	private String stUniqueIDStatus = null;
	@FXML
	private TextField fxTxtNameStatus;
	@FXML
    private void BtnDeleteStatus(ActionEvent event) 
    {
    	try 
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
        }
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		fxTxtNameStatus.setOnKeyPressed(new EventHandler<KeyEvent>() 
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
		});
		
	}

}
