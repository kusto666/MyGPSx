package mygpsx;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CIntoTmplCtrl implements Initializable
{
	DatabaseReference mDatabaseCurrentTmpl;
	//DatabaseReference mDatabaseCurrentTmpl;
	////////////  ��� �� �����!!!   ////////////////////////////////////////
	@FXML
	Label fxLb1;
	@FXML
	Label fxLbTypeControl;
	@FXML
	Label fxLbUniqueID;

	@FXML
	AnchorPane fxAPaneLabel;
	@FXML
	Label fxLbAPaneLabel;
	@FXML
	AnchorPane fxAPaneTextField;
	@FXML
	TextField fxTxtAPaneField;
	@FXML
	AnchorPane fxAPaneTextArea;
	@FXML
	TextArea fxTxtAPaneArea;
	@FXML
	AnchorPane fxAPaneControls;
	
	@FXML
	private TextField fxTxtHeight;
	@FXML
	private TextField fxTxtWidth;
	@FXML
	Button fxBtnDeleteAttrjob;
	@FXML
	Button fxBtnRefreshAttrjob;
	@FXML
	Button fxBtnMoveUp;
	@FXML
	Button fxBtnMoveDown;


	double dAnchorTop = 0.0;
	double dAnchorLeft = 0.0;
	double dAnchorButtom = 0.0;
	////////////////////////////////////////////////////////////////////////
	private AnchorPane fxCellPane;
	CStructAttrTmpl m_TempAttrTmpl;

	@FXML
    private void BtnDeleteAttrjob(ActionEvent event)// ��������� ���� ��� ���������� ���������!!!
    {
/*//		System.out.println("btnOpenFrameWithAttributes!!!");
//		System.out.println("fxLbUniqueID.getText() = " + fxLbUniqueID.getText());
//    	CCONSTANTS_EVENTS_JOB.SAMPLE_JOBING = "ADD_SHIP";
		try 
		{
			if(CCONSTANTS_EVENTS_JOB.SAMPLE_ANY_OR_ANY.equals("ADD"))// Delete attr!
			{
	    		FirebaseDatabase.getInstance()
				.getReference()
				.child(CMAINCONSTANTS.FB_my_owner_settings)
				.child(CMAINCONSTANTS.FB_my_templates)
				.child(CMAINCONSTANTS.m_UniqueTempIDTempate)
				.child(CMAINCONSTANTS.FB_my_adding_attr)
				.child(fxLbUniqueID.getText()).setValue(null);
			}
			if(CCONSTANTS_EVENTS_JOB.SAMPLE_ANY_OR_ANY.equals("EDIT"))// Delete attr!
			{
				FirebaseDatabase.getInstance()
				.getReference()
				.child(CMAINCONSTANTS.FB_my_owner_settings)
				.child(CMAINCONSTANTS.FB_my_templates)
				.child(CMAINCONSTANTS.m_UniqueTempEditIDTempate)
				.child(CMAINCONSTANTS.FB_my_adding_attr)
				.child(fxLbUniqueID.getText()).setValue(null);
				// ������ � ������ ���������
				mDatabaseCurrentTmpl = FirebaseDatabase.getInstance().getReference().child(CMAINCONSTANTS.FB_my_owner_settings)
				.child(CMAINCONSTANTS.FB_my_templates)
				.child(CMAINCONSTANTS.m_UniqueTempEditIDTempate)
				.child(CMAINCONSTANTS.FB_my_adding_attr);
				mDatabaseCurrentTmpl.addValueEventListener(new ValueEventListener() {
					
					@Override
					public void onDataChange(DataSnapshot arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onCancelled(DatabaseError arg0) {
						// TODO Auto-generated method stub
						
					}
				});
				
				
				
			}

    		//System.out.println("Click - BtnDeleteAttrjob!!!");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
    	*/
    }

	@FXML
	private void BtnRefreshAttrjob(ActionEvent event) 
    {
    	try 
    	{
    		if(CCONSTANTS_EVENTS_JOB.SAMPLE_ANY_OR_ANY.equals("ADD"))
			{
    			mDatabaseCurrentTmpl = FirebaseDatabase.getInstance()
				.getReference()
				.child(CMAINCONSTANTS.FB_my_owner_settings)
				.child(CMAINCONSTANTS.FB_my_templates)
				.child(CMAINCONSTANTS.m_UniqueTempIDTempate)
				.child(CMAINCONSTANTS.FB_my_adding_attr)
				.child(fxLbUniqueID.getText());
    			mDatabaseCurrentTmpl.child("MyAttrHeight").setValue(fxTxtHeight.getText());
    			mDatabaseCurrentTmpl.child("MyAttrName").setValue(fxTxtAPaneField.getText());
    			mDatabaseCurrentTmpl.child("MyAttrWidth").setValue(fxTxtWidth.getText());
			}
			if(CCONSTANTS_EVENTS_JOB.SAMPLE_ANY_OR_ANY.equals("EDIT"))
			{
				mDatabaseCurrentTmpl = FirebaseDatabase.getInstance()
				.getReference()
				.child(CMAINCONSTANTS.FB_my_owner_settings)
				.child(CMAINCONSTANTS.FB_my_templates)
				.child(CMAINCONSTANTS.m_UniqueTempEditIDTempate)
				.child(CMAINCONSTANTS.FB_my_adding_attr)
				.child(fxLbUniqueID.getText());

    			mDatabaseCurrentTmpl.child("MyAttrWidth").setValue(fxTxtWidth.getText());
				mDatabaseCurrentTmpl.child("MyAttrHeight").setValue(fxTxtHeight.getText());
				
				if(fxLbTypeControl.getText().equals("Label"))
				{
					System.out.println("fxLbTypeControl = " + fxLbTypeControl.getText());
					mDatabaseCurrentTmpl.child("MyAttrName").setValue(fxLbAPaneLabel.getText());
				}
				if(fxLbTypeControl.getText().equals("TextField"))
				{
					System.out.println("fxLbTypeControl = " + fxLbTypeControl.getText());
					mDatabaseCurrentTmpl.child("MyAttrName").setValue(fxTxtAPaneField.getText());
				}
				if(fxLbTypeControl.getText().equals("TextArea"))
				{
					System.out.println("fxLbTypeControl = " + fxLbTypeControl.getText());
					mDatabaseCurrentTmpl.child("MyAttrName").setValue(fxTxtAPaneArea.getText());
				}
				
				// ��� ��� �� �����, ����� � ������ ����� ������� � ����� ��� ��� ���� �����
				// �� �����������(������������ ����) ID!!!!
				// ��� ����� � ����� ���������, ��� �� ������� � ��� � ���� �� � ���� ��������� ����,
				// ������ ����� �� �������� �� ���� �����???!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				/*ObservableList<Node> tempNodes = fxCellPane.getChildren(); 
				for(int i = 0; i < tempNodes.size(); i++)
				{
					String My_ID_IntoControl_Value = tempNodes.get(i).getId();
					if(My_ID_IntoControl_Value == CUserCellIntoTmpl.m_stMyIDIntoControlValue)
					{
						// �������� ���� ����� �� TextField � AreaField(Label - �� � ����!!! �� �� ��������!!!)
						System.out.println(My_ID_IntoControl_Value);
						mDatabaseCurrentTmpl.child("MyAttrName").setValue(((TextField)tempNodes.get(i)).getText());
					}

				}*/
				System.out.println("BtnRefreshAttrjob!!!");
				System.out.println("fxLbTypeControl = " + fxLbTypeControl.getText());
			}

    		 
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		System.out.println("initialize - CIntoTmplCtrl.");
		
	}
	

}
