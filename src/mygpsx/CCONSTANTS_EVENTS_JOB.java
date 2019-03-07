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
	public static String MY_CURRENT_TEMP_USER_FOR_MSG = null;// Переменная хранит текущего пользователя выбранного для передачи сообщения
	// ему на планшет по его ID-users!!! 
	public static String MY_CURRENT_TEMP_USER_PREFIX = "MyPhoneID_";// Это префикс, т.к. в firebase он используется в начале ID users!!!
	public static String MY_SEPARATOR_MSG = "(%%*%%)"; // Разделитель в ID-сообщения!!!
	
	public static String MYNONE = "none";
	// Переменная говорит о том, что карта загрузилась, проверка двух этапов:
	// 1 - этап: GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG - mapInitialized 
	// 2 - этап: GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG - mapReady
	// if LOAD_GOOGLEMAP_STEP == 2 then GOOD!!!
	public static int LOAD_GOOGLEMAP_STEP = 0;
	
	// Глобальная переменная хранения последнего количества атрибутов в узле my_adding_attr
	public static long COUNT_ATTRIBUTES_IN_my_adding_attr = 0;
	// Переменная отвечает за то, что мы делаем с атрибутом в списке, а именно:
	// - "DEL_AT" - только перемещаем
	// - "MOVE_AT" - только удаляем
	public static String MOVE_OR_DELETE_ATTRIBUTE = "DEL_AT";
	
	public static ArrayList<CStructAttrTmpl> CFXEditTemplateJobCtrl_alAttrjob = null;// Был в классе CFXEditTemplateJobCtrl -> private m_alAttrjob;
	
	// Это временное хранение количества добавленных контролов в создаваемый временный шаблон задачи!!!
	// Эта переменная служил для контроля при выходе из создания шаблона по "крестику" - это предупреждает о наличии
	// проделанной работы в создаваемом шаблоне!!!
	public static int TEMP_COUNT_ADDING_CONTROLS_IN_TMPL = 0; // Здесь пишеться UniqueID приоритета задачи.
	// Это временное хранение приоритета задачи!!!
	public static String TEMP_PRIORITY_JOB = ""; // Здесь пишеться UniqueID приоритета задачи.
	
	public static ObservableList<CStructUser> MAIN_ObservableListUser;// Это список ComboBox с пользователями из второго таба его всегда и обновляем!!!
	public static String MAIN_SELECTED_SHIP = null;// Переменная отвечает за основной выбор судна из левого главного списка,
	// для дальнейших манипуляций с ним!!!
	public static String EDITING_SHIP_ID = ""; // ID - выбранного для редактирования судна!!!
	public static String MAIN_SELECTED_TMPL = "";// Переменная отвечает за основной выбор шаблона из списка юзера,
	// для дальнейших манипуляций с ним!!!
	
	// Значит просто лазеем по проге и ничего несоздаем или редактируем!!!
	public static String SAMPLE_JOBING = "NONE"; 
	// "ADD_SHIP" - Нажали кнопку добавить объект!!!
	// "EDIT_SHIP" - В контекстном меню судна выбрали "Редактировать"
	
	// Здесь переменная отвечает за изменение интерфейса различных окон - 
	// к примеру открыли атрибуты с кнопкой "DEL", но если открыли в добавлении в шаблон,
	// то кнопка "DEL" скрывается, а кнопка "ADD" - появляется!!!
	// И еще вариант работы МОГУЧЕЙ КНОПКИ - это "EDIT" - когда открываем готовый шаблон для редактирования - 
	// тут просто работаем с глобальной переменной ID шаблона CMAINCONSTANTS.m_UniqueTempEditIDTempate!!!
	public static String SAMPLE_ANY_OR_ANY = "DEL"; // "ADD" - значит добавляем в шаблон задач (в данном случае!!!)
	
	// Это тоже связано с шаблоном, но здесь мы смотрим, что делаем, а именно:
	//public static int TEMPLATE_FILLING_OR_EDIT = 1;//1 - Значит заполняем, 0 - создаем или редактируем!!!
	
	// TabPane tb - главная, в которой все основные табы!!!
	public static TabPane MY_TABPANE_MAIN = null;
	public static String MY_TAB_1_NAME = "Сотрудники --> Задачи";
	//public static String MY_TAB_1_NAME777 = "Сотрудники --> Задачи";
	public static boolean MY_CHANGE_TAB_TO_MY_TAB_1_NAME = false;// Эта переменная говорит о том, что переключаемся на вкладку(Tab)
	// посредством их перелистывания - false, или true - когда попадаем на эту вкладку путем нажатия кнопки в ячейке судна "Добавить задачу". 
	
	// Надпись на кнопке в списке судов для текущего задания - или есть или задания нет.
	public static String MY_fxBtnViewJobsText = "Текущее задание";
	
	// ЧАСТО ИСПОЛЬЗУЕМЫЕ ФУНКЦИИ!!!
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
