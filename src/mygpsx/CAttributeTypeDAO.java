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
		//CAttribute Group = new CAttribute("Group", "Группа");
		CAttribute MyLabel = new CAttribute("Label", "Надпись");
		CAttribute MyInteger = new CAttribute("Integer", "Целое число");
		CAttribute MyNumDouble = new CAttribute("Double", "Дробное число");
		CAttribute MyTextField = new CAttribute("TextField", "Одинарная строка");
		CAttribute MyTextArea = new CAttribute("TextArea", "Многострочное описание");
		CAttribute MyPosition = new CAttribute("MyPosition", "Позиция на карте(координаты)");
		CAttribute MyTimeOfPlace = new CAttribute("MyTimeOfPlace", "Время в определенном месте");
		CAttribute MyTimeOfRange = new CAttribute("MyTimeOfRange", "Диапазон(промежуток) времени");
 
        ObservableList<CAttribute> list //
                = FXCollections.observableArrayList(MyLabel, MyInteger, MyNumDouble, MyTextField, MyTextArea,
                		MyPosition, MyTimeOfPlace, MyTimeOfRange);
 
        return list;
    }
	
}
