package mygpsx;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class CUserCellFactory implements Callback<ListView<CUser>, ListCell<CUser>>
{
	@Override
	public ListCell<CUser> call(ListView<CUser> listview) 
	{
		return new CUserCell();
	}
}
