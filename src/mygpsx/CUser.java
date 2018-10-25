package mygpsx;

import com.google.firebase.database.IgnoreExtraProperties;

/////////////////////////////////////////////////////////////
// Основной class пользователя(перемещаемого объекта)!!! 
/////////////////////////////////////////////////////////////

@IgnoreExtraProperties
public class CUser {
    public String phoneID;
    public String MyLatitude;
    public String MyLongitude;
    public String email;
    
    private String MyNameShip;
    private String MyDirectorShip;
    public String MyShortDescriptionShip;
    
   
    
    enum GENDER {
        MALE,
        FEMALE
    }

    public CUser() 
    {

    }

    
    public CUser(String phoneID, String MyNameShip, String MyDirectorShip) 
    {
        this.phoneID = phoneID;
        this.MyNameShip = MyNameShip;
        this.MyDirectorShip = MyDirectorShip;
    }

    public CUser(String phoneID,
    			 String MyLatitude, 
    			 String MyLongitude,  
    			 String email,
    			 String MyNameShip,
    			 String MyDirectorShip,
    			 String MyShortDescriptionShip) 
    {
        this.phoneID = phoneID;
        this.MyLatitude = MyLatitude;
        this.MyLongitude = MyLongitude;
        this.email = email;
        
        this.MyNameShip = MyNameShip;
        this.MyDirectorShip = MyDirectorShip;
        this.MyShortDescriptionShip = MyShortDescriptionShip;
    }
    public String getMyNameShip()
    {
    	return MyNameShip;
    }
    
    public String getMyDirectorShip()
    {
    	return MyDirectorShip;
    }
    
    
}