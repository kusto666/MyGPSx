package mygpsx;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.grpc.netty.shaded.io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.ExitCondition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CAttrjobAddController implements Initializable{

	long m_lChildrenCount = 0;
	@FXML
	private AnchorPane fxAPaneMain;
	@FXML
	private TextField fxTxtNumberAttrjob;
	@FXML
	private TextField fxTxtNameAttrjob;
	@FXML
	private ComboBox<CAttribute> fxCboxTypeAttrs;
	
	
	private String m_stAttributeClassName = null; // ��� �������� �������� � ���������� ��� �����!!!
	private String stAttributeCode = null;// ��� �������� �������� � ����!!!
	
	 // ��������� ���������!!!
    @FXML
    private void AddAttrjob(ActionEvent event) 
    {
    	try 
    	{
    		AddAttrjobInBase();
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		CLPSMain.mDatabase = FirebaseDatabase.getInstance()
				.getReference()
				.child(CMAINCONSTANTS.FB_my_owner_settings)
				.child(CMAINCONSTANTS.FB_my_attrjob);
		// ��������� ������ ������ ����������!!!
		ObservableList<CAttribute> list = CAttributeTypeDAO.getPlanetList();
		fxCboxTypeAttrs.setItems(list);
		fxCboxTypeAttrs.getSelectionModel().select(0);
		
		// ������ �� ��������� �� combobox ������ �������� ���� ��������!!!
		m_stAttributeClassName = ((CAttribute)fxCboxTypeAttrs.getSelectionModel().getSelectedItem()).getName();
		fxTxtNumberAttrjob.setText(m_stAttributeClassName);
		
		fxCboxTypeAttrs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CAttribute>() 
	    {
			@Override
			public void changed(ObservableValue<? extends CAttribute> observable, CAttribute oldValue,
					CAttribute newValue)
			{
				m_stAttributeClassName = ((CAttribute)newValue).getName();
				fxTxtNumberAttrjob.setText(m_stAttributeClassName);
				stAttributeCode = ((CAttribute)newValue).getCode();
				System.out.println("stAttributeCode = " + stAttributeCode);
				
			}
		});
		stAttributeCode = ((CAttribute)fxCboxTypeAttrs.getSelectionModel().getSelectedItem()).getCode();
		System.out.println("stAttributeCode for initialize = " + stAttributeCode);
		
		fxAPaneMain.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{

			@Override
			public void handle(KeyEvent event) 
			{
				//System.out.println("OnKeyInterPressed");
				if (event.getCode().equals(KeyCode.ESCAPE))
	            {
					CAttrjobEditController.m_stageAttrjobAdd.close();
	            }
			}
		});
		fxTxtNumberAttrjob.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{

			@Override
			public void handle(KeyEvent event) 
			{
				//System.out.println("OnKeyInterPressed");
				if (event.getCode().equals(KeyCode.ENTER))
	            {
					AddAttrjobInBase();
	            }
			}
		});
		
		fxTxtNameAttrjob.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{

			@Override
			public void handle(KeyEvent event) 
			{
				//System.out.println("OnKeyInterPressed");
				if (event.getCode().equals(KeyCode.ENTER))
	            {
					AddAttrjobInBase();
	            }
			}
		});
		
	}
	void AddAttrjobInBase()
	{
		/*CLPSMain.mDatabase = FirebaseDatabase.getInstance()
				.getReference()
				.child(CMAINCONSTANTS.FB_my_owner_settings)
				.child(CMAINCONSTANTS.FB_my_attrjob);*/
		// ����� �������� ���������� ������� ��� ��������� MyAutoIncrement:
		
		DatabaseReference rootRef = FirebaseDatabase.getInstance()
				.getReference()
				.child(CMAINCONSTANTS.FB_my_owner_settings)
				.child(CMAINCONSTANTS.FB_my_attrjob);
		rootRef.addValueEventListener(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot arg0) {
				m_lChildrenCount = arg0.getChildrenCount();
				
			}
			
			@Override
			public void onCancelled(DatabaseError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		String uploadId = CLPSMain.mDatabase.push().getKey();
		CLPSMain.mDatabase.child(uploadId).child("MyIDUnique").setValue(uploadId);
		
		CLPSMain.mDatabase.child(uploadId).child("MyAutoIncrement")
		.setValue(Long.toString(m_lChildrenCount));// ����� ����� MyAutoIncrement

		CLPSMain.mDatabase.child(uploadId).child("MyCLassAttrjob").setValue(fxTxtNumberAttrjob.getText());
		CLPSMain.mDatabase.child(uploadId).child("MyNameAttrjob").
		setValue(fxTxtNameAttrjob.getText());
		CLPSMain.mDatabase.child(uploadId).child("MyTypeAttrjob").
		setValue(stAttributeCode);
		CLPSMain.mDatabase.child(uploadId).child("MyHeight").
		setValue(CMAINCONSTANTS.Height_cell_start);
		CLPSMain.mDatabase.child(uploadId).child("MyWidth").
		setValue(CMAINCONSTANTS.Width_control_start);
		
		CAttrjobEditController.m_stageAttrjobAdd.close();
	}
	/*void AddAttrjobInBaseType(String stAttributeCode)
	{
		CLPSMain.mDatabase.child(uploadId).child("MyTypeAttrjob").
		setValue(stAttributeCode);
	}*/
}
