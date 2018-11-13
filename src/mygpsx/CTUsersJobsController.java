package mygpsx;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.tasks.Task;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class CTUsersJobsController implements Initializable
{
	//
	//public static int m_iChangeIndex = 0;
	//private IntegerProperty index = new SimpleIntegerProperty(-1);
	@FXML
	private Label fxLbErrorSelectPriority;
	@FXML
	private Button fxBtnAddingJobNew;
	@FXML
	private AnchorPane fxAPaneMain;
	@FXML
	private ComboBox<CStructUser> fxCbSelectUser;// Здесь список пользователей!!!
	@FXML
	private ComboBox<CStructTmplJob> fxCbSelectTamplateJob; // List of templates!!!
	@FXML
	private ComboBox<CStructPriority> fxCbSelectPriorityJob; // List of priority!!!
	
	private DatabaseReference mDatabaseUsers;
	private Task<Void> mDatabaseAddingJob;
	private DatabaseReference mDatabaseListenSelectUser;// Здесь слушаем выбранного пользователя!!!
	private DatabaseReference mDatabasePriorityJobs;// Это для приоритетов!!!
	private Task<Void> mDatabaseUpdateSelectedUsersTmpls;
	
	private DatabaseReference mDatabaseTamplates;
	
	private ObservableList<CStructTmplJob> m_ObservableListTmpl;
	private ArrayList<CStructTmplJob> m_aTmpl = null;
	
	private ObservableList<CStructUser> m_ObservableList;
	private ArrayList<CStructUser> m_alAttrjob = null;
	
	private ObservableList<CStructPriority> m_ObservableListPriority;
	private ArrayList<CStructPriority> m_alPriority = null;
	
	
	
	private String m_stNameShip = null; // Это название судна в интерфейсе для людей!!!
	private String m_stUsersUniqueID = null;// Это UniqueID User в коде!!!
	
	private String m_stNameTmpl = null; // Это название шаблона в интерфейсе для людей!!!
	private String m_stTmplUniqueID = null;// Это UniqueID шаблона в коде!!!
	
	private int my_i = 0;

	// Добавление новой задачи!!!
    @FXML
    private void BtnAddingJobNew(ActionEvent event) 
    {
    	try 
    	{
    		if(fxCbSelectPriorityJob.getSelectionModel().getSelectedIndex() != 0)
    		{
    			String uploadId = CLPSMain.mDatabase.push().getKey();
        		mDatabaseAddingJob = FirebaseDatabase.getInstance().getReference()
        				.child(CMAINCONSTANTS.FB_my_users_jobs)
        				.child(CMAINCONSTANTS.MyPhoneID_ + CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_SHIP)
        				.child(uploadId).child("MyTemplateJob").setValue(CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_TMPL);
        		System.out.println("BtnAddingJobNew!!!");
    		}
    		else
    		{
    			fxLbErrorSelectPriority.setVisible(true);
    		}
    		
		} 
    	catch (Exception e)
    	{
			e.printStackTrace();
		}
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		fxLbErrorSelectPriority.setVisible(false);
		mDatabaseUsers = FirebaseDatabase.getInstance().getReference()
				.child(CMAINCONSTANTS.FB_users);
		mDatabaseUsers.addValueEventListener(new ValueEventListener()
		 {
			@Override
			public void onDataChange(DataSnapshot arg0)
			{
				try 
				{
					Iterable<DataSnapshot> contactChildren = arg0.getChildren();
					
					m_alAttrjob = new ArrayList<CStructUser>();
					for (DataSnapshot structCStructUser : contactChildren)
	                {
						CStructUser TempSP = structCStructUser.getValue(CStructUser.class);
	                 	System.out.println( "structCStructUser = "  + TempSP.getMyDirectorShip());
	                 	m_alAttrjob.add(TempSP);// Заполнили массив!!!
                	}
		            m_ObservableList = FXCollections.observableArrayList (m_alAttrjob);
		            fxCbSelectUser.setItems(m_ObservableList);
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				fxCbSelectUser.valueProperty().addListener(new ChangeListener<CStructUser>() {

					@Override
					public void changed(ObservableValue<? extends CStructUser> observable, CStructUser oldValue,
							CStructUser newValue) 
					{
						
						
					}
				});
				fxCbSelectUser.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CStructUser>() 
			    {
					@Override
					public void changed(ObservableValue<? extends CStructUser> observable, CStructUser oldValue,
							CStructUser newValue)
					{
						if(newValue != oldValue)
						{
							//System.out.println("Что-то изменилось!!!");
						}
						m_stNameShip =  ((CStructUser)newValue).getMyNameShip();
						m_stUsersUniqueID =  ((CStructUser)newValue).getMyPhoneID();
						CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_SHIP = m_stUsersUniqueID;
						mDatabaseUpdateSelectedUsersTmpls = FirebaseDatabase.getInstance().getReference()
								.child(CMAINCONSTANTS.FB_MyIDUserSelected).setValue(CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_SHIP);
						System.out.println("m_stUsersUniqueID = " + m_stUsersUniqueID);
						//System.out.println("m_stNameShip = " + m_stNameShip);

					}
				});
			}

			@Override
			public void onCancelled(DatabaseError arg0) {
			}
		 });
		mDatabaseListenSelectUser = FirebaseDatabase.getInstance().getReference()
				.child(CMAINCONSTANTS.FB_MyIDUserSelected);
		mDatabaseListenSelectUser.addValueEventListener(new ValueEventListener()
		 {
			@Override
			public void onDataChange(DataSnapshot arg0)
			{
				System.out.println( "Listen CTUsersJobsController  = " + arg0.getValue());
				
				for(my_i = 0; my_i < m_alAttrjob.size(); my_i++)
				{
					if(((CStructUser)m_alAttrjob.get(my_i)).getMyPhoneID().equals(arg0.getValue()))
					{
						try {
							System.out.println("Попали в................ - my_i = " + Integer.toString(my_i));

					Platform.runLater(() -> {
            				  	fxCbSelectUser.setValue(m_alAttrjob.get(my_i));
            			  });
							break;
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				}
			}

			@Override
			public void onCancelled(DatabaseError arg0) {
		
			}
		 });
/////////////////////////////////// INIT TEMPLATES ///////////////////////////////////////////////////
		mDatabaseTamplates = FirebaseDatabase.getInstance().getReference()
				.child(CMAINCONSTANTS.FB_my_owner_settings).child(CMAINCONSTANTS.FB_my_templates);
		
		mDatabaseTamplates.addValueEventListener(new ValueEventListener()
		 {
			@Override
			public void onDataChange(DataSnapshot arg0)
			{
				try 
				{
					Iterable<DataSnapshot> contactChildren = arg0.getChildren();
					
					m_aTmpl = new ArrayList<CStructTmplJob>();
					for (DataSnapshot structTmplJob : contactChildren)
	                {
						CStructTmplJob TempSP = structTmplJob.getValue(CStructTmplJob.class);
	                 	System.out.println( "structTmplJob = "  + TempSP.getMyNameTemplate());
	                 	m_aTmpl.add(TempSP);// Заполнили массив!!!
                	}
		            m_ObservableListTmpl = FXCollections.observableArrayList (m_aTmpl);
		            fxCbSelectTamplateJob.setItems(m_ObservableListTmpl);
		        	Platform.runLater(
	            			  () -> {
	            				  try
	            				  {
	            					  // Здесь если попадаем в исключение, то ниже т.к. нет ни одного шаблона в списке!!!
	            					  fxCbSelectTamplateJob.setValue(m_aTmpl.get(0));
	            				  } 
	            				  catch (Exception e)
	            				  {
	            					  // ... ставим заглушку из пустого объекта CStructTmplJob 
	            					  CStructTmplJob tempStructTmplJob = new CStructTmplJob();
	            					  tempStructTmplJob.setMyNameTemplate("Empty list of templates!");
	            					  m_aTmpl.add(tempStructTmplJob);
	            					  fxCbSelectTamplateJob.setValue(m_aTmpl.get(0));
	            					  //e.getMessage();
								  }
	            				  
	            			  });
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}

			}

			@Override
			public void onCancelled(DatabaseError arg0) {
		
			}
		 });
		fxCbSelectTamplateJob.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CStructTmplJob>() 
	    {
			@Override
			public void changed(ObservableValue<? extends CStructTmplJob> observable, CStructTmplJob oldValue,
					CStructTmplJob newValue)
			{
				if(newValue != oldValue)
				{
					//System.out.println("Что-то изменилось!!!");
				}
				m_stNameTmpl =  ((CStructTmplJob)newValue).getMyNameTemplate();
				m_stTmplUniqueID =  ((CStructTmplJob)newValue).getMyIDUnique();
				CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_TMPL = m_stTmplUniqueID;
				System.out.println("CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_TMPL = " + CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_TMPL);
				
				mDatabaseUpdateSelectedUsersTmpls = FirebaseDatabase.getInstance().getReference()
						.child(CMAINCONSTANTS.FB_MyIDTmplSelected).setValue(CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_TMPL);

			}
		});
		CStructPriority TempSPFirstValue = new CStructPriority();
		TempSPFirstValue.setMyNamePriority("Выберите приоритет задачи...");
/////////////////////////////////////// INIT PRIORITY OF JOBS //////////////////////////////////////////////////		
		mDatabasePriorityJobs = FirebaseDatabase.getInstance().getReference()
				.child(CMAINCONSTANTS.FB_my_owner_settings).child(CMAINCONSTANTS.FB_my_priority);
		mDatabasePriorityJobs.addValueEventListener(new ValueEventListener()
		 {
			
			//TempSPFirstValue.set
			@Override
			public void onDataChange(DataSnapshot arg0)
			{
				try 
				{
					Iterable<DataSnapshot> contactChildren = arg0.getChildren();
					
					m_alPriority = new ArrayList<CStructPriority>();
					m_alPriority.add(TempSPFirstValue);
					for (DataSnapshot structPriorityr : contactChildren)
	                {
						CStructPriority TempSP = structPriorityr.getValue(CStructPriority.class);
	                 	System.out.println( "TempSP = "  + TempSP.getMyNamePriority());
	                 	m_alPriority.add(TempSP);// Заполнили массив!!!
                	}
		            m_ObservableListPriority = FXCollections.observableArrayList (m_alPriority);
		            fxCbSelectPriorityJob.setItems(m_ObservableListPriority);
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				Platform.runLater(() -> {
					fxCbSelectPriorityJob.setValue(m_alPriority.get(my_i));
			  });
				fxCbSelectPriorityJob.valueProperty().addListener(new ChangeListener<CStructPriority>() {

					@Override
					public void changed(ObservableValue<? extends CStructPriority> observable, CStructPriority oldValue,
							CStructPriority newValue) 
					{
						
						
					}
				});
				fxCbSelectPriorityJob.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CStructPriority>() 
			    {
					@Override
					public void changed(ObservableValue<? extends CStructPriority> observable, CStructPriority oldValue,
							CStructPriority newValue)
					{
						if(newValue != oldValue)
						{
							//System.out.println("Что-то изменилось!!!");
						}
						if(fxLbErrorSelectPriority.isVisible()) 
						{
							fxLbErrorSelectPriority.setVisible(false);
						}
						
						CCONSTANTS_EVENTS_JOB.TEMP_PRIORITY_JOB = newValue.getMyIDUnique();
						System.out.println("CCONSTANTS_EVENTS_JOB.TEMP_PRIORITY_JOB = " + CCONSTANTS_EVENTS_JOB.TEMP_PRIORITY_JOB);
					}
				});
			}

			@Override
			public void onCancelled(DatabaseError arg0) {
			}
		 });
	}
}


