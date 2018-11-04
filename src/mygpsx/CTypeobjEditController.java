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

public class CTypeobjEditController implements Initializable
{
	private ObservableList<CStructTypeobj> m_ObservableList;
	@FXML
	private ListView<CStructTypeobj> fxListViewTypeobj;

	private ArrayList<CStructTypeobj> m_alTypeobj = null;
	
	@FXML
	private Parent m_rootTypeobjAdd = null;
	@FXML
	public static Stage m_stageTypeobjAdd = null;

	 // Открытие окна добавление статуса задачи!!! 
    @FXML
    private void FrameAddTypeobj(ActionEvent event) 
    {
    	System.out.println("FrameAddTypeobj- OPEN!!!");
    	try 
    	{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXTypeobjAdd));
            m_rootTypeobjAdd = (Parent)fxmlLoader.load();
            m_stageTypeobjAdd = new Stage();
            m_stageTypeobjAdd.setTitle(CStrings.m_APP_NAME + "->Добавление типа объекта");
            m_stageTypeobjAdd.setScene(new Scene(m_rootTypeobjAdd));  
            m_stageTypeobjAdd.setResizable(false);
            m_stageTypeobjAdd.initModality(Modality.WINDOW_MODAL);
            m_stageTypeobjAdd.initOwner(CLPSMain.m_stageTypeobjEdit);
            m_stageTypeobjAdd.show();
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
		System.out.println("CTypeobjEditController - initialize!!!");
		try
		{
			CLPSMain.mDatabase = FirebaseDatabase.getInstance().getReference()
					.child(CMAINCONSTANTS.FB_my_owner_settings)
					.child(CMAINCONSTANTS.FB_my_typeobj);
			CLPSMain.mDatabase.addValueEventListener(new ValueEventListener()
			 {
				@Override
				public void onDataChange(DataSnapshot arg0)
				{
					try 
					{
			            Iterable<DataSnapshot> contactChildren = arg0.getChildren();
			        	
			            m_alTypeobj = new ArrayList<CStructTypeobj>();
			            for (DataSnapshot structTypeobj : contactChildren)
		                {
			            	CStructTypeobj TempSP = structTypeobj.getValue(CStructTypeobj.class);
                        	System.out.println( "CStructTypeobj = "  + TempSP.getMyNameTypeobj());
                        	m_alTypeobj.add(TempSP);// Заполнили массив!!!
	                	}
			            m_ObservableList = FXCollections.observableArrayList (m_alTypeobj);
			            
			            
			            try 
			            {
			            	Platform.runLater(
	            			  () -> {
	            				  fxListViewTypeobj.setItems(m_ObservableList);

	            				  fxListViewTypeobj.setCellFactory(new Callback<ListView<CStructTypeobj>, ListCell<CStructTypeobj>>() {
									
									@Override
									public ListCell<CStructTypeobj> call(ListView<CStructTypeobj> arg0) {
										// TODO Auto-generated method stub
										return new CUserCellTypeobj();
									}
								});
	            				  fxListViewTypeobj.setOnMouseClicked(new EventHandler<MouseEvent>() {
	            		    			@Override
	            		    			public void handle(MouseEvent click)
	            		    			{
	            		    				if (click.getClickCount() == 1) 
	            		    		        {
	            		    					CStructTypeobj StrTypeobj = fxListViewTypeobj.getSelectionModel().getSelectedItem();
	            		    					System.out.println("StrTypeobj = " + StrTypeobj.getMyIDUnique());
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
