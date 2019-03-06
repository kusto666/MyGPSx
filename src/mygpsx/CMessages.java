package mygpsx;

public class CMessages 
{
	public String msg_title;
	public String msg_body;
	public String msg_time;
	public String msg_text_time;
	private String MyFileUrl;
	public String msg_status;
	
	public CMessages() 
	{
/*		msg_title = null;
		msg_body = null;
		msg_time = null;*/
    }

    public CMessages(String stTitleMsg, String stTitleBody, String stTitleTime, String stStatus) 
    {
        this.msg_title = stTitleMsg;
        this.msg_body = stTitleBody;
        this.msg_time = stTitleTime;
        this.msg_status = stStatus;
    }
    public String getMyFileUrl()
    {
    	return MyFileUrl;
    }
    public void setMyFileUrl(String MyFileUrl)
    {
    	this.MyFileUrl = MyFileUrl;
    }
    
   /* public String GetTitle()
    {
    	return msg_title;
    }
    public String GetBody()
    {
    	return msg_body;
    }
    public String GetTime()
    {
    	return msg_time;
    }*/
}
