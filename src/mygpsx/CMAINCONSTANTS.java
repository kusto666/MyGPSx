package mygpsx;

public class CMAINCONSTANTS {
	
	public static String m_UniqueTempIDTempate = "";
	// Всекие пути к файлам окошек программы!!!
	public static String m_PathMainFxml = "LPSMap.fxml";// Самое главное окно!!!
	
	public static String m_PathFXAddShipFxml = "FXAddShip.fxml";
	public static String m_PathFXListCellFxml = "ListCellUser.fxml";
	
	// Тут приоритеты!!!
	public static String m_PathFXPriorityEdit = "FXPriorityEdit.fxml";
	public static String m_PathFXPriorityAdd = "FXPriorityAdd.fxml";
	public static String m_PathFXListCellPriority = "ListCellPriority.fxml";
	// Тут статусы!!!
	public static String m_PathFXStatusEdit = "FXStatusEdit.fxml";
	public static String m_PathFXStatusAdd = "FXStatusAdd.fxml";
	public static String m_PathFXListCellStatus = "ListCellStatus.fxml";
	
	// Тут типы работ!!!
	public static String m_PathFXTypejobEdit = "FXTypejobEdit.fxml";
	public static String m_PathFXTypejobAdd = "FXTypejobAdd.fxml";
	public static String m_PathFXListCellTypejob = "ListCellTypejob.fxml";
	
	// Тут атрибуты работ!!!
	public static String m_PathFXAttrjobEdit = "FXAttrjobEdit.fxml";
	public static String m_PathFXAttrjobAdd = "FXAttrjobAdd.fxml";
	public static String m_PathFXListCellAttrjob = "ListCellAttrjob.fxml";
	
	// Тут типовые описания работ!!!
	public static String m_PathFXTypedescjobEdit = "FXTypedescjobEdit.fxml";
	public static String m_PathFXTypedescjobAdd = "FXTypedescjobAdd.fxml";
	public static String m_PathFXListCellTypedescjob = "ListCellTypedescjob.fxml";
	
	// Тут атрибуты объектов!!!
	public static String m_PathFXAttrobjEdit = "FXAttrobjEdit.fxml";
	public static String m_PathFXAttrobjAdd = "FXAttrobjAdd.fxml";
	public static String m_PathFXListCellAttrobj = "ListCellAttrobj.fxml";
	
	// Тут типы объектов!!!
	public static String m_PathFXTypeobjEdit = "FXTypeobjEdit.fxml";
	public static String m_PathFXTypeobjAdd = "FXTypeobjAdd.fxml";
	public static String m_PathFXListCellTypeobj = "ListCellTypeobj.fxml";
	
	// Тут все для списка шаблонов!!!
	public static String m_PathFXListTemplatesJobs = "FXListTemplatesJobs.fxml";
	//public static String m_PathFXAttrobjAdd = "FXAttrobjAdd.fxml";
	
	
	// Тут все для создания шаблона!!!
	public static String m_PathFXCreateTemplate = "FXCreateTemplateJobs.fxml";
	public static String m_PathFXCellIntoTmpl = "FXCellIntoTmplJobs.fxml";
	
	// Здесь пути к файлам для окна просмотра шаблона!!!
	public static String m_PathFXPreviewTemplate = "FXPreviewTemplateJob.fxml";// Основное окно просмотра!!! 
	public static String m_PathFXListCellTmplJob = "ListCellTmplJob.fxml";
	
	public static String m_PathFXMessageWaitFxml = "MessageWait.fxml";

	// Основной-корневой путь у файлам в firebase storage cloud!!!
	public static String PATH_NAME_UPLOADS_MAIN = "uploads/";
	
	// Название таблиц(веток JSON) в realtime database:
	public static String FB_users = "users";
	
	// Основной узел настроек для всего и шаблонов и всяких там объктов и вообще настроек!!!
	public static String FB_my_owner_settings = "my_owner_settings";
	
	
	// Значения высоты ячейки списка шаблона и ширины контрола в этой ячейки
	// по умолчанию!!!
	public static String Height_cell_start = "55";
	public static String Width_control_start = "100";
	
	
	// Это все сущности-атрибуты!!!
	public static String MyPhoneID_ = "MyPhoneID_";// Это название-приставка к основному объекту User!!!
	public static String FB_MyIDUserSelected = "MyIDUserSelected";// Это слушаем, кто выбран, когда нажимаем кнопку "Задачи"!!!
	public static String FB_MyIDTmplSelected = "MyIDTmplSelected";// Это слушаем(это шаблон!), кто выбран, когда нажимаем кнопку "Задачи"!!!

	public static String FB_my_priority = "my_priority";
	public static String FB_my_status = "my_status";
	public static String FB_my_typejob = "my_typejob";
	public static String FB_my_attrjob = "my_attrjob";
	public static String FB_my_typedescjob = "my_typedescjob";
	public static String FB_my_attrobj = "my_attrobj";
	public static String FB_my_typeobj = "my_typeobj";
	
	public static String FB_my_templates = "my_templates";
	public static String FB_my_adding_attr = "my_adding_attr";
	
	

}
