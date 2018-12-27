/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygpsx;

import com.google.cloud.storage.Bucket;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.internal.NonNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.Animation;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.SingleSelectionModel;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import netscape.javascript.JSObject;

/**
 *
 * @author Dmitry
 */
public class CMainController implements Initializable, MapComponentInitializedListener
{
	@FXML
	private Label idOfAnyControl;
	@FXML
	private TextField fxTxtFastSearch;// Быстрый поиск объекта по совпадению в...
	// Правый и левый меню аккордионы:
	@FXML
	private Accordion fxAccordRightMain;
	@FXML
	private TitledPane fxTPaneRight1;
	@FXML
	private TitledPane fxTPaneRight2;
	@FXML
	private TitledPane fxTPaneRight3;
	
	// Левый!!
	// Правый и левый меню аккордионы:
	@FXML
	private Accordion fxAccordLeftMain;
	@FXML
	private TitledPane fxTPaneLeft1;
	@FXML
	private TitledPane fxTPaneLeft2;
	@FXML
	private TitledPane fxTPaneLeft3;
	
	////////////////////// Здесь работа с TabPane!!! START///////////////////////////////////////////
	@FXML
	private TabPane fxTabPaneMain;
	@FXML
	private Tab fxTabPaneUsersOnMaps;
	@FXML
	private Tab fxTabPaneUsersAndJobs;
	
	@FXML
	private AnchorPane apPaneUsersAndJobs;
	
	//@FXML
	//private TabPane LPSMapTUserAndJobs;//
	
	private javafx.scene.control.SingleSelectionModel<Tab> m_MainTabsSelectionModel;
	@FXML
	private AnchorPane m_FXAPaneTemplateJobs;// Это панель в которой и находится сам лист с шаблоном!!!
	@FXML
	private ListView<CStructAttrTmplFilling> fxListTmplJob;
	////////////////////// Для вкладки настроек!!! START///////////////////////////////////////////
	 @FXML
	 Button btnSettingsPriorityEdit;
	//////////////////////Для вкладки настроек!!! END///////////////////////////////////////////
	
	// Смотри btnLoadFileToMsg(ActionEvent event), там есть фан openFile(File file)
	private Desktop desktop = Desktop.getDesktop();
	private File m_FileSelectedOne; // 
	@FXML
    private Label fxLbNameFileSelect;

	@FXML
	ListView<CStructUser> fxListView;
	public static ObservableList<CStructUser> m_ObservableListUsers;
	
	@FXML
    private Label label1;
    @FXML
    private Label label2;

	//Popup popWindow;
	// Позиция для 
	// Координаты для CAddShipController //////////////////////////////
	public static LatLong m_LocationTempForCAddShipController = null;
	@FXML
	public static Label fxLbLatitudeText;
	@FXML
	public static Label fxLbLongitudeText;
	@FXML
	CAddShipController m_AddShipController = null;
    /////////////////////////////////////////////////////////////////////
	private static DatabaseReference mDatabase;
	
	private static DatabaseReference mDatabaseRefSendMsg;

	// Это для тестовой проверки(temp) координат на карте!!!
	static LatLong LocationTemp = null;
	MarkerOptions markerOptionsTemp = null;
	Marker MarkerTemp = null;
	InfoWindowOptions infoWindowOptionsTemp = null;
	InfoWindow fredWilkeInfoWindowTemp = null;
	////////////////////////////////////////////////////////
	
	// Первый лист с кораблями!!!
	//@FXML
	//ListView<String> m_lvAllUsers;
	@FXML
	VBox fxvBoxUsersAll;
	
	//Массив маркеров!!!
	static String m_SELECTED_MARKER = null;
	@FXML
	public static Map<Marker, Boolean> markerMap = new HashMap<>();// Для перемещения!!!
	//Map<String, String> markerMapIDKey = new HashMap<>();// Для изменения данных в firebase по ID
	// А это переменная отвечает: двигать или не двигать маркер!!!
	private boolean m_bIsMuveMarker = false;

    @FXML 
    TextArea mymsg;
    @FXML
    private Label label;
    @FXML
    TextArea taOutMsg;
    @FXML
   	AnchorPane fxMessageWait;
    @FXML
    Label fxLbMessage;
    @FXML
    Button btnRestartMod;
    @FXML
    private GoogleMapView mapView;
    @FXML
    public static GoogleMap MyGoogleMap;
    @FXML
    private GeocodingService geocodingService;
    
