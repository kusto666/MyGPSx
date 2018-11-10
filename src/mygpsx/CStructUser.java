package mygpsx;

import com.google.firebase.database.IgnoreExtraProperties;

/////////////////////////////////////////////////////////////
// РћСЃРЅРѕРІРЅРѕР№ class РїРѕР»СЊР·РѕРІР°С‚РµР»СЏ(РїРµСЂРµРјРµС‰Р°РµРјРѕРіРѕ РѕР±СЉРµРєС‚Р°)!!! 
/////////////////////////////////////////////////////////////

@IgnoreExtraProperties
public class CStructUser {
	private String MyPhoneID;
	private String MyLatitude;
	private String MyLongitude;
	private String MyEmail;
    
    private String MyNameShip;
    private String MyDirectorShip;
    private String MyShortDescriptionShip;
    
    private String MyIsUserSelected; // Переменная отвечает за то что обект выбран - true, и наоборот - false!!!
   
    
    enum GENDER {
        MALE,
        FEMALE
    }

    public CStructUser() 
    {

    }

    
    public CStructUser(String MyPhoneID, String MyNameShip, String MyDirectorShip) 
    {
        this.MyPhoneID = MyPhoneID;
        this.MyNameShip = MyNameShip;
        this.MyDirectorShip = MyDirectorShip;
    }

    public CStructUser(String MyPhoneID,
    			 String MyLatitude, 
    			 String MyLongitude,  
    			 String MyEmail,
    			 String MyNameShip,
    			 String MyDirectorShip,
    			 String MyShortDescriptionShip, 
    			 String MyIsUserSelected) 
    {
        this.MyPhoneID = MyPhoneID;
        this.MyLatitude = MyLatitude;
        this.MyLongitude = MyLongitude;
        this.MyEmail = MyEmail;
        
        this.MyNameShip = MyNameShip;
        this.MyDirectorShip = MyDirectorShip;
        this.MyShortDescriptionShip = MyShortDescriptionShip;
        this.MyIsUserSelected = MyIsUserSelected;
    }
    
    public String getMyPhoneID()
    {
    	return MyPhoneID;
    }
    public String getMyLatitude()
    {
    	return MyLatitude;
    }
    public String getMyLongitude()
    {
    	return MyLongitude;
    }
    public String getMyEmail()
    {
    	return MyEmail;
    }
    public String getMyShortDescriptionShip()
    {
    	return MyShortDescriptionShip;
    }
    
    public String getMyDirectorShip()
    {
    	return MyDirectorShip;
    }
    public String getMyNameShip()
    {
    	return MyNameShip;
    }
    public String getMyIsUserSelected()
    {
    	return MyIsUserSelected;
    }
    /////////////////////////////////////SET////////////////////////////////////////
    public void setMyPhoneID(String MyPhoneID)
    {
        this.MyPhoneID = MyPhoneID;
    }
    public void setMyLatitude(String MyLatitude)
    {
        this.MyLatitude = MyLatitude;
    }
    public void setMyLongitude(String MyLongitude)
    {
        this.MyLongitude = MyLongitude;
    }
    public void setMyEmail(String MyEmail)
    {
        this.MyEmail = MyEmail;
    }
    public void setMyShortDescriptionShip(String MyShortDescriptionShip)
    {
        this.MyShortDescriptionShip = MyShortDescriptionShip;
    }
    public void setMyDirectorShip(String MyDirectorShip)
    {
        this.MyDirectorShip = MyDirectorShip;
    }
    public void setMyNameShip(String MyNameShip)
    {
        this.MyNameShip = MyNameShip;
    }
    public void setMyIsUserSelected(String MyIsUserSelected)
    {
        this.MyIsUserSelected = MyIsUserSelected;
    }
    @Override
    public String toString()  {
        return ("Капитан: '" + this.getMyDirectorShip() + "', Судно: '" + this.getMyNameShip() + "'");
    }
    
    
}