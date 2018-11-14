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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;

public class CUserCellIntoTmpl  extends ListCell<CStructAttrTmpl>
{
	////////////  ��� �� �����!!!   ////////////////////////////////////////
	@FXML
	Label fxLb1;
	@FXML
	Label fxLbTypeControl;
	@FXML
	Label fxLbUniqueID;
	
	//@FXML
	//AnchorPane fxCellPane; // ����� ������� ������!!!
	@FXML
	AnchorPane fxAPaneLabel;
	@FXML
	Label fxLbAPaneLabel;
	@FXML
	AnchorPane fxAPaneTextField;
	@FXML
	TextField fxTxtAPaneField;
	@FXML
	AnchorPane fxAPaneTextArea;
	@FXML
	TextArea fxTxtAPaneArea;
	@FXML
	AnchorPane fxAPaneControls;
	
	
	@FXML
	private TextField fxTxtHeight;
	@FXML
	private TextField fxTxtWidth;
	@FXML
	Button fxBtnDeleteAttrjob;
	@FXML
	Button fxBtnRefreshAttrjob;
	
	@FXML
	FXMLLoader mLLoader;
	@FXML
	AnchorPane m_Pane;
	
	double dAnchorTop = 0.0;
	double dAnchorLeft = 0.0;
	double dAnchorButtom = 0.0;
	////////////////////////////////////////////////////////////////////////
	
