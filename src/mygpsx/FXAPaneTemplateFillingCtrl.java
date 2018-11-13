package mygpsx;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

public class FXAPaneTemplateFillingCtrl implements Initializable{

	DatabaseReference mDatabaseCurrentTmpl;
	DatabaseReference mDatabaseMyIDTmplSelected;
	
	private ArrayList<CStructAttrTmplFilling> m_alAttrjob = null;
	private ObservableList<CStructAttrTmplFilling> m_ObservableList;
	@FXML
	private ListView<CStructAttrTmplFilling> fxListTmplJob;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		mDatabaseMyIDTmplSelected = FirebaseDatabase.getInstance().getReference()
				.child(CMAINCONSTANTS.FB_MyIDTmplSelected);
		mDatabaseMyIDTmplSelected.addValueEventListener(new ValueEventListener()
		 {
			@Override
			public void onDataChange(DataSnapshot arg0)
			{
				CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_TMPL = (String) arg0.getValue();
				System.out.println( "Listen FXAPaneTemplateFillingCtrl  = " + CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_TMPL);
				
				// ����� �������������� ������ ���������(��������� ����������� � ������ ������)
				try
				{
					mDatabaseCurrentTmpl = FirebaseDatabase.getInstance().getReference()
							.child(CMAINCONSTANTS.FB_my_owner_settings)
							.child(CMAINCONSTANTS.FB_my_templates)
							.child(CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_TMPL)
							.child(CMAINCONSTANTS.FB_my_adding_attr);
					mDatabaseCurrentTmpl.addValueEventListener(new ValueEventListener()
					 {
						@Override
						public void onDataChange(DataSnapshot arg0)
						{
							try 
							{
								
					            Iterable<DataSnapshot> contactChildren = arg0.getChildren();
					           // long lCountChildren = arg0.getChildrenCount();
					        	
					            m_alAttrjob = new ArrayList<CStructAttrTmplFilling>();
					            for (DataSnapshot structAttrjob : contactChildren)
				                {
					            	CStructAttrTmplFilling TempSP = structAttrjob.getValue(CStructAttrTmplFilling.class);
		                        	//System.out.println( "String CStructAttrTmpl = "  + TempSP);
		                        	m_alAttrjob.add(TempSP);// ��������� ������!!!
			                	}
					            m_ObservableList = FXCollections.observableArrayList (m_alAttrjob);
					            
					            
					            try 
					            {
					            	Platform.runLater(
			            			  () -> {
			            				  System.out.println("FXAPaneTemplateFillingCtrl: ������ � ���������� �������!!!");
			            				  fxListTmplJob.setItems(m_ObservableList);
			            				  fxListTmplJob.setCellFactory(new Callback<ListView<CStructAttrTmplFilling>, ListCell<CStructAttrTmplFilling>>()
			            				  {
			            					    @Override
												public ListCell<CStructAttrTmplFilling> call(ListView<CStructAttrTmplFilling> param)
												{
			            						  return new CUserCellIntoTmplFilling();
												}
										  });
			            				  fxListTmplJob.setOnMouseClicked(new EventHandler<MouseEvent>() 
			            				  {
			            		    			@Override
			            		    			public void handle(MouseEvent click)
			            		    			{
			            		    				if (click.getClickCount() == 1) 
			            		    		        {
			            		    					System.out.println("click.getClickCount() == 1");
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
				
				
				
				
				
				
				
				
				
/*				for(i = 0; i < m_alAttrjob.size(); i++)
				{*/
/*					if(((CStructUser)m_alAttrjob.get(i)).getMyPhoneID().equals(arg0.getValue()))
					{*/
						try 
						{
							//System.out.println("������ �................ - i = " + Integer.toString(i));

							
							Platform.runLater(() ->
							{
            				  	//fxCbSelectUser.setValue(m_alAttrjob.get(i));
            			    });
							//break;
						} 
						catch (Exception e)
						{
							e.printStackTrace();
						}
						
					//}
			//	}
			}

			@Override
			public void onCancelled(DatabaseError arg0) {
		
			}
		 });
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	}

}
