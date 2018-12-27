package mygpsx;

import java.util.*;
import java.text.*;
import java.net.URL;
import java.awt.AWTException;
import java.awt.EventQueue;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

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
/*import com.google.firebase.auth.FirebaseCredential;
import com.google.firebase.auth.FirebaseCredentials;*/
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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
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
	
	public static ArrayList<CStructUser> m_localAllMarkersUsersTempMain = null;
	
	// Это окно добавления кораблей!!!
	@FXML
	public static Parent m_rootAddShip = null;
	@FXML
	public static Stage m_stageAddShip = null;
	
	// Это окно редактирования кораблей!!!
	@FXML
	public static Parent m_rootEditShip = null;
	@FXML
	public static Stage m_stageEditShip = null;
	
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
	
	// This is a window edit, add FXCreateTemplateJobs!!!
	@FXML
	public static Parent m_rootFXEditTemplateJobs = null;
	@FXML
	public static Stage m_stageFXEditTemplateJobs = null;
	
	// This is a window edit, add FXPreviewTemplateJobs!!!
	@FXML
	public static Parent m_rootFXPreviewTemplateJobs = null;
	@FXML
	public static Stage m_stageFXPreviewTemplateJobs = null;
	
	// This is a window edit, add FXPreviewTemplateJobs!!!
	@FXML
	public static Parent m_rootFXSysUserEdit = null;
	@FXML
	public static Stage m_stageFXSysUserEdit = null;

	@FXML
	TextArea mymsg;
	@FXML
	static ListView<CStructUser> m_lvAllUsers;
	 /*@FXML
	 public static AnchorPane fxMessageWait;*/
	 @FXML
	 public static Label fxLbMessage;
	 @FXML
	 public static Button btnRestartMod;
	/*@FXML
	VBox fxvBoxUsersAll;*/
	@FXML
	public static Scene scene = null;// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	@FXML
	public static Stage stage;
	
	public static ObservableList<CStructUser> m_ObservableListUsers;
	@FXML
	static ArrayList<CStructUser> m_alUsersAll = null;

	@FXML
	public static ListView<CStructUser> fxListView;
	/*@FXML
	private Button fxBtnInTabRefreshMap;*/
	@FXML
	Parent root;
	@FXML
	public static FXMLLoader m_Loader;
	//@FXML
	//public static FXMLLoader m_LoaderCell;
	// one icon location is shared between the application tray icon and task bar icon.
    // you could also use multiple icons to allow for clean display of tray icons on hi-dpi devices.
    private static final String iconImageLoc =
            "http://icons.iconarchive.com/icons/scafer31000/bubble-circle-3/16/GameCenter-icon.png";
    
    // a timer allowing the tray icon to provide a periodic notification event.
    private Timer notificationTimer = new Timer();
 // format used to display the current time in a tray icon notification.
    private DateFormat timeFormat = SimpleDateFormat.getTimeInstance();
    public static java.awt.TrayIcon trayIcon;

	@FXML
    public static CTUsersJobsController m_CTUsersJobsController;// Контроллер отвечает за обмен данными с вкладкой "Сотрудники --> Задачи"
	// или Tab #1.
	
	// Самый главный старт!!!!
    @SuppressWarnings("unchecked")
	@Override
    public void start(Stage st) throws Exception
    {
    	
    	//CCONSTANTS_EVENTS_JOB.TEMPLATE_FILLING_OR_EDIT = 1;// Изначально все шаблоны готовы к заполнению!!!
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
        	stage.setY(0);// Прикрепили к верху - для удобства отладки снизу)))
        	// sets up the tray icon (using awt code run on the swing thread).
            javax.swing.SwingUtilities.invokeLater(this::addAppToTray);
        	try 
        	{
        		// Инициализация всех внутренних контролов!!!
        		m_Loader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathMainFxml));
        		root = m_Loader.load();
        		mymsg = (TextArea)m_Loader.getNamespace().get("mymsg");
        		fxListView = (ListView<CStructUser>)m_Loader.getNamespace().get("fxListView");
        		//fxMessageWait = (AnchorPane)m_Loader.getNamespace().get("fxMessageWait");
        		fxLbMessage = (Label)m_Loader.getNamespace().get("fxLbMessage");
        		btnRestartMod = (Button)m_Loader.getNamespace().get("btnRestartMod");
        		//fxBtnInTabRefreshMap = (Button)m_Loader.getNamespace().get("fxBtnInTabRefreshMap");
        		/*fxBtnInTabRefreshMap.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event)
					{

						System.out.println("START - fxBtnInTabRefreshMap.setOnMouseClicked");
						System.out.println("END - fxBtnInTabRefreshMap.setOnMouseClicked");
					}
				});*/
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
            //fxMessageWait.setVisible(true);
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
    		//fxMessageWait.setVisible(false);
    		btnRefreshAllMarkers();
            MyEventListnerFireUsers();
        	root = null;

        	try 
        	{
        		m_Loader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathMainFxml));
        		root = m_Loader.load();
        		mymsg = (TextArea)m_Loader.getNamespace().get("mymsg");
        		fxListView = (ListView<CStructUser>)m_Loader.getNamespace().get("fxListView");
        		//fxMessageWait = (AnchorPane)m_Loader.getNamespace().get("fxMessageWait");
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
           // fxMessageWait.setVisible(true);
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
				    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
				    .setDatabaseUrl("https://mygpsone-kusto1.firebaseio.com/")
				    .build();
			/*FirebaseOptions options = new FirebaseOptions.Builder()
			  .setCredentials(credentials)(Credential.fromCertificate(serviceAccount))
			  .setDatabaseUrl("https://mygpsone-kusto1.firebaseio.com/")
			  .build();*/

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
					ex.printStackTrace();
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
				System.out.println("ОШИБОК СОЗДАНИЯ defaultApp.getName() НЕТ!!!");
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
					 mDatabaseRefUsers = FirebaseDatabase.getInstance().getReference().child(CMAINCONSTANTS.FB_users);
					 mDatabaseRefUsers.addValueEventListener(new ValueEventListener()
					 {
						@Override
						public void onDataChange(DataSnapshot arg0)
						{
							try
							{
								// Выбираем , что слушать, какую ветку данных!!!
					            Iterable<DataSnapshot> contactChildren = arg0.getChildren();
	
					            m_alUsersAll = new ArrayList<CStructUser>();
				            	m_lvAllUsers = new ListView<CStructUser>();
				            	
					            for (DataSnapshot Users : contactChildren)
				                {
					            	CStructUser user = Users.getValue(CStructUser.class);
		                        	System.out.println( "CUser user = " + user.getMyNameShip());
		                        	m_alUsersAll.add(user);// Заполнили массив!!!
			                	}
					            m_ObservableListUsers = FXCollections.observableArrayList (m_alUsersAll);
					            System.out.println( "m_ObservableListUsers.size() = " + m_ObservableListUsers.size());
					            
								if(fxListView == null)
								{
									fxListView = new ListView<CStructUser>();
								}
					            
					            System.out.println( "fxListView.getItems().size() = " + fxListView.getItems().size());
					            try 
					            {
					            	Platform.runLater(
			            			  () -> {
			            				  fxListView.setItems(m_ObservableListUsers);
			            				 fxListView.setPrefSize(200, 500);
			            				 fxListView.setCellFactory(new Callback<ListView<CStructUser>, ListCell<CStructUser>>() 
			            				 {
											
											@Override
											public ListCell<CStructUser> call(ListView<CStructUser> param) 
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
			            		    		        	TabPane tb = (TabPane)CLPSMain.scene.lookup("#fxTabPaneMain");
			            		    		    		javafx.scene.control.SingleSelectionModel<Tab> selectionModel = tb.getSelectionModel();
			            		    		    		selectionModel.select(0);
			            		    		    		
				            		    		          CStructUser us = fxListView.getSelectionModel().getSelectedItem();
				            		    		          System.out.println("tempUser = " + us.getMyNameShip());
				            		    		          double dLat = Double.parseDouble(us.getMyLatitude());
				            		    		          double dLong = Double.parseDouble(us.getMyLongitude());
				            		    		          LatLong ll = new LatLong(dLat,dLong); 
				            		    		          try 
				            		    		          {
				            		    		        	  //CMainController.map = null; // - это для теста ошибки и всплывающего окна в трее!!!
				            		    		        	  CMainController.MyGoogleMap.setCenter(ll);
				            		    		          }
				            		    		          catch (Exception e) 
				            		    		          {
				            		    		        	  e.printStackTrace();
				            		    		        	  trayIcon.displayMessage(
				            		    		        			  	CStrings.m_APP_ERROR,
				            	                                        "Ошибка инициализации карты ->\n перезапустите программу!",
				            	                                        java.awt.TrayIcon.MessageType.INFO
				            	                                );
														  }
				            		    		         
			            		    		          
			            		    		          InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
			            		    		          infoWindowOptions.content("<h4>Имя судна:</h4>" + 
			          										"<u style=\"color: blue;\"><h4>" + us.getMyNameShip() + "</h4></u>" +
			          										"<h4>Капитан:</h4>" + 
			          										"<u style=\"color: blue;\"><h4>" + us.getMyDirectorShip() + "</h4></u>" +
			          										"<h4>Описание судна:</h4>" + 
			          										"<u style=\"color: blue;\"><h4>" + us.getMyShortDescriptionShip() + "</h4></u>");
			            		    		          MyInfoWindow = new InfoWindow(infoWindowOptions);
			            		    		          
			            		    		          markerOptions = new MarkerOptions();

														markerOptions.position(ll);
														MyMarker = new Marker(markerOptions);
														CMainController.MyGoogleMap.addMarker( MyMarker );
														CMainController.markerMap.put(MyMarker, false);
														MyInfoWindow.open(CMainController.MyGoogleMap, MyMarker);
													    CMainController.markerMap.put(MyMarker, true);
			            		    		        }
			            		    		    }
			            					});
			            				    if(CMainController.MyGoogleMap == null)
			            		            {
			            				    	/*CMyToast.makeText(CLPSMain.stage, 
			            				    			"Ошибка инициализации map!", 
			            				    			CMyToast.TOAST_SHORT, CMyToast.TOAST_WARN);*/
			            		            	System.out.println("Ошибка инициализации map!!!");
			            		            	//CLPSMain.fxLbMessage.setText("Ошибка инициализации\nкарты!\nПерегрузите карту,\nнажимте кнопку:\n\"Обновить карту\"");
			            		            	//CLPSMain.btnRestartMod.setVisible(true);
			            		            }
			            		            else
			            		            {
			            		            	//CLPSMain.fxMessageWait.setVisible(false);
			            		            	//CLPSMain.btnRestartMod.setVisible(false);
			            		            }
			            			  }
			            			);
					            	
								}
					            catch (Exception ex)
					            {
					            	ex.getStackTrace();
								}
							} 
							catch (Exception ex) 
							{
								ex.getStackTrace();
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
					ex.getStackTrace();
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

					 //System.out.println(mDatabaseRef.toString());
					// System.out.println( "-----------------------------------" );
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
					            //System.out.println("arg0 = " + arg0.getChildrenCount());
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
    
//    @FXML 
    private void btnRefreshAllMarkers() 
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
						CStructUser tempTransport = null;
        				DataSnapshot usersSnapshot = arg0;
                        Iterable<DataSnapshot> contactChildren = usersSnapshot.getChildren();

                     // Здесь перебераем все устройства, свое по phoneID игнорируем, а по другим
                        // выставляем маркеры!!! Как то так))))
                        for (DataSnapshot arg : contactChildren)
                        {
                        	System.out.println( "----------Начало маркера!!!-------------" );
                        	try 
                        	{
                        		System.out.println(" for (DataSnapshot arg : contactChildren)");
                            	CStructUser user = arg.getValue(CStructUser.class);
                            	System.out.println( ">>>>>>>>>user.phoneID =  " + user.getMyPhoneID() + "<<<<<<<<<<<<");
                            	Double.parseDouble(user.getMyLatitude());
                                Double.parseDouble(user.getMyLongitude());
                                System.out.println( "user.MyLatitude = " + user.getMyLatitude() );
                                System.out.println( "user.MyLongitude = " + user.getMyLongitude() );
                                
                                tempTransport = new CStructUser(user.getMyFreeNameFirst(),
                                								user.getMyPhoneID(),
																user.getMyLatitude(),
																user.getMyLongitude(), 
																"",
																user.getMyNameShip(), 
																user.getMyDirectorShip(), 
																user.getMyShortDescriptionShip(), 
																user.getMyIsUserSelected(),
																user.getMyPass(),
																user.getMySysUserBinding());
                                m_localAllMarkersUsersTempMain.add(tempTransport);
							}
                        	catch (Exception e)
                        	{
								e.getMessage();
								e.printStackTrace();
							}

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
    /**
     * Shows the application stage and ensures that it is brought ot the front of all stages.
     */
    private void showStage() {
        if (stage != null) {
            stage.show();
            stage.toFront();
        }
    }
    /**
     * Sets up a system tray icon for the application.
     */
    private void addAppToTray()
    {
        try 
        {
            // ensure awt toolkit is initialized.
            java.awt.Toolkit.getDefaultToolkit();

            // app requires system tray support, just exit if there is no support.
            if (!java.awt.SystemTray.isSupported()) {
                System.out.println("No system tray support, application exiting.");
                Platform.exit();
            }

            // set up a system tray icon.
            // Заодно проверим и подключение к интернету прямо на иконке!!!
            java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
            URL imageLoc = new URL(iconImageLoc);
            java.awt.Image image = ImageIO.read(imageLoc);
            trayIcon = new java.awt.TrayIcon(image);

            // if the user double-clicks on the tray icon, show the main app stage.
            trayIcon.addActionListener(event -> Platform.runLater(this::showStage));

            // if the user selects the default menu item (which includes the app name), 
            // show the main app stage.
            java.awt.MenuItem openItem = new java.awt.MenuItem("hello, world");
            openItem.addActionListener(event -> Platform.runLater(this::showStage));

            // the convention for tray icons seems to be to set the default icon for opening
            // the application stage in a bold font.
            java.awt.Font defaultFont = java.awt.Font.decode(null);
            java.awt.Font boldFont = defaultFont.deriveFont(java.awt.Font.BOLD);
            openItem.setFont(boldFont);

            // to really exit the application, the user must go to the system tray icon
            // and select the exit option, this will shutdown JavaFX and remove the
            // tray icon (removing the tray icon will also shut down AWT).
            java.awt.MenuItem exitItem = new java.awt.MenuItem("Exit");
            exitItem.addActionListener(event -> {
            	
            	int dialogResult = JOptionPane.showConfirmDialog(null, CStrings.m_APP_NAME_QUESTION_EXIT, CStrings.m_APP_NAME, JOptionPane.YES_NO_OPTION);

            	if (dialogResult == JOptionPane.YES_OPTION) {
            		// Здесь все подчищаем и выходим!!!
        			notificationTimer.cancel();
        			Platform.exit();
        			tray.remove(trayIcon);
            		System.out.println("Выход->");
            		System.exit(0);
            	}
            });

            // setup the popup menu for the application.
            final java.awt.PopupMenu popup = new java.awt.PopupMenu();
            popup.add(openItem);
            popup.addSeparator();
            popup.add(exitItem);
            trayIcon.setPopupMenu(popup);

            // create a timer which periodically displays a notification message.
           /* notificationTimer.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            javax.swing.SwingUtilities.invokeLater(() ->
                                trayIcon.displayMessage(
                                        CStrings.m_APP_NAME,
                                        "Я - время): " + timeFormat.format(new Date()),
                                        java.awt.TrayIcon.MessageType.INFO
                                )
                            );
                        }
                    },
                    5_000,
                    60_000
            );*/
           
            // add the application tray icon to the system tray.
            tray.add(trayIcon);
            trayIcon.displayMessage(
                    CStrings.m_APP_NAME,
                    "Я - время): " + timeFormat.format(new Date()),
                    java.awt.TrayIcon.MessageType.INFO
            );
        } 
        catch (java.awt.AWTException | IOException e) 
        {
        	InputStream is = null;
			try 
			{
				is = new BufferedInputStream(new FileInputStream("c:\\my_projects\\eclipse_projects\\MyGPSx\\src\\MyIconErrorConn.png"));
				//is = new BufferedInputStream(new FileInputStream("/src/MyIconErrorConn.png"));
				java.awt.Image image = ImageIO.read(is);
	        	trayIcon = new java.awt.TrayIcon(image);
	        	java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
	        	tray.add(trayIcon);
	        	trayIcon.displayMessage(
	                    CStrings.m_APP_NAME,
	                    "Проверьте подключение!",
	                    java.awt.TrayIcon.MessageType.ERROR
	            );
			} 
			catch (FileNotFoundException e1) 
			{
				e1.printStackTrace();
			}
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
			catch (AWTException e1)
			{
				e1.printStackTrace();
			}
            System.out.println("Unable to init system tray");
            e.printStackTrace();
        }
    }
}
