package mygpsx;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.google.api.services.bigquery.Bigquery.Datasets.List;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class CFXListTmplJobsCtrl implements Initializable{

	DatabaseReference mDatabaseCurrentTmpl;
	private ArrayList<CStructTmplJob> m_alTmplJob = null;
	private ObservableList<CStructTmplJob> m_ObservableList;
	@FXML
	private ListView<CStructTmplJob> fxLvTmplJobs;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		try 
		{
			mDatabaseCurrentTmpl = FirebaseDatabase.getInstance().getReference()
					.child(CMAINCONSTANTS.FB_my_owner_settings)
					.child(CMAINCONSTANTS.FB_my_templates);
			mDatabaseCurrentTmpl.addValueEventListener(new ValueEventListener()
			 {
				@Override
				public void onDataChange(DataSnapshot arg0)
				{
					try 
					{
			            Iterable<DataSnapshot> contactChildren = arg0.getChildren();
			            //GenericTypeIndicator<java.util.List<CStructTmplJob>> t = new GenericTypeIndicator<java.util.List<CStructTmplJob>>() {};
			        	
			            m_alTmplJob = new ArrayList<CStructTmplJob>();
			            for (DataSnapshot structTmplJob : contactChildren)
		                {
			            	//CStructTmplJob TempSP = new CStructTmplJob(); 
			            	//String sttt = ((CStructTmplJob)structTmplJob.getValue(t)).getMyIDUnique();
			            	CStructTmplJob TempSP = structTmplJob.getValue(CStructTmplJob.class);
			            	//TempSP.setMyIDUnique(sttt);
	                       	//System.out.println( "String CStructTmplJob = "  + TempSP);
	                       	m_alTmplJob.add(TempSP);// Заполнили массив!!!
	                	}
			            m_ObservableList = FXCollections.observableArrayList (m_alTmplJob);
			            
			            
			            try 
			            {
			            	Platform.runLater(
	            			  () -> {
	            				  fxLvTmplJobs.setItems(m_ObservableList);
	            				// fxListViewPriority.setPrefSize(200, 500);
	            				  fxLvTmplJobs.setCellFactory(new Callback<ListView<CStructTmplJob>, ListCell<CStructTmplJob>>() {
									
	            					  @Override
										public ListCell<CStructTmplJob> call(ListView<CStructTmplJob> param)
										{
										// TODO Auto-generated method stub
										return new CUserCellTmplJob();
									}
								});
	            				  fxLvTmplJobs.setOnMouseClicked(new EventHandler<MouseEvent>() {
	            		    			@Override
	            		    			public void handle(MouseEvent click)
	            		    			{
	            		    				if (click.getClickCount() == 1) 
	            		    		        {
	            		    					//String StrAttrjob = fxListTmplJob.getSelectionModel().getSelectedItem();
	            		    					//System.out.println("StrAttrjob = " + StrAttrjob.getMyIDUnique());
	            		    					//System.out.println("StrAttrjob = " + StrAttrjob);
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
						e.printStackTrace();
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
		catch (Exception ex) 
		{
			ex.getMessage();
		}
		
		
	}

}
