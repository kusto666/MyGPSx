package mygpsx;

//��������� ����� �����!!!
public class CStructAttrjob {
	private String MyIDPrevNode;// ID ���� � ������� ���������!!!(��� ��� ������ ��� ������ � �����)!!!
	private String MyIDUnique;
	private String MyCLassAttrjob;
	private String MyNameAttrjob;
	private String MyTypeAttrjob; // ��� ��������(��� �������� �� ������ � � ������ ��������� ��������)
	
	public CStructAttrjob() {// ����������� ���������, � �� ����� ������ ���� ��� ����� ����:
    }// Class mygpsx.CStructPriority is missing a constructor with no arguments !!!!!!!!!!!!!!!!!!
	
	public CStructAttrjob(String MyIDUnique, String MyCLassAttrjob, String MyNameAttrjob) 
	{
		this.MyIDUnique = MyIDUnique;
		this.MyCLassAttrjob = MyCLassAttrjob;
		this.MyNameAttrjob = MyNameAttrjob;
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
}
