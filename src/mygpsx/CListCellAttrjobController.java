package mygpsx;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import javafx.application.Platform;
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
import javafx.scene.layout.Border;

@SuppressWarnings("unused")
public class CListCellAttrjobController implements Initializable{
	DatabaseReference mDatabase;
	//Query mDatabaseCurrentTmpl;

	private String stUniqueIDAttrjob = null;
	@FXML
	private Label fxLbUniqueID;
	@FXML
	private Label fxLbHiddenTypeAttr;
	@FXML
	private Button fxBtnDeleteAttrjob;
	@FXML
	private Button fxBtnAddAttrjobIntoTmpl;
	@FXML
	private TextField fxTxtNameAttrjob;
	@FXML
	private TextField fxTxtHeight;
	@FXML
	private TextField fxTxtWidth;

	@FXML
    private void BtnDeleteAttrjob(ActionEvent event) 
    {
    	try 
    	{
    		
    		System.out.println("BtnDeleteAttrjob");
    		mDatabase = FirebaseDatabase.getInstance()
    				.getReference()
    				.child(CMAINCONSTANTS.FB_my_owner_settings)
    				.child(CMAINCONSTANTS.FB_my_attrjob);
    		
    		Button btn = (Button)event.getSource();// Здесь получили кнопку!!!
    		System.out.println("btn = " + btn.toString());
    		AnchorPane ap = (AnchorPane)btn.getParent();// Здесь получили родительскую панель!!!
    		System.out.println("ap = " + ap.toString());
    		
    		ObservableList<Node> listNode = ap.getChildren();// Здесь получаем массив всех дочерних объектов родителя!!! 
    		Label nodeOne = (Label)listNode.get(5);// Выбераем по ID(ID - это от 0 и т.д. выше!) объект(контролл)
    		stUniqueIDAttrjob = nodeOne.getText();// Порядок можно посмотреть в Scene Biulder
    		System.out.println("stUniqueIDAttrjob = " + stUniqueIDAttrjob);
    		mDatabase = FirebaseDatabase.getInstance()
    				.getReference()
    				.child(CMAINCONSTANTS.FB_my_owner_settings)
    				.child(CMAINCONSTANTS.FB_my_attrjob)
    				.child(stUniqueIDAttrjob);
    		mDatabase.setValueAsync(null);// Удаляем значение(объект) из базы!!!
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }
	@FXML
    private void BtnAddAttrjobIntoTmpl(ActionEvent event) 
    {
		// Это самая крутая кнопка! ))) 
		// Самая рабочая, мы на нее еще вариант повесим для добавление сущностей в шаблон задачи
		// открытый для редактирования!!!
		CCONSTANTS_EVENTS_JOB.TEMP_COUNT_ADDING_CONTROLS_IN_TMPL++;
		mDatabase = FirebaseDatabase.getInstance()
				.getReference()
				.child(CMAINCONSTANTS.FB_my_owner_settings)
				.child(CMAINCONSTANTS.FB_my_attrjob);
		String stTempUniqueID = mDatabase.push().getKey();
		
		if(CCONSTANTS_EVENTS_JOB.SAMPLE_ANY_OR_ANY.equals("ADD"))
		{
			// Это добавление в firebase real!!!

			mDatabase = FirebaseDatabase.getInstance()
					.getReference()
					.child(CMAINCONSTANTS.FB_my_owner_settings)
					.child(CMAINCONSTANTS.FB_my_templates)
					.child(CMAINCONSTANTS.m_UniqueTempIDTempate).child(CMAINCONSTANTS.FB_my_adding_attr);
		}
		if(CCONSTANTS_EVENTS_JOB.SAMPLE_ANY_OR_ANY.equals("EDIT"))
		{
			// Это добавление в firebase real!!!
			mDatabase = FirebaseDatabase.getInstance()
					.getReference()
					.child(CMAINCONSTANTS.FB_my_owner_settings)
					.child(CMAINCONSTANTS.FB_my_templates)
					.child(CMAINCONSTANTS.m_UniqueTempEditIDTempate).child(CMAINCONSTANTS.FB_my_adding_attr);

		}
		// Здесь слушаем количество для сортировки

			mDatabase.addValueEventListener(new ValueEventListener() {
				
				@Override
				public void onDataChange(DataSnapshot arg0) {
					CCONSTANTS_EVENTS_JOB.COUNT_ATTRIBUTES_IN_my_adding_attr = (long) arg0.getChildrenCount();
					System.out.println("CCONSTANTS_EVENTS_JOB.COUNT_ATTRIBUTES_IN_my_adding_attr ===========  "
					+ CCONSTANTS_EVENTS_JOB.COUNT_ATTRIBUTES_IN_my_adding_attr);
					
				}
				
				@Override
				public void onCancelled(DatabaseError arg0) {
					// TODO Auto-generated method stub
					
				}
				
			});

				CStructAttrTmpl tmpl = new CStructAttrTmpl(stTempUniqueID,
						fxLbUniqueID.getText(),
						fxTxtNameAttrjob.getText(),
						fxTxtHeight.getText(),
						fxTxtWidth.getText(),
						fxLbHiddenTypeAttr.getText(),
						CCONSTANTS_EVENTS_JOB.COUNT_ATTRIBUTES_IN_my_adding_attr);
				//mDatabase.child(stTempUniqueID).setValueAsync(tmpl);
				mDatabase.child(stTempUniqueID).setValue(tmpl, new CompletionListener() {
					
					@Override
					public void onComplete(DatabaseError arg0, DatabaseReference arg1) {
						// TODO Auto-generated method stub
						
					}
				});
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		
		
		
		if(CCONSTANTS_EVENTS_JOB.SAMPLE_ANY_OR_ANY.equals("DEL")) 
		{
			fxBtnDeleteAttrjob.setVisible(true);
			fxBtnAddAttrjobIntoTmpl.setVisible(false);
		}
		if(CCONSTANTS_EVENTS_JOB.SAMPLE_ANY_OR_ANY.equals("ADD") || CCONSTANTS_EVENTS_JOB.SAMPLE_ANY_OR_ANY.equals("EDIT")) 
		{
			// Здесь слушаем количество контролов для будущей сортировки//////////////////////
			// Но надо проверить, какой шаблон слушаем: m_UniqueTempIDTempate or m_UniqueTempEditIDTempate
			if(CCONSTANTS_EVENTS_JOB.SAMPLE_ANY_OR_ANY.equals("ADD"))// m_UniqueTempIDTempate
			{
				mDatabase = FirebaseDatabase.getInstance()
						.getReference()
						.child(CMAINCONSTANTS.FB_my_owner_settings)
						.child(CMAINCONSTANTS.FB_my_templates)
						.child(CMAINCONSTANTS.m_UniqueTempIDTempate).child(CMAINCONSTANTS.FB_my_adding_attr);
			}
			if(CCONSTANTS_EVENTS_JOB.SAMPLE_ANY_OR_ANY.equals("EDIT"))// m_UniqueTempEditIDTempate
			{
				mDatabase = FirebaseDatabase.getInstance()
						.getReference()
						.child(CMAINCONSTANTS.FB_my_owner_settings)
						.child(CMAINCONSTANTS.FB_my_templates)
						.child(CMAINCONSTANTS.m_UniqueTempEditIDTempate).child(CMAINCONSTANTS.FB_my_adding_attr);
			}
			
			mDatabase.addValueEventListener(new ValueEventListener() {
				
				@Override
				public void onDataChange(DataSnapshot arg0) {
					CCONSTANTS_EVENTS_JOB.COUNT_ATTRIBUTES_IN_my_adding_attr = (long) arg0.getChildrenCount();
					System.out.println("CCONSTANTS_EVENTS_JOB.COUNT_ATTRIBUTES_IN_my_adding_attr ===========  "
					+ CCONSTANTS_EVENTS_JOB.COUNT_ATTRIBUTES_IN_my_adding_attr);
					
				}
				
				@Override
				public void onCancelled(DatabaseError arg0) {
					// TODO Auto-generated method stub
					
				}
				
			});
			////Здесь заканчиваем слушать количество контролов для будущей сортировки - END!!! /////////////////
			
			
			fxBtnDeleteAttrjob.setVisible(false);
			fxBtnAddAttrjobIntoTmpl.setVisible(true);
		}
		fxTxtNameAttrjob.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{
			@Override
			public void handle(KeyEvent event) 
			{
				System.out.println("OnKeyInterPressed");
				if (event.getCode().equals(KeyCode.ENTER))
	            {
					AnchorPane ap = (AnchorPane)fxTxtNameAttrjob.getParent();// Здесь получили родительскую панель!!!
					ObservableList<Node> listNode = ap.getChildren();// Здесь получаем массив всех дочерних объектов родителя!!! 
		    		Label nodeOne = (Label)listNode.get(5);
		    		stUniqueIDAttrjob = nodeOne.getText();
		    		
		    		mDatabase = FirebaseDatabase.getInstance()
		    				.getReference()
		    				.child(CMAINCONSTANTS.FB_my_owner_settings)
		    				.child(CMAINCONSTANTS.FB_my_attrjob)
		    				.child(stUniqueIDAttrjob);
		    		mDatabase.child("MyNameAttrjob").setValueAsync(fxTxtNameAttrjob.getText());
	            }
			}
		});
		
	}

}
