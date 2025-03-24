public class Steganography {
    public static void clearLow(Pixel p){
        int r = p.getRed();
        int g = p.getGreen();
        int b = p.getBlue();
        r = r/4;
        g = g/4;
        b = b/4;
        r=r*4;
        g=g*4;
        b=b*4;
    }
    
}
