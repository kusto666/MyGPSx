package mygpsx;

import java.text.SimpleDateFormat;
import java.util.Date;

// Класс для работы со временем для отображения ПОКА в переписке!!!
public class CDateTime 
{
	private Date m_MyDate;
	private String m_MyTime;
	private Long m_MyLongTime;
	//private String m_MyDate;
	
	
	public CDateTime()
	{
		m_MyDate = new Date();
		m_MyLongTime = System.currentTimeMillis()/* / 1000L*/;
		System.out.println("UNIX m_MyLongTime = " + m_MyLongTime.toString());
		
		m_MyDate = new Date(m_MyLongTime);
        System.out.println(m_MyDate);  
        
        //SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
        String dateText = format.format(m_MyDate);
        System.out.println(dateText);
        
	}
	
	public Long GetLongTime()
	{
		return m_MyLongTime;
	}
	/*public String GetPrintTime()
	{
		
	}*/
}
