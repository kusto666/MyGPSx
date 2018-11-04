package mygpsx;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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

public class CAttrobjEditController implements Initializable
{
	private ObservableList<CStructAttrobj> m_ObservableList;
	@FXML
	private ListView<CStructAttrobj> fxListViewAttrobj;

	private ArrayList<CStructAttrobj> m_alAttrobj = null;
	
	@FXML
	private Parent m_rootAttrobjAdd = null;
	@FXML
	public static Stage m_stageAttrobjAdd = null;

	 // Открытие окна добавление статуса задачи!!! 
    @FXML
    private void FrameAddAttrobj(ActionEvent event) 
    {
    	System.out.println("FrameAddAttrjob - OPEN!!!");
    	try 
    	{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXAttrobjAdd));
            m_rootAttrobjAdd = (Parent)fxmlLoader.load();
            m_stageAttrobjAdd = new Stage();
            m_stageAttrobjAdd.setTitle(CStrings.m_APP_NAME + "->Добавление атрибута объекта");
            m_stageAttrobjAdd.setScene(new Scene(m_rootAttrobjAdd));  
            m_stageAttrobjAdd.setResizable(false);
            m_stageAttrobjAdd.initModality(Modality.WINDOW_MODAL);
            m_stageAttrobjAdd.initOwner(CLPSMain.m_stageAttrobjEdit);
            m_stageAttrobjAdd.show();
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
		System.out.println("CAttrobjEditController - initialize!!!");
		try
		{
			CLPSMain.mDatabase = FirebaseDatabase.getInstance().getReference()
					.child(CMAINCONSTANTS.FB_my_owner_settings)
					.child(CMAINCONSTANTS.FB_my_attrobj);
			CLPSMain.mDatabase.addValueEventListener(new ValueEventListener()
			 {
				@Override
				public void onDataChange(DataSnapshot arg0)
				{
					try 
					{
			            Iterable<DataSnapshot> contactChildren = arg0.getChildren();
			        	
			            m_alAttrobj = new ArrayList<CStructAttrobj>();
			            for (DataSnapshot structAttrobj : contactChildren)
		                {
			            	CStructAttrobj TempSP = structAttrobj.getValue(CStructAttrobj.class);
                        	System.out.println( "CStructAttrobj = "  + TempSP.getMyNameAttrobj());
                        	m_alAttrobj.add(TempSP);// Заполнили массив!!!
	                	}
			            m_ObservableList = FXCollections.observableArrayList (m_alAttrobj);
			            
			            
			            try 
			            {
			            	Platform.runLater(
	            			  () -> {
	            				  fxListViewAttrobj.setItems(m_ObservableList);
	            				// fxListViewPriority.setPrefSize(200, 500);
	            				  fxListViewAttrobj.setCellFactory(new Callback<ListView<CStructAttrobj>, ListCell<CStructAttrobj>>() {
									
									@Override
									public ListCell<CStructAttrobj> call(ListView<CStructAttrobj> param)
									{
										// TODO Auto-generated method stub
										return new CUserCellAttrobj();
									}
								});
	            				  fxListViewAttrobj.setOnMouseClicked(new EventHandler<MouseEvent>() {
	            		    			@Override
	            		    			public void handle(MouseEvent click)
	            		    			{
	            		    				if (click.getClickCount() == 1) 
	            		    		        {
	            		    					CStructAttrobj StrAttrobj = fxListViewAttrobj.getSelectionModel().getSelectedItem();
	            		    					System.out.println("StrAttrobj = " + StrAttrobj.getMyIDUnique());
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
		
		
	}
	

}
