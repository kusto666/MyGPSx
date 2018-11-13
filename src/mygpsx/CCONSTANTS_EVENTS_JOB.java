package mygpsx;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CCONSTANTS_EVENTS_JOB
{
	// Это временное хранение количества добавленных контролов в создаваемый временный шаблон задачи!!!
	// Эта переменная служил для контроля при выходе из создания шаблона по "крестику" - это предупреждает о наличии
	// проделанной работы в создаваемом шаблоне!!!
	public static int TEMP_COUNT_ADDING_CONTROLS_IN_TMPL = 0; // Здесь пишеться UniqueID приоритета задачи.
	// Это временное хранение приоритета задачи!!!
	public static String TEMP_PRIORITY_JOB = ""; // Здесь пишеться UniqueID приоритета задачи.
	
	public static String MAIN_SELECTED_SHIP = "";// Переменная отвечает за основной выбор судна из левого главного списка,
	// для дальнейших манипуляций с ним!!!
	public static String MAIN_SELECTED_TMPL = "";// Переменная отвечает за основной выбор шаблона из списка юзера,
	// для дальнейших манипуляций с ним!!!
	
	// Значит просто лазеем по проге и ничего несоздаем или редактируем!!!
	public static String SAMPLE_JOBING = "NONE"; 
	// "ADD_SHIP" - Нажали кнопку добавить объект!!!
	
	// Здесь переменная отвечает за изменение интерфейса различных окон - 
	// к примеру открыли атрибуты с кнопкой "DEL", но если открыли в добавлении в шаблон,
	// то кнопка "DEL" скрывается, а кнопка "ADD" - появляется!!!
	public static String SAMPLE_ANY_OR_ANY = "DEL"; // "ADD" - значит добавляем в шаблон задач (в данном случае!!!)
	
	// Это тоже связано с шаблоном, но здесь мы смотрим, что делаем, а именно:
	//public static int TEMPLATE_FILLING_OR_EDIT = 1;//1 - Значит заполняем, 0 - создаем или редактируем!!!
	
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
