package GUI;

/**
 *
 * @author Administrator
 */

public class ConstantAtribute {
    private static int rowHeight=25;
    private static int columnWidth=250;
    private static int distanceBoxes=50;
    private static int typeOfNodeSize=10;
    private static int contentSize=15;
    private static double factor=1.1;
    public static void zoomIn(){
        rowHeight*=factor;
        columnWidth*=factor;
        distanceBoxes*=factor;
        typeOfNodeSize*=factor;
        contentSize*=factor;
    }
    public static void zoomOut(){
        rowHeight/=factor;
        columnWidth/=factor;
        distanceBoxes/=factor;
        typeOfNodeSize/=factor;
        contentSize/=factor;
    }

    public static double getFactor() {
        return factor;
    }
    
    public static int getTypeOfNodeSize() {
        return typeOfNodeSize;
    }

    public static int getContentSize() {
        return contentSize;
    }
    
    public static int getRowHeight() {
        return rowHeight;
    }
    
    public static int getColumnWidth() {
        return columnWidth;
    }

    public static int getDistanceBoxes() {
        return distanceBoxes;
    }
}
