package mygpsx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import javafx.scene.control.ComboBox;

public class CAttributeTypeDAO
{
	//public String m_stNumber = "����� �����"; // ����� �����.
	//public String m_stNumFraction = "������� �����"; // ������� �����.
	//public String m_stShortDesc = "������ ��� ����������."; // ������ ��� ����������.
	//public String m_stFullDesc = "���� ��� ��������"; // ������ ��� ��������.
	//public String m_stPosition = "������� �� �����(����������)"; // ������� �� �����(����������)
	//public String m_stTimeOfPlace = "����� � ������������ �����"; // ����� � ������������ �����.
	//public String m_stTimeOfRange = "��������(����������) �������"; // ��������(����������) �������.

	public static ObservableList<CAttribute> getPlanetList() {
		//CAttribute Group = new CAttribute("Group", "������");
		CAttribute MyInteger = new CAttribute("MyInteger", "����� �����");
		CAttribute MyNumDouble = new CAttribute("MyNumDouble", "������� �����");
		CAttribute MyTextField = new CAttribute("TextField", "��������� ������");
		CAttribute MyTextArea = new CAttribute("TextArea", "������������� ��������");
		CAttribute MyPosition = new CAttribute("MyPosition", "������� �� �����(����������)");
		CAttribute MyTimeOfPlace = new CAttribute("MyTimeOfPlace", "����� � ������������ �����");
		CAttribute MyTimeOfRange = new CAttribute("MyTimeOfRange", "��������(����������) �������");
 
        ObservableList<CAttribute> list //
                = FXCollections.observableArrayList(MyInteger, MyNumDouble, MyTextField, MyTextArea,
                		MyPosition, MyTimeOfPlace, MyTimeOfRange);
 
        return list;
    }
	
}
