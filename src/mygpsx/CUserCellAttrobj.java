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

public class CUserCellAttrobj  extends ListCell<CStructAttrobj>
{
	
	@FXML
	Label fxLbUniqueID;
	@FXML
	TextField fxTxtNumberAttrobj;
	@FXML
	public TextField fxTxtNameAttrobj;
	@FXML
	Button fxBtnDeleteAttrobj;
	@FXML
	FXMLLoader mLLoader;
	@FXML
	AnchorPane m_Pane;
	
	@Override
	public void updateItem(CStructAttrobj  item, boolean empty) 
	{
		super.updateItem(item, empty);
		
        if(empty || item == null) 
        {
            setText(null);
            setGraphic(null);
        } 
        else
        {
            mLLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXListCellAttrobj));
            try 
            {
                mLLoader.load();
                fxLbUniqueID = (Label)mLLoader.getNamespace().get("fxLbUniqueID");
                fxTxtNumberAttrobj = (TextField)mLLoader.getNamespace().get("fxTxtNumberAttrobj");
                fxTxtNumberAttrobj.setEditable(false);
                fxTxtNameAttrobj = (TextField)mLLoader.getNamespace().get("fxTxtNameAttrobj");
                fxBtnDeleteAttrobj = (Button)mLLoader.getNamespace().get("fxBtnDeleteAttrobj");
        		m_Pane = (AnchorPane)mLLoader.getNamespace().get("fxCellPane");
        		
        		fxLbUniqueID.setText(String.valueOf(item.getMyIDUnique()));
        		fxTxtNumberAttrobj.setText(String.valueOf(item.getMyCLassAttrobj()));
        		fxTxtNameAttrobj.setText(String.valueOf(item.getMyNameAttrobj()));
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }

            System.out.println(fxTxtNameAttrobj.getText());
            setText(null);
            setGraphic(m_Pane);
        }
	}
}