package mygpsx;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CCONSTANTS_EVENTS_JOB
{
	// ��� ��������� �������� ���������� ����������� ��������� � ����������� ��������� ������ ������!!!
	// ��� ���������� ������ ��� �������� ��� ������ �� �������� ������� �� "��������" - ��� ������������� � �������
	// ����������� ������ � ����������� �������!!!
	public static int TEMP_COUNT_ADDING_CONTROLS_IN_TMPL = 0; // ����� �������� UniqueID ���������� ������.
	// ��� ��������� �������� ���������� ������!!!
	public static String TEMP_PRIORITY_JOB = ""; // ����� �������� UniqueID ���������� ������.
	
	public static String MAIN_SELECTED_SHIP = "";// ���������� �������� �� �������� ����� ����� �� ������ �������� ������,
	// ��� ���������� ����������� � ���!!!
	public static String MAIN_SELECTED_TMPL = "";// ���������� �������� �� �������� ����� ������� �� ������ �����,
	// ��� ���������� ����������� � ���!!!
	
	// ������ ������ ������ �� ����� � ������ ��������� ��� �����������!!!
	public static String SAMPLE_JOBING = "NONE"; 
	// "ADD_SHIP" - ������ ������ �������� ������!!!
	
	// ����� ���������� �������� �� ��������� ���������� ��������� ���� - 
	// � ������� ������� �������� � ������� "DEL", �� ���� ������� � ���������� � ������,
	// �� ������ "DEL" ����������, � ������ "ADD" - ����������!!!
	public static String SAMPLE_ANY_OR_ANY = "DEL"; // "ADD" - ������ ��������� � ������ ����� (� ������ ������!!!)
	
	// ��� ���� ������� � ��������, �� ����� �� �������, ��� ������, � ������:
	//public static int TEMPLATE_FILLING_OR_EDIT = 1;//1 - ������ ���������, 0 - ������� ��� �����������!!!
	
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
