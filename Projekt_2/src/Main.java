import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static int count;
    public static double prog;
    public static double poprawneDlaSet = 0;
    public static double poprawneDlaVer = 0;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Prosze podac a");
        double a = scanner.nextDouble();
        /*System.out.println("Prosze podac train-set");
        String trainSet = scanner.next();
        System.out.println("Prosze podac test-set");
        String testSet = scanner.next();*/

        FileReader FtestSet = new FileReader("test-set.csv");
        //FileReader FtrainSet = new FileReader("train-set.csv");
        FileReader FtrainSet = new FileReader("Mesh.csv");
        //FileReader FtestSet = new FileReader(testSet);
        //FileReader FtrainSet = new FileReader(trainSet);
        Scanner StestSet = new Scanner(FtestSet);
        Scanner StrainSet = new Scanner(FtrainSet);

        count = counter(StrainSet.next());

        //FtrainSet = new FileReader("train-set.csv");
        FtrainSet = new FileReader("Mesh.csv");
        //FtrainSet = new FileReader(trainSet);
        StrainSet = new Scanner(FtrainSet);

        String nazwy[] = new String[2];
        int nazwyLenght[] = new int[2];
        String str2 = StestSet.next();
        nazwy[0] = serchNazwa(str2);
        while(StestSet.hasNextLine()){
            String str1 = StestSet.next();
            str1 = serchNazwa(str1);
            if(str2 != str1){
                nazwy[1] = str1;
            }
        }

        nazwyLenght[0] = nazwy[0].length();
        nazwyLenght[1] = nazwy[1].length();

        FtestSet = new FileReader("test-set.csv");
        //FtestSet = new FileReader(testSet);
        StestSet = new Scanner(FtestSet);

        prog = rnd(-5,5);

        double wagi[] = new double[count];
        for(int i = 0; i < wagi.length; i++){
            wagi[i] = rnd(-5,5);
        }

        while(StrainSet.hasNextLine()){
            int y;
            String main = StrainSet.next();
            double mainTab[] = wektor(main);
            String mainNazwa = serchNazwa(main);
            int skalar = 0;
            for(int i = 0; i < count; i++){
                skalar += (mainTab[i] * wagi[i]);
            }
            if(skalar >= prog) {
                y = 1;
                if(mainNazwa.length() != nazwyLenght[1]){
                    wagi = regulaDelta(wagi,mainTab,y,0,a);
                }
            }else if(skalar < prog){
                y = 0;
                if(mainNazwa.length() != nazwyLenght[0]){
                    wagi = regulaDelta(wagi,mainTab,y,1,a);
                }
            }
        }

        while(StestSet.hasNextLine()){
            int y;
            String main = StestSet.next();
            double mainTab[] = wektor(main);
            String mainNazwa = serchNazwa(main);
            int skalar = 0;
            for(int i = 0; i < count; i++){
                skalar += (mainTab[i] * wagi[i]);
            }
            if(skalar >= prog) {
                y = 1;
                if(mainNazwa.length() == nazwyLenght[1]){
                    poprawneDlaSet++;
                }
            }else if(skalar < prog){
                y = 0;
                if(mainNazwa.length() == nazwyLenght[0]){
                    poprawneDlaVer++;
                }
            }
        }
        System.out.println("Poprawne dla 1 " + poprawneDlaSet/15);
        System.out.println("Poprawne dla 0 " + poprawneDlaVer/15);
        double sum = poprawneDlaSet + poprawneDlaVer;
        System.out.println("Poprawne sum " + sum/30);

        System.out.println("Prosze podac wektor, jezeli nie pan(i) chce podawac wektor prosze wipisac 1");
        String wek = scanner.next();
        while(wek.length() != 1){
            int y;
            double mainTab[] = wektor(wek);
            System.out.println();
            int skalar = 0;
            for(int i = 0; i < count; i++){
                skalar += (mainTab[i] * wagi[i]);
            }
            if(skalar >= prog) {
                y = 1;
                System.out.println("Iris-versicolor");
            }else if(skalar < prog){
                y = 0;
                System.out.println("Iris-setosa");
            }
            wek = scanner.next();
        }
    }

    public static double[] regulaDelta2(double[] w, double[] x, double y, double d, double a){
        double tabDeltaW[] = new double[count];
        double newTabX[] = new double[count];
        prog = prog + (d-y) * a * (-1);
        for(int i = 0; i < newTabX.length; i++){
            newTabX[i] = ((d - y) * a) * x[i];
        }
        for(int i = 0; i < count; i++){
            tabDeltaW[i] = newTabX[i] + w[i];
        }
        return tabDeltaW;
    }

    public static double[] regulaDelta(double[] w, double[] x, double y, double d, double a){
        double newTabX[] = new double[count+1];
        double newTabW[] = new double[count+1];

        for(int i = 0; i < w.length; i++){
            newTabW[i] = w[i];
        }
        newTabW[newTabW.length - 1] = prog;

        for(int i = 0; i < x.length; i++){
            newTabX[i] = x[i];
        }
        newTabX[newTabX.length - 1] = -1;

        for(int i = 0; i < newTabX.length; i++){
            newTabX[i] = (d-y) * a * newTabX[i];
        }

        double deltaW[] = new double[count+1];
        for(int i = 0; i < count + 1; i++){
            deltaW[i] = newTabW[i] + newTabX[i];
        }

        prog = deltaW[deltaW.length - 1];

        double lastDeltaW[] = new double[count];
        for(int i = 0; i < lastDeltaW.length; i++){
            lastDeltaW[i] = deltaW[i];
        }

        return lastDeltaW;
    }

    public static int counter(String start){
        int countered = 0;
        for(int i = 0; i < start.length(); i++){
            if(start.charAt(i) == ';'){
                countered++;
            }
        }
        return countered;
    }

    public static double[] wektor(String start){
        double wTab[] = new double[count];
        int x = 0, y = 3;
        String str = "";
        for (int i = 0; i < count; i++) {
            while (x < y) {
                str += start.charAt(x);
                x++;
            }
            wTab[i] = Double.parseDouble(str);
            str = "";
            x += 1;
            y += 4;
        }
        return wTab;
    }

    public static String serchNazwa(String start){
        String nazwa = "";
        for (int i = 4*count; i < start.length(); i++) {
            nazwa += start.charAt(i);
        }
        return nazwa;
    }

    public static double rnd(int min, int max)
    {
        max -= min;
        return (Math.random() * ++max) + min;
    }
}
