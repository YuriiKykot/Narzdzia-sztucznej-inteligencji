import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Perceptron {
    private String nazwa;
    private double[] wagi = new double[26];
    private double prog;
    private ArrayList leng;

    public Perceptron(String nazwa) {
        this.nazwa = nazwa;
        for(int i = 0; i < wagi.length; i++) {
            wagi[i] = Math.random();
        }
        prog = Math.random();
//        leng = readLeng(katalog,nazwa);
    }

//    public static double rnd(int min, int max)
//    {
//        max -= min;
//        return (Math.random() * ++max) + min;
//    }

//    public static ArrayList readLeng(String katalog1, String katalog2){
//        ArrayList tmp = new ArrayList();
//        File dir = new File(katalog1 + "/" + katalog2);
//        for(String path : dir.list()) {
//            tmp.add(path);
//        }
//        return tmp;
//    }

    public String getNazwa() {
        return nazwa;
    }

    public double[] getWagi() {
        return wagi;
    }

    public double getProg() {
        return prog;
    }

    public ArrayList getLeng() {
        return leng;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setWagi(double[] wagi) {
        this.wagi = wagi;
    }

    public void setProg(double prog) {
        this.prog = prog;
    }

    public void setLeng(ArrayList leng) {
        this.leng = leng;
    }
}
