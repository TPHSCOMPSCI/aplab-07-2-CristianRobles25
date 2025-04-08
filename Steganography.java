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
}
