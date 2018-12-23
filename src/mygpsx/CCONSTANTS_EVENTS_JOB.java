package mygpsx;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CCONSTANTS_EVENTS_JOB
{
	public static String MYNONE = "none";
	// ���������� ������� � ���, ��� ����� �����������, �������� ���� ������:
	// 1 - ����: GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG - mapInitialized 
	// 2 - ����: GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG - mapReady
	// if LOAD_GOOGLEMAP_STEP == 2 then GOOD!!!
	public static int LOAD_GOOGLEMAP_STEP = 0;
	
	// ���������� ���������� �������� ���������� ���������� ��������� � ���� my_adding_attr
	public static long COUNT_ATTRIBUTES_IN_my_adding_attr = 0;
	// ���������� �������� �� ��, ��� �� ������ � ��������� � ������, � ������:
	// - "DEL_AT" - ������ ����������
	// - "MOVE_AT" - ������ �������
	public static String MOVE_OR_DELETE_ATTRIBUTE = "DEL_AT";
	
	public static ArrayList<CStructAttrTmpl> CFXEditTemplateJobCtrl_alAttrjob = null;// ��� � ������ CFXEditTemplateJobCtrl -> private m_alAttrjob;
	
	// ��� ��������� �������� ���������� ����������� ��������� � ����������� ��������� ������ ������!!!
	// ��� ���������� ������ ��� �������� ��� ������ �� �������� ������� �� "��������" - ��� ������������� � �������
	// ����������� ������ � ����������� �������!!!
	public static int TEMP_COUNT_ADDING_CONTROLS_IN_TMPL = 0; // ����� �������� UniqueID ���������� ������.
	// ��� ��������� �������� ���������� ������!!!
	public static String TEMP_PRIORITY_JOB = ""; // ����� �������� UniqueID ���������� ������.
	
	public static ObservableList<CStructUser> MAIN_ObservableListUser;// ��� ������ ComboBox � �������������� �� ������� ���� ��� ������ � ���������!!!
	public static String MAIN_SELECTED_SHIP = null;// ���������� �������� �� �������� ����� ����� �� ������ �������� ������,
	// ��� ���������� ����������� � ���!!!
	public static String EDITING_SHIP_ID = ""; // ID - ���������� ��� �������������� �����!!!
	public static String MAIN_SELECTED_TMPL = "";// ���������� �������� �� �������� ����� ������� �� ������ �����,
	// ��� ���������� ����������� � ���!!!
	
	// ������ ������ ������ �� ����� � ������ ��������� ��� �����������!!!
	public static String SAMPLE_JOBING = "NONE"; 
	// "ADD_SHIP" - ������ ������ �������� ������!!!
	// "EDIT_SHIP" - � ����������� ���� ����� ������� "�������������"
	
	// ����� ���������� �������� �� ��������� ���������� ��������� ���� - 
	// � ������� ������� �������� � ������� "DEL", �� ���� ������� � ���������� � ������,
	// �� ������ "DEL" ����������, � ������ "ADD" - ����������!!!
	// � ��� ������� ������ ������� ������ - ��� "EDIT" - ����� ��������� ������� ������ ��� �������������� - 
	// ��� ������ �������� � ���������� ���������� ID ������� CMAINCONSTANTS.m_UniqueTempEditIDTempate!!!
	public static String SAMPLE_ANY_OR_ANY = "DEL"; // "ADD" - ������ ��������� � ������ ����� (� ������ ������!!!)
	
	// ��� ���� ������� � ��������, �� ����� �� �������, ��� ������, � ������:
	//public static int TEMPLATE_FILLING_OR_EDIT = 1;//1 - ������ ���������, 0 - ������� ��� �����������!!!
	
	// TabPane tb - �������, � ������� ��� �������� ����!!!
	public static TabPane MY_TABPANE_MAIN = null;
	public static String MY_TAB_1_NAME = "���������� --> ������";
	public static boolean MY_CHANGE_TAB_TO_MY_TAB_1_NAME = false;// ��� ���������� ������� � ���, ��� ������������� �� �������(Tab)
	// ����������� �� �������������� - false, ��� true - ����� �������� �� ��� ������� ����� ������� ������ � ������ ����� "�������� ������". 
	
	// ������� �� ������ � ������ ����� ��� �������� ������� - ��� ���� ��� ������� ���.
	public static String MY_fxBtnViewJobsText = "������� �������";
	
	// ����� ������������ �������!!!
	public static <T> boolean OpenAnyFrame( Class<T> MyClassFrame, 
											String stPathFXFrame,
											Parent MyParent,
											Stage MyStage,
											String stAPP_NAME,
											boolean bResizable,
											boolean bIsModal,
											boolean bAlwaysOnTop
											)
	{

		boolean bRet = true;
		 FXMLLoader fxmlLoader = new FXMLLoader(MyClassFrame.getResource(stPathFXFrame));
         try {
        	 MyParent = (Parent)fxmlLoader.load();
		} catch (IOException e) {
			bRet = false;
			e.printStackTrace();
		}
         MyStage = new Stage();
         MyStage.setTitle(CStrings.m_APP_NAME + stAPP_NAME);
         MyStage.setScene(new Scene(MyParent));  
         MyStage.setResizable(bResizable);
         MyStage.setAlwaysOnTop(bAlwaysOnTop);
         if(bIsModal)
         {
        	 MyStage.initModality(Modality.WINDOW_MODAL);
         }
         
         MyStage.initOwner(CLPSMain.m_stageFXCreateTemplateJobs);
         MyStage.show();
 		 System.out.println("OpenAnyFrame(" + MyClassFrame.getName() + ")!!!");
		return bRet;
	}
	public CCONSTANTS_EVENTS_JOB()
	{
		
	}
}
