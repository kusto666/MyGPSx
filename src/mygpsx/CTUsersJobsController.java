package mygpsx;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	public Label fxLbNameUser;
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
	private ArrayList<CStructUser> m_alUsers = null;
	
	private ObservableList<CStructPriority> m_ObservableListPriority;
	private ArrayList<CStructPriority> m_alPriority = null;
	
	
	
	private String m_stNameShip = null; // Это название судна в интерфейсе для людей!!!
	private String m_stUsersUniqueID = null;// Это UniqueID User в коде!!!
	
	private String m_stNameTmpl = null; // Это название шаблона в интерфейсе для людей!!!
	private String m_stTmplUniqueID = null;// Это UniqueID шаблона в коде!!!
	
	private int my_i = 0;
	
	int m_iTemp = 0;
	//CStructUser cStructUser2 = null;
	
	

	public void RefreshSelectedUser(String stSelectedUserShip) 
	{  
		m_iTemp = 0;
//		fxLbNameUser.setText(stSelectedUserShip);
//		System.out.println("RefreshSelectedUser = fxLbNameUser.getText() = " + fxLbNameUser.getText());
		if(CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_SHIP == null)
		{
			fxCbSelectUser.setValue(CCONSTANTS_EVENTS_JOB.MAIN_ObservableListUser.get(m_iTemp));
			return;
		}
		
		for (CStructUser cStructUser : CCONSTANTS_EVENTS_JOB.MAIN_ObservableListUser) 
		{
			try
			{
				if(cStructUser.getMyPhoneID().equals(CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_SHIP))
				{
					fxCbSelectUser.setValue(CCONSTANTS_EVENTS_JOB.MAIN_ObservableListUser.get(m_iTemp));
					System.out.println("RefreshSelectedUser = " + cStructUser.getMyDirectorShip() + " - номер - " + Integer.toString(m_iTemp));
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();// Чтобы не ругался на первый элемент ComboBox - он только фиктивен и не для выбора вообще!!!
			}
			m_iTemp++;
		} 
    }  
	// Добавление новой задачи!!!
    @FXML
    private void BtnAddingJobNew(ActionEvent event) 
    {
    	//m_iTemp = 2;
    	//fxCbSelectUser.setValue(CCONSTANTS_EVENTS_JOB.MAIN_ObservableListUser.get(m_iTemp));
    	try 
    	{
    		if(fxCbSelectPriorityJob.getSelectionModel().getSelectedIndex() != 0)
    		{
    			
    			String uploadIdTask = CLPSMain.mDatabase.push().getKey();// Здесь генериться уникальный ключ задачи!!!
    			
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
    			// Перед началом привязки задачи(Task-Job) проверим нет ли уже текущей задачи у выбранного SysUser`a
    			DatabaseReference mDatabaseSysUsers = FirebaseDatabase.getInstance().getReference()
    					.child(CMAINCONSTANTS.FB_my_sys_users_binding)/*
    					.child(CMAINCONSTANTS.NoFB_MyIDSysUserSelected)*/;
    			mDatabaseSysUsers.addListenerForSingleValueEvent(new ValueEventListener()
    			 {
    				
//// ЧТО ЗА ХУЙНЯ???? - ГДЕ ДАННЫЕ?????????????????????? - ЗАРАБОТАЛО!!!!!!!!!!!!!!!
					@Override
					public void onDataChange(DataSnapshot arg0) 
					{
						System.out.println("mDatabaseSysUsers.addValueEventListener(new ValueEventListener()!!!");
						CStructSysUser suTemp = null;
						Iterable<DataSnapshot> contactChildren = arg0.getChildren();
						for (DataSnapshot structCStructSysUser : contactChildren)
		                {
							
							suTemp = structCStructSysUser.getValue(CStructSysUser.class);
		                 	System.out.println( "suTemp.getMyCurrentTaskID( = "  + suTemp.getMyCurrentTaskID());
		                 	// Певое проверим вообще выбранного пользователя!!!
		                 	if(CMAINCONSTANTS.NoFB_MyIDSysUserSelected.equals(suTemp.getMyIDSysUser()))
                 			{
		                 		// Вот тогда и уже и смотрим есть ли привязанная задача!!!
			                 	if(!suTemp.getMyCurrentTaskID().equals(CCONSTANTS_EVENTS_JOB.MYNONE))
								{
			                 		Platform.runLater(
									() -> {
									CMyToast.makeText(CLPSMain.stage, 
					      	    			  "У пользователя уже есть привязанная задача!",
					      	    			  CMyToast.TOAST_LONG, CMyToast.TOAST_ERROR);
									
									});
			                 		return;
								}
			                 	else
			                 	{
			                 	// Здесь добавляем задачу в общую ветку - "my_users_jobs".
			            			FirebaseDatabase.getInstance().getReference()
			        				.child(CMAINCONSTANTS.FB_my_users_jobs)
			        				.child(CMAINCONSTANTS.NoFB_MyIDSysUserSelected)
			        				.child(uploadIdTask).child("MyTemplateJob").setValueAsync(CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_TMPL);
			            			
			            			// Далее, если выбран пользователь(SysUser) то в "my_sys_users_binding" также изменим
			            			// "myCurrentTaskID" с "none" -  на "uploadId"
			            			FirebaseDatabase.getInstance().getReference()
			        				.child(CMAINCONSTANTS.FB_my_sys_users_binding)
			        				.child(CMAINCONSTANTS.NoFB_MyIDSysUserSelected)
			        				.child("myCurrentTaskID").setValueAsync(uploadIdTask);
			            			
			                		 //Это старый вариант с привязкой по ID телефона!!!
			                		/* FirebaseDatabase.getInstance().getReference()
			                				.child(CMAINCONSTANTS.FB_my_users_jobs)
			                				.child(CMAINCONSTANTS.MyPhoneID_ + CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_SHIP)
			                				.child(uploadId).child("MyTemplateJob").setValueAsync(CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_TMPL);*/
			            			Platform.runLater(
											() -> {
						            			 CMyToast.makeText(CLPSMain.stage, 
						            	    			  "Задача успешно добавлена!",
						            	    			  CMyToast.TOAST_LONG, CMyToast.TOAST_SUCCESS);
											});
			                		System.out.println("BtnAddingJobNew!!!");
			                 	}
                 			}

	                	}
						
					}

					@Override
					public void onCancelled(DatabaseError arg0) {
						// TODO Auto-generated method stub
						
					}
    				 
    			 });
   			
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
		CLPSMain.m_CTUsersJobsController = this;// Инициализация статического контроллера для доступа из вне(других контроллов)
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

						m_alUsers = new ArrayList<CStructUser>();
						
						CStructUser TempSP = new CStructUser();// Создадим пустого пользователя для первого элемента списка!!!
						TempSP.setMyFreeNameFirst("Выбор судна(необязательно)");
						TempSP.setMyPhoneID("facking_id");
						m_alUsers.add(TempSP);
						
						for (DataSnapshot structCStructUser : contactChildren)
		                {
							TempSP = structCStructUser.getValue(CStructUser.class);
		                 	System.out.println( "structCStructUser = "  + TempSP.getMyDirectorShip());
		                 	m_alUsers.add(TempSP);// Заполнили массив!!!
	                	}
						 m_ObservableList = FXCollections.observableArrayList (m_alUsers);
				            CCONSTANTS_EVENTS_JOB.MAIN_ObservableListUser = m_ObservableList;
						Platform.runLater(
					        	() -> {
						           // m_ObservableList = FXCollections.observableArrayList (m_alUsers);
						            //CCONSTANTS_EVENTS_JOB.MAIN_ObservableListUser = m_ObservableList;
						            fxCbSelectUser.setItems(CCONSTANTS_EVENTS_JOB.MAIN_ObservableListUser);
						            fxCbSelectUser.setValue(CCONSTANTS_EVENTS_JOB.MAIN_ObservableListUser.get(m_iTemp));
					        	});
				       
					}
					catch (Exception e) 
					{
						e.printStackTrace();
					}

					fxCbSelectUser.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CStructUser>() 
				    {
						@Override
						public void changed(ObservableValue<? extends CStructUser> observable, CStructUser oldValue,
								CStructUser newValue)
						{
							if(newValue != oldValue)
							{
								Platform.runLater(
							        	() -> {
							        		try 
							        		{
												m_stNameShip =  ((CStructUser)newValue).getMyNameShip();
												m_stUsersUniqueID =  ((CStructUser)newValue).getMyPhoneID();
												CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_SHIP = m_stUsersUniqueID;
												CMAINCONSTANTS.NoFB_MyIDSysUserSelected = ((CStructUser)newValue).getMySysUserBinding();
												
												System.out.println("CMAINCONSTANTS.NoFB_MyIDSysUserSelected = " + CMAINCONSTANTS.NoFB_MyIDSysUserSelected);
												System.out.println("CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_SHIP = " + CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_SHIP);
												//fxCbSelectUser.setValue(newValue);
											} 
							        		catch (Exception e) 
							        		{
										//		e.printStackTrace();
											}

							        });
							}
							
						}
					});
				}

				@Override
				public void onCancelled(DatabaseError arg0) {
				}
			 });


/////////////////////////////////// ### INIT TEMPLATES ### ///////////////////////////////////////////////////
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
					System.out.println("Что-то изменилось - типа выбрали другой шаблон!!!");
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
					Platform.runLater(() ->
					{
			            m_ObservableListPriority = FXCollections.observableArrayList (m_alPriority);
			            fxCbSelectPriorityJob.setItems(m_ObservableListPriority);
			            fxCbSelectPriorityJob.setValue(m_ObservableListPriority.get(0));
					});

				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}

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


