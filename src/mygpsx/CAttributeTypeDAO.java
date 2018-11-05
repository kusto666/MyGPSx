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
		CAttribute Group = new CAttribute("Group", "������");
		CAttribute Number = new CAttribute("Number", "����� �����");
		CAttribute NumFraction = new CAttribute("NumFraction", "������� �����");
		CAttribute ShortDesc = new CAttribute("ShortDesc", "������ ��� ����������");
		CAttribute FullDesc = new CAttribute("FullDesc", "���� ��� ��������");
		CAttribute Position = new CAttribute("Position", "������� �� �����(����������)");
		CAttribute TimeOfPlace = new CAttribute("TimeOfPlace", "����� � ������������ �����");
		CAttribute TimeOfRange = new CAttribute("TimeOfRange", "��������(����������) �������");
 
        ObservableList<CAttribute> list //
                = FXCollections.observableArrayList(Number, NumFraction, ShortDesc, FullDesc, Position, TimeOfPlace, TimeOfRange);
 
        return list;
    }
	
}
