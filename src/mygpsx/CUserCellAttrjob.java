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

public class CUserCellAttrjob  extends ListCell<CStructAttrjob>
{
	
	@FXML
	Label fxLbUniqueID;
	@FXML
	Label fxLbHiddenTypeAttr;
	@FXML
	TextField fxTxtNumberAttrjob;
	@FXML
	public TextField fxTxtNameAttrjob;
	@FXML
	Button fxBtnDeleteAttrjob;
	@FXML
	TextField fxTxtHeight;
	@FXML
	FXMLLoader mLLoader;
	@FXML
	AnchorPane m_Pane;
	
	@Override
	public void updateItem(CStructAttrjob  item, boolean empty) 
	{
		super.updateItem(item, empty);
		
        if(empty || item == null) 
        {
            setText(null);
            setGraphic(null);
        } 
        else
        {
            mLLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXListCellAttrjob));
            try 
            {
                mLLoader.load();
                fxLbUniqueID = (Label)mLLoader.getNamespace().get("fxLbUniqueID");
                fxLbHiddenTypeAttr = (Label)mLLoader.getNamespace().get("fxLbHiddenTypeAttr");
                fxTxtNumberAttrjob = (TextField)mLLoader.getNamespace().get("fxTxtNumberAttrjob");
                fxTxtNumberAttrjob.setEditable(false);
                fxTxtNameAttrjob = (TextField)mLLoader.getNamespace().get("fxTxtNameAttrjob");
                fxBtnDeleteAttrjob = (Button)mLLoader.getNamespace().get("fxBtnDeleteAttrjob");
                fxTxtHeight = (TextField)mLLoader.getNamespace().get("fxTxtHeight");
        		m_Pane = (AnchorPane)mLLoader.getNamespace().get("fxCellPane");
        		
        		fxLbUniqueID.setText(String.valueOf(item.getMyIDUnique()));
        		fxLbHiddenTypeAttr.setText(String.valueOf(item.getMyTypeAttrjob()));
        		fxTxtNumberAttrjob.setText(String.valueOf(item.getMyCLassAttrjob()));
        		fxTxtNameAttrjob.setText(String.valueOf(item.getMyNameAttrjob()));
            } 
            catch (IOException e) 
            {
            	e.getMessage();
                e.printStackTrace();
            }

            System.out.println(fxTxtNameAttrjob.getText());
            setText(null);
            setGraphic(m_Pane);
        }
	}
}