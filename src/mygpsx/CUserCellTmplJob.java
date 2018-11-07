package mygpsx;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class CUserCellTmplJob  extends ListCell<CStructTmplJob>
{
	
	@FXML
	Label fxLbUniqueID;
	@FXML
	TextField fxTxtNameTmplJob;
	@FXML
	Button fxBtnDeleteTmplJob;
	@FXML
	Button fxBtnEditTmplJob;
	@FXML
	FXMLLoader mLLoader;
	@FXML
	AnchorPane m_Pane;
	
	@Override
	public void updateItem(CStructTmplJob  item, boolean empty) 
	{
		super.updateItem(item, empty);
		
        if(empty || item == null) 
        {
            setText(null);
            setGraphic(null);
        } 
        else
        {
            mLLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXListCellTmplJob));
            try 
            {
                mLLoader.load();
                fxLbUniqueID = (Label)mLLoader.getNamespace().get("fxLbUniqueID");
                fxTxtNameTmplJob = (TextField)mLLoader.getNamespace().get("fxTxtNameTmplJob");
                fxBtnDeleteTmplJob = (Button)mLLoader.getNamespace().get("fxBtnDeleteTmplJob");
                fxBtnEditTmplJob = (Button)mLLoader.getNamespace().get("fxBtnEditTmplJob");
        		m_Pane = (AnchorPane)mLLoader.getNamespace().get("fxCellPane");
        		
        		fxLbUniqueID.setText(String.valueOf(item.getMyIDUnique()));
        		fxTxtNameTmplJob.setText(String.valueOf(item.getMyNameTemplate()));
            } 
            catch (IOException e) 
            {
            	System.out.println(e.getMessage());
            	//System.out.println(e.printStackTrace());
                e.printStackTrace();
            }

            setText(null);
            setGraphic(m_Pane);
        }
	}
}