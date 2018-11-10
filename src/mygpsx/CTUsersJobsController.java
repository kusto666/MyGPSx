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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

public class CTUsersJobsController implements Initializable
{
	//public static int m_iChangeIndex = 0;
	private IntegerProperty index = new SimpleIntegerProperty(-1);
	@FXML
	private AnchorPane fxAPaneMain;
	@FXML
	private ComboBox<CStructUser> fxCbSelectUser;
	@FXML
	private ComboBox<CStructTmplJob> fxCbSelectTamplateJob;
	
	private DatabaseReference mDatabaseUsers;
	private DatabaseReference mDatabaseListenSelectUser;// Здесь слушаем выбранного пользователя!!!
	private Task<Void> mDatabaseUpdateSelectedUser;
	
	private DatabaseReference mDatabaseTamplates;
	private ObservableList<CStructTmplJob> m_ObservableListTmpl;
	private ArrayList<CStructTmplJob> m_aTmpl = null;
	
	private ObservableList<CStructUser> m_ObservableList;
	private ArrayList<CStructUser> m_alAttrjob = null;
	
	private String m_stNameShip = null; // Это название судна в интерфейсе для людей!!!
	private String m_stUsersUniqueID = null;// Это UniqueID User в коде!!!
	
	private String m_stNameTmpl = null; // Это название шаблона в интерфейсе для людей!!!
	private String m_stTmplUniqueID = null;// Это UniqueID шаблона в коде!!!
	
	int i = 0;
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
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
						mDatabaseUpdateSelectedUser = FirebaseDatabase.getInstance().getReference()
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
				
				for(i = 0; i < m_alAttrjob.size(); i++)
				{
					if(((CStructUser)m_alAttrjob.get(i)).getMyPhoneID().equals(arg0.getValue()))
					{
						try {
							System.out.println("Попали в................ - i = " + Integer.toString(i));

							Platform.runLater(
            			  () -> {
            				  	fxCbSelectUser.setValue(m_alAttrjob.get(i));
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
		        	/*Platform.runLater(
	            			  () -> {
	            				  fxCbSelectTamplateJob.setValue(m_aTmpl.get(0));
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
				/*CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_SHIP = m_stUsersUniqueID;
				mDatabaseUpdateSelectedUser = FirebaseDatabase.getInstance().getReference()
						.child(CMAINCONSTANTS.FB_MyIDUserSelected).setValue(CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_SHIP);
				System.out.println("m_stUsersUniqueID = " + m_stUsersUniqueID);
				System.out.println("m_stNameShip = " + m_stNameShip);*/

			}
		});
	}
}


