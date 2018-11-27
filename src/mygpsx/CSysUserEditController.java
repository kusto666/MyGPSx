package mygpsx;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CSysUserEditController implements Initializable
{
	private ObservableList<CStructSysUser> m_ObservableList;
	@FXML
	private ListView<CStructSysUser> fxListViewStatus;

	private ArrayList<CStructSysUser> m_alSysUser = null;
	
	@FXML
	private Parent m_rootSysUserAdd = null;
	@FXML
	public static Stage m_stageSysUserAdd = null;

	 // Открытие окна добавление статуса задачи!!! 
    @FXML
    private void FrameAddSysUser(ActionEvent event) 
    {
    	System.out.println("FrameAddSysUser - OPEN!!!");
    	try 
    	{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXSysUserAdd));
            m_rootSysUserAdd = (Parent)fxmlLoader.load();
            m_stageSysUserAdd = new Stage();
            m_stageSysUserAdd.setTitle(CStrings.m_APP_NAME + "->Добавление SysUser");
            m_stageSysUserAdd.setScene(new Scene(m_rootSysUserAdd));  
            m_stageSysUserAdd.setResizable(false);
            m_stageSysUserAdd.initModality(Modality.WINDOW_MODAL);
            m_stageSysUserAdd.initOwner(CLPSMain.m_stageFXSysUserEdit);
            m_stageSysUserAdd.show();
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }

	//@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		// Start listing users from the beginning, 1000 at a time.
		CStructSysUser SysUser = null;
		m_alSysUser = new ArrayList<CStructSysUser>();
		
		ListUsersPage page = null;
		try 
		{
			page = FirebaseAuth.getInstance().listUsers(null);
		} 
		catch (FirebaseAuthException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (page != null)
		{
		  for (ExportedUserRecord user : page.getValues())
		  {
			  SysUser = new CStructSysUser(user.getUid(), user.getEmail(), user.getPasswordHash());
			  m_alSysUser.add(SysUser);
			  System.out.println("SysUser: " + user.getUid());
			  System.out.println("user.getEmail(): " + user.getEmail());
			  System.out.println("user.getPasswordHash(): " + user.getPasswordHash());
		  }
		  page = page.getNextPage();
		}
		m_ObservableList = FXCollections.observableArrayList (m_alSysUser);
	
		
        try 
        {
        	Platform.runLater(
			  () -> {
				  fxListViewStatus.setItems(m_ObservableList);
				// fxListViewPriority.setPrefSize(200, 500);
				  fxListViewStatus.setCellFactory(new Callback<ListView<CStructSysUser>, ListCell<CStructSysUser>>() {
					
					@Override
					public ListCell<CStructSysUser> call(ListView<CStructSysUser> param)
					{
						// TODO Auto-generated method stub
						return new CUserCellSysUser();
					}
				});
				  fxListViewStatus.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    			@Override
		    			public void handle(MouseEvent click)
		    			{
		    				if (click.getClickCount() == 1) 
		    		        {
		    					System.out.println("click.getClickCount() == 1");
		    					//CStructStatus StrStatus = fxListViewStatus.getSelectionModel().getSelectedItem();
		    					//System.out.println("StrPrior = " + StrStatus.getMyIDUnique());
		    		        }
		    		        if (click.getClickCount() == 2) 
		    		        {
		    		        	System.out.println("click.getClickCount() == 2");
		    		        }
		    		    }
					});
			  }
			);
        	
		}
        catch (Exception ex)
        {
        	ex.getMessage();
		}
/*		System.out.println("CStatusEditController - initialize!!!");
		try
		{
			CLPSMain.mDatabase = FirebaseDatabase.getInstance().getReference()
					.child(CMAINCONSTANTS.FB_my_owner_settings)
					.child(CMAINCONSTANTS.FB_my_status);
			CLPSMain.mDatabase.addValueEventListener(new ValueEventListener()
			 {
				@Override
				public void onDataChange(DataSnapshot arg0)
				{
					try 
					{
			            Iterable<DataSnapshot> contactChildren = arg0.getChildren();
			        	
			            m_alStatus = new ArrayList<CStructStatus>();
			            for (DataSnapshot structStatus : contactChildren)
		                {
			            	CStructStatus TempSP = structStatus.getValue(CStructStatus.class);
                        	System.out.println( "CStructStatus = "  + TempSP.getMyNameStatus());
                        	m_alStatus.add(TempSP);// Заполнили массив!!!
	                	}
			            m_ObservableList = FXCollections.observableArrayList (m_alStatus);
			            
			            
			            try 
			            {
			            	Platform.runLater(
	            			  () -> {
	            				  fxListViewStatus.setItems(m_ObservableList);
	            				// fxListViewPriority.setPrefSize(200, 500);
	            				  fxListViewStatus.setCellFactory(new Callback<ListView<CStructStatus>, ListCell<CStructStatus>>() {
									
									@Override
									public ListCell<CStructStatus> call(ListView<CStructStatus> param)
									{
										// TODO Auto-generated method stub
										return new CUserCellStatus();
									}
								});
	            				  fxListViewStatus.setOnMouseClicked(new EventHandler<MouseEvent>() {
	            		    			@Override
	            		    			public void handle(MouseEvent click)
	            		    			{
	            		    				if (click.getClickCount() == 1) 
	            		    		        {
	            		    					CStructStatus StrStatus = fxListViewStatus.getSelectionModel().getSelectedItem();
	            		    					System.out.println("StrPrior = " + StrStatus.getMyIDUnique());
	            		    		        }
	            		    		        if (click.getClickCount() == 2) 
	            		    		        {
	            		    		        	System.out.println("click.getClickCount() == 2");
	            		    		        }
	            		    		    }
	            					});
	            			  }
	            			);
			            	
						}
			            catch (Exception ex)
			            {
			            	ex.getMessage();
						}
					} 
					catch (Exception e) 
					{
						System.out.println(e.getMessage());
					}
				}
				@Override
				public void onCancelled(DatabaseError arg0)
				{
					System.out.println(arg0.getMessage());
				}
			 });
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		*/
		
	}
	

}