    //Open list of window with SysUsers -- >> FrameSettingsSysUserEdit
    @FXML
    private void FrameSettingsSysUserEdit(ActionEvent event) 
    {
    	System.out.println("FrameSettingsSysUserEdit!!!");
    	//CCONSTANTS_EVENTS_JOB.SAMPLE_JOBING = "ADD_SHIP";
    	try 
    	{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXSysUserEdit));
            CLPSMain.m_rootFXSysUserEdit = (Parent)fxmlLoader.load();
            CLPSMain.m_stageFXSysUserEdit = new Stage();
            CLPSMain.m_stageFXSysUserEdit.setTitle(CStrings.m_APP_NAME + "-> Список аккаунтов");
            CLPSMain.m_stageFXSysUserEdit.setScene(new Scene(CLPSMain.m_rootFXSysUserEdit));  
            CLPSMain.m_stageFXSysUserEdit.setResizable(false);
            CLPSMain.m_stageFXSysUserEdit.initModality(Modality.WINDOW_MODAL);// Было , кода думал, что так лучше))) Но так не выбрать координаты!!!
            CLPSMain.m_stageFXSysUserEdit.initOwner(CLPSMain.stage);
            CLPSMain.m_stageFXSysUserEdit.show();
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }
    // Открытие окна для создания нового шаблона ОТЧЕТА!!!
    @FXML
    private void FrameSettingsFXCreateTmplOrders(ActionEvent event) 
    {
    	System.out.println("FrameSettingsFXCreateTmplOrders!!!");
    	CMyToast.makeText(CLPSMain.stage, "System.out.println(\"FrameSettingsFXCreateTmplOrders!!!\");", CMyToast.TOAST_SHORT, CMyToast.TOAST_WARN);
    }
    
    // Открытие окна со списком готовых шаблонов ОТЧЕТОВ для редактирования!!!
    @FXML
    private void FrameSettingsFXListOrders(ActionEvent event) 
    {
    	System.out.println("FrameSettingsFXListOrders!!!");
    	CMyToast.makeText(CLPSMain.stage, "System.out.println(\"FrameSettingsFXCreateTmplOrders!!!\");", CMyToast.TOAST_LONG, CMyToast.TOAST_SUCCESS);
    }
    // Открытие окна для создания нового шаблона задачи!!!
    @FXML
    private void FrameSettingsFXCreateTemplate(ActionEvent event) 
    {
    	System.out.println("FrameSettingsFXCreateTemplate!!!");
    	//CCONSTANTS_EVENTS_JOB.SAMPLE_JOBING = "ADD_SHIP";
    	try 
    	{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXCreateTemplate));
            CLPSMain.m_rootFXCreateTemplateJobs = (Parent)fxmlLoader.load();
            CLPSMain.m_stageFXCreateTemplateJobs = new Stage();
            CLPSMain.m_stageFXCreateTemplateJobs.setTitle(CStrings.m_APP_NAME + "->Создание нового шаблона задачи");
            CLPSMain.m_stageFXCreateTemplateJobs.setScene(new Scene(CLPSMain.m_rootFXCreateTemplateJobs));  
            CLPSMain.m_stageFXCreateTemplateJobs.setResizable(false);
            CLPSMain.m_stageFXCreateTemplateJobs.initModality(Modality.WINDOW_MODAL);// Было , кода думал, что так лучше))) Но так не выбрать координаты!!!
            CLPSMain.m_stageFXCreateTemplateJobs.initOwner(CLPSMain.stage);
            CLPSMain.m_stageFXCreateTemplateJobs.show();
            
            CLPSMain.m_stageFXCreateTemplateJobs.setOnCloseRequest(new EventHandler<WindowEvent>() 
    		{
              
            	// Здесь перед закрытием окна по крестику проверим изменилось ли название шаблона!!!
    			@Override
    			public void handle(WindowEvent arg0) 
    			{
            		// Здесь перед выходом смотрим меняли ли что-то в темповом, открытом шаблоне,
            		// типа название или что-то уже в шаблон добавляли!!!
            		// Пока проверим только изменение названия!!! потом добавим отслеживание
            		// других изменений!!!
    				for (Node node : CLPSMain.m_rootFXCreateTemplateJobs.getChildrenUnmodifiable()) 
    				{
    					
						String stTempID = node.getId();
						//TextField txtFldTemp
						System.out.println("node.getId() = " + stTempID);
						
						try
						{
							if(stTempID.equals("fxTxtNameTmplJob"))// Получили нужный "node" с названием задачи и проверяем на изменение онной!!!
							{
								// Здесь еще нужно убедиться, что пользователь уже(еще) не наляпал в шаблон контролов,
								// а то пиздец как не удобно перед ним выйдет)))
								
								TextField txtFldTemp = (TextField)node;
								if(txtFldTemp.getText().equals(CFXCreateTemplateJobCtrl.m_stTempNameJob) 
										/*$$ =:))*/&& CCONSTANTS_EVENTS_JOB.TEMP_COUNT_ADDING_CONTROLS_IN_TMPL == 0)
								{
									System.out.println("Значит изменений не было и мы просто закрываем окно создания шаблона задачи!!!");
									CLPSMain.mDatabase = FirebaseDatabase.getInstance()
											.getReference()
											.child(CMAINCONSTANTS.FB_my_owner_settings)
											.child(CMAINCONSTANTS.FB_my_templates);
									CLPSMain.mDatabase.child(CMAINCONSTANTS.m_UniqueTempIDTempate).removeValueAsync();
									//CCONSTANTS_EVENTS_JOB.TEMPLATE_FILLING_OR_EDIT = 1;// Вышли из создания и редактирования
									CCONSTANTS_EVENTS_JOB.TEMP_COUNT_ADDING_CONTROLS_IN_TMPL = 0; // Обнуляем кол-во контролов.
	    							// шаблона, потому опять == 1.
								}
								else// Однако они были, тогда предлагаем выбор!!!
								{
									Alert alert = new Alert(AlertType.CONFIRMATION);
				            		alert.setTitle("Закрыть шаблон без сохранения!");
				            		alert.setHeaderText("Вы действительно хотите закрыть шаблон без сохранения?");
				            		alert.setContentText(CStrings.m_APP_NAME_CHOOSE);
				            		ButtonType buttonTypeYes = new ButtonType(CStrings.m_APP_NAME_CHOOSE_YES);
				            		ButtonType buttonTypeNo = new ButtonType(CStrings.m_APP_NAME_CHOOSE_NO);
				            		
				            		alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
				            		
				            		java.util.Optional<ButtonType> result = alert.showAndWait();
				            		if (result.get() == buttonTypeYes)
				            		{
				            			CLPSMain.mDatabase = FirebaseDatabase.getInstance()
		    									.getReference()
		    									.child(CMAINCONSTANTS.FB_my_owner_settings)
		    									.child(CMAINCONSTANTS.FB_my_templates);
		    							CLPSMain.mDatabase.child(CMAINCONSTANTS.m_UniqueTempIDTempate).removeValueAsync();
		    							//CCONSTANTS_EVENTS_JOB.TEMPLATE_FILLING_OR_EDIT = 1;// Вышли из создания и редактирования
		    							// шаблона, потому опять == 1.
		    							CCONSTANTS_EVENTS_JOB.TEMP_COUNT_ADDING_CONTROLS_IN_TMPL = 0; // Обнуляем кол-во контролов.
		    							break;
				            		} 
				            		else
				            		{
				            			arg0.consume();// Отмена выхода из программы!!!
				            			break;
				            		}
								}
							}
						} catch (Exception e) {
							e.getMessage();
						}
    			    }
    			}
    		});
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }
    
    // Открытие окна со списком готовых шаблонов ЗАДАЧ для редактирования!!!
    @FXML
    private void FrameSettingsFXListTemplates(ActionEvent event) 
    {
    	System.out.println("FrameSettingsFXListTemplates!!!");
    	CCONSTANTS_EVENTS_JOB.SAMPLE_JOBING = "ADD_SHIP";
    	try 
    	{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXListTemplatesJobs));
            CLPSMain.m_rootFXListTemplates = (Parent)fxmlLoader.load();
            CLPSMain.m_stageFXListTemplates = new Stage();
            CLPSMain.m_stageFXListTemplates.setTitle(CStrings.m_APP_NAME + "->Список шаблонов");
            CLPSMain.m_stageFXListTemplates.setScene(new Scene(CLPSMain.m_rootFXListTemplates));  
            CLPSMain.m_stageFXListTemplates.setResizable(false);
            CLPSMain.m_stageFXListTemplates.initModality(Modality.WINDOW_MODAL);// Было , кода думал, что так лучше))) Но так не выбрать координаты!!!
            CLPSMain.m_stageFXListTemplates.initOwner(CLPSMain.stage);
            CLPSMain.m_stageFXListTemplates.show();
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }
    
    // Открытие окна редактирования типов объектов!!!
    @FXML
    private void FrameSettingsTypeobjEdit(ActionEvent event) 
    {
    	System.out.println("FrameSettingsTypeobjEdit!!!");
    	CCONSTANTS_EVENTS_JOB.SAMPLE_JOBING = "ADD_SHIP";
    	try 
    	{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXTypeobjEdit));
            CLPSMain.m_rootTypeobjEdit = (Parent)fxmlLoader.load();
            CLPSMain.m_stageTypeobjEdit = new Stage();
            CLPSMain.m_stageTypeobjEdit.setTitle(CStrings.m_APP_NAME + "->Редактирование типов объектов");
            CLPSMain.m_stageTypeobjEdit.setScene(new Scene(CLPSMain.m_rootTypeobjEdit));  
            CLPSMain.m_stageTypeobjEdit.setResizable(false);
            CLPSMain.m_stageTypeobjEdit.initModality(Modality.WINDOW_MODAL);// Было , кода думал, что так лучше))) Но так не выбрать координаты!!!
            CLPSMain.m_stageTypeobjEdit.initOwner(CLPSMain.stage);
            CLPSMain.m_stageTypeobjEdit.show();
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }
    
    // Открытие окна редактирования атрибутов объектов!!!
    @FXML
    private void FrameSettingsAttrobjEdit(ActionEvent event) 
    {
    	System.out.println("FrameSettingsAttrobjEdit!!!");
    	CCONSTANTS_EVENTS_JOB.SAMPLE_JOBING = "ADD_SHIP";
    	try 
    	{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXAttrobjEdit));
            CLPSMain.m_rootAttrobjEdit = (Parent)fxmlLoader.load();
            CLPSMain.m_stageAttrobjEdit = new Stage();
            CLPSMain.m_stageAttrobjEdit.setTitle(CStrings.m_APP_NAME + "->Редактирование атрибутов объектов");
            CLPSMain.m_stageAttrobjEdit.setScene(new Scene(CLPSMain.m_rootAttrobjEdit));  
            CLPSMain.m_stageAttrobjEdit.setResizable(false);
            CLPSMain.m_stageAttrobjEdit.initModality(Modality.WINDOW_MODAL);// Было , кода думал, что так лучше))) Но так не выбрать координаты!!!
            CLPSMain.m_stageAttrobjEdit.initOwner(CLPSMain.stage);
            CLPSMain.m_stageAttrobjEdit.show();
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }
    
    // Открытие окна редактирования типовых описаний работ!!!
    @FXML
    private void FrameSettingsTypedescjobEdit(ActionEvent event) 
    {
    	System.out.println("FrameSettingsTypedescjobEdit!!!");
    	CCONSTANTS_EVENTS_JOB.SAMPLE_JOBING = "ADD_SHIP";
    	try 
    	{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXTypedescjobEdit));
            CLPSMain.m_rootTypedescjobEdit = (Parent)fxmlLoader.load();
            CLPSMain.m_stageTypedescjobEdit = new Stage();
            CLPSMain.m_stageTypedescjobEdit.setTitle(CStrings.m_APP_NAME + "->Редактирование типовых описаний задач");
            CLPSMain.m_stageTypedescjobEdit.setScene(new Scene(CLPSMain.m_rootTypedescjobEdit));  
            CLPSMain.m_stageTypedescjobEdit.setResizable(false);
            CLPSMain.m_stageTypedescjobEdit.initModality(Modality.WINDOW_MODAL);// Было , кода думал, что так лучше))) Но так не выбрать координаты!!!
            CLPSMain.m_stageTypedescjobEdit.initOwner(CLPSMain.stage);
            CLPSMain.m_stageTypedescjobEdit.show();
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }
    
    // Открытие окна редактирования атрибутов работ!!!
    @FXML
    private void FrameSettingsAttrjobEdit(ActionEvent event) 
    {
    	System.out.println("FrameSettingsAttrjobEdit!!!");
    	CCONSTANTS_EVENTS_JOB.SAMPLE_JOBING = "ADD_SHIP";
    	try 
    	{
    		CCONSTANTS_EVENTS_JOB.SAMPLE_ANY_OR_ANY = "DEL";
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXAttrjobEdit));
            CLPSMain.m_rootAttrjobEdit = (Parent)fxmlLoader.load();
            CLPSMain.m_stageAttrjobEdit = new Stage();
            CLPSMain.m_stageAttrjobEdit.setTitle(CStrings.m_APP_NAME + "->Редактирование атрибутов задач");
            CLPSMain.m_stageAttrjobEdit.setScene(new Scene(CLPSMain.m_rootAttrjobEdit));  
            CLPSMain.m_stageAttrjobEdit.setResizable(false);
            CLPSMain.m_stageAttrjobEdit.initModality(Modality.WINDOW_MODAL);// Было , кода думал, что так лучше))) Но так не выбрать координаты!!!
            CLPSMain.m_stageAttrjobEdit.initOwner(CLPSMain.stage);
            CLPSMain.m_stageAttrjobEdit.show();
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }
    
    // Открытие окна редактирования типов работ!!!
    @FXML
    private void FrameSettingsTypejobEdit(ActionEvent event) 
    {
    	System.out.println("FrameSettingsTypejobEdit!!!");
    	CCONSTANTS_EVENTS_JOB.SAMPLE_JOBING = "ADD_SHIP";
    	try 
    	{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXTypejobEdit));
            CLPSMain.m_rootTypejobEdit = (Parent)fxmlLoader.load();
            CLPSMain.m_stageTypejobEdit = new Stage();
            CLPSMain.m_stageTypejobEdit.setTitle(CStrings.m_APP_NAME + "->Редактирование типов задач");
            CLPSMain.m_stageTypejobEdit.setScene(new Scene(CLPSMain.m_rootTypejobEdit));  
            CLPSMain.m_stageTypejobEdit.setResizable(false);
            CLPSMain.m_stageTypejobEdit.initModality(Modality.WINDOW_MODAL);// Было , кода думал, что так лучше))) Но так не выбрать координаты!!!
            CLPSMain.m_stageTypejobEdit.initOwner(CLPSMain.stage);
            CLPSMain.m_stageTypejobEdit.show();
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }

    // Открытие окна редактирования приоритетов!!!
    @FXML
    private void FrameSettingsStatusEdit(ActionEvent event) 
    {
    	System.out.println("FrameSettingsStatusEdit!!!");
    	CCONSTANTS_EVENTS_JOB.SAMPLE_JOBING = "ADD_SHIP";
    	try 
    	{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXStatusEdit));
            CLPSMain.m_rootStatusEdit = (Parent)fxmlLoader.load();
            CLPSMain.m_stageStatusEdit = new Stage();
            CLPSMain.m_stageStatusEdit.setTitle(CStrings.m_APP_NAME + "->Редактирование статусов задач");
            CLPSMain.m_stageStatusEdit.setScene(new Scene(CLPSMain.m_rootStatusEdit));  
            CLPSMain.m_stageStatusEdit.setResizable(false);
            CLPSMain.m_stageStatusEdit.initModality(Modality.WINDOW_MODAL);// Было , кода думал, что так лучше))) Но так не выбрать координаты!!!
            CLPSMain.m_stageStatusEdit.initOwner(CLPSMain.stage);
            CLPSMain.m_stageStatusEdit.show();
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }
    // Открытие окна редактирования приоритетов!!!
    @FXML
    private void FrameSettingsPrioritetsEdit(ActionEvent event) 
    {
    	System.out.println("btnSettingsPrioritetsEdit!!!");
    	CCONSTANTS_EVENTS_JOB.SAMPLE_JOBING = "ADD_SHIP";
    	try 
    	{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXPriorityEdit));
            CLPSMain.m_rootPriorityEdit = (Parent)fxmlLoader.load();
            CLPSMain.m_stagePriorityEdit = new Stage();
            CLPSMain.m_stagePriorityEdit.setTitle(CStrings.m_APP_NAME + "->Редактирование приоритетов задач");
            CLPSMain.m_stagePriorityEdit.setScene(new Scene(CLPSMain.m_rootPriorityEdit));  
            CLPSMain.m_stagePriorityEdit.setResizable(false);
            CLPSMain.m_stagePriorityEdit.initModality(Modality.WINDOW_MODAL);// Было , кода думал, что так лучше))) Но так не выбрать координаты!!!
            CLPSMain.m_stagePriorityEdit.initOwner(CLPSMain.stage);
            CLPSMain.m_stagePriorityEdit.show();
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }
    // Открытие окна типа Добавить новый объект(типа пока судно!!!)
    @FXML
    private void FrameAddShip(ActionEvent event) {
    	System.out.println("btnAddShip!!!");
    	CCONSTANTS_EVENTS_JOB.SAMPLE_JOBING = "ADD_SHIP";
    	try 
    	{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXAddShipFxml));
            CLPSMain.m_rootAddShip = (Parent)fxmlLoader.load();
            CLPSMain.m_stageAddShip = new Stage();
            CLPSMain.m_stageAddShip.setTitle(CStrings.m_APP_NAME + "->Добавление судна");
            CLPSMain.m_stageAddShip.setScene(new Scene(CLPSMain.m_rootAddShip));  
            CLPSMain.m_stageAddShip.setResizable(false);
           // CLPSMain.m_stageAddShip.initModality(Modality.WINDOW_MODAL);// Было , кода думал, что так лучше))) Но так не выбрать координаты!!!
            CLPSMain.m_stageAddShip.setAlwaysOnTop(true); // А так ВЫБРАТЬ!!!
            CLPSMain.m_stageAddShip.initOwner(CLPSMain.stage);
            CLPSMain.m_stageAddShip.show();
            
            fxLbLatitudeText = (Label)fxmlLoader.getNamespace().get("fxLbLatitudeText");
            fxLbLongitudeText = (Label)fxmlLoader.getNamespace().get("fxLbLongitudeText");
           // System.out.println("fxLbLatitudeText = " + fxLbLatitudeText.getText());
           // System.out.println("fxLbLongitudeText = " + fxLbLongitudeText.getText());
            
            }
    		catch(Exception e) 
    		{
               e.printStackTrace();
            }
    }
    @FXML
    private void handleDeleteAllMarkers(ActionEvent event) 
    {
    	//TabPane tb = (TabPane)CLPSMain.scene.lookup("#fxTabPaneMain");
		javafx.scene.control.SingleSelectionModel<Tab> selectionModel = CCONSTANTS_EVENTS_JOB.MY_TABPANE_MAIN.getSelectionModel();
		m_MainTabsSelectionModel.select(0);
		
    	if(MyGoogleMap != null)
    	{
    		MyGoogleMap.clearMarkers();
        	System.out.println("Удалить все маркеры!!!");
    	}
    	else
    	{
    		System.out.println("Error - map == null");
    	}
    	
    }
    @FXML
    private void btnRefreshMapMod(ActionEvent event) throws IOException// Обновить карту вручную!!!
    {
    	/*	Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle(CStrings.m_APP_NAME);
    		alert.setHeaderText("КОСЯК!!!");
    		alert.setContentText("Что-то пошло не так)))\nМожет перегрузим карту???");
    		ButtonType buttonTypeYes = new ButtonType(CStrings.m_APP_NAME_CHOOSE_YES);
    		ButtonType buttonTypeNo = new ButtonType(CStrings.m_APP_NAME_CHOOSE_NO);
    		
    		alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
    		
    		java.util.Optional<ButtonType> result = alert.showAndWait();
    		if (result.get() == buttonTypeYes)
    		{*/
				  CLPSMain.stage.close();
				  CLPSMain reload = new CLPSMain();
				  reload.reload();
    		/*} 
    		else
    		{
    			alert.close();
    		}*/
     }
    @FXML
    private void btnRefreshMap(ActionEvent event) throws IOException// Обновить карту вручную!!!
    {
    		/*Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle(CStrings.m_APP_NAME);
    		alert.setHeaderText("КОСЯК!!!");
    		alert.setContentText("Что-то пошло не так)))\nМожет перегрузим карту???");
    		ButtonType buttonTypeYes = new ButtonType(CStrings.m_APP_NAME_CHOOSE_YES);
    		ButtonType buttonTypeNo = new ButtonType(CStrings.m_APP_NAME_CHOOSE_NO);
    		
    		alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
    		
    		java.util.Optional<ButtonType> result = alert.showAndWait();
    		if (result.get() == buttonTypeYes)
    		{*/
				  CLPSMain.stage.close();
				  CLPSMain reload = new CLPSMain();
				  reload.reload();
    		/*} 
    		else
    		{
    			alert.close();
    		}*/
     }
    @FXML
    private void btnLoadFileToMsg(ActionEvent event) 
    {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Выберите документ");
    	//fileChooser.showOpenDialog(CLPSMain.stage);
    	m_FileSelectedOne = fileChooser.showOpenDialog(CLPSMain.stage);
        if (m_FileSelectedOne != null)
        {
        	try 
        	{
            	fxLbNameFileSelect.setText(m_FileSelectedOne.getName());
            	uploadImage(m_FileSelectedOne);
                //openFile(file); - Эта фан нужна если мы хотим файл открыть для просмотра на компе программой просмотра!!!
			} 
        	catch (Exception e)
        	{
				// TODO: handle exception
			}
        	
        }

    }
    //openFile(file); - Эта фан нужна если мы хотим файл открыть для просмотра на компе программой просмотра!!!
    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
            		CMainController.class.getName()).log(
                    Level.SEVERE, null, ex
                );
        }
    }
    @FXML
    private void handleSendMsg(ActionEvent event) {
    	mDatabaseRefSendMsg = FirebaseDatabase.getInstance().getReference().child("message_to_android");
    	 try 
    	 {
    		 mDatabaseRefSendMsg.child("msg_555555").child("msg_body").setValueAsync(taOutMsg.getText().toString());
             taOutMsg.clear();
             System.out.println("Типа послали сообщение!!!");
         } 
    	 catch (Exception e) 
    	 {
             e.printStackTrace();
         }
    }
    @FXML
    private void btnClearMessages(ActionEvent event) {
    	mymsg.clear();
    }
    
    @FXML
    private boolean ShowAllMarkersAfterRefresh()
    {
    	boolean bRet = true;
    	try
    	{
    		for(int i = 0; i < CLPSMain.m_localAllMarkersUsersTempMain.size(); i++)
        	{
        		CStructUser us = (CStructUser)CLPSMain.m_localAllMarkersUsersTempMain.get(i);
        		
        		
        		LatLong markerLocation = new LatLong(Double.parseDouble(us.getMyLatitude()),
        												Double.parseDouble(us.getMyLongitude()));
        		MarkerOptions markerOptions = new MarkerOptions();
        		//markerOptions.i
        		//markerOptions.icon(iconPath)
        		markerOptions.position(markerLocation);
        		Marker MyMarker = new Marker(markerOptions);
        		
        		
        		MyGoogleMap.addMarker( MyMarker );
                markerMap.put(MyMarker, false);

                InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                infoWindowOptions.content("<h4>Имя судна:</h4>" + 
										"<u style=\"color: blue;\"><h4>" + us.getMyNameShip() + "</h4></u>" +
										"<h4>Капитан:</h4>" + 
										"<u style=\"color: blue;\"><h4>" + us.getMyDirectorShip() + "</h4></u>" +
										"<h4>Описание судна:</h4>" + 
										"<u style=\"color: blue;\"><h4>" + us.getMyShortDescriptionShip() + "</h4></u>");

                InfoWindow MyInfoWindow = new InfoWindow(infoWindowOptions);
                
                MyGoogleMap.addUIEventHandler(MyMarker, UIEventType.click, (JSObject obj) -> {
                    if (markerMap.get(MyMarker)) 
                    {

                    	MyInfoWindow.close();
                        markerMap.put(MyMarker, false);
                        m_bIsMuveMarker = false;
                        m_SELECTED_MARKER = null;
                    }
                    else 
                    {
                    	MyInfoWindow.open(MyGoogleMap, MyMarker);
                        markerMap.put(MyMarker, true);
                        m_bIsMuveMarker = true;
                        System.out.println("us.getMyPhoneID() == " + us.getMyPhoneID());
                        m_SELECTED_MARKER = us.getMyPhoneID();
                    }
                });
                
        		System.out.println("us.getMyPhoneID() == " + us.getMyPhoneID());
        	}

        	System.out.println("Показать все маркеры!!!");
		}
    	catch (Exception ex) 
    	{
    		bRet = false;
			/*Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(CStrings.m_APP_NAME);
			alert.setHeaderText("Нажмите кнопку:");
			alert.setContentText("Обновить все маркеры");
			alert.showAndWait();*/
		}
    	return bRet;
    }
    
    @FXML
    private void btnShowAllMarkers(ActionEvent event) 
    {
    	//TabPane tb = (TabPane)CLPSMain.scene.lookup("#fxTabPaneMain");
		javafx.scene.control.SingleSelectionModel<Tab> selectionModel = CCONSTANTS_EVENTS_JOB.MY_TABPANE_MAIN.getSelectionModel();
		m_MainTabsSelectionModel.select(0);
		
    	if(!ShowAllMarkersAfterRefresh())
    	{
    		System.out.println("btnRefreshAllMarkers(); - а потом показываем!!!");
    		//btnRefreshAllMarkers();
    	}
    	else
    	{
    		System.out.println("Сразу показываем, так как уже обновили!!!");
    	}
    }
    
