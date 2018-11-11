package mygpsx;

import java.io.IOException;

import javafx.application.Platform;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;

public class CUserCellIntoTmpl  extends ListCell<CStructAttrTmpl>
{
	// fxLb2
	@FXML
	Label fxLb1;
	@FXML
	Label fxLb2;
	@FXML
	Label fxLbUniqueID;
	@FXML
	TextField fxTxtNumberAttrjob;
	@FXML
	public TextField fxTxtNameAttrjob;
	@FXML
	Button fxBtnDeleteAttrjob;
	@FXML
	FXMLLoader mLLoader;
	@FXML
	AnchorPane m_Pane;
	
	@Override
	public void updateItem(CStructAttrTmpl  item, boolean empty) 
	{
		super.updateItem(item, empty);
		// Rectangle rect = new Rectangle(100, 20);
        if(empty || item == null) 
        {
        	//rect.setFill(Color.web("blue"));
        	//item.getMyHeight();
            setText(null);
            setGraphic(null);
        }
        else
        {
            mLLoader = new FXMLLoader(getClass().getResource(CMAINCONSTANTS.m_PathFXCellIntoTmpl));
            try 
            {
                mLLoader.load();
                fxLb1 = (Label)mLLoader.getNamespace().get("fxLb1");
                fxLb2 = (Label)mLLoader.getNamespace().get("fxLb2");
                fxLbUniqueID = (Label)mLLoader.getNamespace().get("fxLbUniqueID");
                fxTxtNumberAttrjob = (TextField)mLLoader.getNamespace().get("fxTxtNumberAttrjob");
                fxTxtNumberAttrjob.setEditable(false);
                fxTxtNameAttrjob = (TextField)mLLoader.getNamespace().get("fxTxtNameAttrjob");
                fxBtnDeleteAttrjob = (Button)mLLoader.getNamespace().get("fxBtnDeleteAttrjob");
                
                // ��� ������� ������������ ��� ������ �������!!!
                fxLbUniqueID.setVisible(false);
                fxTxtNumberAttrjob.setVisible(false);
                fxTxtNameAttrjob.setVisible(false);
                fxLb1.setVisible(false);
                fxLb2.setVisible(false);
                
                if(CCONSTANTS_EVENTS_JOB.TEMPLATE_FILLING_OR_EDIT == 1)
                {
                	fxBtnDeleteAttrjob.setVisible(false);
                }
                else
                {
                	fxBtnDeleteAttrjob.setVisible(true);
                }
                
                
                
                
        		m_Pane = (AnchorPane)mLLoader.getNamespace().get("fxCellPane");
        		// ����� ���������� ������ ������ ��� ������ ����� ������� - ����� ! ��������!!!
        		// Get all different heigth from realbase firebase!!!!!!!!!!!!!!!!!!!!
        		// It`s JOBING!!!!!
        		// ���������� ����� �������� �����!!!
        		try 
        		{
        			
        			Platform.runLater( () -> {
        				  m_Pane.setPrefHeight(Double.parseDouble(item.getMyAttrHeight()));
        				  // ������ ��������� ������� ���-������ �� ����������,
        				  // �� ����� my_templates->UniqueID->my_adding_attr->UniqueID
        				  if(((CStructAttrTmpl)item).getMyAttrType().equals("Label"))
        				  {
        					  System.out.println("Adding Label into Template.");
        					  Label LbTest = new Label();
        					  LbTest.setText(item.getMyAttrName());
        					  double dAnchorTop = 2.0;
        					  double dAnchorLeft = 2.0;
        					  AnchorPane.setTopAnchor(LbTest, dAnchorTop);
	      					  AnchorPane.setLeftAnchor(LbTest, dAnchorLeft);
	      						
        					  m_Pane.getChildren().add(LbTest);
        				  }
        				  if(((CStructAttrTmpl)item).getMyAttrType().equals("TextField"))
        				  {
        					  System.out.println("Adding TextField into Template.");
        					  TextField TxtField= new TextField();
        					  TxtField.setText(item.getMyAttrName());
        					  double dAnchorTop = 2.0;
        					  double dAnchorLeft = 2.0;
        					  AnchorPane.setTopAnchor(TxtField, dAnchorTop);
	      					  AnchorPane.setLeftAnchor(TxtField, dAnchorLeft);
	      						
        					  m_Pane.getChildren().add(TxtField);
        				  }

        			  }
        			);
				} 
        		catch (Exception ex) 
        		{
        			ex.getMessage();
				}
        		

        		fxLbUniqueID.setText(String.valueOf(item.getMyIDUnique()));
        		fxTxtNumberAttrjob.setText(String.valueOf(item.getMyAttrID()));
        		fxTxtNameAttrjob.setText(String.valueOf(item.getMyAttrName()));
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }

           // System.out.println(fxTxtNameAttrobj.getText());
            setText(null);
            setGraphic(m_Pane);
        }
	}
}