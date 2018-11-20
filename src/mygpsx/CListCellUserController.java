package mygpsx;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/*import com.google.firebase.tasks.Task;*/

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CListCellUserController implements Initializable{

	private ComboBox<CStructUser> fxCbSelectUser2;
	
	
	@FXML
	private Label fxLbUniqueID;
	@FXML
	private Button fxBtnViewJobs;
	@FXML
	private Label lbMyNameShip;
	@FXML
	private Label lbMyDirectorShip;
	@FXML
	private AnchorPane fxCellPane;
	
/*	private Task<Void> mDatabase;*/
	//private Task<Void> mDatabaseIn;
	//String stUserUniqueIDClass = "";
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	//
	@FXML
    private void BtnViewJobs(ActionEvent event) 
    {
//		fxCellPane.requestFocus();
		CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_SHIP = fxLbUniqueID.getText();
		TabPane tb = (TabPane)CLPSMain.scene.lookup("#fxTabPaneMain");
		SingleSelectionModel<Tab> selectionModel = tb.getSelectionModel();
		selectionModel.select(1);
		/*mDatabase = */FirebaseDatabase.getInstance().getReference()
				.child(CMAINCONSTANTS.FB_MyIDUserSelected).setValueAsync(CCONSTANTS_EVENTS_JOB.MAIN_SELECTED_SHIP);
		//System.out.println("BtnViewJobs(ActionEvent event)");
    }

}
