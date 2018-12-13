package mygpsx;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/*import com.google.firebase.tasks.Task;*/

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
	private ComboBox<CStructUser> fxCbSelectUser;// Здесь список "обычных" пользователей!!!
	@FXML
	private ComboBox<CStructTmplJob> fxCbSelectTemplateJob; // List of templates!!!
	@FXML
	private ComboBox<CStructPriority> fxCbSelectPriorityJob; // List of priority!!!
	
	private DatabaseReference mDatabaseUsers;
	private DatabaseReference mDatabaseAddingJob;
	private DatabaseReference mDatabaseListenSelectUser;// Здесь слушаем выбранного пользователя!!!
	private DatabaseReference mDatabasePriorityJobs;// Это для приоритетов!!!
	private DatabaseReference mDatabaseUpdateSelectedUsersTmpls;
	
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
    			
    			// Тут надо будет сделать проверки , что правильно выбрали пользователя!!!
    			if(CMAINCONSTANTS.NoFB_MyIDSysUserSelected == null)// - это типа вообще только начало, только запустили и не понимаем)))
    			{
    				CMyToast.makeText(CLPSMain.stage, 
        	    			  "Как раз доделать без привязки пользователя к задаче!!!",
        	    			  CMyToast.TOAST_LONG, CMyToast.TOAST_ERROR);
      				return;
    			}
    			if(CMAINCONSTANTS.NoFB_MyIDSysUserSelected.equals(CCONSTANTS_EVENTS_JOB.MYNONE))// Это типа без пользователя SysUser!!!
    			{
    				CMyToast.makeText(CLPSMain.stage, 
      	    			  "К судну не привязан пользователь!",
      	    			  CMyToast.TOAST_LONG, CMyToast.TOAST_ERROR);
    				return;
    			}
    			
    			FirebaseDatabase.getInstance().getReference()
				.child(CMAINCONSTANTS.FB_my_users_jobs)
				.child(CMAINCONSTANTS.NoFB_MyIDSysUserSelected)
				.child(uploadId).child("MyTemplateJob").setValueAsync(CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_TMPL);
    			
    			
        		/* Это старый вариант с привызкой по ID телефона!!!
        		 * FirebaseDatabase.getInstance().getReference()
        				.child(CMAINCONSTANTS.FB_my_users_jobs)
        				.child(CMAINCONSTANTS.MyPhoneID_ + CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_SHIP)
        				.child(uploadId).child("MyTemplateJob").setValueAsync(CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_TMPL);*/
    			 CMyToast.makeText(CLPSMain.stage, 
    	    			  "Задача успешно добавлена!",
    	    			  CMyToast.TOAST_LONG, CMyToast.TOAST_SUCCESS);
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
		CMAINCONSTANTS.NoFB_MyIDSysUserSelected = "MyIDSysUserSelected";
		System.out.println("Проверка при инициализации CMAINCONSTANTS.NoFB_MyIDSysUserSelected = " + CMAINCONSTANTS.NoFB_MyIDSysUserSelected);
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
					
					CStructUser TempSP = new CStructUser();// Создадим пустого пользователя для первого элемента списка!!!
					TempSP.setMyFreeNameFirst("Выбор судна(необязательно)");
					m_alAttrjob.add(TempSP);
					
					for (DataSnapshot structCStructUser : contactChildren)
	                {
						TempSP = structCStructUser.getValue(CStructUser.class);
	                 	System.out.println( "structCStructUser = "  + TempSP.getMyDirectorShip());
	                 	m_alAttrjob.add(TempSP);// Заполнили массив!!!
                	}
					Platform.runLater(
				        	() -> {
					            m_ObservableList = FXCollections.observableArrayList (m_alAttrjob);
					            fxCbSelectUser.setItems(m_ObservableList);
					            fxCbSelectUser.setValue(m_ObservableList.get(0));
					            m_stUsersUniqueID = null;
				        	});
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
						Platform.runLater(
					        	() -> {
					        		try 
					        		{
										m_stNameShip =  ((CStructUser)newValue).getMyNameShip();
										m_stUsersUniqueID =  ((CStructUser)newValue).getMyPhoneID();
										CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_SHIP = m_stUsersUniqueID;
										CMAINCONSTANTS.NoFB_MyIDSysUserSelected = ((CStructUser)newValue).getMySysUserBinding();
										FirebaseDatabase.getInstance().getReference()
												.child(CMAINCONSTANTS.FB_MyIDUserSelected).setValueAsync(CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_SHIP);
										System.out.println("m_stUsersUniqueID = " + m_stUsersUniqueID);
									} 
					        		catch (Exception e) 
					        		{
										// TODO: handle exception
									}

					        });
					}
				});
			}

			@Override
			public void onCancelled(DatabaseError arg0) {
			}
		 });
		

