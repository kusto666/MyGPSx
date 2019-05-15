package mygpsx;

import java.util.*;
import java.text.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
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
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import com.google.api.Page;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.auth.http.HttpTransportFactory;
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
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.database.ChildEventListener;
/*import com.google.firebase.auth.FirebaseCredential;
import com.google.firebase.auth.FirebaseCredentials;*/
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

import javafx.application.Application;
import javafx.application.HostServices;
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
	private static CLPSMain mInstance;
	
	// Пока для тестов - надо реализовать!!!
	private static final Logger LOGGER = Logger.getLogger(CLPSMain.class.getName());
	
	public static FirebaseApp defaultApp;
	
	public static DatabaseReference mDatabase;
	public static DatabaseReference mDatabaseRef;
	public static DatabaseReference mDatabaseRefSingle;
	public static Query mQueryRefSingle;// Здесь слушаем по одному разу когда перещелкиваем судно!!!!
	public static DatabaseReference mDatabaseRefUsers;
	public static DatabaseReference mDatabaseRefUsersMsg;
	public static DatabaseReference mDatabaseRefUsersMsgSending;
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


	//@FXML
	//private TextArea mymsg;
	@FXML
	static ListView<CStructUser> m_lvAllUsers;
	@FXML
	static ListView<CStructUser> m_lvAllUsersMsg;
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
	
	// Это для отображения кораблей(судов) в левой колонке - "Флот(Осн. объекты)" ///////////////////////////
	public static ObservableList<CStructUser> m_ObservableListUsers;
	@FXML
	static ArrayList<CStructUser> m_alUsersAll = null;
	@FXML
	public static ListView<CStructUser> fxListView;
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public static HostServices m_myHostServicesLinks;// Сервис для работы с сылками для их открытия документов в браузере например...
	
	// Верняя и нижния панели AnchorPane для оображения сообщений:
	@FXML
	public static AnchorPane fxAPanMsgTop = null;
	@FXML
	public static AnchorPane fxAPanMsgDown = null;
	
	// Здесь будем отображать пользователей в переписке!!!
	public static ObservableList<CStructUser> m_ObservableListUsersMsg;
	@FXML
	static ArrayList<CStructUser> m_alUsersAllMsg = null;
	@FXML
	public static ListView<CStructUser> fxListUsersMsg;
	
	// Здесь будем заполнять и отображать сами сообщения в fxListUserViewOfMsg
	public static ObservableList<CMessages> m_ObservableListUsersMsgSending;
	@FXML
	static ArrayList<CMessages> m_alUsersAllMsgSending = null;
	@FXML
	public static ListView<CMessages> fxListUserViewOfMsg;// Это список сообщений от пользователя или(или все вместе!!!)
	
	
	/*@FXML
	private Button fxBtnInTabRefreshMap;*/
	@FXML
	Parent root;
	@FXML
	private FXMLLoader m_Loader;

	// Иконки для информирования удачного или неудачного соединения с инетом!!!
    private static final String MyIconSuccessConn = "src/MyIconSuccessConn.png";
    private static final String MyIconErrorConn = "src/MyIconErrorConn.png";
    // Это для показа нового сообщения!!!
    private static final String MyIconMyNewMsg = "src/MyNewMsg.png";
    // Строка для проверки инета, проверяем в месте создания MyIconSuccessConn!!!
    private static final String MyIsConnectionInet = "http://www.google.com";
    
    // a timer allowing the tray icon to provide a periodic notification event.
    private Timer notificationTimer = new Timer();
 // format used to display the current time in a tray icon notification.
    public static  DateFormat m_MyTimeFormat = SimpleDateFormat.getTimeInstance();
    public static java.awt.TrayIcon m_MyTrayIcon;
    public static java.awt.TrayIcon m_MyTrayIconMyNewMsg;
    public static java.awt.SystemTray m_MytrayInfoDialog;

	@FXML
    public static CTUsersJobsController m_CTUsersJobsController;// Контроллер отвечает за обмен данными с вкладкой "Сотрудники --> Задачи"
	
	@SuppressWarnings("unchecked")
	public void INIT_ALL_CONTROLS() throws IOException
	{
		// Инициализация всех внутренних контролов!!!
		m_Loader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathMainFxml));
		root = m_Loader.load();
		
		fxAPanMsgTop = (AnchorPane)m_Loader.getNamespace().get("fxAPanMsgTop");
		fxAPanMsgDown = (AnchorPane)m_Loader.getNamespace().get("fxAPanMsgDown");
		
		//CMainController.mymsg = (TextArea)m_Loader.getNamespace().get("mymsg");
		CMainController.fxLbSelectedUser = (Label)m_Loader.getNamespace().get("fxLbSelectedUser");
		fxListView = (ListView<CStructUser>)m_Loader.getNamespace().get("fxListView");
		fxListUsersMsg = (ListView<CStructUser>)m_Loader.getNamespace().get("fxListUsersMsg");
		fxListUserViewOfMsg = (ListView<CMessages>)m_Loader.getNamespace().get("fxListUserViewOfMsg");
		//fxMessageWait = (AnchorPane)m_Loader.getNamespace().get("fxMessageWait");
		fxLbMessage = (Label)m_Loader.getNamespace().get("fxLbMessage");
		btnRestartMod = (Button)m_Loader.getNamespace().get("btnRestartMod");
		
		CMainController.fxTxtArLogs = (TextArea)m_Loader.getNamespace().get("fxTxtArLogs");
		CMainController.fxTxtArLogs.setText("ЛОГИРОВАНИЕ В РАЗРАБОТКАЕ...");
		
		m_myHostServicesLinks = CLPSMain.getInstance().getHostServices();
		
		
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

	// или Tab #1.
	
	// Самый главный старт!!!!
    @SuppressWarnings("unchecked")
	@Override
    public void start(Stage st) throws Exception
    {/*
    	System.setProperty("https.proxyHost","192.168.32.9");
         System.setProperty("https.proxyPort","3128");
         System.setProperty("https.proxyUserName","DKustov");
         System.setProperty("https.proxyPassword","761set31Potr");
         System.setProperty("https.proxySet","true");*/
    	
    	/*System.setProperty("java.net.useSystemProxies","true");
    	System.setProperty("https.proxyHost", "https://mygpsone-kusto1.firebaseio.com");
    	System.setProperty("https.proxyPort", "3128");*/
    	
    	/*System.setProperty("java.net.useSystemProxies","true");
    	System.setProperty("https.proxyHost", "192.168.32.9");
    	System.setProperty("https.proxyPort", "3128");*/
         
    	CDateTime MyTestTime = new CDateTime();
    	System.out.println("GetCurrLongTime = " + MyTestTime.GetCurrLongTime());
    	
    	//MyTestTime.GetPrintTime(MyTestTime.GetCurrLongTime());
    	System.out.println("GetPrintTime = " + MyTestTime.GetPrintTime(MyTestTime.GetCurrLongTime()));
    	
    	// Для тестов - надо реализовать!!!!!!!!!!!!!!!!
    	 Handler consoleHandler = null;
         Handler fileHandler  = null;
         try
         {
             //Creating consoleHandler and fileHandler
             consoleHandler = new ConsoleHandler();
             fileHandler  = new FileHandler("./mygpsx_log.log");
              
             //Assigning handlers to LOGGER object
             LOGGER.addHandler(consoleHandler);
             LOGGER.addHandler(fileHandler);
              
             //Setting levels to handlers and LOGGER
             consoleHandler.setLevel(Level.ALL);
             fileHandler.setLevel(Level.ALL);
             LOGGER.setLevel(Level.ALL);
              
             LOGGER.config("Configuration done.");
              
             //Console handler removed
             LOGGER.removeHandler(consoleHandler);
              
             LOGGER.log(Level.FINE, "Finer logged");
         }
         catch(IOException exception)
         {
             LOGGER.log(Level.SEVERE, "Error occur in FileHandler.", exception);
         }
          
         LOGGER.finer("Finest example on LOGGER handler completed.");
    	//LOGGER.info("Logger Name: "+LOGGER.getName());
        //LOGGER.warning("Can cause ArrayIndexOutOfBoundsException");
         
        //////////////////////////////////////////////////////////////////////////////////
        // Подключение к Github для проверки обновлений!!!
        //////////////////////////////////////////////////////////////////////////////////         
        if(ConnGitHubUpdate())
        {
        	System.out.println("ConnGitHubUpdate() - УСПЕШНОЕ ПОДКЛЮЧЕНИЯ к GitHub!!!");
        }
        else
        {
        	System.out.println("ConnGitHubUpdate() - ошибка подключения к GitHub!!!");
        }
    	//CCONSTANTS_EVENTS_JOB.TEMPLATE_FILLING_OR_EDIT = 1;// Изначально все шаблоны готовы к заполнению!!!
    	if(!InitFireBase())
    	{
    		System.out.println("InitFireBase() - ошибка инициализации!!!");
    		
    	}
    	else
    	{
    		btnRefreshAllMarkers();
            MyEventListnerFireUsers();
            MyLoadAndListenUserMsg();
        	root = null;
        	stage = st;
        	stage.setY(0);// Прикрепили к верху - для удобства отладки снизу)))
        	// sets up the tray icon (using awt code run on the swing thread).
            javax.swing.SwingUtilities.invokeLater(this::addAppToTray);
        	try 
        	{
        		INIT_ALL_CONTROLS();
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
            MyLoadAndListenUserMsg();
        	root = null;

        	try 
        	{
        		INIT_ALL_CONTROLS();
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
 	
 	 public CLPSMain() {
 		mInstance = this;
     }
 	
 	public static CLPSMain getInstance() {
        return mInstance;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
       /* System.setProperty("http.proxyHost","192.168.32.9");
        System.setProperty("http.proxyPort","3128");
        System.setProperty("http.proxyUserName","DKustov");
        System.setProperty("http.proxyPassword","761set31Potr");
        System.setProperty("http.proxySet","true");*/
        
        
        
       /* Handler consoleHandler = null;
        Handler fileHandler  = null;
        try{
            //Creating consoleHandler and fileHandler
            consoleHandler = new ConsoleHandler();
            fileHandler  = new FileHandler("./javacodegeeks.log");
             
            //Assigning handlers to LOGGER object
            LOGGER.addHandler(consoleHandler);
            LOGGER.addHandler(fileHandler);
             
            //Setting levels to handlers and LOGGER
            consoleHandler.setLevel(Level.ALL);
            fileHandler.setLevel(Level.ALL);
            LOGGER.setLevel(Level.ALL);
             
            LOGGER.config("Configuration done.");
             
            //Console handler removed
            LOGGER.removeHandler(consoleHandler);
             
            LOGGER.log(Level.FINE, "Finer logged");
        }catch(IOException exception){
            LOGGER.log(Level.SEVERE, "Error occur in FileHandler.", exception);
        }
         
        LOGGER.finer("Finest example on LOGGER handler completed.");*/
    }
    public void FirebaseEventProxy() {
    	InputStream serviceAccount = this.getClass().getResourceAsStream("/555.json");
    	  String firebaseLocation = "https://crackling-torch-392.firebaseio.com";
    	  Map<String, Object> databaseAuthVariableOverride = new HashMap<String, Object>();
    	  // uid and provider will have to match what you have in your firebase security rules
    	  databaseAuthVariableOverride.put("uid", "gae-firebase-event-proxy");
    	  databaseAuthVariableOverride.put("provider", "com.example");
    	  try {
    	    FirebaseOptions options = new FirebaseOptions.Builder()
    	    		 .setCredentials(GoogleCredentials.fromStream(serviceAccount))
    	        .setDatabaseUrl(firebaseLocation)
    	        .setDatabaseAuthVariableOverride(databaseAuthVariableOverride).build();
    	    FirebaseApp.initializeApp(options);
    	  } catch (IOException e) {
    	    throw new RuntimeException(
    	        "Error reading firebase secrets from file: src/main/webapp/gae-firebase-secrets.json: "
    	        + e.getMessage());
    	  }
    	}
    // Подключение к GitHub для проверки обновлений в репозитории!!!
    private boolean ConnGitHubUpdate()
    {
    	boolean bRet = true;
    	/*FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
    	try {
			Repository repository = repositoryBuilder.setGitDir(new File("/path/to/repo/.git"))
			        .readEnvironment() // scan environment GIT_* variables
			        .findGitDir() // scan up the file system tree
			        .setMustExist(true)
			        .build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	return bRet;
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
					

					/*FirebaseOptions options = new FirebaseOptions.Builder()// Это мой родной(изначальный рабочий вариант!!!)
				    .setCredentials(GoogleCredentials.fromStream(serviceAccount))// Если что, то просто его раскамментить!!!
				    .setDatabaseUrl("https://mygpsone-kusto1.firebaseio.com/")
				    .build();*/
			FirebaseOptions options = null;
					String firebaseLocation = "https://mygpsone-kusto1.firebaseio.com/";
			    	  Map<String, Object> databaseAuthVariableOverride = new HashMap<String, Object>();
			    	  // uid and provider will have to match what you have in your firebase security rules
			    	  databaseAuthVariableOverride.put("uid", "gae-firebase-event-proxy");
			    	  databaseAuthVariableOverride.put("provider", "192.168.32.9");
			    	  try {
			    	    options = new FirebaseOptions.Builder()
			    	    		 .setCredentials(GoogleCredentials.fromStream(serviceAccount))
			    	        .setDatabaseUrl(firebaseLocation)
			    	        .setDatabaseAuthVariableOverride(databaseAuthVariableOverride).build();
			    	    //FirebaseApp.initializeApp(options);
			    	  } catch (IOException e) {
			    	    throw new RuntimeException(
			    	        "Error reading firebase secrets from file: src/main/webapp/gae-firebase-secrets.json: "
			    	        + e.getMessage());
			    	  }
			
			/*Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.32.9", 3128));
			HttpTransport httpTransport = new NetHttpTransport.Builder().setProxy(proxy).build();

			HttpTransportFactory httpTransportFactory = () -> httpTransport;

			FirebaseOptions options = new FirebaseOptions.Builder()
			   .setCredentials(GoogleCredentials.fromStream(serviceAccount, httpTransportFactory))
			   .setDatabaseUrl("https://mygpsone-kusto1.firebaseio.com/")
			   .setHttpTransport(httpTransport)
			   .build();*/
			//FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdTokenAsync(<token>).get();
			
			
			
			
			
			/*Map<String, Object> databaseAuthVariableOverride = new HashMap<String, Object>();
			FirebaseOptions options =
			          new FirebaseOptions.Builder()
			          .setCredentials(GoogleCredentials.fromStream(serviceAccount))
			              .setDatabaseUrl("https://mygpsone-kusto1.firebaseio.com/")
			              .setDatabaseAuthVariableOverride(databaseAuthVariableOverride)
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
    
    // Это для отображения кораблей(судов) в левой колонке - "Флот(Осн. объекты)" ///////////////////////////
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
				            		    		        	  CMainController.fxTxtArLogs.setText(e.getMessage());
				            		    		        	  e.printStackTrace();
				            		    		        	  m_MyTrayIcon.displayMessage(
				            		    		        			  	CStrings.m_APP_ERROR,
				            	                                        "Ошибка инициализации карты ->\n перезапустите программу!",
				            	                                        java.awt.TrayIcon.MessageType.INFO
				            	                                );
														  }
				            		    		         
			            		    		          
			            		    		          InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
			            		    		          // Это упрощенное описание - только название!!!
			            		    		          infoWindowOptions.content("<u style=\"color: blue;\"><h4>" + us.getMyNameShip() + "</h4></u>");
			            		    		          // Это расширенное описание судна!!!
			            		    		         /* infoWindowOptions.content("<h4>Имя судна:</h4>" + 
			          										"<u style=\"color: blue;\"><h4>" + us.getMyNameShip() + "</h4></u>" +
			          										"<h4>Капитан:</h4>" + 
			          										"<u style=\"color: blue;\"><h4>" + us.getMyDirectorShip() + "</h4></u>" +
			          										"<h4>Описание судна:</h4>" + 
			          										"<u style=\"color: blue;\"><h4>" + us.getMyShortDescriptionShip() + "</h4></u>");*/
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
			            				    	CMainController.fxTxtArLogs.setText("Ошибка инициализации map!!!");
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
					            	CMainController.fxTxtArLogs.setText(ex.getMessage());
					            	ex.getStackTrace();
								}
							} 
							catch (Exception ex) 
							{
								CMainController.fxTxtArLogs.setText(ex.getMessage());
								ex.getStackTrace();
							}
						}
						
						@Override
						public void onCancelled(DatabaseError arg0)
						{
							CMainController.fxTxtArLogs.setText(arg0.getMessage());
							System.out.println(arg0.getMessage());
							
						}
					});
				} 
				catch (Exception ex) 
				{
					CMainController.fxTxtArLogs.setText(ex.getMessage());
					ex.getStackTrace();
				}
			//}
		//});
    }
    // Здесь формируем список показываемых сообщений по новому , а именно черед "ЛИСТ" - fxListUserViewOfMsg
    // А именно будем выводить все сообщения не черед обычный TextView (типа - CMainController.mymsg.appendText(MyMsg.msg_time);),
    // через ListCellUserMsgSending.fxml
    private void MyLoadListUserViewOfMsg(CMessages MyMessageSending)
    {
    	System.out.println("MyLoadListUserViewOfMsg() = " + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    	try
    	{
    		//m_alUsersAllMsg = new ArrayList<CStructUser>();
        	//m_lvAllUsersMsg = new ListView<CStructUser>();
		} 
    	catch (Exception ex) 
    	{
			ex.printStackTrace();
		}
    }
    // Здесь загружаем список пользователей во вкладку "сообщения"
    private void MyLoadAndListenUserMsg()
    {
			try
			{
				 mDatabaseRefUsersMsg = FirebaseDatabase.getInstance().getReference().child(CMAINCONSTANTS.FB_users);
				 mDatabaseRefUsersMsg.addValueEventListener(new ValueEventListener()
				 {
					@Override
					public void onDataChange(DataSnapshot arg0)
					{
						try
						{
							// Выбираем , что слушать, какую ветку данных!!!
				            Iterable<DataSnapshot> contactChildren = arg0.getChildren();

				            m_alUsersAllMsg = new ArrayList<CStructUser>();
			            	//m_lvAllUsersMsg = new ListView<CStructUser>();
			            	
				            for (DataSnapshot Users : contactChildren)
			                {
				            	CStructUser user = Users.getValue(CStructUser.class);
	                        	System.out.println( "CUser user = " + user.getMyNameShip());
	                        	m_alUsersAllMsg.add(user);// Заполнили массив!!!
		                	}
				            m_ObservableListUsersMsg = FXCollections.observableArrayList (m_alUsersAllMsg);
				            System.out.println( "m_ObservableListUsersMsg.size() = " + m_ObservableListUsersMsg.size());
				            
							if(fxListUsersMsg == null)
							{
								fxListUsersMsg = new ListView<CStructUser>();
							}
				            
				            System.out.println( "fxListUsersMsg.getItems().size() = " + fxListUsersMsg.getItems().size());
				            try 
				            {
				            	Platform.runLater(
		            			  () -> {

		            				  fxListUsersMsg.setItems(m_ObservableListUsersMsg);
		            				  fxListUsersMsg.setPrefSize(200, 500);
		            				  fxListUsersMsg.setCellFactory(new Callback<ListView<CStructUser>, ListCell<CStructUser>>() 
		            				 {
										
										@Override
										public ListCell<CStructUser> call(ListView<CStructUser> param) 
										{
											System.out.println("return new CUserCell();");
											return new CUserCellMsg();
										}
									});
		            				 
		            				  fxListUsersMsg.setOnMouseClicked(new EventHandler<MouseEvent>() {
		            		    			@Override
		            		    			public void handle(MouseEvent click)
		            		    			{
		            		    				/*Marker MyMarker = null;
		            		    				InfoWindow MyInfoWindow = null;
		            		    				MarkerOptions markerOptions;*/
		            		    				
		            		    				if (click.getClickCount() == 1) 
		            		    		        {
		            		    					/*if(CMainController.mymsg != null)
        		    			                	{
		            		    						CMainController.mymsg.clear();
        		    			                	}*/
		            		    					CStructUser TempUserForMsg = fxListUsersMsg.getSelectionModel().getSelectedItem();
		            		    					//int TempUserForMsg2 = fxListUsersMsg.getSelectionModel().getSelectedIndex();
		            		    					//m_ObservableListUsersMsg.
		            		    					//CUserCellMsg mmmm = TempUserForMsg.
		            		    					//CUserCellMsg cell=(CUserCellMsg) fxListUsersMsg.getCell(fxListUsersMsg.getSelectionModel().getSelectedIndex());
		            		    					
		            		    					// Присваеваем в CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG выбранного пользователя для
		            		    					// передачи сообщения ему!!!
		            		    					CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG = 
		            		    							CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_PREFIX + TempUserForMsg.getMyPhoneID();
		            		    					CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_SHIP_FOR_MSG = TempUserForMsg.getMyNameShip();	
		            		    					CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG_FIREBASES = CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG;
		            		    							
		            		    					System.out.println("tempUserMsgForSending(Типа кликнули мышкой!!!) = " + CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG);
		            		    					System.out.println("MY_CURRENT_TEMP_USER_FOR_MSG_FIREBASES = " + CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG_FIREBASES);
		            		    					System.out.println("MY_CURRENT_TEMP_USER_SHIP_FOR_MSG = " + CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_SHIP_FOR_MSG);
		            		    					CMainController.fxLbSelectedUser.setText(CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_SHIP_FOR_MSG);
		            		    					
		            		    					// Тут выводим после "клика" сообщения только для конкретного пользователя!!!
		            		    					// т.е. по его "CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG"
		            		    					 mQueryRefSingle = FirebaseDatabase.getInstance().getReference()
		            		    							 .child("message_to_android").child(CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG);//...
		            		    							 //.equalTo(CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG, "msg_to_user");
		            		    							 
		            		    					 mQueryRefSingle.addListenerForSingleValueEvent(new ValueEventListener() {// Здесь ветка слушается один раз при загрузке программы!!!
		            		    					 //mDatabaseRef.addValueEventListener(new ValueEventListener() {// Это старый вариант - здесь слушается ветка все время!!!
		            		    						@Override
		            		    						public void onDataChange(DataSnapshot arg0) 
		            		    						{
		            		    							// Выбираем , что слушать, какую ветку данных!!!
		            		    				            Iterable<DataSnapshot> messageChildren = arg0.getChildren();
		            		    				            
		            		    				            m_alUsersAllMsgSending = new ArrayList<CMessages>();

		            		    				            for (DataSnapshot message : messageChildren)
		            		    			                {
		            		    				            	CMessages MyMsg = message.getValue(CMessages.class);
		            		    				            	//if(MyMsg.msg_to_user.equals(CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG))
		            		    				            	if(MyMsg.msg_to_user.equals(CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG) ||
		            		    				            			MyMsg.msg_from_user.equals(CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG))
	            		    			                		{
	            		    			                			 m_alUsersAllMsgSending.add(MyMsg);
	            		    			                		}
		            		    			                }
		            		    				            
		            		    				            m_ObservableListUsersMsgSending = FXCollections.observableArrayList (m_alUsersAllMsgSending);
		            		    				            System.out.println( "m_ObservableListUsersMsgSending.size() = " + m_ObservableListUsersMsgSending.size());
		            		    				            Platform.runLater(
		            		    			            			  () -> {

		            		    			            				  fxListUserViewOfMsg.setItems(m_ObservableListUsersMsgSending);
		            		    			            				  fxListUserViewOfMsg.setPrefSize(200, 500);
		            		    			            				  fxListUserViewOfMsg.setCellFactory(new Callback<ListView<CMessages>, ListCell<CMessages>>() 
		            		    			            				 {
		            		    											
		            		    											@Override
		            		    											public ListCell<CMessages> call(ListView<CMessages> param) 
		            		    											{
		            		    												System.out.println("return new CMessages(); -- MyLoadAndListenUserMsg()");
		            		    												return new CUserCellMsgSending();
		            		    											}
		            		    										});
		            		    			            			  });
		            		    				            fxListUserViewOfMsg.scrollTo(m_alUsersAllMsgSending.size()-1);
		            		    						}
		            		    						public void onCancelled(DatabaseError arg0) 
		            		    						{
		            		    							System.out.println("DATABASE ERROR - " + arg0.getCode());
		            		    			                
		            		    						}; 
		            		    						});
		            		    					 
		            		    		        }
		            		    				 
		            		    				// Здесь пока ничего не придумали по двойному клику по пользователю для переписки!!!
		            		    		        if (click.getClickCount() == 2) 
		            		    		        {

		            		    		        }
		            		    		    }
		            					});
		            			  }
		            			);
				            	
							}
				            catch (Exception ex)
				            {
				            	//CMainController.fxTxtArLogs.setText(ex.getMessage());
				            	ex.printStackTrace();
							}
						} 
						catch (Exception ex) 
						{
							//CMainController.fxTxtArLogs.setText(ex.getMessage());
							ex.printStackTrace();
						}
					}
					
					@Override
					public void onCancelled(DatabaseError arg0)
					{
						//CMainController.fxTxtArLogs.setText(arg0.getMessage());
						System.out.println(arg0.getMessage());
						
					}
				});
			} 
			catch (Exception ex) 
			{
				//CMainController.fxTxtArLogs.setText(ex.getMessage());
				ex.printStackTrace();
			}
    }
    
    
    @FXML
    private void MyEventListnerFireMessage()
    {
		try
		{
			System.out.println( "MyEventListnerFireMessage()!!!");
			mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("message_to_android");
			mDatabaseRef.orderByChild("msg_unix_time").addChildEventListener(new ChildEventListener() {
			// mDatabaseRef.addChildEventListener(new ChildEventListener() {
				
				@Override
				public void onChildRemoved(DataSnapshot arg0) {
					System.out.println( "onChildRemoved - MyEventListnerFireMessage" );
					
				}
				
				@Override
				public void onChildMoved(DataSnapshot arg0, String arg1) {
					System.out.println( "onChildMoved - MyEventListnerFireMessage" );

				}
				
				@Override
				public void onChildChanged(DataSnapshot arg0, String arg1)
				{
					System.out.println( "onChildChanged - MyEventListnerFireMessage" );
					 System.out.println( "CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG_FIREBASES = " + CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG_FIREBASES);	 
					 mQueryRefSingle.addListenerForSingleValueEvent(new ValueEventListener() {// Здесь ветка слушается один раз при загрузке программы!!!
					 //mDatabaseRef.addValueEventListener(new ValueEventListener() {// Это старый вариант - здесь слушается ветка все время!!!
						@Override
						public void onDataChange(DataSnapshot arg0) 
						{
							// Выбираем , что слушать, какую ветку данных!!!
				            Iterable<DataSnapshot> messageChildren = arg0.getChildren();
				            
				            m_alUsersAllMsgSending = new ArrayList<CMessages>();

				            for (DataSnapshot message : messageChildren)
			                {
				            	CMessages MyMsg = message.getValue(CMessages.class);
				            	CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG_FIREBASES = MyMsg.msg_to_user;
				            	System.out.println( "CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG_FIREBASES = " + CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG_FIREBASES);
				            	
				            	//if(MyMsg.msg_to_user.equals(CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG_FIREBASES)) // Старый вариант!!!
				            	//if(MyMsg.msg_to_user.equals(CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG_FIREBASES))
		                		//{
				            		
		                			 m_alUsersAllMsgSending.add(MyMsg);
		                		//}
			                }
				            
				            m_ObservableListUsersMsgSending = FXCollections.observableArrayList (m_alUsersAllMsgSending);
				            System.out.println( "m_ObservableListUsersMsgSending.size() = " + m_ObservableListUsersMsgSending.size());
				            Platform.runLater(
			            			  () -> {

			            				  fxListUserViewOfMsg.setItems(m_ObservableListUsersMsgSending);
			            				  fxListUserViewOfMsg.setPrefSize(200, 500);
			            				  fxListUserViewOfMsg.setCellFactory(new Callback<ListView<CMessages>, ListCell<CMessages>>() 
			            				 {
											
											@Override
											public ListCell<CMessages> call(ListView<CMessages> param) 
											{
												System.out.println("return new CMessages(); -- MyLoadAndListenUserMsg()");
												return new CUserCellMsgSending();
											}
										});
			            			  });
				            fxListUserViewOfMsg.scrollTo(m_alUsersAllMsgSending.size()-1);
						}
						public void onCancelled(DatabaseError arg0) 
						{
							System.out.println("DATABASE ERROR - " + arg0.getCode());
			                
						}; 
					});

				}
				
				@Override
				public void onChildAdded(DataSnapshot arg0, String arg1) 
				{
/*					System.out.println( "onChildAdded - MyEventListnerFireMessage" );
					 System.out.println( "CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG = " + CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG);	 
					 mQueryRefSingle.addListenerForSingleValueEvent(new ValueEventListener() {// Здесь ветка слушается один раз при загрузке программы!!!
					 //mDatabaseRef.addValueEventListener(new ValueEventListener() {// Это старый вариант - здесь слушается ветка все время!!!
						@Override
						public void onDataChange(DataSnapshot arg0) 
						{
							// Выбираем , что слушать, какую ветку данных!!!
				            Iterable<DataSnapshot> messageChildren = arg0.getChildren();
				            
				            m_alUsersAllMsgSending = new ArrayList<CMessages>();

				            for (DataSnapshot message : messageChildren)
			                {
				            	CMessages MyMsg = message.getValue(CMessages.class);
				            	CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG_FIREBASES = MyMsg.msg_to_user;
				            	System.out.println( "CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG_FIREBASES = " + CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG_FIREBASES);
				            	
				            	if(MyMsg.msg_to_user.equals(CCONSTANTS_EVENTS_JOB.MY_CURRENT_TEMP_USER_FOR_MSG))
		                		{
				            		
		                			 m_alUsersAllMsgSending.add(MyMsg);
		                		}
			                }
				            
				            m_ObservableListUsersMsgSending = FXCollections.observableArrayList (m_alUsersAllMsgSending);
				            System.out.println( "m_ObservableListUsersMsgSending.size() = " + m_ObservableListUsersMsgSending.size());
				            Platform.runLater(
			            			  () -> {

			            				  fxListUserViewOfMsg.setItems(m_ObservableListUsersMsgSending);
			            				  fxListUserViewOfMsg.setPrefSize(200, 500);
			            				  fxListUserViewOfMsg.setCellFactory(new Callback<ListView<CMessages>, ListCell<CMessages>>() 
			            				 {
											
											@Override
											public ListCell<CMessages> call(ListView<CMessages> param) 
											{
												System.out.println("onChildAdded - return new CMessages(); -- MyLoadAndListenUserMsg()");
												return new CUserCellMsgSending();
											}
										});
			            			  });
						}
						public void onCancelled(DatabaseError arg0) 
						{
							System.out.println("DATABASE ERROR - " + arg0.getCode());
			                
						}; 
					});*/
				}
				
				@Override
				public void onCancelled(DatabaseError arg0) {
					System.out.println( "onCancelled" );
				}
			});
		} 
		catch (Exception ex) 
		{
			System.out.println( "FUCKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK - ex.printStackTrace();!!!");
			ex.printStackTrace();
		}
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

            
            /*File root = new File("yourfolder");
            root.mkdir(); //this makes sure the folder exists
            File file = new File(root,"mytextfile.txt");
            FileOutputStream fout = new FileOutputStream(file, false);*/
           
            
            // set up a system tray icon.
            m_MytrayInfoDialog = java.awt.SystemTray.getSystemTray();
            
            InputStream is = null;
            
            
            is = new BufferedInputStream(new FileInputStream(MyIconSuccessConn));
  
            
            // Проверка инета!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            URL urlTestInet = new URL(MyIsConnectionInet);
            URLConnection conn = urlTestInet.openConnection();
            conn.connect();
            conn.getInputStream().close();
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            
            java.awt.Image image = ImageIO.read(is);
            m_MyTrayIcon = new java.awt.TrayIcon(image);

            // if the user double-clicks on the tray icon, show the main app stage.
            m_MyTrayIcon.addActionListener(event -> Platform.runLater(this::showStage));

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
        			m_MytrayInfoDialog.remove(m_MyTrayIcon);
            		System.out.println("Выход->");
            		System.exit(0);
            	}
            });

            // setup the popup menu for the application.
            final java.awt.PopupMenu popup = new java.awt.PopupMenu();
            popup.add(openItem);
            popup.addSeparator();
            popup.add(exitItem);
            m_MyTrayIcon.setPopupMenu(popup);

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
            m_MytrayInfoDialog.add(m_MyTrayIcon);
            m_MyTrayIcon.displayMessage(
                    CStrings.m_APP_NAME,
                    "Я - время): " + m_MyTimeFormat.format(new Date()),
                    java.awt.TrayIcon.MessageType.INFO
            );
        } 
        catch (java.awt.AWTException | IOException e) 
        {
        	//CMainController.fxTxtArLogs.setText(e.getMessage());
        	InputStream is = null;
			try 
			{
				is = new BufferedInputStream(new FileInputStream(MyIconErrorConn));
				//is = new BufferedInputStream(new FileInputStream("/src/MyIconErrorConn.png"));
				java.awt.Image image = ImageIO.read(is);
				m_MyTrayIcon = new java.awt.TrayIcon(image);
	        	java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
	        	tray.add(m_MyTrayIcon);
	        	m_MyTrayIcon.displayMessage(
	                    CStrings.m_APP_NAME,
	                    "Проверьте подключение!",
	                    java.awt.TrayIcon.MessageType.ERROR
	            );
			} 
			catch (FileNotFoundException e1) 
			{
				CMainController.fxTxtArLogs.setText(e1.getLocalizedMessage());
				e1.printStackTrace();
			}
			catch (IOException e1) 
			{
				CMainController.fxTxtArLogs.setText(e1.getLocalizedMessage());
				e1.printStackTrace();
			}
			catch (AWTException e1)
			{
				CMainController.fxTxtArLogs.setText(e1.getLocalizedMessage());
				e1.printStackTrace();
			}
            System.out.println("Unable to init system tray");
            CMainController.fxTxtArLogs.setText(e.getMessage());
            e.printStackTrace();
        }
    }
}
