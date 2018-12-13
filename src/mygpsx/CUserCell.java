package mygpsx;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

public class CUserCell  extends ListCell<CStructUser>
{
	//fxLbUniqueID
	@FXML
	Label fxLbUniqueID;
	@FXML
	Label lbMyNameShip;
	@FXML
	Label lbMyDirectorShip;
	@FXML
	Button fxBtnViewJobs;
	@FXML
	Label fxLbBindingEmail;
	@FXML
	FXMLLoader mLLoader;
	@FXML
	AnchorPane m_Pane;
	
	
	@Override
	public void updateItem(CStructUser item, boolean empty) 
	{
		super.updateItem(item, empty);
		
        if(empty || item == null) 
        {
            setText(null);
            setGraphic(null);
        } 
        else
        {
        	System.out.println("mLLoader = new FXMLLoader(getClass().getResource(CLPSMain.m_PathFXListCellFxml));");
            mLLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXListCellFxml));
            try 
            {
                mLLoader.load();
                fxLbUniqueID = (Label)mLLoader.getNamespace().get("fxLbUniqueID");
                lbMyNameShip = (Label)mLLoader.getNamespace().get("lbMyNameShip");
                fxBtnViewJobs = (Button)mLLoader.getNamespace().get("fxBtnViewJobs");
                lbMyDirectorShip = (Label)mLLoader.getNamespace().get("lbMyDirectorShip");
                fxLbBindingEmail = (Label)mLLoader.getNamespace().get("fxLbBindingEmail");
        		m_Pane = (AnchorPane)mLLoader.getNamespace().get("fxCellPane");
        		if(lbMyNameShip == null)
        		{
        			System.out.println("lbMyNameShip == null!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        		}
        		if(lbMyDirectorShip == null)
        		{
        			System.out.println("label2 == null!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        		}
        		if(m_Pane == null)
        		{
        			System.out.println("m_Pane == null!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        		}
        		fxLbUniqueID.setText(String.valueOf(item.getMyPhoneID()));
        		lbMyNameShip.setText(String.valueOf(item.getMyNameShip()));
        		lbMyDirectorShip.setText(String.valueOf(item.getMyDirectorShip()));
        		fxLbBindingEmail.setText(String.valueOf(item.getMyEmail()));
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }

            System.out.println("<<<<<<<<<<<<<<<  " + lbMyNameShip.getText() + "  >>>>>>>>>>>>>");
            setText(null);
            setGraphic(m_Pane);
        }
	}
}
