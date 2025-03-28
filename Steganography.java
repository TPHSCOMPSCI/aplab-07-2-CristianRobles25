public class Steganography {
    public static void main(String[] args) {
        Picture beach = new Picture("beach.jpg");
        beach.explore();
        Picture copy = testClearLow(beach);
        copy.explore();
    }
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
}
