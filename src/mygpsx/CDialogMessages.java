package mygpsx;

import com.google.common.base.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

public class CDialogMessages {
	Alert alert = new Alert(AlertType.CONFIRMATION);
	//alert.setTitle("Confirmation Dialog with Custom Actions");
	//alert.setHeaderText("Look, a Confirmation Dialog with Custom Actions");
	//alert.setContentText("Choose your option.");

	ButtonType buttonTypeOne = new ButtonType("One");
	ButtonType buttonTypeTwo = new ButtonType("Two");
	ButtonType buttonTypeThree = new ButtonType("Three");
	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
	static boolean DialogYesNo()
	{
		boolean bRet = false;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog with Custom Actions");
		alert.setHeaderText("Look, a Confirmation Dialog with Custom Actions");
		alert.setContentText("Choose your option.");
		ButtonType buttonTypeYes = new ButtonType("Да");
		ButtonType buttonTypeNo = new ButtonType("Нет");
		java.util.Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeYes){
			bRet = true;
		} 
		else
		{
			bRet = false;
		}
		return bRet;
	}
}
