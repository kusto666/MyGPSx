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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class CFXCreateTemplateJobCtrl implements Initializable{

	DatabaseReference mDatabaseCurrentTmpl;
	private ArrayList<CStructAttrTmpl> m_alAttrjob = null;
	private ObservableList<CStructAttrTmpl> m_ObservableList;
	@FXML
	private ListView<CStructAttrTmpl> fxListTmplJob;
	
	public static String m_stTempNameJob = "Временное название"; // Это чтобы потом меняли на нормальное название!!

	@FXML
	private AnchorPane fxAPaneMain;
	@FXML
	private AnchorPane m_FXAPaneTemplateJobs;// Это панель в которой и находится сам лист с шаблоном!!!
	@FXML
	private AnchorPane fxAPaneEditTmpl;
	
	@FXML
	private Button fxBtnCloseFrame;
	@FXML
	private Button fxBtnPreview;
	@FXML
	private Label fxLbErrorSaveTmplJob;
	@FXML
	private TextField fxTxtNameTmplJob;
	
	/*@FXML
    private void btnPreview(ActionEvent event) throws IOException 
    {
		CCONSTANTS_EVENTS_JOB.OpenAnyFrame(CFXCreateTemplateJobCtrl.class, CMAINCONSTANTS.m_PathFXPreviewTemplate,
											CLPSMain.m_rootFXPreviewTemplateJobs, CLPSMain.m_stageFXPreviewTemplateJobs,
											"Предварительный просмотр шаблона...", false, true, false);
    }*/
	
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
			 //CCONSTANTS_EVENTS_JOB.TEMPLATE_FILLING_OR_EDIT = 1;
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
            CLPSMain.m_stageAttrjobEdit.initOwner(CLPSMain.m_stageFXCreateTemplateJobs);
            CLPSMain.m_stageAttrjobEdit.show();
        }
		catch(Exception e) 
		{
           e.printStackTrace();
        }
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		//CCONSTANTS_EVENTS_JOB.TEMPLATE_FILLING_OR_EDIT = 0;
		try 
		{
			CCONSTANTS_EVENTS_JOB.SAMPLE_ANY_OR_ANY = "ADD";
			// Добавим сразу в fxAPaneMain для шаблона файлик FXAPaneTemplateJobs.fxml
			m_FXAPaneTemplateJobs = FXMLLoader.load(getClass().getResource(CMAINCONSTANTS.m_PathFXAPaneTemplateJobs));
			fxAPaneMain.getChildren().add(m_FXAPaneTemplateJobs);
			fxAPaneMain.setTopAnchor(m_FXAPaneTemplateJobs, 70.0);
			fxAPaneMain.setLeftAnchor(m_FXAPaneTemplateJobs, 10.0);
			fxAPaneMain.setRightAnchor(m_FXAPaneTemplateJobs, 10.0);
			fxListTmplJob = (ListView<CStructAttrTmpl>)m_FXAPaneTemplateJobs.getChildren().get(0);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		fxTxtNameTmplJob.setText(m_stTempNameJob);// Это чтобы потом меняли на нормальное название!!
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
			            long lCountChildren = arg0.getChildrenCount();
			        	
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
	            				  System.out.println("Попали в создание шаблона!!!");
	            				  if(lCountChildren > 0)
	            				  {
	            					//  System.out.println("Раз lCountChildren  > 0 == тогда показываем из базы все прелести интерфейса!!!");
	            					//  Button b = new Button("button.getText()!!!");
	  	      						//fxAPaneMain.add(b);
	  	      						//AnchorPane.setTopAnchor(b, 10.0);
	  	      						//AnchorPane.setLeftAnchor(b, 10.0);
	  	      						//fxAPaneEditTmpl.getChildren().add(b);
	  	      						//Border brd = CFXCreateTemplateJobCtrl.fxAPaneEditTmpl.getBorder();
	            				  }
	            				
	            				  
	            				  fxListTmplJob.setItems(m_ObservableList);
	            				// fxListViewPriority.setPrefSize(200, 500);
	            				  fxListTmplJob.setCellFactory(new Callback<ListView<CStructAttrTmpl>, ListCell<CStructAttrTmpl>>() {
									
	            					  @Override
										public ListCell<CStructAttrTmpl> call(ListView<CStructAttrTmpl> param)
										{
	            						 // param.setM(200);
										// TODO Auto-generated method stub
										return new CUserCellIntoTmpl();
									}
								});
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