	@Override
	public void updateItem(CStructAttrTmpl  item, boolean empty) 
	{
		super.updateItem(item, empty);
        if(empty || item == null) 
        {
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
                fxLbTypeControl = (Label)mLLoader.getNamespace().get("fxLbTypeControl");
               
                fxLbUniqueID = (Label)mLLoader.getNamespace().get("fxLbUniqueID");
                
                fxAPaneLabel = (AnchorPane)mLLoader.getNamespace().get("fxAPaneLabel");
                fxLbAPaneLabel = (Label)mLLoader.getNamespace().get("fxLbAPaneLabel");
                fxAPaneTextField = (AnchorPane)mLLoader.getNamespace().get("fxAPaneTextField");
                fxTxtAPaneField = (TextField)mLLoader.getNamespace().get("fxTxtAPaneField");
                fxAPaneTextArea = (AnchorPane)mLLoader.getNamespace().get("fxAPaneTextArea");
                fxTxtAPaneArea = (TextArea)mLLoader.getNamespace().get("fxTxtAPaneArea");
                
                fxAPaneControls = (AnchorPane)mLLoader.getNamespace().get("fxAPaneControls");
                fxTxtHeight = (TextField)mLLoader.getNamespace().get("fxTxtHeight");
                fxTxtWidth = (TextField)mLLoader.getNamespace().get("fxTxtWidth");
                fxBtnDeleteAttrjob = (Button)mLLoader.getNamespace().get("fxBtnDeleteAttrjob");
                fxBtnRefreshAttrjob = (Button)mLLoader.getNamespace().get("fxBtnRefreshAttrjob");

                fxLb1.setVisible(false);
                fxLbTypeControl.setVisible(false);
                fxLbUniqueID.setVisible(false);
        		m_Pane = (AnchorPane)mLLoader.getNamespace().get("fxCellPane");

        		// ���������� ����� �������� �����!!!
        		try 
        		{
        			
        			Platform.runLater( () -> {
        				  //m_Pane.setPrefHeight(Double.parseDouble(item.getMyAttrHeight()));
        				  // ������ ��������� ������� ���-������ �� ����������,
        				  // �� ����� my_templates->UniqueID->my_adding_attr->UniqueID
        				  AnchorPane.setTopAnchor(fxAPaneControls, dAnchorTop);
        				  if(((CStructAttrTmpl)item).getMyAttrType().equals("Label"))
        				  {
        					  System.out.println("Adding Label into Template.");
       					  	  fxAPaneLabel.setVisible(true);
        					  fxAPaneTextField.setVisible(false);
        					  fxAPaneTextArea.setVisible(false);
        					  
        					  m_Pane.setPrefHeight(Double.parseDouble(item.getMyAttrHeight()));
        					  
        					  AnchorPane.setTopAnchor(fxAPaneLabel, dAnchorTop);
        					  //AnchorPane.setLeftAnchor(fxAPaneLabel, dAnchorLeft);
        					  
        					  fxAPaneLabel.setPrefWidth(Double.parseDouble(item.getMyAttrWidth()));
        					  fxAPaneLabel.setPrefHeight(Double.parseDouble(item.getMyAttrHeight()));
        					  // ����� ������ �������� � ������� � ���������!!!
        					  fxLbAPaneLabel.setPrefWidth(Double.parseDouble(item.getMyAttrWidth()));
        					  fxLbAPaneLabel.setPrefHeight(Double.parseDouble(item.getMyAttrHeight()));
        					  fxLbAPaneLabel.setText(item.getMyAttrName());
        				  }
        				  if(((CStructAttrTmpl)item).getMyAttrType().equals("TextField"))
        				  {
        					  System.out.println("Adding TextField into Template.");

        					  fxAPaneLabel.setVisible(false);
        					  fxAPaneTextField.setVisible(true);
        					  fxAPaneTextArea.setVisible(false);
        					  
        					  m_Pane.setPrefHeight(Double.parseDouble(item.getMyAttrHeight()));
        					  
        					  AnchorPane.setTopAnchor(fxAPaneTextField, dAnchorTop);
        					  
        					  fxAPaneTextField.setPrefWidth(Double.parseDouble(item.getMyAttrWidth()));
        					  fxAPaneTextField.setPrefHeight(Double.parseDouble(item.getMyAttrHeight()));
        					  
        					  // ����� ������ �������� � ������� � ���������!!!
        					  fxTxtAPaneField.setPrefWidth(Double.parseDouble(item.getMyAttrWidth()));
        					  fxTxtAPaneField.setPrefHeight(Double.parseDouble(item.getMyAttrHeight()));
        					  fxTxtAPaneField.setText(item.getMyAttrName());
        				  }
        				  if(((CStructAttrTmpl)item).getMyAttrType().equals("TextArea"))
        				  {
        					  System.out.println("Adding TextArea into Template.");
        					  
        					  fxAPaneLabel.setVisible(false);
        					  fxAPaneTextField.setVisible(false);
        					  fxAPaneTextArea.setVisible(true);
        					  
        					  m_Pane.setPrefHeight(Double.parseDouble(item.getMyAttrHeight()));
        					  
        					  AnchorPane.setTopAnchor(fxAPaneTextArea, dAnchorTop);
        					  
        					  fxAPaneTextArea.setPrefWidth(Double.parseDouble(item.getMyAttrWidth()));
        					  fxAPaneTextArea.setPrefHeight(Double.parseDouble(item.getMyAttrHeight()));
        					  
        					// ����� ������ �������� � ������� � ���������!!!
        					  fxTxtAPaneArea.setPrefWidth(Double.parseDouble(item.getMyAttrWidth()));
        					  fxTxtAPaneArea.setPrefHeight(Double.parseDouble(item.getMyAttrHeight()));
        					  fxTxtAPaneArea.setText(item.getMyAttrName());
        				  }
        				  fxLbTypeControl.setText(item.getMyAttrType());
        				  System.out.println("fxLbTypeControl = " + fxLbTypeControl.getText());
        				  fxTxtHeight.setText(item.getMyAttrHeight());
        				  fxTxtWidth.setText(item.getMyAttrWidth());
         	        	  fxLbUniqueID.setText(String.valueOf(item.getMyIDUnique()));
        			  }
        			);
				} 
        		catch (Exception ex) 
        		{
        			ex.getMessage();
				}
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }

            setText(null);
            setGraphic(m_Pane);
        }
	}
}