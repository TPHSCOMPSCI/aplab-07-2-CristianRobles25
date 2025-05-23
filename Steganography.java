import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
public class Steganography {
    public static void main(String[] args) {
        Picture beach = new Picture("beach.jpg");
       Picture motorcycle = new Picture("blueMotorcycle.jpg");
       Picture hidden = hidePicture(beach, motorcycle);
       hidden.explore();
       Picture robot = new Picture("robot.jpg");
       Picture flower1 = new Picture("flower1.jpg");
       beach.explore();
       Picture hidden1 = hidePicture2(beach, robot, 65, 208);
       Picture hidden2 = hidePicture2(hidden1, flower1, 280, 110);
       hidden2.explore();
       Picture unhidden = revealPicture(hidden2);
       unhidden.explore();
       Picture swan = new Picture("swan.jpg");
       Picture swan2 = new Picture("swan.jpg");
       System.out.println("Swan and swan2 are the same: " +
       isSame(swan, swan2));
       swan = testClearLow(swan);
       System.out.println("Swan and swan2 are the same (after clearLow run on swan): "
       + isSame(swan, swan2));
       Picture arch = new Picture("arch.jpg");
       Picture arch2 = new Picture("arch.jpg");
       Picture koala = new Picture("koala.jpg") ;
       Picture robot1 = new Picture("robot.jpg");
       ArrayList<Point> pointList = findDifferences(arch, arch2);
       System.out.println("PointList after comparing two identical pictures " +
       "has a size of " + pointList.size());
       pointList = findDifferences(arch, koala);
       System.out.println("PointList after comparing two different sized pictures " + "has a size of " + pointList.size());
       arch2 = hidePicture2(arch, robot1, 65, 102);
       pointList = findDifferences(arch, arch2);
       System.out.println("Pointlist after hiding a picture has a size of " + pointList.size());
       arch.show();
       arch2.show(); 
       Picture hall = new Picture("femaleLionAndHall.jpg");
       Picture robot2 = new Picture("robot.jpg");
       Picture flower2 = new Picture("flower1.jpg");
       Picture hall2 = hidePicture2(hall, robot2, 50, 300);
       Picture hall3 = hidePicture2(hall2, flower2, 115, 275);
       hall3.explore();
       if(!isSame(hall, hall3)) {
       Picture hall4 = showDifferentArea(hall,
           findDifferences(hall, hall3));
       hall4.show();
       Picture unhiddenHall3 = revealPicture(hall3);
       unhiddenHall3.show(); 
       }
       String hiddenMessage = "WHY HELLO THERE";
       System.out.println("Hidden message: " + hiddenMessage);
       hideText(beach, hiddenMessage);
       beach.explore();
       String revealedMessage = revealText(beach);
       System.out.println("Revealed message: " + revealedMessage);


        } 

    
    public static void clearLow(Pixel p){
        p.setRed((p.getRed()/4)*4);
        p.setGreen((p.getGreen()/4)*4);
        p.setBlue((p.getBlue()/4)*4);
    }
    public static Picture testClearLow(Picture P){
        Picture copy = new Picture(P);
        Pixel[][] pixels = copy.getPixels2D();
        for (int i = 0; i<pixels.length;i++){
            for(int j = 0; j<pixels[0].length; j++){
                clearLow(pixels[i][j]);
            }
        }
        return copy;
    }
    public static void setLow(Pixel p, Color c){
        int r1 = p.getRed();
        int g1 = p.getGreen();
        int b1 = p.getBlue();
        r1 = r1/4;
        g1 = g1/4;
        b1 = b1/4;
        p.setRed(r1*4+c.getRed()/64);
        p.setGreen(g1*4+c.getGreen()/64);
        p.setBlue(b1*4+c.getBlue()/64);
    }
    public static Picture testSetLow(Picture p, Color c){
        Picture copy = new Picture(p);
        Pixel[][] pixels = copy.getPixels2D();
        for (int i = 0; i<pixels.length;i++){
            for(int j = 0; j<pixels[0].length; j++){
                setLow(pixels[i][j], c);
            }
        }
        return copy;
    }
    public static Picture revealPicture(Picture hidden){
        Picture copy = new Picture(hidden);
        Pixel[][] pixels = copy.getPixels2D();
        Pixel[][] source = hidden.getPixels2D();
        for (int r = 0; r < pixels.length; r++){
            for (int c = 0; c < pixels[0].length; c++){
                Color col = source[r][c].getColor();
                int r1 = col.getRed();
                int g1 = col.getGreen();
                int b1 = col.getBlue();
                pixels[r][c].setRed((r1-(r1/4)*4)*64+r1/4);
                pixels[r][c].setGreen((g1-(g1/4)*4)*64+g1/4);
                pixels[r][c].setBlue((b1-(b1/4)*4)*64+b1/4);
            }
        }
        return copy;
    }
    public static boolean canHide(Picture source, Picture secret){
        if (source.getHeight() >= secret.getHeight() && source.getWidth() >= secret.getWidth()){
            Picture copy = hidePicture(source, secret);
            copy.explore();
            revealPicture(copy);
            revealPicture(copy).explore();
            return true;
        }
        else{
            return false;
        }
    }
    public static Picture hidePicture(Picture source, Picture secret){
        Picture copy = new Picture(source);
        Pixel[][] pixles = copy.getPixels2D();
        Pixel[][] secretP = secret.getPixels2D();
        for (int r=0;r<pixles.length;r++){
            for(int c=0;c<pixles[0].length;c++){
                Color col = secretP[r][c].getColor();
                Color col1 = pixles[r][c].getColor();
                int r1 = col.getRed();
                int g1 = col.getGreen();
                int b1 = col.getBlue();
                int r2 = col1.getRed();
                int g2 = col1.getGreen();
                int b2 = col1.getBlue();
                r1 = r1/64;
                g1 = g1/64;
                b1 = b1/64;
                r2 = (r2/4)*4;
                g2 = (g2/4)*4;
                b2 = (b2/4)*4;
                pixles[r][c].setRed(r1+r2);
                pixles[r][c].setGreen(g1+g2);
                pixles[r][c].setBlue(b1+b2);
            }
        }
        return copy;
    }
    public static Picture hidePicture2(Picture source, Picture secret, int startRow, int startColumn){
        Picture copy = new Picture(source);
        Pixel[][] pixles = copy.getPixels2D();
        Pixel[][] secretP = secret.getPixels2D();
        for (int r=startRow;r<startRow+secret.getHeight();r++){
            for(int c=startColumn;c<startColumn+secret.getWidth();c++){
                Color col = secretP[r-startRow][c-startColumn].getColor();
                Color col1 = pixles[r][c].getColor();
                int r1 = col.getRed();
                int g1 = col.getGreen();
                int b1 = col.getBlue();
                int r2 = col1.getRed();
                int g2 = col1.getGreen();
                int b2 = col1.getBlue();
                r1 = r1/64;
                g1 = g1/64;
                b1 = b1/64;
                r2 = (r2/4)*4;
                g2 = (g2/4)*4;
                b2 = (b2/4)*4;
                pixles[r][c].setRed(r1+r2);
                pixles[r][c].setGreen(g1+g2);
                pixles[r][c].setBlue(b1+b2);
                
            }
        }
        return copy;
    }
    public static boolean isSame(Picture first, Picture second){
        Pixel[][] firstP = first.getPixels2D();
        Pixel[][] secondP = second.getPixels2D();
        if (firstP.length == secondP.length && firstP[0].length == secondP[0].length){
            for (int r=0; r<firstP.length;r++){
                for (int c=0;c<firstP[0].length;c++){
                    if (firstP[r][c].getRed()!=secondP[r][c].getRed() || firstP[r][c].getGreen()!=secondP[r][c].getGreen() || firstP[r][c].getBlue()!=secondP[r][c].getBlue()){
                        return false;
                    }
                }
            }
            return true;
        }
        else{
            return false;
        } 
    }
    public static ArrayList findDifferences(Picture p1, Picture p2){
        Pixel[][] firstP = p1.getPixels2D();
        Pixel[][] secondP = p2.getPixels2D();
        ArrayList<Point> pointList = new ArrayList<>();
        if (firstP.length == secondP.length && firstP[0].length == secondP[0].length){
            for (int r=0; r<firstP.length;r++){
                for (int c=0;c<firstP[0].length;c++){
                    if (firstP[r][c].getRed()!=secondP[r][c].getRed() || firstP[r][c].getGreen()!=secondP[r][c].getGreen() || firstP[r][c].getBlue()!=secondP[r][c].getBlue()){
                        pointList.add(new Point(r, c));
                    }
                }
            }
        return pointList;
        }
        else{
            return pointList;
        }
    }
    public static Picture showDifferentArea(Picture p, ArrayList<Point> differences){
        int minRow = Integer.MAX_VALUE, maxRow = Integer.MIN_VALUE;
       int minCol = Integer.MAX_VALUE, maxCol = Integer.MIN_VALUE;
       for (Point point : differences) {
           int row = point.x;
           int col = point.y;
           if (row < minRow) minRow = row;
           if (row > maxRow) maxRow = row;
           if (col < minCol) minCol = col;
           if (col > maxCol) maxCol = col;
       }
       Picture result = new Picture(p);
       Pixel[][] pixels = result.getPixels2D();
       Color rectColor = Color.RED;
       for (int c = minCol; c <= maxCol; c++) {
           if (minRow >= 0 && minRow < pixels.length)
               pixels[minRow][c].setColor(rectColor);
           if (maxRow >= 0 && maxRow < pixels.length)
               pixels[maxRow][c].setColor(rectColor);
       }
       for (int r = minRow; r <= maxRow; r++) {
           if (minCol >= 0 && minCol < pixels[0].length)
               pixels[r][minCol].setColor(rectColor);
           if (maxCol >= 0 && maxCol < pixels[0].length)
               pixels[r][maxCol].setColor(rectColor);
       }
       return result;

    }
    public static ArrayList<Integer> encodeString(String s){
        s = s.toUpperCase();
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < s.length(); i++){
            if (s.substring(i,i+1).equals(" ")){
                result.add(27);
             }
            else{
            result.add(alpha.indexOf(s.substring(i,i+1))+1);
            }
        }
        result.add(0);
        return result;
    } 
    private static String decodeString(ArrayList<Integer> codes){
        String result="";
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i=0; i < codes.size(); i++){
            if (codes.get(i) == 27){
            result = result + " ";
            }
        else{
        result = result + alpha.substring(codes.get(i)-1,codes.get(i));
        }
    }
    return result;
    }
    private static int[] getBitPairs(int num){
        int[] bits = new int[3];
        int code = num;
        for (int i = 0; i < 3; i++){
        bits[i] = code % 4;
        code = code / 4;
        }
        return bits;
    }
    public static Picture hideText(Picture source, String s) {
        Picture copy = new Picture(source);
        ArrayList<Integer> codes = encodeString(s);
        Pixel[][] pixels = copy.getPixels2D();
        int index = 0;
        outer:
        for (int r = 0; r < pixels.length; r++) {
            for (int c = 0; c < pixels[0].length; c++) {
                if (index >= codes.size()) break outer;
                int code = codes.get(index);
                int redBits = (code >> 4) & 0x3;
                int greenBits = (code >> 2) & 0x3;
                int blueBits = code & 0x3;
                int red = (pixels[r][c].getRed() / 4) * 4 + redBits;
                int green = (pixels[r][c].getGreen() / 4) * 4 + greenBits;
                int blue = (pixels[r][c].getBlue() / 4) * 4 + blueBits;
                pixels[r][c].setColor(new Color(red, green, blue));
                index++;
            }
        }
        if (index < codes.size()) {
            System.out.println("Warning: Not enough pixels to hide the entire message.");
        }
        return copy;
    }
    public static String revealText(Picture source) {
        Pixel[][] pixels = source.getPixels2D();
        ArrayList<Integer> codes = new ArrayList<Integer>();
        outer:
        for (int r = 0; r < pixels.length; r++) {
            for (int c = 0; c < pixels[0].length; c++) {
                int redLow = pixels[r][c].getRed() % 4;
                int greenLow = pixels[r][c].getGreen() % 4;
                int blueLow = pixels[r][c].getBlue() % 4;
                int code = (redLow << 4) | (greenLow << 2) | blueLow;
                codes.add(code);
                if (code == 0) break outer;
            }
        }
        return decodeString(codes);
    }
    private static Picture setBlack(Picture P){
        Picture copy = new Picture(P);
        Pixel[][] pixels = copy.getPixels2D();
        for (int i = 0; i<pixels.length;i++){
            for(int j = 0; j<pixels[0].length; j++){
                pixels[i][j].setRed(0);
                pixels[i][j].setGreen(0);
                pixels[i][j].setBlue(0);
            }
        }
        return copy;
    }
}