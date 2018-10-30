
package mygpsx;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import com.google.api.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.datastore.Blob;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.ServiceAccount;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BucketListOption;
import com.google.cloud.storage.StorageClass;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseCredential;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;


/**
 *
 * @author Dmitry
 */
public class CLPSMain extends Application  
{
	public static FirebaseApp defaultApp;
	
	public static DatabaseReference mDatabase;
	public static DatabaseReference mDatabaseRef;
	public static DatabaseReference mDatabaseRefUsers;
	 //Firebase - ����� �������  ����� �� �������!!!
	StorageReference storageReference;
	FirebaseStorage firebasestorage;
	Storage storage;
	
	public static ArrayList<CUser> m_localAllMarkersUsersTempMain = null;
	
	@FXML
	public static Parent m_rootAddShip = null;
	@FXML
	public static Stage m_stageAddShip = null;
	
	
	
	

	@FXML
	TextArea mymsg;
	@FXML
	static ListView<CUser> m_lvAllUsers;
	 @FXML
	 public static AnchorPane fxMessageWait;
	 @FXML
	 public static Label fxLbMessage;
	 @FXML
	 public static Button btnRestartMod;
	/*@FXML
	VBox fxvBoxUsersAll;*/
	@FXML
	Scene scene = null;
	@FXML
	public static Stage stage;
	
	public static String m_PathMainFxml = "LPSMap.fxml";
	public static String m_PathFXAddShipFxml = "FXAddShip.fxml";
	public static String m_PathFXListCellFxml = "ListCell.fxml";
	public static String m_PathFXMessageWaitFxml = "MessageWait.fxml";

	public static ObservableList<CUser> m_ObservableListUsers;
	@FXML
	static ArrayList<CUser> m_alUsersAll = null;

	@FXML
	public static ListView<CUser> fxListView;
	
	@FXML
	Parent root;
	@FXML
	public static FXMLLoader m_Loader;
	@FXML
	public static FXMLLoader m_LoaderCell;

    @SuppressWarnings("unchecked")
	@Override
    public void start(Stage st) throws Exception 
    {
    	if(!InitFireBase())
    	{
    		System.out.println("InitFireBase() - ������ �������������!!!");
    	}
    	else
    	{
    		btnRefreshAllMarkers();
            MyEventListnerFireUsers();
        	root = null;
        	stage = st;
        	try 
        	{
        		m_Loader = new FXMLLoader(getClass().getResource(m_PathMainFxml));
        		root = m_Loader.load();
        		
        		/*m_LoaderCell = new FXMLLoader(getClass().getResource(m_PathFXListCellFxml));
        		m_LoaderCell.setController(this);
        		m_LoaderCell.load();*/
        		
        		
        		mymsg = (TextArea)m_Loader.getNamespace().get("mymsg");
        		//fxvBoxUsersAll = (VBox)loader.getNamespace().get("fxvBoxUsersAll");
        		fxListView = (ListView<CUser>)m_Loader.getNamespace().get("fxListView");
        		fxMessageWait = (AnchorPane)m_Loader.getNamespace().get("fxMessageWait");
        		fxLbMessage = (Label)m_Loader.getNamespace().get("fxLbMessage");
        		btnRestartMod = (Button)m_Loader.getNamespace().get("btnRestartMod");
    		} 
        	catch (Exception ex) 
        	{
        		System.out.println(ex.getLocalizedMessage());
    			ex.getStackTrace();
    		}
            
    ///////////////////////////////////////////////////////////////////////////////////////////        
            scene = new Scene(root);
            stage.setTitle(CStrings.m_APP_NAME);
            stage.setScene(scene);

            stage.setOnCloseRequest(new EventHandler<WindowEvent>()
            {
                @Override
                public void handle(WindowEvent t)
                {
                	Alert alert = new Alert(AlertType.CONFIRMATION);
            		alert.setTitle(CStrings.m_APP_NAME);
            		alert.setHeaderText(CStrings.m_APP_NAME_QUESTION_EXIT);
            		alert.setContentText(CStrings.m_APP_NAME_CHOOSE);
            		ButtonType buttonTypeYes = new ButtonType(CStrings.m_APP_NAME_CHOOSE_YES);
            		ButtonType buttonTypeNo = new ButtonType(CStrings.m_APP_NAME_CHOOSE_NO);
            		
            		alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            		
            		java.util.Optional<ButtonType> result = alert.showAndWait();
            		if (result.get() == buttonTypeYes)
            		{
            			// ����� ��� ��������� � �������!!!
            			Platform.exit();
                		System.out.println("Exit!!!");
                		System.exit(0);
            		} 
            		else
            		{
            			t.consume();// ������ ������ �� ���������!!!
            		}
                }
            });
            stage.show();
            fxMessageWait.setVisible(true);
            
            /*InitFireBase();
            MyEventListnerFireUsers();*/
            MyEventListnerFireMessage();
    	}

    }
    // �������� � �����������!!! - btnRefreshMap
    
