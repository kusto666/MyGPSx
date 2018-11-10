package mygpsx;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CCONSTANTS_EVENTS_JOB
{
	public static String MAIN_SELECTED_SHIP = "";// ѕеременна€ отвечает за основной выбор судна из левого главного списка,
	// дл€ дальнейших манипул€ций с ним!!!
	public static String MAIN_SELECTED_TMPL = "";// ѕеременна€ отвечает за основной выбор шаблона из списка юзера,
	// дл€ дальнейших манипул€ций с ним!!!
	
	// «начит просто лазеем по проге и ничего несоздаем или редактируем!!!
	public static String SAMPLE_JOBING = "NONE"; 
	// "ADD_SHIP" - Ќажали кнопку добавить объект!!!
	
	// «десь переменна€ отвечает за изменение интерфейса различных окон - 
	// к примеру открыли атрибуты с кнопкой "DEL", но если открыли в добавлении в шаблон,
	// то кнопка "DEL" скрываетс€, а кнопка "ADD" - по€вл€етс€!!!
	public static String SAMPLE_ANY_OR_ANY = "DEL"; // "ADD" - значит добавл€ем в шаблон задач (в данном случае!!!)
	
	// „ј—“ќ »—ѕќЋ№«”≈ћџ≈ ‘”Ќ ÷»»!!!
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
