package mygpsx;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

public class CUserCell  extends ListCell<CUser>
{
	@FXML
	Label lbMyNameShip;
	@FXML
	Label lbMyDirectorShip;
	@FXML
	FXMLLoader mLLoader;
	@FXML
	AnchorPane m_Pane;
	
	//ObservableList<CUser> m_ObservableListUsers;
	
	@Override
	public void updateItem(CUser item, boolean empty) 
	{
		super.updateItem(item, empty);
		
        if(empty || item == null) 
        {
            setText(null);
            setGraphic(null);
        } 
        else
        {
            //if (mLLoader == null) 
            //{
            	System.out.println("mLLoader = new FXMLLoader(getClass().getResource(CLPSMain.m_PathFXListCellFxml));");
                mLLoader = new FXMLLoader(getClass().getResource(CLPSMain.m_PathFXListCellFxml));
               // mLLoader.setController(this);

                try 
                {
                    mLLoader.load();
                    lbMyNameShip = (Label)mLLoader.getNamespace().get("lbMyNameShip");
                    lbMyDirectorShip = (Label)mLLoader.getNamespace().get("lbMyDirectorShip");
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
            		lbMyNameShip.setText(String.valueOf(item.getMyNameShip()));
            		lbMyDirectorShip.setText(String.valueOf(item.getMyDirectorShip()));
                     
                     /*System.out.println("<<<<<<<<<<<<<<<  " + label1.getText() + "  >>>>>>>>>>>>>");
                     setText(null);
                     setGraphic(m_Pane);*/
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }

           // }
            System.out.println("<<<<<<<<<<<<<<<  " + lbMyNameShip.getText() + "  >>>>>>>>>>>>>");
            setText(null);
            setGraphic(m_Pane);
            //CMainController.map = null;
            /*if(CMainController.map == null)
            {
            	System.out.println("Ошибка инициализации map!!!");
            	CLPSMain.fxLbMessage.setText("Ошибка инициализации\nкарты!\nПерегрузите карту,\nнажимте кнопку:\n\"Обновить карту\"");
            	CLPSMain.btnRestartMod.setVisible(true);
            }
            else
            {
            	CLPSMain.fxMessageWait.setVisible(false);
            }*/
        }
	}
}
