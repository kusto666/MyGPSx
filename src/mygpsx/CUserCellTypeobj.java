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

public class CUserCellTypeobj  extends ListCell<CStructTypeobj>
{
	
	@FXML
	Label fxLbUniqueID;
	@FXML
	TextField fxTxtNumberTypeobj;
	@FXML
	public TextField fxTxtNameTypeobj;
	@FXML
	Button fxBtnDeleteTypeobj;
	@FXML
	FXMLLoader mLLoader;
	@FXML
	AnchorPane m_Pane;
	
	@Override
	public void updateItem(CStructTypeobj item, boolean empty) 
	{
		super.updateItem(item, empty);
		
        if(empty || item == null) 
        {
            setText(null);
            setGraphic(null);
        } 
        else
        {
            mLLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXListCellTypeobj));
            try 
            {
                mLLoader.load();
                fxLbUniqueID = (Label)mLLoader.getNamespace().get("fxLbUniqueID");
                fxTxtNumberTypeobj = (TextField)mLLoader.getNamespace().get("fxTxtNumberTypeobj");
                fxTxtNumberTypeobj.setEditable(false);
                fxTxtNameTypeobj = (TextField)mLLoader.getNamespace().get("fxTxtNameTypeobj");
                fxBtnDeleteTypeobj = (Button)mLLoader.getNamespace().get("fxBtnDeleteTypeobj");
        		m_Pane = (AnchorPane)mLLoader.getNamespace().get("fxCellPane");
        		
        		fxLbUniqueID.setText(String.valueOf(item.getMyIDUnique()));
        		fxTxtNumberTypeobj.setText(String.valueOf(item.getMyCLassTypeobj()));
        		fxTxtNameTypeobj.setText(String.valueOf(item.getMyNameTypeobj()));
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }

            System.out.println(fxTxtNameTypeobj.getText());
            setText(null);
            setGraphic(m_Pane);
        }
	}
}