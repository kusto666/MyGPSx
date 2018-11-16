package mygpsx;

import java.io.IOException;

import com.google.firebase.database.FirebaseDatabase;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;

public class CUserCellIntoTmpl  extends ListCell<CStructAttrTmpl>
{
	////////////  ВСЕ ПО НОВОЙ!!!   ////////////////////////////////////////
	@FXML
	private Label fxLb1;
	@FXML
	private Label fxLbTypeControl;
	@FXML
	private Label fxLbUniqueID;
	
	//@FXML
	//AnchorPane fxCellPane; // Самая главная панель!!!
	@FXML
	private AnchorPane fxAPaneLabel;
	@FXML
	private Label fxLbAPaneLabel;
	@FXML
	private AnchorPane fxAPaneTextField;
	@FXML
	private TextField fxTxtAPaneField;
	@FXML
	private AnchorPane fxAPaneTextArea;
	@FXML
	private TextArea fxTxtAPaneArea;
	@FXML
	private AnchorPane fxAPaneControls;
	
	
	@FXML
	private TextField fxTxtHeight;
	@FXML
	private TextField fxTxtWidth;
	@FXML
	private Button fxBtnDeleteAttrjob;
	@FXML
	private Button fxBtnRefreshAttrjob;
	@FXML
	private Button fxBtnMoveUp;
	@FXML
	private Button fxBtnMoveDown;
	
	@FXML
	private FXMLLoader mLLoader;
	@FXML
	private AnchorPane m_Pane;
	
	private double dAnchorTop = 0.0;
	//private double dAnchorLeft = 0.0;
	//private double dAnchorButtom = 0.0;
	////////////////////////////////////////////////////////////////////////
	private CStructAttrTmpl m_TempAttrTmpl;
	
	private CStructAttrTmpl GetItemByAttrOrder(long lNumOrder)
	{
		CStructAttrTmpl tempStruct = null;
		for(int i = 0; i < CCONSTANTS_EVENTS_JOB.CFXEditTemplateJobCtrl_alAttrjob.size(); i++)
		{
			if(lNumOrder == CCONSTANTS_EVENTS_JOB.CFXEditTemplateJobCtrl_alAttrjob.get(i).getMyAttrOrder())
			{
				tempStruct = CCONSTANTS_EVENTS_JOB.CFXEditTemplateJobCtrl_alAttrjob.get(i);
			}
		}
		return tempStruct;
	}
	
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
                fxBtnMoveUp = (Button)mLLoader.getNamespace().get("fxBtnMoveUp");
                fxBtnMoveUp.setOnMouseClicked(new EventHandler<MouseEvent>() 
                {
					@Override
					public void handle(MouseEvent event) 
					{
						
						long iTempAttrOrder = item.getMyAttrOrder();
						m_TempAttrTmpl = GetItemByAttrOrder(iTempAttrOrder - 1);
						if(m_TempAttrTmpl != null)
						{
							FirebaseDatabase.getInstance().getReference()
							.child(CMAINCONSTANTS.FB_my_owner_settings)
							.child(CMAINCONSTANTS.FB_my_templates)
							.child(CMAINCONSTANTS.m_UniqueTempEditIDTempate)
							.child(CMAINCONSTANTS.FB_my_adding_attr)
							.child(m_TempAttrTmpl.getMyIDUnique()).child("myAttrOrder").setValue(iTempAttrOrder);// Опускаем контролл!!!

							item.setMyAttrOrder(iTempAttrOrder - 1);// Поднимаем поднимаем .
							FirebaseDatabase.getInstance().getReference()
							.child(CMAINCONSTANTS.FB_my_owner_settings)
							.child(CMAINCONSTANTS.FB_my_templates)
							.child(CMAINCONSTANTS.m_UniqueTempEditIDTempate)
							.child(CMAINCONSTANTS.FB_my_adding_attr)
							.child(item.getMyIDUnique()).child("myAttrOrder").setValue(iTempAttrOrder - 1);
						}
						else
						{
							System.out.println("CUserCellIntoTmpl - что-то пошло не так!!!");
						}

					}
				});

                if(item.getMyAttrOrder() == 0)
                {
                	fxBtnMoveUp.setVisible(false);
                }
                fxBtnMoveDown = (Button)mLLoader.getNamespace().get("fxBtnMoveDown");
                fxBtnMoveDown.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						//System.out.println("fxBtnMoveDown.setOnMouseClicked.");
					}
				});
                if(item.getMyAttrOrder() == (CCONSTANTS_EVENTS_JOB.COUNT_ATTRIBUTES_IN_my_adding_attr - 1))
                {
                	fxBtnMoveDown.setVisible(false);
                }
                fxLb1.setVisible(false);
                fxLbTypeControl.setVisible(false);
                fxLbUniqueID.setVisible(false);
        		m_Pane = (AnchorPane)mLLoader.getNamespace().get("fxCellPane");

        		// Используем поток создания листа!!!
        		try 
        		{
        			
        			Platform.runLater( () -> {
        				  //m_Pane.setPrefHeight(Double.parseDouble(item.getMyAttrHeight()));
        				  // Теперь попробуем вывести что-нибудь из интерфейса,
        				  // из ветки my_templates->UniqueID->my_adding_attr->UniqueID
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
        					  // Здесь данные контрола и позиция с размерами!!!
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
        					  
        					  // Здесь данные контрола и позиция с размерами!!!
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
        					  
        					// Здесь данные контрола и позиция с размерами!!!
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