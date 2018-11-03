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

public class CStatusEditController implements Initializable
{
	private ObservableList<CStructPriority> m_ObservableList;
	@FXML
	private ListView<CStructPriority> fxListViewStatus;

	private ArrayList<CStructPriority> m_alStatus = null;
	
	@FXML
	private Parent m_rootStatusAdd = null;
	@FXML
	public static Stage m_stageStatusAdd = null;

	 // Открытие окна добавление статуса задачи!!! 
    @FXML
    private void FrameAddPriority(ActionEvent event) 
    {
    	System.out.println("FrameAddPriority - OPEN!!!");
    	try 
    	{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXPriorityAdd));
            m_rootStatusAdd = (Parent)fxmlLoader.load();
            m_stageStatusAdd = new Stage();
            m_stageStatusAdd.setTitle(CStrings.m_APP_NAME + "->Добавление приоритета");
            m_stageStatusAdd.setScene(new Scene(m_rootStatusAdd));  
            m_stageStatusAdd.setResizable(false);
            m_stageStatusAdd.initModality(Modality.WINDOW_MODAL);
            m_stageStatusAdd.initOwner(CLPSMain.m_stagePriorityEdit);
            m_stageStatusAdd.show();
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
		System.out.println("CPriorityEditController - initialize!!!");
		try
		{
			CLPSMain.mDatabase = FirebaseDatabase.getInstance().getReference()
					.child(CMAINCONSTANTS.FB_my_owner_settings)
					.child(CMAINCONSTANTS.FB_my_priority);
			CLPSMain.mDatabase.addValueEventListener(new ValueEventListener()
			 {
				@Override
				public void onDataChange(DataSnapshot arg0)
				{
					try 
					{
			            Iterable<DataSnapshot> contactChildren = arg0.getChildren();
			        	
			            m_alStatus = new ArrayList<CStructPriority>();
			            for (DataSnapshot structPriority : contactChildren)
		                {
			            	CStructPriority TempSP = structPriority.getValue(CStructPriority.class);
                        	System.out.println( "CStructPriority = "  + TempSP.getMyNamePriority());
                        	m_alStatus.add(TempSP);// Заполнили массив!!!
	                	}
			            m_ObservableList = FXCollections.observableArrayList (m_alStatus);
			            
			            
			            try 
			            {
			            	Platform.runLater(
	            			  () -> {
	            				  fxListViewStatus.setItems(m_ObservableList);
	            				// fxListViewPriority.setPrefSize(200, 500);
	            				  fxListViewStatus.setCellFactory(new Callback<ListView<CStructPriority>, ListCell<CStructPriority>>() {

									@Override
									public ListCell<CStructPriority> call(ListView<CStructPriority> param) {
										// TODO Auto-generated method stub
										return new CUserCellPriority();
									}
								});
	            				 
	            				  fxListViewStatus.setOnMouseClicked(new EventHandler<MouseEvent>() {
	            		    			@Override
	            		    			public void handle(MouseEvent click)
	            		    			{
	            		    				if (click.getClickCount() == 1) 
	            		    		        {
	            		    					CStructPriority StrPrior = fxListViewStatus.getSelectionModel().getSelectedItem();
	            		    					System.out.println("StrPrior = " + StrPrior.getMyIDUnique());
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
