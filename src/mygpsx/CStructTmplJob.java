package mygpsx;

//import java.util.HashMap;

//Структура типов задач!!!
public class CStructTmplJob {
	private String MyIDUnique;
	private String MyNameTemplate;
	//private String my_adding_attr;
	
	public CStructTmplJob() {// ОБЯЗАТЕЛЬНО УКАЗЫВАЕМ, А ТО БУДЕТ ОШИБКА ТИПА КАК ЗДЕСЬ БЫЛО:
    }// Class mygpsx.CStructPriority is missing a constructor with no arguments !!!!!!!!!!!!!!!!!!
	
	/*public CStructTmplJob(String MyIDUnique, String MyNameTemplate, HashMap my_adding_attr) 
	{
		this.MyIDUnique = MyIDUnique;
		this.MyNameTemplate = MyNameTemplate;
		this.my_adding_attr = my_adding_attr;
	}*/
	public CStructTmplJob(String MyIDUnique, String MyNameTemplate) 
	{
		this.MyIDUnique = MyIDUnique;
		this.MyNameTemplate = MyNameTemplate;
	}
	public String getMyIDUnique()
    {
        return MyIDUnique;
    }
    public String getMyNameTemplate()
    {
        return MyNameTemplate;
    }
    
    /*public String getmy_adding_attr()
    {
        return my_adding_attr;
    }
    public void setmy_adding_attr(String my_adding_attr)
    {
    	this.my_adding_attr = my_adding_attr;
    }*/
    
    public void setMyIDUnique(String MyIDUnique)
    {
        this.MyIDUnique = MyIDUnique;
    }
    public void setMyNameTemplate(String MyNameTemplate)
    {
        this.MyNameTemplate = MyNameTemplate;
    }
    /*public void setmy_adding_attr(String my_adding_attr)
    {
        this.my_adding_attr = my_adding_attr;
    }*/
    @Override
    public String toString()  {
        return this.getMyNameTemplate();
    }
}
