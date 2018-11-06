package mygpsx;

import java.io.IOException;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class FXCreateTemplateCtrl implements Initializable{

	DatabaseReference mDatabaseCurrentTmpl;
	private ArrayList<CStructAttrTmpl> m_alAttrjob = null;
	private ObservableList<CStructAttrTmpl> m_ObservableList;
	@FXML
	private ListView<CStructAttrTmpl> fxListTmplJob;
	
	public static String m_stTempNameJob = "Временное название"; // Это чтобы пото меняли на нормальное название!!
	//fxBtnCloseFrame
	@FXML
	private Button fxBtnCloseFrame;
	@FXML
	private Button fxBtnPreview;
	@FXML
	private Label fxLbErrorSaveTmplJob;
	@FXML
	private TextField fxTxtNameTmplJob;
	
	@FXML
    private void btnPreview(ActionEvent event) throws IOException 
    {
		 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXPreviewTemplate));
         CLPSMain.m_rootFXPreviewTemplate = (Parent)fxmlLoader.load();
         CLPSMain.m_stageFXPreviewTemplate = new Stage();
         CLPSMain.m_stageFXPreviewTemplate.setTitle(CStrings.m_APP_NAME + "->Предварительный просмотр шаблона...");
         CLPSMain.m_stageFXPreviewTemplate.setScene(new Scene(CLPSMain.m_rootFXPreviewTemplate));  
         CLPSMain.m_stageFXPreviewTemplate.setResizable(false);
         CLPSMain.m_stageFXPreviewTemplate.initModality(Modality.WINDOW_MODAL);// Было , кода думал, что так лучше))) Но так не выбрать координаты!!!
         CLPSMain.m_stageFXPreviewTemplate.initOwner(CLPSMain.m_stageFXCreateTemplate);
         CLPSMain.m_stageFXPreviewTemplate.show();
		 System.out.println("btnPreview(ActionEvent event)!!!");
    }
	
	@FXML
    private void btnCloseFrame(ActionEvent event) 
    {
		 if(fxTxtNameTmplJob.getText().equals(m_stTempNameJob))
		 {
			 fxLbErrorSaveTmplJob.setText("<-СДЕЛАЙТЕ НАЗВАНИЕ ШАБЛОНА УНИКАЛЬНЫМ!");
			 //System.out.println("CLPSMain.mDatabase.child(CMAINCONSTANTS.m_UniqueTempIDTempate).removeValue();");
			 //CLPSMain.mDatabase.child(CMAINCONSTANTS.m_UniqueTempIDTempate).removeValue();
		 }
		 else// Здесь все сохраняем!!!
		 {
			 // Здесь все сохраняем!!!
			 CLPSMain.mDatabase = FirebaseDatabase.getInstance()
						.getReference()
						.child(CMAINCONSTANTS.FB_my_owner_settings)
						.child(CMAINCONSTANTS.FB_my_templates);
			 CLPSMain.mDatabase.child(CMAINCONSTANTS.m_UniqueTempIDTempate).
			 child("MyNameTemplate").setValue(fxTxtNameTmplJob.getText());
			 ((Stage)fxBtnCloseFrame.getScene().getWindow()).close();// И успешно закрываем окно!!!
		 }
		

    }
	@FXML
    private void btnOpenFrameWithAttributes(ActionEvent event)// Открываем окно для добавления атрибутов!!!
    {
		System.out.println("FrameSettingsAttrjobEdit!!!");
    	CCONSTANTS_EVENTS_JOB.SAMPLE_JOBING = "ADD_SHIP";
    	try 
    	{
    		CCONSTANTS_EVENTS_JOB.SAMPLE_ANY_OR_ANY = "ADD";
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXAttrjobEdit));
            CLPSMain.m_rootAttrjobEdit = (Parent)fxmlLoader.load();
            CLPSMain.m_stageAttrjobEdit = new Stage();
            CLPSMain.m_stageAttrjobEdit.setTitle(CStrings.m_APP_NAME + "->Добавление сущностей в шаблон");
            CLPSMain.m_stageAttrjobEdit.setScene(new Scene(CLPSMain.m_rootAttrjobEdit));  
            CLPSMain.m_stageAttrjobEdit.setResizable(false);
            CLPSMain.m_stageAttrjobEdit.initModality(Modality.WINDOW_MODAL);// Было , кода думал, что так лучше))) Но так не выбрать координаты!!!
            CLPSMain.m_stageAttrjobEdit.initOwner(CLPSMain.m_stageFXCreateTemplate);
            CLPSMain.m_stageAttrjobEdit.show();
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fxTxtNameTmplJob.setText(m_stTempNameJob);// Это чтобы пото меняли на нормальное название!!
		fxLbErrorSaveTmplJob.setText("ИЗМЕНИТЕ НАЗВАНИЕ ШАБЛОНА!");
		
		CLPSMain.mDatabase = FirebaseDatabase.getInstance()
				.getReference()
				.child(CMAINCONSTANTS.FB_my_owner_settings)
				.child(CMAINCONSTANTS.FB_my_templates);
		CMAINCONSTANTS.m_UniqueTempIDTempate = CLPSMain.mDatabase.push().getKey();
		CLPSMain.mDatabase.child(CMAINCONSTANTS.m_UniqueTempIDTempate).child("MyIDUnique").setValue(CMAINCONSTANTS.m_UniqueTempIDTempate);
		CLPSMain.mDatabase.child(CMAINCONSTANTS.m_UniqueTempIDTempate).child("MyNameTemplate").setValue(m_stTempNameJob);
		
		// Здесь инициализируем список сущностей(атрибутов добавленных в шаблон задачи)
		try
		{
			mDatabaseCurrentTmpl = FirebaseDatabase.getInstance().getReference()
					.child(CMAINCONSTANTS.FB_my_owner_settings)
					.child(CMAINCONSTANTS.FB_my_templates)
					.child(CMAINCONSTANTS.m_UniqueTempIDTempate)
					.child(CMAINCONSTANTS.FB_my_adding_attr);
			mDatabaseCurrentTmpl.addValueEventListener(new ValueEventListener()
			 {
				@Override
				public void onDataChange(DataSnapshot arg0)
				{
					try 
					{
			            Iterable<DataSnapshot> contactChildren = arg0.getChildren();
			        	
			            m_alAttrjob = new ArrayList<CStructAttrTmpl>();
			            for (DataSnapshot structAttrjob : contactChildren)
		                {
			            	CStructAttrTmpl TempSP = structAttrjob.getValue(CStructAttrTmpl.class);
                        	System.out.println( "String CStructAttrTmpl = "  + TempSP);
                        	m_alAttrjob.add(TempSP);// Заполнили массив!!!
	                	}
			            m_ObservableList = FXCollections.observableArrayList (m_alAttrjob);
			            
			            
			            try 
			            {
			            	Platform.runLater(
	            			  () -> {
	            				  fxListTmplJob.setItems(m_ObservableList);
	            				// fxListViewPriority.setPrefSize(200, 500);
	            				  fxListTmplJob.setCellFactory(new Callback<ListView<CStructAttrTmpl>, ListCell<CStructAttrTmpl>>() {
									
	            					  @Override
										public ListCell<CStructAttrTmpl> call(ListView<CStructAttrTmpl> param)
										{
										// TODO Auto-generated method stub
										return new CUserCellIntoTmpl();
									}
								});
	            				  /*setCellFactory(new Callback<ListView<CStructAttrTmpl>, ListCell<CStructAttrTmpl>>() {
									
									@Override
									public ListCell<CStructAttrTmpl> call(ListView<CStructAttrTmpl> param)
									{
										// TODO Auto-generated method stub
										return new CStructAttrTmpl();
									}
								});*/
	            				  fxListTmplJob.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