/*    @FXML
    private synchronized void btnRefreshAllMarkers(ActionEvent event) 
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
            			 CLPSMain.m_localAllMarkersUsersTempMain = new ArrayList<>();
        				 //MarkerOptions mo = null;
        			    // LatLong ll = null;
        			     CStructUser tempTransport = null;
        				DataSnapshot usersSnapshot = arg0;
        				//System.out.println("arg0 = " + arg0.getChildrenCount());
                        Iterable<DataSnapshot> contactChildren = usersSnapshot.getChildren();
                        
                        //System.out.println("usersSnapshot.getChildrenCount() = " + usersSnapshot.getChildrenCount());
                     // Здесь перебераем все устройства, свое по phoneID игнорируем, а по другим
                        // выставляем маркеры!!! Как то так))))
                        for (DataSnapshot arg : contactChildren)
                        {
                        	System.out.println( "----------Начало маркера!!!-------------" );
                        	System.out.println(" for (DataSnapshot arg : contactChildren)");
                        	CStructUser user = arg.getValue(CStructUser.class);
                        	System.out.println( ">>>>>>>>>user.getMyPhoneID() =  " + user.getMyPhoneID() + "<<<<<<<<<<<<");
                        	Double.parseDouble(user.getMyLatitude());
                            Double.parseDouble(user.getMyLongitude());
                            System.out.println( "user.MyLatitude = " + user.getMyLatitude() );
                            System.out.println( "user.MyLongitude = " + user.getMyLongitude() );
                            
                            tempTransport = new CStructUser(user.getMyPhoneID(), user.getMyLatitude(), user.getMyLongitude(), "",
                            		user.getMyNameShip(), user.getMyDirectorShip(), user.getMyShortDescriptionShip(), user.getMyIsUserSelected(), user.getMyPass());
                            CLPSMain.m_localAllMarkersUsersTempMain.add(tempTransport);
                            System.out.println( "----------Конец маркера!!!-------------" );
                            //break;
                        }
    				}
            		catch (Exception ex)
            		{
            			ex.getStackTrace();
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
			ex.getStackTrace();
		}

    }*/
    @Override
    public void mapInitialized() 
    {
    	CCONSTANTS_EVENTS_JOB.LOAD_GOOGLEMAP_STEP = 0;
    	try 
    	{
    		//geocodingService = new GeocodingService();
 	        MapOptions mapOptions = new MapOptions();
 	        
 	        mapOptions.center(new LatLong(56.890471, 37.3498416))
 	                .mapType(MapTypeIdEnum.ROADMAP)
 	                .overviewMapControl(false)
 	                .panControl(true)
 	                .rotateControl(false)
 	                .scaleControl(true)
 	                .streetViewControl(true)
 	                .zoomControl(false)
 	                .zoom(12);
 	       System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG - mapInitialized");
 	       
 	       CCONSTANTS_EVENTS_JOB.LOAD_GOOGLEMAP_STEP++;// Первый этап инициализации пройден!!!
 	       
 	       MyGoogleMap = mapView.createMap(mapOptions);
       
 	       mapReady(markerMap);
 	       
 	       // Если оба этапа инициализации карты прошли:
 	       if(CCONSTANTS_EVENTS_JOB.LOAD_GOOGLEMAP_STEP == 2)
 	       {
 	    	  CMyToast.makeText(CLPSMain.stage, 
 	    			  "Карта загружается!",
 	    			  CMyToast.TOAST_LONG, CMyToast.TOAST_SUCCESS);
 	       }
 	       else
 	       {
 	    	  CMyToast.makeText(CLPSMain.stage, 
 	    			  "Ошибка загрузки карты - нажмите кнопку \"Обновить\"",
 	    			  CMyToast.TOAST_LONG, CMyToast.TOAST_ERROR);
 	       }
		} 
    	catch (Exception ex)
    	{
    		ex.getMessage();
		}
    }
   
    @FXML
    public synchronized void mapReady(Map<Marker, Boolean> markerMap)
    {
    	try 
    	{
    		MyGoogleMap.addUIEventHandler(UIEventType.click, (JSObject obj) -> {
                LatLong ll = new LatLong((JSObject) obj.getMember("latLng"));
                System.out.println("Latitude: " + ll.getLatitude());
                System.out.println("Longitude: " + ll.getLongitude());
                System.out.println("--------------------------------");
                
                try
                {
                	fxLbLatitudeText.setText(Double.toString(ll.getLatitude()));
                    fxLbLongitudeText.setText(Double.toString(ll.getLongitude()));
                    System.out.println("fxLbLatitudeText = " + fxLbLatitudeText.getText());
                    System.out.println("fxLbLongitudeText = " + fxLbLongitudeText.getText());
				} 
                catch (Exception e) 
                {
					// TODO: handle exception
				}
                
                
                if(CCONSTANTS_EVENTS_JOB.SAMPLE_JOBING == "ADD_SHIP")
                {
                	m_LocationTempForCAddShipController = ll;
                }
                
                if(m_bIsMuveMarker)
                {
                	try 
                	{
                		Set set = markerMap.entrySet();
                        Iterator iterator = set.iterator();
                        //iterator.
                        while(iterator.hasNext()) 
                        {
                           Map.Entry tempMarkerSelected = (Map.Entry)iterator.next();
                           System.out.print("key is: "+ tempMarkerSelected.getKey() + " & Value is: ");
                           System.out.println(tempMarkerSelected.getValue());
                           if((boolean)tempMarkerSelected.getValue())
                           {
                        	   try 
                        	   {
                             	  System.out.println("Правильный маркер)))");
                            	  Marker mkTemp = (Marker)tempMarkerSelected.getKey();
                            	  mkTemp.setPosition(ll);
                            	  // Обновляем координаты в firebase!!!!
                             	 mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child("MyPhoneID_" + m_SELECTED_MARKER);
                            	 try 
                            	 {
                            		 System.out.println("mDatabase.child(\"MyLatitude\").setValueAsync(Double.toString(ll.getLatitude()));");
                            		 System.out.println("mDatabase.child(\"MyLongitude\").setValueAsync(Double.toString(ll.getLongitude()));");
                                    mDatabase.child("myLatitude").setValueAsync(Double.toString(ll.getLatitude()));
                                    mDatabase.child("myLongitude").setValueAsync(Double.toString(ll.getLongitude()));

                                 }
                            	 catch (Exception e) 
                            	 {
                                     e.printStackTrace();
                                 }
                            	 //System.out.println("Типа послали сообщение!!!");
                        	   } 
                        	   catch (Exception ex)
                        	   {
	                        		ex.getMessage();
                        	   }

                           }
                        }
    				} 
                	catch (Exception ex) 
                	{
                		ex.getMessage();
      				}
                	
                }
            });
    		CCONSTANTS_EVENTS_JOB.LOAD_GOOGLEMAP_STEP++;// Второй этап инициализации пройден!!!
    		System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG - mapReady");
		}
    	catch (Exception ex) 
    	{
			ex.getMessage();
		}
    	/*System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG - mapReady");*/
    }
    @Override
    public synchronized void initialize(URL url, ResourceBundle rb)
    {
    	fxAccordLeftMain.setExpandedPane(fxTPaneLeft2);
    	fxAccordRightMain.setExpandedPane(fxTPaneRight1);
    	try 
    	{
    		apPaneUsersAndJobs = FXMLLoader.load(getClass().getResource("LPSMapTUserAndJobs.fxml"));
    		fxTabPaneUsersAndJobs.setContent(apPaneUsersAndJobs);
    		
    		// Добавим сразу в fxAPaneMain для шаблона файлик FXAPaneTemplateJobs.fxml
			m_FXAPaneTemplateJobs = FXMLLoader.load(getClass().getResource(CMAINCONSTANTS.m_PathFXAPaneTemplateFilling));
			apPaneUsersAndJobs.getChildren().add(m_FXAPaneTemplateJobs);
			apPaneUsersAndJobs.setTopAnchor(m_FXAPaneTemplateJobs, 170.0);
			apPaneUsersAndJobs.setLeftAnchor(m_FXAPaneTemplateJobs, 10.0);
			fxListTmplJob = (ListView<CStructAttrTmplFilling>)m_FXAPaneTemplateJobs.getChildren().get(0);
		} 
    	catch (Exception e)
    	{
			e.printStackTrace();
		}
    	
    	CCONSTANTS_EVENTS_JOB.MY_TABPANE_MAIN = fxTabPaneMain;
    	m_MainTabsSelectionModel = CCONSTANTS_EVENTS_JOB.MY_TABPANE_MAIN.getSelectionModel();
    	//m_MainTabsSelectionModel.
    	m_MainTabsSelectionModel.selectedItemProperty().addListener(new ChangeListener<Tab>() 
    	{
			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue)
			{
				System.out.println("selectionModel.select() = " + newValue.getText());
				if(newValue.getText().equals(CCONSTANTS_EVENTS_JOB.MY_TAB_1_NAME))
				{
					//Platform.runLater(
				    //    	() -> {
					//FXMLLoader fxmlLoader = new FXMLLoader();
					//fxmlLoader.setLocation(getClass().getResource("LPSMapTUserAndJobs.fxml"));
					
					try 
					{
						
						
						if(CCONSTANTS_EVENTS_JOB.MY_CHANGE_TAB_TO_MY_TAB_1_NAME)// Проверим пришли ли по кнопке!!!
						{
							CLPSMain.m_CTUsersJobsController.RefreshSelectedUser("7777777");
							System.out.println("Пришли по кнопке!!!");
						}
						else
						{
							System.out.println("Пришли НЕ !!! по кнопке!!!");
							CLPSMain.m_CTUsersJobsController.RefreshSelectedUser("5555555");
						}
						CCONSTANTS_EVENTS_JOB.MY_CHANGE_TAB_TO_MY_TAB_1_NAME = false;// Вернем обратно, чтобы не запутаться, что мы заходили посредством
						 // нажатия к нопки в судне "Добавить задачу".

						
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
					//CTUsersJobsController tUsersJobsController = (CTUsersJobsController) fxmlLoader.getController();
					//tUsersJobsController.RefreshSelectedUser(CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_SHIP);
				         // 	});
				}
			}
		});
    	
    	//selectionModel.select(tab); //select by object
    	m_MainTabsSelectionModel.select(0); //select by index starting with 0
    	try 
    	{
            mapView.addMapInializedListener(this);
		}
    	catch (Exception ex)
    	{
    		ex.getMessage();
		}
    	
    	// Здесь потом инициализируем поиск по вхождению!!! 

    } 
    private void uploadImage(File fFile)
    {
        if(fFile != null)// File is chooser!!!
        {
        	try 
        	{
        		//String ext = Files.getFileExtension(fFile.getName());
        		// Пишем прямо здесь в базу сразу!!!
            	InputStream targetStream = new FileInputStream(fFile);
        		Bucket bucket = CLPSMain.MyGoogleStorage.get(("mygpsone-kusto1.appspot.com"));
				com.google.cloud.storage.Blob blob = bucket.create(CMAINCONSTANTS.PATH_NAME_UPLOADS_MAIN + fFile.getName(), targetStream,"image/jpg");
				
				System.out.println(blob.getSelfLink());
				System.out.println(blob.getMediaLink());
				System.out.println(blob.getEtag());
				
				
				// Print blob metadata
				System.out.println("Bucket: " + blob.getBucket());
				System.out.println("CacheControl: " + blob.getCacheControl());
				System.out.println("ComponentCount: " + blob.getComponentCount());
				System.out.println("ContentDisposition: " + blob.getContentDisposition());
				System.out.println("ContentEncoding: " + blob.getContentEncoding());
				System.out.println("ContentLanguage: " + blob.getContentLanguage());
				System.out.println("ContentType: " + blob.getContentType());
				System.out.println("Crc32c: " + blob.getCrc32c());
				System.out.println("ETag: " + blob.getEtag());
				System.out.println("Generation: " + blob.getGeneration());
				System.out.println("Id: " + blob.getBlobId());
				System.out.println("KmsKeyName: " + blob.getKmsKeyName());
				System.out.println("Md5Hash: " + blob.getMd5());
				System.out.println("MediaLink: " + blob.getMediaLink());
				System.out.println("Metageneration: " + blob.getMetageneration());
				System.out.println("Name: " + blob.getName());
				System.out.println("Size: " + blob.getSize());
				System.out.println("StorageClass: " + blob.getStorageClass());
				System.out.println("TimeCreated: " + new Date(blob.getCreateTime()));
				System.out.println("Last Metadata Update: " + new Date(blob.getUpdateTime()));
				Boolean temporaryHoldIsEnabled = (blob.getTemporaryHold() != null && blob.getTemporaryHold());
				System.out.println("temporaryHold: " + (temporaryHoldIsEnabled ? "enabled" : "disabled"));
				Boolean eventBasedHoldIsEnabled = (blob.getEventBasedHold() != null && blob.getEventBasedHold());
				System.out.println("eventBasedHold: " + (eventBasedHoldIsEnabled ? "enabled" : "disabled"));
				if (blob.getRetentionExpirationTime() != null) {
				  System.out.println("retentionExpirationTime: " + new Date(blob.getRetentionExpirationTime()));
				}
				if (blob.getMetadata() != null) {
				  System.out.println("\n\n\nUser metadata:");
				  for (Map.Entry<String, String> userMetadata : blob.getMetadata().entrySet()) {
				    System.out.println(userMetadata.getKey() + "=" + userMetadata.getValue());
				  }
				}
				//System.out.println(blob2.to);
				
				// Доделаем ссылку URL 1 завтра!!!
				String img_url = "https://firebasestorage.googleapis.com/v0/b/" + blob.getBucket() + "/o/"
						+ blob.getName()
						+ "?alt=media&token=";
						//+ blob.getMetadata().get("");

				System.out.println("URL = " + img_url);
				
				
				
				// Create a reference with an initial file path and name
				//var storage = firebase.storage();
				//var pathReference = storage.ref('images/stars.jpg');
				//CLPSMain.MyGoogleStorage.
				
				
				Upload upload;// Объект для загрузки в realbase!!!
               // Uri downloadUri = task.getResult();
                upload = new Upload(fFile.getName(),
                		blob.getSelfLink(),
                		blob.getMediaLink());
                           //* taskSnapshot.getUploadSessionUri().toString());*//*

                //adding an upload to firebase database
                mDatabase = FirebaseDatabase.getInstance().getReference();
                String uploadId = mDatabase.push().getKey();
                mDatabase.child("my_files").child(uploadId).setValueAsync(upload);
                
                //progressDialog.dismiss();
                //Toast.makeText(getActivity().getApplicationContext(), "Файл отправлен!", Toast.LENGTH_SHORT).show();
				//System.out.println(blob2.);
			}
        	catch (Exception ex)
        	{
				ex.getMessage();
			}
        }
        else
        {
        	System.out.println("No selected file!!!");
        }

    }
    
}

