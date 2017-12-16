package GUI;

/**
 *
 * @author Administrator
 */

public class Constants {
    private static double rowHeight=25;
    private static double columnWidth=250;
    private static double distanceBoxes=50;
    private static double typeOfNodeSize=10;
    private static double contentSize=15;
    private static final double factor=1.1;
    private static final int limitAllowed=3;
    private static boolean ableToZoomOut=true;
    public static void zoomIn(){
        rowHeight*=factor;
        columnWidth*=factor;
        distanceBoxes*=factor;
        typeOfNodeSize*=factor;
        contentSize*=factor;
    }
    public static void zoomOut(){
        if(typeOfNodeSize>limitAllowed*factor){
        ableToZoomOut=true;
        rowHeight/=factor;
        columnWidth/=factor;
        distanceBoxes/=factor;
        typeOfNodeSize/=factor;
        contentSize/=factor;  
        }
        else ableToZoomOut=false;
    }
    public static boolean isAbleToZoomOut() {
        
        return ableToZoomOut;
    }
    
    public static double getFactor() {
        return factor;
    }
    
    public static int getTypeOfNodeSize() {
        return (int)typeOfNodeSize;
    }

    public static int getContentSize() {
        return (int)contentSize;
    }
    
    public static int getRowHeight() {
        return (int)rowHeight;
    }
    
    public static int getColumnWidth() {
        return (int)columnWidth;
    }

    public static int getDistanceBoxes() {
        return (int)distanceBoxes;
    }
}
