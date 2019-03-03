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
	////////////  ВСЕ ПО НОВОЙ!!!   ////////////////////////////////////////
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
    private void BtnDeleteAttrjob(ActionEvent event)// Открываем окно для добавления атрибутов!!!
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
				.child(fxLbUniqueID.getText()).setValueAsync(null);
			}
			if(CCONSTANTS_EVENTS_JOB.SAMPLE_ANY_OR_ANY.equals("EDIT"))// Delete attr!
			{
				FirebaseDatabase.getInstance()
				.getReference()
				.child(CMAINCONSTANTS.FB_my_owner_settings)
				.child(CMAINCONSTANTS.FB_my_templates)
				.child(CMAINCONSTANTS.m_UniqueTempEditIDTempate)
				.child(CMAINCONSTANTS.FB_my_adding_attr)
				.child(fxLbUniqueID.getText()).setValueAsync(null);
				// Попали в список атрибутов
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
    			mDatabaseCurrentTmpl.child("myAttrHeight").setValueAsync(fxTxtHeight.getText());
    			mDatabaseCurrentTmpl.child("myAttrName").setValueAsync(fxTxtAPaneField.getText());
    			mDatabaseCurrentTmpl.child("myAttrWidth").setValueAsync(fxTxtWidth.getText());
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

    			mDatabaseCurrentTmpl.child("myAttrWidth").setValueAsync(fxTxtWidth.getText());
				mDatabaseCurrentTmpl.child("myAttrHeight").setValueAsync(fxTxtHeight.getText());
				
				if(fxLbTypeControl.getText().equals("Label"))
				{
					System.out.println("fxLbTypeControl = " + fxLbTypeControl.getText());
					mDatabaseCurrentTmpl.child("myAttrName").setValueAsync(fxLbAPaneLabel.getText());
				}
				if(fxLbTypeControl.getText().equals("TextField"))
				{
					System.out.println("fxLbTypeControl = " + fxLbTypeControl.getText());
					mDatabaseCurrentTmpl.child("myAttrName").setValueAsync(fxTxtAPaneField.getText());
				}
				if(fxLbTypeControl.getText().equals("TextArea"))
				{
					System.out.println("fxLbTypeControl = " + fxLbTypeControl.getText());
					mDatabaseCurrentTmpl.child("myAttrName").setValueAsync(fxTxtAPaneArea.getText());
				}
				
				// Вот оно то самое, когда я создаю левый контрол и потом мне его надо найти
				// по уникальному(придуманному мной) ID!!!!
				// Вот здесь и будем проверять, что за контрол у нас и есть ли у него текстовое поле,
				// точнее можно ли получить от него текст???!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				/*ObservableList<Node> tempNodes = fxCellPane.getChildren(); 
				for(int i = 0; i < tempNodes.size(); i++)
				{
					String My_ID_IntoControl_Value = tempNodes.get(i).getId();
					if(My_ID_IntoControl_Value == CUserCellIntoTmpl.m_stMyIDIntoControlValue)
					{
						// Получить пока можно из TextField и AreaField(Label - не в счет!!! он не меняется!!!)
						System.out.println(My_ID_IntoControl_Value);
						mDatabaseCurrentTmpl.child("MyAttrName").setValueAsync(((TextField)tempNodes.get(i)).getText());
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
