package mygpsx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import javafx.scene.control.ComboBox;

public class CAttributeTypeDAO
{
	//public String m_stNumber = "Целое число"; // Целое число.
	//public String m_stNumFraction = "Дробное число"; // Дробное число.
	//public String m_stShortDesc = "Строка для заголовков."; // Строка для заголовков.
	//public String m_stFullDesc = "Поле для описания"; // Строка для описания.
	//public String m_stPosition = "Позиция на карте(координаты)"; // Позиция на карте(координаты)
	//public String m_stTimeOfPlace = "Время в определенном месте"; // Время в определенном месте.
	//public String m_stTimeOfRange = "Диапазон(промежуток) времени"; // Диапазон(промежуток) времени.

	public static ObservableList<CAttribute> getPlanetList() {
		CAttribute Group = new CAttribute("Group", "Группа");
		CAttribute Number = new CAttribute("Number", "Целое число");
		CAttribute NumFraction = new CAttribute("NumFraction", "Дробное число");
		CAttribute ShortDesc = new CAttribute("ShortDesc", "Строка для заголовков");
		CAttribute FullDesc = new CAttribute("FullDesc", "Поле для описания");
		CAttribute Position = new CAttribute("Position", "Позиция на карте(координаты)");
		CAttribute TimeOfPlace = new CAttribute("TimeOfPlace", "Время в определенном месте");
		CAttribute TimeOfRange = new CAttribute("TimeOfRange", "Диапазон(промежуток) времени");
 
        ObservableList<CAttribute> list //
                = FXCollections.observableArrayList(Number, NumFraction, ShortDesc, FullDesc, Position, TimeOfPlace, TimeOfRange);
 
        return list;
    }
	
}
