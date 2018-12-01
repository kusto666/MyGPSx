package mygpsx;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public final class CMyToast
{
	// Стили тоста!!!
	public static final int TOAST_SUCCESS = 11; // GREEN
    public static final int TOAST_WARN = 12; // YELLOW
    public static final int TOAST_ERROR = 13;// RED
    // Еще стили только короткий тост или долгий
    public static final int TOAST_SHORT = 0; // GREEN
    public static final int TOAST_LONG = 1;
    
    static int toastDelay = 0;
    static int fadeInDelay = 0;
	static int fadeOutDelay = 0;
    
    public static void makeText(Stage ownerStage, String toastMsg, int toastTime, int toastInfoStyle )
    /*public static void makeText(Stage ownerStage, String toastMsg, int toastDelay, int fadeInDelay, int fadeOutDelay)*/
    {
    	toastDelay = 0;
    	
    	// Установка короткого или длинного тоста!!!
    	switch (toastTime)
    	{
	        case TOAST_SHORT:
	        	toastDelay = 1200; 
	        	fadeInDelay = 250; 
	        	fadeOutDelay = 250;
	            break;
	        case TOAST_LONG:
	        	toastDelay = 3500;
	        	fadeInDelay = 500;
	        	fadeOutDelay = 500;
	            break;
    	}
        Stage toastStage=new Stage();
        toastStage.initOwner(ownerStage);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Text text = new Text(toastMsg);
        text.setFont(Font.font("Verdana", 20));
 
     // Установка стиля информации!!!
    	switch (toastInfoStyle)
    	{
	        case TOAST_SUCCESS:
	            text.setFill(Color.LAWNGREEN);
	            break;
	        case TOAST_WARN:
	            text.setFill(Color.YELLOW);
	            break;
	        case TOAST_ERROR:
	            text.setFill(Color.RED);
	            break;
    	}

        StackPane root = new StackPane(text);
        root.setStyle("-fx-background-radius: 10; -fx-background-color: rgba(0, 0, 0, 0.7); -fx-padding: 10px;");
        root.setOpacity(0);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        toastStage.setScene(scene);
        toastStage.show();

        Timeline fadeInTimeline = new Timeline();
        KeyFrame fadeInKey1 = new KeyFrame(Duration.millis(fadeInDelay), new KeyValue (toastStage.getScene().getRoot().opacityProperty(), 1)); 
        fadeInTimeline.getKeyFrames().add(fadeInKey1);   
        fadeInTimeline.setOnFinished((ae) -> 
        {
            new Thread(() -> {
                try
                {
                    Thread.sleep(toastDelay);
                }
                catch (InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                   Timeline fadeOutTimeline = new Timeline();
                    KeyFrame fadeOutKey1 = new KeyFrame(Duration.millis(fadeOutDelay), new KeyValue (toastStage.getScene().getRoot().opacityProperty(), 0)); 
                    fadeOutTimeline.getKeyFrames().add(fadeOutKey1);   
                    fadeOutTimeline.setOnFinished((aeb) -> toastStage.close()); 
                    fadeOutTimeline.play();
            }).start();
        }); 
        fadeInTimeline.play();
    }
    // ЭТО БЫЛО ИЗНАЧАЛЬНО!!!
   /* public static void makeText(Stage ownerStage, String toastMsg, int toastDelay, int fadeInDelay, int fadeOutDelay)
    {
        Stage toastStage=new Stage();
        toastStage.initOwner(ownerStage);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Text text = new Text(toastMsg);
        text.setFont(Font.font("Verdana", 40));
        text.setFill(Color.RED);

        StackPane root = new StackPane(text);
        root.setStyle("-fx-background-radius: 20; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-padding: 50px;");
        root.setOpacity(0);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        toastStage.setScene(scene);
        toastStage.show();

        Timeline fadeInTimeline = new Timeline();
        KeyFrame fadeInKey1 = new KeyFrame(Duration.millis(fadeInDelay), new KeyValue (toastStage.getScene().getRoot().opacityProperty(), 1)); 
        fadeInTimeline.getKeyFrames().add(fadeInKey1);   
        fadeInTimeline.setOnFinished((ae) -> 
        {
            new Thread(() -> {
                try
                {
                    Thread.sleep(toastDelay);
                }
                catch (InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                   Timeline fadeOutTimeline = new Timeline();
                    KeyFrame fadeOutKey1 = new KeyFrame(Duration.millis(fadeOutDelay), new KeyValue (toastStage.getScene().getRoot().opacityProperty(), 0)); 
                    fadeOutTimeline.getKeyFrames().add(fadeOutKey1);   
                    fadeOutTimeline.setOnFinished((aeb) -> toastStage.close()); 
                    fadeOutTimeline.play();
            }).start();
        }); 
        fadeInTimeline.play();
    }*/
}
