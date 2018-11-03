package mygpsx;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CPriorityEditController implements Initializable
{
	
	
	/*@FXML
	private Parent root;
	@FXML*/
	//private FXMLLoader m_Loader;

	private ObservableList<String> m_ObservableListUsers;
	@FXML
	private ListView<String> fxListViewPriority;
	//@FXML
	private ArrayList<String> m_alPriority = null;
	
	@FXML
	private Parent m_rootPriorityAdd = null;
	@FXML
	public static Stage m_stagePriorityAdd = null;
	
	 // Открытие окна добавление приоритета задачи!!!
    @FXML
    private void FrameAddPriority(ActionEvent event) 
    {
    	System.out.println("FrameAddPriority - OPEN!!!");
    	try 
    	{
	            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXPriorityAdd));
	            m_rootPriorityAdd = (Parent)fxmlLoader.load();
	            m_stagePriorityAdd = new Stage();
	            m_stagePriorityAdd.setTitle(CStrings.m_APP_NAME + "->Добавление приоритета");
	            m_stagePriorityAdd.setScene(new Scene(m_rootPriorityAdd));  
	            m_stagePriorityAdd.setResizable(false);
	            m_stagePriorityAdd.initModality(Modality.WINDOW_MODAL);
	            m_stagePriorityAdd.initOwner(CLPSMain.m_stagePriorityEdit);
	            m_stagePriorityAdd.show();
            
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
			m_alPriority = new ArrayList<String>();
			m_alPriority.add("777");// Заполнили массив!!!
			m_ObservableListUsers = FXCollections.observableArrayList (m_alPriority);
			fxListViewPriority.setItems(m_ObservableListUsers);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		
	}
	

}
