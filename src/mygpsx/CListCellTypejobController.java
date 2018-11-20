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
public class CListCellTypejobController implements Initializable{

	private String stUniqueIDTypejob = null;
	@FXML
	private TextField fxTxtNameTypejob;
	@FXML
    private void BtnDeleteTypejob(ActionEvent event) 
    {
    	try 
    	{
    		
    		System.out.println("BtnDeleteTypejob");
    		CLPSMain.mDatabase = FirebaseDatabase.getInstance()
    				.getReference()
    				.child(CMAINCONSTANTS.FB_my_owner_settings)
    				.child(CMAINCONSTANTS.FB_my_typejob);
    		
    		Button btn = (Button)event.getSource();// ����� �������� ������!!!
    		System.out.println("btn = " + btn.toString());
    		AnchorPane ap = (AnchorPane)btn.getParent();// ����� �������� ������������ ������!!!
    		System.out.println("ap = " + ap.toString());
    		
    		ObservableList<Node> listNode = ap.getChildren();// ����� �������� ������ ���� �������� �������� ��������!!! 
    		Label nodeOne = (Label)listNode.get(5);// �������� �� ID(ID - ��� �� 0 � �.�. ����!) ������(��������)
    		stUniqueIDTypejob = nodeOne.getText();// ������� ����� ���������� � Scene Biulder
    		System.out.println("stUniqueIDStatus = " + stUniqueIDTypejob);
    		CLPSMain.mDatabase = FirebaseDatabase.getInstance()
    				.getReference()
    				.child(CMAINCONSTANTS.FB_my_owner_settings)
    				.child(CMAINCONSTANTS.FB_my_typejob)
    				.child(stUniqueIDTypejob);
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
		fxTxtNameTypejob.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{
			@Override
			public void handle(KeyEvent event) 
			{
				System.out.println("OnKeyInterPressed");
				if (event.getCode().equals(KeyCode.ENTER))
	            {
					AnchorPane ap = (AnchorPane)fxTxtNameTypejob.getParent();// ����� �������� ������������ ������!!!
					ObservableList<Node> listNode = ap.getChildren();// ����� �������� ������ ���� �������� �������� ��������!!! 
		    		Label nodeOne = (Label)listNode.get(5);
		    		stUniqueIDTypejob = nodeOne.getText();
		    		
		    		CLPSMain.mDatabase = FirebaseDatabase.getInstance()
		    				.getReference()
		    				.child(CMAINCONSTANTS.FB_my_owner_settings)
		    				.child(CMAINCONSTANTS.FB_my_typejob)
		    				.child(stUniqueIDTypejob);
		    		CLPSMain.mDatabase.child("MyNameTypejob").setValueAsync(fxTxtNameTypejob.getText());
	            }
			}
		});
		
	}

}
