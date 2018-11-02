package mygpsx;

import java.net.URL;
import java.sql.Time;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.Marker;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import netscape.javascript.JSObject;

public class CAddShipController implements Initializable{
	 
	DatabaseReference mDatabaseRef;
	
	 @FXML
	 TextField fxLbNameShip;
	 @FXML
	 TextField fxLbDirectorShip;
	 @FXML
	 TextArea fxTaShortDescriptionShip;
	 @FXML
	 Label flLbInfoSaveErrors;
	 @FXML
	 Label fxLbLatitudeText;
	 @FXML
	 Label fxLbLongitudeText;
	 @FXML
	 private CMainController objectController;
	 

	/* public CAddShipController(CMainController objectController) 
	 {
		this.objectController = objectController;
		fxLbLatitudeText = objectController.getMyLabelLatitude();
	 }
	 public void writeThis(String whatToWrite) {
		 fxLbLatitudeText.setText(whatToWrite);
	    }*/

	 @FXML
	 private void btnAddShip(ActionEvent event) 
	 {
		 // РўРµРєСѓС‰Р°СЏ РґР°С‚Р° РґР»СЏ СЃРѕР·РґР°РЅРёСЏ СЃСѓРґРЅР°(РѕР±СЉРµРєС‚Р° - РїРѕРєР° РЅРµ Р·РЅР°СЋ РґР»СЏ С‡РµРіРѕ))))
		Date date = new Date();
		long lUnixTimeCreate = date.getTime();
		System.out.println("lUnixTimeCreate = " + Long.toString(lUnixTimeCreate));
		System.out.println("btnAddShip!!!");
		
		// РЎР»СѓС‡Р°Р№РЅС‹Р№ СѓРЅРёРєР°Р»СЊРЅС‹Р№ ID СѓСЃС‚СЂРѕР№СЃС‚РІР° - Рє РїСЂРёРјСѓСЂСѓ!!!
		String symbols = "abcdefghijklmnopqrstuvwxyz";
		String randomIDPhoneTest= new Random().ints(11, 0, symbols.length())
		    .mapToObj(symbols::charAt)
		    .map(Object::toString)
		    .collect(Collectors.joining());
		System.out.println("String random = " + randomIDPhoneTest);
		try 
   	 	{
			// Р Р°Р±РѕС‚Р°РµРј СЃ firebase - Р·Р°РїРёСЃС‹РІР°РµРј!!!
			mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users");
		

			// РџРѕС‚РѕРј РґРѕР±Р°РІРёРј РїСЂРѕРІРµСЂРєСѓ РЅР° РїСѓСЃС‚РѕС‚Сѓ РїРѕР»РµР№!!!
			if(fxLbNameShip.getText().replaceAll("\\s","").length() == 0)
			{
				flLbInfoSaveErrors.setText("Р—РђРџРћР›РќР�РўР• Р’РЎР• РџРћР›РЇ!");
				return;
			}
			if(fxLbDirectorShip.getText().replaceAll("\\s","").length() == 0)
			{
				flLbInfoSaveErrors.setText("Р—РђРџРћР›РќР�РўР• Р’РЎР• РџРћР›РЇ!");
				return;
			}
			if(fxTaShortDescriptionShip.getText().replaceAll("\\s","").length() == 0)
			{
				flLbInfoSaveErrors.setText("Р—РђРџРћР›РќР�РўР• Р’РЎР• РџРћР›РЇ!");
				return;
			}
			//if(fxLbNameShip.getText() == "" || fxLbDirectorShip == "" || )
			
			
			// РџСЂРѕРІРµСЂРєР° РЅР° РєРѕРѕСЂРґРёРЅР°С‚С‹ РґР»СЏ СЃРѕР·РґРѕРІР°РµРјРѕРіРѕ РѕР±СЉРµРєС‚Р°!!!
			if(CMainController.m_LocationTempForCAddShipController == null)
			{
				flLbInfoSaveErrors.setText("РћРўРЎРЈРўРЎРўР’РЈР®Рў РљРћРћР Р”Р�РќРђРўР«! Р’Р«Р‘Р•Р Р•РўР• РќРђ РљРђР РўР• РўРћР§РљРЈ РџРћР—Р�Р¦Р�Р�.");
				return;
			}
			mDatabaseRef.child("phoneID_" + randomIDPhoneTest).child("phoneID").
			setValue(randomIDPhoneTest);
			mDatabaseRef.child("phoneID_" + randomIDPhoneTest).child("MyLatitude").
			setValue(Double.toString(CMainController.m_LocationTempForCAddShipController.getLatitude()));
			mDatabaseRef.child("phoneID_" + randomIDPhoneTest).child("MyLongitude").
			setValue(Double.toString(CMainController.m_LocationTempForCAddShipController.getLongitude()));
			mDatabaseRef.child("phoneID_" + randomIDPhoneTest).child("MyNameShip").
			setValue(fxLbNameShip.getText());
			mDatabaseRef.child("phoneID_" + randomIDPhoneTest).child("MyDirectorShip").
			setValue(fxLbDirectorShip.getText());
			mDatabaseRef.child("phoneID_" + randomIDPhoneTest).child("MyShortDescriptionShip").
			setValue(fxTaShortDescriptionShip.getText());
			
			flLbInfoSaveErrors.setText("");
			CMainController.m_LocationTempForCAddShipController = null;
            System.out.println("РўРёРїР° СЃРѕР·РґР°Р»Рё РєРѕСЂР°Р±Р»РёРє!!!");
            CLPSMain.m_stageAddShip.close();
        } 
   	 	catch (Exception e) 
   	 	{
            e.printStackTrace();
        }
		
		
	 }
	 
	 @FXML
	 private void btnInsertStartCoords(ActionEvent event) 
	 {
		 System.out.println("btnInsertStartCoords!!!");
	 }
	 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	

}
