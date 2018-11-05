package mygpsx;

//Ñòğóêòóğà òèïîâ çàäà÷!!!
public class CStructAttrTmpl {
	private String MyIDUnique;
	private String attr_id;
	private String attr_name;
	
	public CStructAttrTmpl() {// ÎÁßÇÀÒÅËÜÍÎ ÓÊÀÇÛÂÀÅÌ, À ÒÎ ÁÓÄÅÒ ÎØÈÁÊÀ ÒÈÏÀ ÊÀÊ ÇÄÅÑÜ ÁÛËÎ:
    }// Class mygpsx.CStructPriority is missing a constructor with no arguments !!!!!!!!!!!!!!!!!!
	
	public CStructAttrTmpl(String MyIDUnique, String attr_id, String attr_name) 
	{
		this.MyIDUnique = MyIDUnique;
		this.attr_id = attr_id;
		this.attr_name = attr_name;
	}
	
	public String getMyIDUnique()
    {
        return MyIDUnique;
    }

    public String getattr_id()
    {
        return attr_id;
    }

    public String getattr_name()
    {
        return attr_name;
    }
   
    public void setMyIDUnique(String MyIDUnique)
    {
        this.MyIDUnique = MyIDUnique;
    }
    public void setattr_id(String attr_id)
    {
        this.attr_id = attr_id;
    }
    public void setattr_name(String attr_name)
    {
        this.attr_name = attr_name;
    }

}