	public void reload() throws IOException
    {
		
		
    	if(!InitFireBase())
    	{
    		System.out.println("InitFireBase() - ������ �������������!!!");
    	}
    	else
    	{
    		fxMessageWait.setVisible(false);
    		btnRefreshAllMarkers();
            MyEventListnerFireUsers();
        	root = null;
        	//stage = st;
        	try 
        	{
        		m_Loader = new FXMLLoader(getClass().getResource(m_PathMainFxml));
        		root = m_Loader.load();
        		
        		/*m_LoaderCell = new FXMLLoader(getClass().getResource(m_PathFXListCellFxml));
        		m_LoaderCell.setController(this);
        		m_LoaderCell.load();*/
        		
        		
        		mymsg = (TextArea)m_Loader.getNamespace().get("mymsg");
        		//fxvBoxUsersAll = (VBox)loader.getNamespace().get("fxvBoxUsersAll");
        		fxListView = (ListView<CUser>)m_Loader.getNamespace().get("fxListView");
        		fxMessageWait = (AnchorPane)m_Loader.getNamespace().get("fxMessageWait");
        		fxLbMessage = (Label)m_Loader.getNamespace().get("fxLbMessage");
        		btnRestartMod = (Button)m_Loader.getNamespace().get("btnRestartMod");
    		} 
        	catch (Exception ex) 
        	{
        		System.out.println(ex.getLocalizedMessage());
    			ex.getStackTrace();
    		}
            
    ///////////////////////////////////////////////////////////////////////////////////////////        
            scene = new Scene(root);
            stage.setTitle(CStrings.m_APP_NAME);
            stage.setScene(scene);

            stage.setOnCloseRequest(new EventHandler<WindowEvent>()
            {
                @Override
                public void handle(WindowEvent t)
                {
                	Alert alert = new Alert(AlertType.CONFIRMATION);
            		alert.setTitle(CStrings.m_APP_NAME);
            		alert.setHeaderText(CStrings.m_APP_NAME_QUESTION_EXIT);
            		alert.setContentText(CStrings.m_APP_NAME_CHOOSE);
            		ButtonType buttonTypeYes = new ButtonType(CStrings.m_APP_NAME_CHOOSE_YES);
            		ButtonType buttonTypeNo = new ButtonType(CStrings.m_APP_NAME_CHOOSE_NO);
            		
            		alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            		
            		java.util.Optional<ButtonType> result = alert.showAndWait();
            		if (result.get() == buttonTypeYes)
            		{
            			// ����� ��� ��������� � �������!!!
            			Platform.exit();
                		System.out.println("Exit!!!");
                		System.exit(0);
            		} 
            		else
            		{
            			t.consume();// ������ ������ �� ���������!!!
            		}
                }
            });
            stage.show();
            fxMessageWait.setVisible(true);
            
            /*InitFireBase();
            MyEventListnerFireUsers();*/
            MyEventListnerFireMessage();
    	}
		
		/*m_Loader = new FXMLLoader(getClass().getResource(m_PathMainFxml));
    	root = m_Loader.load();
    	
    	mymsg = (TextArea)m_Loader.getNamespace().get("mymsg");
    	//fxvBoxUsersAll = (VBox)loader.getNamespace().get("fxvBoxUsersAll");
    	
    	fxListView = (ListView<CUser>)m_Loader.getNamespace().get("fxListView");

    	
        scene = new Scene(root);
        stage.setTitle("GUI");
        stage.setTitle(CStrings.m_APP_NAME);
        //stage.getIcons().add(icone);
        stage.setScene(scene);
        stage.show();
        fxMessageWait.setVisible(false);
        InitFireBase();
        MyEventListnerFireUsers();
        MyEventListnerFireMessage();
        //fxMessageWait.setVisible(false);
*/    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
    
    @FXML
    private boolean InitFireBase()
    {
    	
    	boolean bRet = true;
    	try 
    	{
    		///////////////////////////////////////////////////////////////////////////////////
    		// Enable Storage
    	   /* Storage storage = StorageOptions.newBuilder()
    	      .authCredentials(AuthCredentials.createForJson(new FileInputStream("/555.json"))
    	      .build()
    	      .service();*/
    		
    		
    		//FileInputStream serviceAccount2 = new FileInputStream("path/to/serviceAccountKey.json");

    		
    		///////////////////////////////////////////////////////////////////////////////////
    		
    		//fxMessageWait.setVisible(true);
    		InputStream serviceAccount = this.getClass().getResourceAsStream("/555.json");
			 
			if(serviceAccount == null)
			{
				System.out.println("Fucking!!!");
			}

			FirebaseOptions options = new FirebaseOptions.Builder()
			  .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
			  .setDatabaseUrl("https://mygpsone-kusto1.firebaseio.com/")
			  .build();
			
			

			
			// Authenticate using a service account
			/*Storage storage = StorageOptions.Builder().
			
			    .authCredentials(AuthCredentials.createForJson(new FileInputStream("/path/to/my/key.json"))
			    .build()
			    .service();*/
			
			/*FileInputStream serviceAccount2 = new FileInputStream("google-services.json");

			FirebaseOptions options2 = new FirebaseOptions.Builder()
			    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
			    .setStorageBucket("<BUCKET_NAME>.appspot.com")
			    .build();
			FirebaseApp.initializeApp(options);

			Bucket bucket = StorageClient.getInstance().bucket();*/
			
			
			
			/*storage = StorageOptions.Builder
		    	      .authCredentials(AuthCredentials.createForJson(new FileInputStream("/555.json"))
		    	      .build()
		    	      .service());*/
			
			if(defaultApp == null)
			{
				defaultApp = FirebaseApp.initializeApp(options);
				System.out.println(defaultApp.getName());
				
				FirebaseAuth.getInstance(defaultApp);
				FirebaseDatabase.getInstance(defaultApp);

				FirebaseAuth.getInstance();
				FirebaseDatabase.getInstance();
				
				/*
				FirebaseOptions options2 = new FirebaseOptions.Builder().setCredential(FirebaseCredential)(GoogleCredentials.fromStream(serviceAccount))
		    		    .setStorageBucket("<BUCKET_NAME>.appspot.com")
		    		    .build();
		    		FirebaseApp.initializeApp(options);

		    		Bucket bucket = StorageClient.getInstance().bucket();*/
				//defaultApp.
				System.out.println("START FirebaseStorage>>>");
				//Storage storage = StorageOptions.getDefaultInstance().getService();
				
				
				BigQuery bigquery = BigQueryOptions.newBuilder().setProjectId("mygpsone-kusto1")
			            .setCredentials(
			                    ServiceAccountCredentials.fromStream(new FileInputStream("c:\\my_projects\\eclipse_projects\\MyGPSx\\src\\555.json"))
			            ).build().getService();
				Datastore datastore = DatastoreOptions.newBuilder().setProjectId("mygpsone-kusto1")
			            .setCredentials(
			                    ServiceAccountCredentials.fromStream(new FileInputStream("c:\\my_projects\\eclipse_projects\\MyGPSx\\src\\555.json"))
			            ).build().getService();
				
				Storage storage = StorageOptions.newBuilder().setProjectId("mygpsone-kusto1")
						.setCredentials(
						ServiceAccountCredentials.fromStream(new FileInputStream("c:\\my_projects\\eclipse_projects\\MyGPSx\\src\\555.json"))
						).build().getService();
				
				
				System.out.println("datastore.toString() == " + datastore.toString());
				//bigquery.c
				
				System.out.println("StorageOptions.getDefaultInstance().getApplicationName() = " 
				+ StorageOptions.getDefaultInstance().getApplicationName());
				
				System.out.println("StorageOptions.getDefaultInstance().getHost() = " 
						+ StorageOptions.getDefaultInstance().getHost());
				
				System.out.println("StorageOptions.getDefaultInstance().getLibraryVersion() = " 
						+ StorageOptions.getDefaultInstance().getLibraryVersion());
				
				//System.out.println("StorageOptions.getDefaultInstance().getProjectId() = " 
				//		+ StorageOptions.getDefaultInstance().getProjectId());
				//Bucket bucket = storage.create(BucketInfo.of("777555"));
				
				
				// Include a prefix of bucket-name to reduce search space.
				// For more information read https://cloud.google.com/storage/docs/json_api/v1/buckets/list\
				try {
					com.google.api.gax.paging.Page<Bucket> buckets =
						    storage.list(BucketListOption.pageSize(100), BucketListOption.prefix("/"));
						for (Bucket bucket : buckets.iterateAll()) {
							System.out.println("StorageOptions.getDefaultInstance().getLibraryVersion() = ");
							System.out.println(storage.getServiceAccount("mygpsone-kusto1"));
						}
				} catch (Exception e) {
					//System.out.println(e.getLocalizedMessage());
					//System.out.println("7777777777777777777777");
				}
				
				ServiceAccount saccount = storage.getServiceAccount("mygpsone-kusto1");
				System.out.println(saccount.getEmail());
				
				/*Bucket bucket =
					    storage.create(
					        BucketInfo.newBuilder("TestBucket")
					            // See here for possible values: http://g.co/cloud/storage/docs/storage-classes
					            .setStorageClass(StorageClass.COLDLINE)
					            // Possible values: http://g.co/cloud/storage/docs/bucket-locations#location-mr
					            .setLocation("asia")
					            .build());*/
				

				BlobId blobId = BlobId.of("mygpsone-kusto1.appspot.com", "uploads/test");
				BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
				com.google.cloud.storage.Blob blob = storage.create(blobInfo, "Hello, Cloud Storage!Hello, Cloud Storage!".getBytes());
				
				
				File f1 = new File("k:\\jjjpppggg.jpg");
				InputStream targetStream = new FileInputStream(f1);
				
				Bucket bucket=storage.get(("mygpsone-kusto1.appspot.com"));
				//BlobId blobId2 = BlobId.of("mygpsone-kusto1.appspot.com", "uploads/test");
				//BlobInfo blobInfo2 = BlobInfo.newBuilder(blobId2).setContentType("application/pdf").build();
//				com.google.cloud.storage.Blob blob2 = bucket.create("uploads/jjjpppggg.jpg", targetStream,"application/jpg");
				com.google.cloud.storage.Blob blob2 = bucket.create("uploads/jjjpppggg.jpg", targetStream,"image/jpg");
				
				
				//storageReference = storage.getIamPolicy(bucket, options)
				//StorageOptions.getDefaultInstance().getApplicationName()
				//System.out.println(StorageOptions.getDefaultInstance().getApplicationName());
				/*try {
					System.out.println("START FirebaseStorage>>>");
					firebasestorage = FirebaseStorage.getInstance("gs://mygpsone-kusto1.appspot.com");
	    	       // storageReference = firebasestorage.getReference();
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}*/
					
			}
			else
			{
				System.out.println("������ �������� defaultApp.getName() !!!");
				bRet = false;
			}
		} 
    	catch (Exception ex) 
    	{
    		 System.out.println(ex.getMessage() + " - 7777777777");
    		 bRet = false;
		}
    	return bRet;
    }
    

    public static void MyEventListnerFireUsers()
    {
    	
		//EventQueue.invokeLater(new Runnable()
    	//Platform.setImplicitExit(false);
    	//Platform.runLater(new Runnable()
		//{
			//public void run() 
			//{
				try
				{
					//Platform.setImplicitExit(false);
					 mDatabaseRefUsers = FirebaseDatabase.getInstance().getReference().child("users");
					 mDatabaseRefUsers.addValueEventListener(new ValueEventListener()
					 {
						@Override
						public void onDataChange(DataSnapshot arg0)
						{
							try
							{
								// �������� , ��� �������, ����� ����� ������!!!
					            Iterable<DataSnapshot> contactChildren = arg0.getChildren();
	
					            m_alUsersAll = new ArrayList<CUser>();
				            	m_lvAllUsers = new ListView<CUser>();
				            	
					            for (DataSnapshot Users : contactChildren)
				                {
					            	CUser user = Users.getValue(CUser.class);
		                        	System.out.println( "CUser user = " + user.getMyNameShip());
		                        	m_alUsersAll.add(user);// ��������� ������!!!
			                	}
					            m_ObservableListUsers = FXCollections.observableArrayList (m_alUsersAll);
					            System.out.println( "m_ObservableListUsers.size() = " + m_ObservableListUsers.size());
					            
								if(fxListView == null)
								{
									fxListView = new ListView<CUser>();
								}
					            
					            System.out.println( "fxListView.getItems().size() = " + fxListView.getItems().size());
					            try 
					            {
					            	Platform.runLater(
			            			  () -> {
			            				 
			            				 /* try 
			            				  {
			            					  m_LoaderCell = new FXMLLoader(getClass().getResource(m_PathFXListCellFxml));
			            			    		m_LoaderCell.setController(this);
			            			    		m_LoaderCell.load();
			            				  } 
			            				  catch (Exception e)
			            				  {
			            					  System.out.println( e.getMessage() );
										  }*/
			            				  
			            				  //fxvBoxUsersAll.getChildren().addAll(fxListView);
			            				  fxListView.setItems(m_ObservableListUsers);
			            				 fxListView.setPrefSize(200, 500);
			            				//fxListView.setMaxSize(200, 1000);
			            				//  fxListView.setMaxHeight(1000);
			            				 //fxListView.setCellFactory(new CUserCellFactory());
			            				 fxListView.setCellFactory(new Callback<ListView<CUser>, ListCell<CUser>>() 
			            				 {
											
											@Override
											public ListCell<CUser> call(ListView<CUser> param) 
											{
												//fxListView.setPrefSize(200, 150);
												/*@Override
												public void updateItem(CUser item, boolean empty) 
												{
													super.updateItem(item, empty);
												};*/
												System.out.println("return new CUserCell();");
												return new CUserCell();
											}
										});
			            				 
			            				 //fxListView.setPrefSize(200, 150);
			            				  fxListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			            		    			@Override
			            		    			public void handle(MouseEvent click)
			            		    			{
			            		    				Marker MyMarker = null;
			            		    				InfoWindow MyInfoWindow = null;
			            		    				MarkerOptions markerOptions;
			            		    				
			            		    				 
			            		    				
			            		    		        if (click.getClickCount() == 2) 
			            		    		        {
			            		    		          CUser us = fxListView.getSelectionModel().getSelectedItem();
			            		    		          System.out.println("tempUser = " + us.getMyNameShip());
			            		    		          double dLat = Double.parseDouble(us.MyLatitude);
			            		    		          double dLong = Double.parseDouble(us.MyLongitude);
			            		    		          LatLong ll = new LatLong(dLat,dLong); 
			            		    		          CMainController.map.setCenter(ll);
			            		    		          
			            		    		          InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
			            		    		          infoWindowOptions.content("<h4>��� �����:</h4>" + 
			          										"<u style=\"color: blue;\"><h4>" + us.getMyNameShip() + "</h4></u>" +
			          										"<h4>�������:</h4>" + 
			          										"<u style=\"color: blue;\"><h4>" + us.getMyDirectorShip() + "</h4></u>" +
			          										"<h4>�������� �����:</h4>" + 
			          										"<u style=\"color: blue;\"><h4>" + us.MyShortDescriptionShip + "</h4></u>");
			            		    		          MyInfoWindow = new InfoWindow(infoWindowOptions);
			            		    		          
			            		    		          markerOptions = new MarkerOptions();

														markerOptions.position(ll);
														MyMarker = new Marker(markerOptions);
														CMainController.map.addMarker( MyMarker );
														CMainController.markerMap.put(MyMarker, false);
														MyInfoWindow.open(CMainController.map, MyMarker);
													    CMainController.markerMap.put(MyMarker, true);
			            		    		        }
			            		    		    }
			            					});
			            				  if(CMainController.map == null)
			            		            {
			            		            	System.out.println("������ ������������� map!!!");
			            		            	CLPSMain.fxLbMessage.setText("������ �������������\n�����!\n����������� �����,\n������� ������:\n\"�������� �����\"");
			            		            	CLPSMain.btnRestartMod.setVisible(true);
			            		            }
			            		            else
			            		            {
			            		            	CLPSMain.fxMessageWait.setVisible(false);
			            		            }
			            			  }
			            			);
					            	
								}
					            catch (Exception ex)
					            {
					            	ex.getMessage();
								}
							} 
							catch (Exception ex) 
							{
								ex.getMessage();
							}
						}
						
						@Override
						public void onCancelled(DatabaseError arg0)
						{
							System.out.println(arg0.getMessage());
							
						}
					});
				} 
				catch (Exception ex) 
				{
					ex.getMessage();
				}
			//}
		//});
    }
    
    
    @FXML
    private void MyEventListnerFireMessage()
    {
		//EventQueue.invokeLater(new Runnable()
    	//Platform.runLater(new Runnable()
		//{
		//	public void run() 
			//{
				try
				{
					mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("messages");

					 System.out.println(mDatabaseRef.toString());
					 System.out.println( "-----------------------------------" );
					 mDatabaseRef.addValueEventListener(new ValueEventListener()
					 {
						@Override
						public void onDataChange(DataSnapshot arg0)
						{
							try
							{
								// �������� , ��� �������, ����� ����� ������!!!
								DataSnapshot messagesSnapshot = arg0;
					            Iterable<DataSnapshot> messageChildren = messagesSnapshot.getChildren();
					            System.out.println("arg0 = " + arg0.getChildrenCount());
					            for (DataSnapshot message : messageChildren)
				                {
					            	System.out.println( "message!!!" );
				                    CMessages MyMsg = message.getValue(CMessages.class);
				                    //mymsg = (TextArea)fxmlLoader2.getNamespace().get("mymsg");
				                	if(mymsg != null)
				                	{
				                		System.out.println( ">>>>>>>>>User MyMsg msg_title : " + MyMsg.msg_title );
					                	System.out.println( ">>>>>>>>>User MyMsg msg_body : " + MyMsg.msg_body );
				                		mymsg.appendText("\n");
				                		mymsg.appendText(MyMsg.msg_body);
				                	}
	
				                	System.out.println( ">>>>>>>>>User MyMsg msg_time : " + MyMsg.msg_time );
				                	System.out.println( ">>>>>>>>>User MyMsg msg_status : " + MyMsg.msg_status );
				                }
							} 
							catch (Exception ex) 
							{
								ex.getMessage();
							}
						}
						
						@Override
						public void onCancelled(DatabaseError arg0)
						{
							System.out.println( arg0.getMessage() );
							
						}
					});
				} 
				catch (Exception ex) 
				{
					ex.getMessage();
				}
		//	}
		//});

    }
    
    @FXML
    private synchronized void btnRefreshAllMarkers(/*ActionEvent event*/) 
    {
    	try
    	{
        	mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        	// �������� ��������� ������� � ��������� �����!!!    	
            mDatabase.addValueEventListener(new ValueEventListener() 
            {
            	@Override
    			public void onDataChange(DataSnapshot arg0)
    			{
            		try 
            		{
            			 System.out.println("mDatabase.addValueEventListener");
            			 m_localAllMarkersUsersTempMain = new ArrayList<>();
        				 //MarkerOptions mo = null;
        			    // LatLong ll = null;
        			     CUser tempTransport = null;
        				DataSnapshot usersSnapshot = arg0;
        				//System.out.println("arg0 = " + arg0.getChildrenCount());
                        Iterable<DataSnapshot> contactChildren = usersSnapshot.getChildren();
                        
                        //System.out.println("usersSnapshot.getChildrenCount() = " + usersSnapshot.getChildrenCount());
                     // ����� ���������� ��� ����������, ���� �� phoneID ����������, � �� ������
                        // ���������� �������!!! ��� �� ���))))
                        for (DataSnapshot arg : contactChildren)
                        {
                        	System.out.println( "----------������ �������!!!-------------" );
                        	System.out.println(" for (DataSnapshot arg : contactChildren)");
                        	CUser user = arg.getValue(CUser.class);
                        	System.out.println( ">>>>>>>>>user.phoneID =  " + user.phoneID + "<<<<<<<<<<<<");
                        	Double.parseDouble(user.MyLatitude);
                            Double.parseDouble(user.MyLongitude);
                            System.out.println( "user.MyLatitude = " + user.MyLatitude );
                            System.out.println( "user.MyLongitude = " + user.MyLongitude );
                            
                            tempTransport = new CUser(user.phoneID, user.MyLatitude, user.MyLongitude, "",
                            		user.getMyNameShip(), user.getMyDirectorShip(), user.MyShortDescriptionShip);
                            m_localAllMarkersUsersTempMain.add(tempTransport);
                            System.out.println( "----------����� �������!!!-------------" );
                            //break;
                        }
    				}
            		catch (Exception ex)
            		{
            			ex.getMessage();
    				}
    				
                 
    			}
    			@Override
    			public void onCancelled(DatabaseError arg0) {
    				System.out.println(arg0.getMessage() );
    				System.out.println( "Firebase - �����!!!" );
    			}
    		});
		} 
    	catch (Exception ex) 
    	{
			ex.getMessage();
		}

    }
}
