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

public class CTypejobEditController implements Initializable
{
	private ObservableList<CStructTypejob> m_ObservableList;
	@FXML
	private ListView<CStructTypejob> fxListViewTypejob;

	private ArrayList<CStructTypejob> m_alTypejob = null;
	
	@FXML
	private Parent m_rootTypejobAdd = null;
	@FXML
	public static Stage m_stageTypejobAdd = null;

	 // Открытие окна добавление статуса задачи!!! 
    @FXML
    private void FrameAddTypejob(ActionEvent event) 
    {
    	System.out.println("FrameAddTypejob - OPEN!!!");
    	try 
    	{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXTypejobAdd));
            m_rootTypejobAdd = (Parent)fxmlLoader.load();
            m_stageTypejobAdd = new Stage();
            m_stageTypejobAdd.setTitle(CStrings.m_APP_NAME + "->Добавление типа задачи");
            m_stageTypejobAdd.setScene(new Scene(m_rootTypejobAdd));  
            m_stageTypejobAdd.setResizable(false);
            m_stageTypejobAdd.initModality(Modality.WINDOW_MODAL);
            m_stageTypejobAdd.initOwner(CLPSMain.m_stageTypejobEdit);
            m_stageTypejobAdd.show();
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
		System.out.println("CTypejobEditController - initialize!!!");
		try
		{
			CLPSMain.mDatabase = FirebaseDatabase.getInstance().getReference()
					.child(CMAINCONSTANTS.FB_my_owner_settings)
					.child(CMAINCONSTANTS.FB_my_typejob);
			CLPSMain.mDatabase.addValueEventListener(new ValueEventListener()
			 {
				@Override
				public void onDataChange(DataSnapshot arg0)
				{
					try 
					{
			            Iterable<DataSnapshot> contactChildren = arg0.getChildren();
			        	
			            m_alTypejob = new ArrayList<CStructTypejob>();
			            for (DataSnapshot structTypejob : contactChildren)
		                {
			            	CStructTypejob TempSP = structTypejob.getValue(CStructTypejob.class);
                        	System.out.println( "CStructTypejob = "  + TempSP.getMyNameTypejob());
                        	m_alTypejob.add(TempSP);// Заполнили массив!!!
	                	}
			            m_ObservableList = FXCollections.observableArrayList (m_alTypejob);
			            
			            
			            try 
			            {
			            	Platform.runLater(
	            			  () -> {
	            				  fxListViewTypejob.setItems(m_ObservableList);

	            				  fxListViewTypejob.setCellFactory(new Callback<ListView<CStructTypejob>, ListCell<CStructTypejob>>() {
									
									@Override
									public ListCell<CStructTypejob> call(ListView<CStructTypejob> arg0) {
										// TODO Auto-generated method stub
										return new CUserCellTypejob();
									}
								});
	            				  
	            				  /*.setCellFactory(new Callback<ListView<CStructTypejob>, ListCell<CStructTypejob>>() {
									
									@Override
									public ListCell<CStructTypejob> call(ListView<CStructTypejob> param)
									{
										// TODO Auto-generated method stub
										return new CUserCellTypejob();
									}
								});*/
	            				  fxListViewTypejob.setOnMouseClicked(new EventHandler<MouseEvent>() {
	            		    			@Override
	            		    			public void handle(MouseEvent click)
	            		    			{
	            		    				if (click.getClickCount() == 1) 
	            		    		        {
	            		    					CStructTypejob StrStatus = fxListViewTypejob.getSelectionModel().getSelectedItem();
	            		    					System.out.println("StrPrior = " + StrStatus.getMyIDUnique());
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
