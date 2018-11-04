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

public class CTypedescjobEditController implements Initializable
{
	private ObservableList<CStructTypedescjob> m_ObservableList;
	@FXML
	private ListView<CStructTypedescjob> fxListViewTypedescjob;

	private ArrayList<CStructTypedescjob> m_alTypedescjob = null;
	
	@FXML
	private Parent m_rootTypedescjobAdd = null;
	@FXML
	public static Stage m_stageTypedescjobAdd = null;

	 // Открытие окна добавление статуса задачи!!! 
    @FXML
    private void FrameAddTypedescjob(ActionEvent event) 
    {
    	System.out.println("FrameTypedescjobjob - OPEN!!!");
    	try 
    	{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXTypedescjobAdd));
            m_rootTypedescjobAdd = (Parent)fxmlLoader.load();
            m_stageTypedescjobAdd = new Stage();
            m_stageTypedescjobAdd.setTitle(CStrings.m_APP_NAME + "->Добавление типового описания");
            m_stageTypedescjobAdd.setScene(new Scene(m_rootTypedescjobAdd));  
            m_stageTypedescjobAdd.setResizable(false);
            m_stageTypedescjobAdd.initModality(Modality.WINDOW_MODAL);
            m_stageTypedescjobAdd.initOwner(CLPSMain.m_stageTypedescjobEdit);
            m_stageTypedescjobAdd.show();
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
		System.out.println("CTypedescjobEditController - initialize!!!");
		try
		{
			CLPSMain.mDatabase = FirebaseDatabase.getInstance().getReference()
					.child(CMAINCONSTANTS.FB_my_owner_settings)
					.child(CMAINCONSTANTS.FB_my_typedescjob);
			CLPSMain.mDatabase.addValueEventListener(new ValueEventListener()
			 {
				@Override
				public void onDataChange(DataSnapshot arg0)
				{
					try 
					{
			            Iterable<DataSnapshot> contactChildren = arg0.getChildren();
			        	
			            m_alTypedescjob = new ArrayList<CStructTypedescjob>();
			            for (DataSnapshot structAttrjob : contactChildren)
		                {
			            	CStructTypedescjob TempSP = structAttrjob.getValue(CStructTypedescjob.class);
                        	System.out.println( "CStructTypedescjob = "  + TempSP.getMyNameTypedescjob());
                        	m_alTypedescjob.add(TempSP);// Заполнили массив!!!
	                	}
			            m_ObservableList = FXCollections.observableArrayList (m_alTypedescjob);
			            
			            
			            try 
			            {
			            	Platform.runLater(
	            			  () -> {
	            				  fxListViewTypedescjob.setItems(m_ObservableList);
	            				// fxListViewPriority.setPrefSize(200, 500);
	            				  fxListViewTypedescjob.setCellFactory(new Callback<ListView<CStructTypedescjob>, ListCell<CStructTypedescjob>>() {
									
									@Override
									public ListCell<CStructTypedescjob> call(ListView<CStructTypedescjob> param)
									{
										// TODO Auto-generated method stub
										return new CUserCellTypedescjob();
									}
								});
	            				  fxListViewTypedescjob.setOnMouseClicked(new EventHandler<MouseEvent>() {
	            		    			@Override
	            		    			public void handle(MouseEvent click)
	            		    			{
	            		    				if (click.getClickCount() == 1) 
	            		    		        {
	            		    					CStructTypedescjob StrTypedescjob = fxListViewTypedescjob.getSelectionModel().getSelectedItem();
	            		    					System.out.println("StrTypedescjob = " + StrTypedescjob.getMyIDUnique());
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
