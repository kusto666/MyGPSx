package mygpsx;

//��������� ����� �����!!!
public class CStructAttrjob 
{
	private String MyAutoIncrement;
	private String MyIDPrevNode;// ID ���� � ������� ���������!!!(��� ��� ������ ��� ������ � �����)!!!
	private String MyIDUnique;
	private String MyCLassAttrjob;
	private String MyNameAttrjob;
	private String MyTypeAttrjob; // ��� ��������(��� �������� �� ������ � � ������ ��������� ��������)
	private String MyHeight;
	
	public CStructAttrjob() {// ����������� ���������, � �� ����� ������ ���� ��� ����� ����:
    }// Class mygpsx.CStructPriority is missing a constructor with no arguments !!!!!!!!!!!!!!!!!!
	
	public CStructAttrjob(String MyAutoIncrement, String MyIDUnique, String MyCLassAttrjob, String MyNameAttrjob, String MyHeight) 
	{
		this.MyAutoIncrement = MyAutoIncrement;
		this.MyIDUnique = MyIDUnique;
		this.MyCLassAttrjob = MyCLassAttrjob;
		this.MyNameAttrjob = MyNameAttrjob;
		this.MyHeight = MyHeight;
	}
	
	
	public String getMyAutoIncrement()
    {
        return MyAutoIncrement;
    }

	public String getMyIDUnique()
    {
        return MyIDUnique;
    }

    public String getMyCLassAttrjob()
    {
        return MyCLassAttrjob;
    }

    public String getMyNameAttrjob()
    {
        return MyNameAttrjob;
    }
    public String getMyTypeAttrjob()
    {
        return MyTypeAttrjob;
    }
    public String getMyHeight()
    {
        return MyHeight;
    }
    
    public void setMyAutoIncrement(String MyAutoIncrement)
    {
        this.MyAutoIncrement = MyAutoIncrement;
    }
    public void setMyIDUnique(String MyIDUnique)
    {
        this.MyIDUnique = MyIDUnique;
    }
    public void setMyCLassAttrjob(String MyCLassAttrjob)
    {
        this.MyCLassAttrjob = MyCLassAttrjob;
    }
    public void setMyNameAttrjob(String MyNameAttrjob)
    {
        this.MyNameAttrjob = MyNameAttrjob;
    }
    public void setMyTypeAttrjob(String MyTypeAttrjob)
    {
        this.MyTypeAttrjob = MyTypeAttrjob;
    }
    public void setMyHeight(String MyHeight)
    {
        this.MyHeight = MyHeight;
    }
}
