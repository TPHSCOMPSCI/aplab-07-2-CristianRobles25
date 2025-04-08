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
}
