
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
	 //Firebase - потом обобщим  здесь не оставим!!!
	StorageReference storageReference;
	FirebaseStorage firebasestorage;
	public static Storage MyGoogleStorage;
	
	public static ArrayList<CUser> m_localAllMarkersUsersTempMain = null;
	
	// Это окно добавления кораблей!!!
	@FXML
	public static Parent m_rootAddShip = null;
	@FXML
	public static Stage m_stageAddShip = null;
	
	// This is a window edit, add priority!!!
	@FXML
	public static Parent m_rootPriorityEdit = null;
	@FXML
	public static Stage m_stagePriorityEdit = null;
	
	// This is a window edit, add Status!!!
	@FXML
	public static Parent m_rootStatusEdit = null;
	@FXML
	public static Stage m_stageStatusEdit = null;
	
	// This is a window edit, add Typejob!!!
	@FXML
	public static Parent m_rootTypejobEdit = null;
	@FXML
	public static Stage m_stageTypejobEdit = null;
	
	// This is a window edit, add Attrjob!!!
	@FXML
	public static Parent m_rootAttrjobEdit = null;
	@FXML
	public static Stage m_stageAttrjobEdit = null;
	
	// This is a window edit, add Typedescjob!!!
	@FXML
	public static Parent m_rootTypedescjobEdit = null;
	@FXML
	public static Stage m_stageTypedescjobEdit = null;
	
	// // This is a window edit, add Attrobj!!!
	@FXML
	public static Parent m_rootAttrobjEdit = null;
	@FXML
	public static Stage m_stageAttrobjEdit = null;
	
	// This is a window edit, add Typejob!!!
	@FXML
	public static Parent m_rootTypeobjEdit = null;
	@FXML
	public static Stage m_stageTypeobjEdit = null;
	
	// This is a window edit, add FXListTemplates!!!
	@FXML
	public static Parent m_rootFXListTemplates = null;
	@FXML
	public static Stage m_stageFXListTemplates = null;
	
	// This is a window edit, add FXCreateTemplateJobs!!!
	@FXML
	public static Parent m_rootFXCreateTemplateJobs = null;
	@FXML
	public static Stage m_stageFXCreateTemplateJobs = null;
	
	// This is a window edit, add FXPreviewTemplateJobs!!!
	@FXML
	public static Parent m_rootFXPreviewTemplateJobs = null;
	@FXML
	public static Stage m_stageFXPreviewTemplateJobs = null;

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

	// Самый главный старт!!!!
    @SuppressWarnings("unchecked")
	@Override
    public void start(Stage st) throws Exception
    {
    	if(!InitFireBase())
    	{
    		System.out.println("InitFireBase() - ошибка инициализации!!!");
    	}
    	else
    	{
    		btnRefreshAllMarkers();
            MyEventListnerFireUsers();
        	root = null;
        	stage = st;
        	try 
        	{
        		// Инициализация всех внутренних контролов!!!
        		m_Loader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathMainFxml));
        		root = m_Loader.load();
        		mymsg = (TextArea)m_Loader.getNamespace().get("mymsg");
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
            			// Здесь все подчищаем и выходим!!!
            			Platform.exit();
                		System.out.println("Exit!!!");
                		System.exit(0);
            		} 
            		else
            		{
            			t.consume();// Отмена выхода из программы!!!
            		}
                }
            });
            stage.show();
            fxMessageWait.setVisible(true);
            MyEventListnerFireMessage();
    	}

    }
    // вызываем в контроллере!!! - btnRefreshMap
    
	@SuppressWarnings("unchecked")
	public void reload() throws IOException
    {
    	if(!InitFireBase())
    	{
    		System.out.println("InitFireBase() - ошибка инициализации!!!");
    	}
    	else
    	{
    		fxMessageWait.setVisible(false);
    		btnRefreshAllMarkers();
            MyEventListnerFireUsers();
        	root = null;

        	try 
        	{
        		m_Loader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathMainFxml));
        		root = m_Loader.load();
        		mymsg = (TextArea)m_Loader.getNamespace().get("mymsg");
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
            			// Здесь все подчищаем и выходим!!!
            			Platform.exit();
                		System.out.println("Exit!!!");
                		System.exit(0);
            		} 
            		else
            		{
            			t.consume();// Отмена выхода из программы!!!
            		}
                }
            });
            stage.show();
            fxMessageWait.setVisible(true);
            MyEventListnerFireMessage();
    	}
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
    
    // Здесь инициализируем подключение к базе данных!!!
    @FXML
    private boolean InitFireBase()
    {
    	boolean bRet = true;
    	try 
    	{
    		InputStream serviceAccount = this.getClass().getResourceAsStream("/555.json");
			 
			if(serviceAccount == null)
			{
				System.out.println("Fucking!!!");
			}

			FirebaseOptions options = new FirebaseOptions.Builder()
			  .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
			  .setDatabaseUrl("https://mygpsone-kusto1.firebaseio.com/")
			  .build();

			if(defaultApp == null)
			{
				defaultApp = FirebaseApp.initializeApp(options);
				System.out.println(defaultApp.getName());
				
				FirebaseAuth.getInstance(defaultApp);
				FirebaseDatabase.getInstance(defaultApp);

				FirebaseAuth.getInstance();
				FirebaseDatabase.getInstance();
//////////////////////////Что-то интересное - пока просто написасал)))//////////////////////////////////////////
				System.out.println("START FirebaseStorage>>>");
				/*BigQuery bigquery = BigQueryOptions.newBuilder().setProjectId("mygpsone-kusto1")
			            .setCredentials(
			                    ServiceAccountCredentials.fromStream(new FileInputStream("c:\\my_projects\\eclipse_projects\\MyGPSx\\src\\555.json"))
			            ).build().getService();
				Datastore datastore = DatastoreOptions.newBuilder().setProjectId("mygpsone-kusto1")
			            .setCredentials(
			                    ServiceAccountCredentials.fromStream(new FileInputStream("c:\\my_projects\\eclipse_projects\\MyGPSx\\src\\555.json"))
			            ).build().getService();
				System.out.println("datastore.toString() == " + datastore.toString());*/
				
				/*System.out.println("StorageOptions.getDefaultInstance().getApplicationName() = " 
						+ StorageOptions.getDefaultInstance().getApplicationName());
						
						System.out.println("StorageOptions.getDefaultInstance().getHost() = " 
								+ StorageOptions.getDefaultInstance().getHost());
						
						System.out.println("StorageOptions.getDefaultInstance().getLibraryVersion() = " 
								+ StorageOptions.getDefaultInstance().getLibraryVersion());*/
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				try 
				{
					MyGoogleStorage = StorageOptions.newBuilder().setProjectId("mygpsone-kusto1")
							.setCredentials(
							/*ServiceAccountCredentials.fromStream(new FileInputStream("c:\\my_projects\\eclipse_projects\\MyGPSx\\src\\555.json"))*/
							ServiceAccountCredentials.fromStream(new FileInputStream("src/555.json"))
							).build().getService();
					/*firebaseStorage = StorageOptions.newBuilder().setProjectId("mygpsone-kusto1")
							.setCredentials(
							ServiceAccountCredentials.fromStream(new FileInputStream("c:\\my_projects\\eclipse_projects\\MyGPSx\\src\\555.json"))
							).build().getService();*/
				} 
				catch (Exception ex) 
				{
					System.out.println(ex.getMessage());
					bRet = false;
				}
				
				//ServiceAccount saccount = MyGoogleStorage.getServiceAccount("mygpsone-kusto1");
				//System.out.println(saccount.getEmail());// Так для теста!!!
				//////////////////////////////////////////////////////////////////////////////////////////////////////////////
				// Это типа была проверка записи в базу!!!
				////////////////////////////////////////////////////////////////////////////////////////////////////////////
				/*BlobId blobId = BlobId.of("mygpsone-kusto1.appspot.com", "uploads/test");
				BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
				com.google.cloud.storage.Blob blob = MyGoogleStorage.create(blobInfo, "Hello, Cloud Storage!Hello, Cloud Storage!".getBytes());
				
				
				File f1 = new File("k:\\jjjpppggg.jpg");
				InputStream targetStream = new FileInputStream(f1);
				
				Bucket bucket = MyGoogleStorage.get(("mygpsone-kusto1.appspot.com"));
				com.google.cloud.storage.Blob blob2 = bucket.create("uploads/jjjpppggg.jpg", targetStream,"image/jpg");*/
				//////////////////////////////////////////////////////////////////////////////////////////////////////////////
				// Это типа была проверка записи в базу!!! - конец проверки!!!
				////////////////////////////////////////////////////////////////////////////////////////////////////////////

			}
			else
			{
				System.out.println("ОШИБКА СОЗДАНИЯ defaultApp.getName() !!!");
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
					 mDatabaseRefUsers = FirebaseDatabase.getInstance().getReference().child("users");
					 mDatabaseRefUsers.addValueEventListener(new ValueEventListener()
					 {
						@Override
						public void onDataChange(DataSnapshot arg0)
						{
							try
							{
								// Выбираем , что слушать, какую ветку данных!!!
					            Iterable<DataSnapshot> contactChildren = arg0.getChildren();
	
					            m_alUsersAll = new ArrayList<CUser>();
				            	m_lvAllUsers = new ListView<CUser>();
				            	
					            for (DataSnapshot Users : contactChildren)
				                {
					            	CUser user = Users.getValue(CUser.class);
		                        	System.out.println( "CUser user = " + user.getMyNameShip());
		                        	m_alUsersAll.add(user);// Заполнили массив!!!
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
			            				  fxListView.setItems(m_ObservableListUsers);
			            				 fxListView.setPrefSize(200, 500);
			            				 fxListView.setCellFactory(new Callback<ListView<CUser>, ListCell<CUser>>() 
			            				 {
											
											@Override
											public ListCell<CUser> call(ListView<CUser> param) 
											{
												System.out.println("return new CUserCell();");
												return new CUserCell();
											}
										});
			            				 
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
			            		    		          infoWindowOptions.content("<h4>Имя судна:</h4>" + 
			          										"<u style=\"color: blue;\"><h4>" + us.getMyNameShip() + "</h4></u>" +
			          										"<h4>Капитан:</h4>" + 
			          										"<u style=\"color: blue;\"><h4>" + us.getMyDirectorShip() + "</h4></u>" +
			          										"<h4>Описание судна:</h4>" + 
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
			            		            	System.out.println("Ошибка инициализации map!!!");
			            		            	CLPSMain.fxLbMessage.setText("Ошибка инициализации\nкарты!\nПерегрузите карту,\nнажимте кнопку:\n\"Обновить карту\"");
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
								// Выбираем , что слушать, какую ветку данных!!!
								DataSnapshot messagesSnapshot = arg0;
					            Iterable<DataSnapshot> messageChildren = messagesSnapshot.getChildren();
					            System.out.println("arg0 = " + arg0.getChildrenCount());
					            for (DataSnapshot message : messageChildren)
				                {
					            	System.out.println( "message!!!" );
				                    CMessages MyMsg = message.getValue(CMessages.class);

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
        	// Временно загружаем маркеры и проверяем здесь!!!    	
            mDatabase.addValueEventListener(new ValueEventListener() 
            {
            	@Override
    			public void onDataChange(DataSnapshot arg0)
    			{
            		try 
            		{
            			 System.out.println("mDatabase.addValueEventListener");
            			 m_localAllMarkersUsersTempMain = new ArrayList<>();
        			     CUser tempTransport = null;
        				DataSnapshot usersSnapshot = arg0;
                        Iterable<DataSnapshot> contactChildren = usersSnapshot.getChildren();

                     // Здесь перебераем все устройства, свое по phoneID игнорируем, а по другим
                        // выставляем маркеры!!! Как то так))))
                        for (DataSnapshot arg : contactChildren)
                        {
                        	System.out.println( "----------Начало маркера!!!-------------" );
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
                            System.out.println( "----------Конец маркера!!!-------------" );
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
    				System.out.println( "Firebase - косяк!!!" );
    			}
    		});
		} 
    	catch (Exception ex) 
    	{
			ex.getMessage();
		}

    }
}
