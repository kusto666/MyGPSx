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
public class CListCellTypedescjobController implements Initializable{

	private String stUniqueIDTypedescjob = null;
	@FXML
	private TextField fxTxtNameTypedescjob;
	@FXML
    private void BtnDeleteTypedescjob(ActionEvent event) 
    {
    	try 
    	{
    		
    		System.out.println("BtnDeleteTypedescjob");
    		CLPSMain.mDatabase = FirebaseDatabase.getInstance()
    				.getReference()
    				.child(CMAINCONSTANTS.FB_my_owner_settings)
    				.child(CMAINCONSTANTS.FB_my_typedescjob);
    		
    		Button btn = (Button)event.getSource();// ����� �������� ������!!!
    		System.out.println("btn = " + btn.toString());
    		AnchorPane ap = (AnchorPane)btn.getParent();// ����� �������� ������������ ������!!!
    		System.out.println("ap = " + ap.toString());
    		
    		ObservableList<Node> listNode = ap.getChildren();// ����� �������� ������ ���� �������� �������� ��������!!! 
    		Label nodeOne = (Label)listNode.get(5);// �������� �� ID(ID - ��� �� 0 � �.�. ����!) ������(��������)
    		stUniqueIDTypedescjob = nodeOne.getText();// ������� ����� ���������� � Scene Biulder
    		System.out.println("stUniqueIDTypedescjob = " + stUniqueIDTypedescjob);
    		CLPSMain.mDatabase = FirebaseDatabase.getInstance()
    				.getReference()
    				.child(CMAINCONSTANTS.FB_my_owner_settings)
    				.child(CMAINCONSTANTS.FB_my_typedescjob)
    				.child(stUniqueIDTypedescjob);
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
		fxTxtNameTypedescjob.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{
			@Override
			public void handle(KeyEvent event) 
			{
				System.out.println("OnKeyInterPressed");
				if (event.getCode().equals(KeyCode.ENTER))
	            {
					AnchorPane ap = (AnchorPane)fxTxtNameTypedescjob.getParent();// ����� �������� ������������ ������!!!
					ObservableList<Node> listNode = ap.getChildren();// ����� �������� ������ ���� �������� �������� ��������!!! 
		    		Label nodeOne = (Label)listNode.get(5);
		    		stUniqueIDTypedescjob = nodeOne.getText();
		    		
		    		CLPSMain.mDatabase = FirebaseDatabase.getInstance()
		    				.getReference()
		    				.child(CMAINCONSTANTS.FB_my_owner_settings)
		    				.child(CMAINCONSTANTS.FB_my_typedescjob)
		    				.child(stUniqueIDTypedescjob);
		    		CLPSMain.mDatabase.child("MyNameTypedescjob").setValueAsync(fxTxtNameTypedescjob.getText());
	            }
			}
		});
		
	}

}
