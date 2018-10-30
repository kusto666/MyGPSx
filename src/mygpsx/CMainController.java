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
import com.google.firebase.tasks.OnFailureListener;
import com.google.firebase.tasks.Task;
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

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;
import netscape.javascript.JSObject;

/**
 *
 * @author Dmitry
 */
public class CMainController implements Initializable, MapComponentInitializedListener {
	
	private Desktop desktop = Desktop.getDesktop();
	private File m_FileSelectedOne; // 
	@FXML
    private Label fxLbNameFileSelect;
	
    
	
	private String PATH_NAME_UPLOADS_MAIN = "uploads/";
	
	@FXML
	ListView<CUser> fxListView;
	public static ObservableList<CUser> m_ObservableListUsers;
	
	@FXML
    private Label label1;
    @FXML
    private Label label2;

	//Popup popWindow;
	// Позиция для 
	// Координаты для CAddShipController //////////////////////////////
	public static LatLong m_LocationTempForCAddShipController = null;
	 @FXML
	 Label fxLbLatitudeText;
	 @FXML
	 Label fxLbLongitudeText;
	 @FXML
	 CAddShipController m_AddShipController = null;
    /////////////////////////////////////////////////////////////////////
	private static DatabaseReference mDatabase;
	
	private static DatabaseReference mDatabaseRefSendMsg;
	//public static DatabaseReference mDatabaseRefUSers;
	//public static FirebaseApp defaultApp;
	/*private static ArrayList<CUser> localMarkersUsersTemp = null;*/
	
	
	
	
	// Это для тестовой проверки(temp) координат на карте!!!
	static LatLong LocationTemp = null;
	MarkerOptions markerOptionsTemp = null;
	Marker MarkerTemp = null;
	InfoWindowOptions infoWindowOptionsTemp = null;
	InfoWindow fredWilkeInfoWindowTemp = null;
	////////////////////////////////////////////////////////
	
	// Первый лист с кораблями!!!
	@FXML
	ListView<String> m_lvAllUsers;
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
    public static GoogleMap map;
    @FXML
    private GeocodingService geocodingService;
    
   /* @FXML
    public Label getMyLabelLatitude()
    {
        return fxLbLatitudeText;
    }*/
    
    // Добавить новый объект(типа пока судно!!!)
    @FXML
    private void btnAddShip(ActionEvent event) {
    	System.out.println("btnAddShip!!!");
    	CConstantsEventsClicksJob.SAMPLE_JOBING = "ADD_SHIP";
    	try {
	            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CLPSMain.m_PathFXAddShipFxml));
	            CLPSMain.m_rootAddShip = (Parent)fxmlLoader.load();
	            CLPSMain.m_stageAddShip = new Stage();
	            CLPSMain.m_stageAddShip.setTitle(CStrings.m_APP_NAME + " <<< Добавление судна... >>>");
	            CLPSMain.m_stageAddShip.setScene(new Scene(CLPSMain.m_rootAddShip));  
	            CLPSMain.m_stageAddShip.setResizable(false);
	           // CLPSMain.m_stageAddShip.initModality(Modality.WINDOW_MODAL);// Было , кода думал, что так лучше))) Но так не выбрать координаты!!!
	            CLPSMain.m_stageAddShip.setAlwaysOnTop(true); // А так ВЫБРАТЬ!!!
	            CLPSMain.m_stageAddShip.initOwner(CLPSMain.stage);
	            CLPSMain.m_stageAddShip.show();
	            
