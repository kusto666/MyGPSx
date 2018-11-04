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

public class CAttrjobEditController implements Initializable
{
	private ObservableList<CStructAttrjob> m_ObservableList;
	@FXML
	private ListView<CStructAttrjob> fxListViewAttrjob;

	private ArrayList<CStructAttrjob> m_alAttrjob = null;
	
	@FXML
	private Parent m_rootAttrjobAdd = null;
	@FXML
	public static Stage m_stageAttrjobAdd = null;

	 // Открытие окна добавление статуса задачи!!! 
    @FXML
    private void FrameAddAttrjob(ActionEvent event) 
    {
    	System.out.println("FrameAddAttrjob - OPEN!!!");
    	try 
    	{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXAttrjobAdd));
            m_rootAttrjobAdd = (Parent)fxmlLoader.load();
            m_stageAttrjobAdd = new Stage();
            m_stageAttrjobAdd.setTitle(CStrings.m_APP_NAME + "->Добавление атрибута");
            m_stageAttrjobAdd.setScene(new Scene(m_rootAttrjobAdd));  
            m_stageAttrjobAdd.setResizable(false);
            m_stageAttrjobAdd.initModality(Modality.WINDOW_MODAL);
            m_stageAttrjobAdd.initOwner(CLPSMain.m_stageAttrjobEdit);
            m_stageAttrjobAdd.show();
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
		System.out.println("CAttrjobEditController - initialize!!!");
		try
		{
			CLPSMain.mDatabase = FirebaseDatabase.getInstance().getReference()
					.child(CMAINCONSTANTS.FB_my_owner_settings)
					.child(CMAINCONSTANTS.FB_my_attrjob);
			CLPSMain.mDatabase.addValueEventListener(new ValueEventListener()
			 {
				@Override
				public void onDataChange(DataSnapshot arg0)
				{
					try 
					{
			            Iterable<DataSnapshot> contactChildren = arg0.getChildren();
			        	
			            m_alAttrjob = new ArrayList<CStructAttrjob>();
			            for (DataSnapshot structAttrjob : contactChildren)
		                {
			            	CStructAttrjob TempSP = structAttrjob.getValue(CStructAttrjob.class);
                        	System.out.println( "CStructStatus = "  + TempSP.getMyNameAttrjob());
                        	m_alAttrjob.add(TempSP);// Заполнили массив!!!
	                	}
			            m_ObservableList = FXCollections.observableArrayList (m_alAttrjob);
			            
			            
			            try 
			            {
			            	Platform.runLater(
	            			  () -> {
	            				  fxListViewAttrjob.setItems(m_ObservableList);
	            				// fxListViewPriority.setPrefSize(200, 500);
	            				  fxListViewAttrjob.setCellFactory(new Callback<ListView<CStructAttrjob>, ListCell<CStructAttrjob>>() {
									
									@Override
									public ListCell<CStructAttrjob> call(ListView<CStructAttrjob> param)
									{
										// TODO Auto-generated method stub
										return new CUserCellAttrjob();
									}
								});
	            				  fxListViewAttrjob.setOnMouseClicked(new EventHandler<MouseEvent>() {
	            		    			@Override
	            		    			public void handle(MouseEvent click)
	            		    			{
	            		    				if (click.getClickCount() == 1) 
	            		    		        {
	            		    					CStructAttrjob StrAttrjob = fxListViewAttrjob.getSelectionModel().getSelectedItem();
	            		    					System.out.println("StrAttrjob = " + StrAttrjob.getMyIDUnique());
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