/*		mDatabaseListenSelectUser = FirebaseDatabase.getInstance().getReference()
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
		 });*/
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
					
					CStructTmplJob TempSP = new CStructTmplJob();// Создадим пустой шаблон для первого элемента списка!!!
					TempSP.setMyNameTemplate("Выберите шаблон...");
					m_aTmpl.add(TempSP);
					
					for (DataSnapshot structTmplJob : contactChildren)
	                {
						//HashMap<String, CStructTmplJob> hmTemp = new HashMap<>();
						
						TempSP = structTmplJob.getValue(CStructTmplJob.class);
	                 	System.out.println( "structTmplJob = "  + TempSP.getMyNameTemplate());
	                 	m_aTmpl.add(TempSP);// Заполнили массив!!!
                	}
					Platform.runLater(
			    			  () -> {
						            m_ObservableListTmpl = FXCollections.observableArrayList (m_aTmpl);
						            fxCbSelectTemplateJob.setItems(m_ObservableListTmpl);
						            fxCbSelectTemplateJob.setValue(m_ObservableListTmpl.get(0));
						            m_stTmplUniqueID = null;
			    			  });
	/*	        	Platform.runLater(
	    			  () -> {
	    				  try
	    				  {
	    					  if(m_aTmpl.size() < 0)
	    					  {
	    						  System.out.println("CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_TMPL = " + CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_TMPL);
	    					  }
	    					  // Здесь если попадаем в исключение, то ниже т.к. нет ни одного шаблона в списке!!!
	    					  fxCbSelectTemplateJob.setValue(m_aTmpl.get(0));
	    				  } 
	    				  catch (Exception e)
	    				  {
	    					  // ... ставим заглушку из пустого объекта CStructTmplJob 
	    					 CStructTmplJob tempStructTmplJob = new CStructTmplJob();
	    					  tempStructTmplJob.setMyNameTemplate("Empty list of templates!");
	    					  //m_aTmpl.add(tempStructTmplJob);
	    					  //fxCbSelectTemplateJob.setValue(m_aTmpl.get(0));
	    					  //e.getMessage();
						  }
	    				  
	    			  });*/
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
		fxCbSelectTemplateJob.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CStructTmplJob>() 
	    {
			@Override
			public void changed(ObservableValue<? extends CStructTmplJob> observable, CStructTmplJob oldValue,
					CStructTmplJob newValue)
			{
				if(newValue != oldValue)
				{
					System.out.println("Что-то изменилось!!!");
				}
				m_stNameTmpl =  ((CStructTmplJob)newValue).getMyNameTemplate();
				m_stTmplUniqueID =  ((CStructTmplJob)newValue).getMyIDUnique();
				CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_TMPL = m_stTmplUniqueID;
				System.out.println("CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_TMPL = " + CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_TMPL);
				
				FirebaseDatabase.getInstance().getReference()
						.child(CMAINCONSTANTS.FB_MyIDTmplSelected).setValueAsync(CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_TMPL);
			}
		});
		
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
					
					CStructPriority TempSPFirstValue = new CStructPriority();
					TempSPFirstValue.setMyNamePriority("Выберите приоритет задачи...");
					
					m_alPriority.add(TempSPFirstValue);
					for (DataSnapshot structPriorityr : contactChildren)
	                {
						CStructPriority TempSP = structPriorityr.getValue(CStructPriority.class);
	                 	System.out.println( "TempSP = "  + TempSP.getMyNamePriority());
	                 	m_alPriority.add(TempSP);// Заполнили массив!!!
                	}
					Platform.runLater(() -> {
		            m_ObservableListPriority = FXCollections.observableArrayList (m_alPriority);
		            fxCbSelectPriorityJob.setItems(m_ObservableListPriority);
		            fxCbSelectPriorityJob.setValue(m_ObservableListPriority.get(0));
					});
		           // m_stTmplUniqueID = null;
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				/*Platform.runLater(() -> {
					fxCbSelectPriorityJob.setValue(m_alPriority.get(my_i));
			  });*/
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