	            fxLbLatitudeText = (Label)fxmlLoader.getNamespace().get("fxLbLatitudeText");
	            fxLbLongitudeText = (Label)fxmlLoader.getNamespace().get("fxLbLongitudeText");
	            System.out.println("fxLbLatitudeText = " + fxLbLatitudeText.getText());
	            System.out.println("fxLbLongitudeText = " + fxLbLongitudeText.getText());
            
            }
    		catch(Exception e) 
    		{
               e.printStackTrace();
            }
    }
    @FXML
    private void handleDeleteAllMarkers(ActionEvent event) {
    	if(map != null)
    	{
    		map.clearMarkers();
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
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle(CStrings.m_APP_NAME);
    		alert.setHeaderText("КОСЯК!!!");
    		alert.setContentText("Что-то пошло не так)))\nМожет перегрузим карту???");
    		ButtonType buttonTypeYes = new ButtonType(CStrings.m_APP_NAME_CHOOSE_YES);
    		ButtonType buttonTypeNo = new ButtonType(CStrings.m_APP_NAME_CHOOSE_NO);
    		
    		alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
    		
    		/*java.util.Optional<ButtonType> result = alert.showAndWait();
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
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle(CStrings.m_APP_NAME);
    		alert.setHeaderText("КОСЯК!!!");
    		alert.setContentText("Что-то пошло не так)))\nМожет перегрузим карту???");
    		ButtonType buttonTypeYes = new ButtonType(CStrings.m_APP_NAME_CHOOSE_YES);
    		ButtonType buttonTypeNo = new ButtonType(CStrings.m_APP_NAME_CHOOSE_NO);
    		
    		alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
    		
    		/*java.util.Optional<ButtonType> result = alert.showAndWait();
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
                //openFile(file); - for display on ext programms!!!
			} 
        	catch (Exception e)
        	{
				// TODO: handle exception
			}
        	
        }

    }
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
    		 mDatabaseRefSendMsg.child("msg_555555").child("msg_body").setValue(taOutMsg.getText().toString());
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
        		CUser us = (CUser)CLPSMain.m_localAllMarkersUsersTempMain.get(i);
        		
        		
        		LatLong markerLocation = new LatLong(Double.parseDouble(us.MyLatitude),
        												Double.parseDouble(us.MyLongitude));
        		MarkerOptions markerOptions = new MarkerOptions();
        		//markerOptions.i
        		//markerOptions.icon(iconPath)
        		markerOptions.position(markerLocation);
        		Marker MyMarker = new Marker(markerOptions);
        		
        		
                map.addMarker( MyMarker );
                markerMap.put(MyMarker, false);

                InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                infoWindowOptions.content("<h4>Имя судна:</h4>" + 
										"<u style=\"color: blue;\"><h4>" + us.getMyNameShip() + "</h4></u>" +
										"<h4>Капитан:</h4>" + 
										"<u style=\"color: blue;\"><h4>" + us.getMyDirectorShip() + "</h4></u>" +
										"<h4>Описание судна:</h4>" + 
										"<u style=\"color: blue;\"><h4>" + us.MyShortDescriptionShip + "</h4></u>");

                InfoWindow MyInfoWindow = new InfoWindow(infoWindowOptions);
                
                map.addUIEventHandler(MyMarker, UIEventType.click, (JSObject obj) -> {
                    if (markerMap.get(MyMarker)) 
                    {

                    	MyInfoWindow.close();
                        markerMap.put(MyMarker, false);
                        m_bIsMuveMarker = false;
                        m_SELECTED_MARKER = null;
                    }
                    else 
                    {
                    	MyInfoWindow.open(map, MyMarker);
                        markerMap.put(MyMarker, true);
                        m_bIsMuveMarker = true;
                        System.out.println("us.phoneID == " + us.phoneID);
                        m_SELECTED_MARKER = us.phoneID;
                    }
                });
                
        		System.out.println("us.phoneID == " + us.phoneID);
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
    	if(!ShowAllMarkersAfterRefresh())
    	{
    		System.out.println("btnRefreshAllMarkers(); - а потом показываем!!!");
    		btnRefreshAllMarkers();
    	}
    	else
    	{
    		System.out.println("Сразу показываем, так как уже обновили!!!");
    	}
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
            			 CLPSMain.m_localAllMarkersUsersTempMain = new ArrayList<>();
        				 //MarkerOptions mo = null;
        			    // LatLong ll = null;
        			     CUser tempTransport = null;
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
                        	CUser user = arg.getValue(CUser.class);
                        	System.out.println( ">>>>>>>>>user.phoneID =  " + user.phoneID + "<<<<<<<<<<<<");
                        	Double.parseDouble(user.MyLatitude);
                            Double.parseDouble(user.MyLongitude);
                            System.out.println( "user.MyLatitude = " + user.MyLatitude );
                            System.out.println( "user.MyLongitude = " + user.MyLongitude );
                            
                            tempTransport = new CUser(user.phoneID, user.MyLatitude, user.MyLongitude, "",
                            		user.getMyNameShip(), user.getMyDirectorShip(), user.MyShortDescriptionShip);
                            CLPSMain.m_localAllMarkersUsersTempMain.add(tempTransport);
                            System.out.println( "----------Конец маркера!!!-------------" );
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
    				System.out.println( "Firebase - косяк!!!" );
    			}
    		});
		} 
    	catch (Exception ex) 
    	{
			ex.getMessage();
		}

    }
    @FXML
    public void mapInitialized() 
    {
    	try 
    	{
    		geocodingService = new GeocodingService();
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

 	        map = mapView.createMap(mapOptions);
       
 	        mapReady(markerMap);
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
            map.addUIEventHandler(UIEventType.click, (JSObject obj) -> {
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
                
                
                if(CConstantsEventsClicksJob.SAMPLE_JOBING == "ADD_SHIP")
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
                             	 mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child("phoneID_" + m_SELECTED_MARKER);
                            	 try 
                            	 {
                                     mDatabase.child("MyLatitude").setValue(Double.toString(ll.getLatitude()));
                                     mDatabase.child("MyLongitude").setValue(Double.toString(ll.getLongitude()));

                                 }
                            	 catch (Exception e) 
                            	 {
                                     e.printStackTrace();
                                 }
                            	 System.out.println("Типа послали сообщение!!!");
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
		}
    	catch (Exception ex) 
    	{
			ex.getMessage();
		}

    }
    @Override
    public synchronized void initialize(URL url, ResourceBundle rb)
    {
    	fxLbNameFileSelect.setText("");
    	//CLPSMain.MyEventListnerFireUsers();
    	/*fxListView.setCellFactory(new Callback<ListView<CUser>, ListCell<CUser>>() {
			
			@Override
			public ListCell<CUser> call(ListView<CUser> param) {
				// TODO Auto-generated method stub
				return new StudentListViewCell();
			}
		});*/
    	/*setCellFactory(new Callback<ListView<Student>, ListCell<Student>>() {
    	    @Override
    	    public ListCell<CUser> call(ListView<CUser> studentListView) {
    	        return new StudentListViewCell();
    	    }
    	});*/
    	try 
    	{
    	//	fxListView.setItems(m_ObservableListUsers);
    	//	fxListView.setCellFactory(studentListView -> new CMainController());
    	//	fxListView.setItems(CLPSMain.m_ObservableListUsers);
       //    fxListView.setCellFactory(fxListViewUsersMy -> new CMainController());
            mapView.addMapInializedListener(this);
		}
    	catch (Exception ex)
    	{
    		ex.getMessage();
		}

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
				com.google.cloud.storage.Blob blob2 = bucket.create(PATH_NAME_UPLOADS_MAIN + fFile.getName(), targetStream,"image/jpg");
				
				System.out.println(blob2.getSelfLink());
				System.out.println(blob2.getMediaLink());
				
				Upload upload;// Объект для загрузки в realbase!!!
               // Uri downloadUri = task.getResult();
                upload = new Upload(fFile.getName(),
                		blob2.getSelfLink(),
                		blob2.getMediaLink());
                           //* taskSnapshot.getUploadSessionUri().toString());*//*

                //adding an upload to firebase database
                mDatabase = FirebaseDatabase.getInstance().getReference();
                String uploadId = mDatabase.push().getKey();
                mDatabase.child("my_files").child(uploadId).setValue(upload);
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

