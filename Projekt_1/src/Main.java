import org.w3c.dom.ls.LSOutput;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Prosze podac k");
        int k = scanner.nextInt();
        /*System.out.println("Prosze podac test-set");
        String test = scanner.next();
        System.out.println("Prosze podac train-set");
        String train = scanner.next();*/
        String test = "test-set.csv";
        String train = "train-set.csv";
        double main[] = new double[4];
        String nazwaMain = "";
        double poprawne = 0;

        FileReader rToTest = null;
        FileReader rToTrain = null;

        rToTest = new FileReader(test);
        Scanner sToTest = new Scanner(rToTest);

        while(sToTest.hasNextLine()) {
            rToTrain = new FileReader(train);
            Scanner sToTrain = new Scanner(rToTrain);

            String start = sToTest.next();
            nazwaMain = "";

            int x = 0, y = 3;
            String str = "";
            for (int i = 0; i < 4; i++) {
                while (x < y) {
                    str += start.charAt(x);
                    x++;
                }
                main[i] = Double.parseDouble(str);
                str = "";
                x += 1;
                y += 4;
            }

            for (int i = 16; i < start.length(); i++) {
                nazwaMain += start.charAt(i);
            }

            double kTab[] = new double[k];
            String kTabNazwy[] = new String[k];
            int g = 0;
            double max = 0;

            while (sToTrain.hasNextLine()) {
                String iris = sToTrain.next();

                double por[] = new double[4];
                String nazwa = "";
                double sum = 0;

                int z = 0, d = 3;
                String gdr = "";
                for (int i = 0; i < 4; i++) {
                    while (z < d) {
                        gdr += iris.charAt(z);
                        z++;
                    }
                    por[i] = Double.parseDouble(gdr);
                    gdr = "";
                    z += 1;
                    d += 4;
                }
                for (int i = 16; i < iris.length(); i++) {
                    nazwa += iris.charAt(i);
                }

                sum = Math.sqrt(Math.pow((main[0] - por[0]), 2) + Math.pow((main[1] - por[1]), 2) + Math.pow((main[2] - por[2]), 2) + Math.pow((main[3] - por[3]), 2));

                if (g == 0) {
                    max = sum;
                }
                if (g < kTab.length) {
                    kTab[g] = sum;
                    kTabNazwy[g] = nazwa;
                    if (sum > max) {
                        max = sum;
                    }
                } else if (g >= kTab.length) {
                    if (sum < max) {
                        for (int i = 0; i < kTab.length; i++) {
                            if (kTab[i] == max) {
                                kTab[i] = sum;
                                kTabNazwy[i] = nazwa;
                                max = sum;
                            }
                        }
                        for (int i = 0; i < kTab.length; i++) {
                            if (kTab[i] > max) {
                                max = kTab[i];
                            }
                        }
                    }
                }
                g++;
            }

            int nalezyDoVir = 0;
            int nalezyDoVer = 0;
            int nalezyDoSet = 0;
            String result = "";

            System.out.println();
            for(int i = 0; i < main.length; i++){
                System.out.print(main[i] + ";");
            }
            System.out.print(" " + nazwaMain + " nalezy do");
            for (int i = 0; i < kTabNazwy.length; i++) {
                switch (kTabNazwy[i].length()) {
                    case 11: nalezyDoSet++;break;
                    case 15: nalezyDoVer++;break;
                    case 14: nalezyDoVir++;break;
                }
            }
            if (nalezyDoSet >= nalezyDoVer && nalezyDoSet >= nalezyDoVir) {
                result = "Iris-setosa";
                System.out.print(" Iris-setosa \n");
            } else if (nalezyDoVer > nalezyDoSet && nalezyDoVer >= nalezyDoVir) {
                result = "Iris-versicolor";
                System.out.print(" Iris-versicolor \n");
            } else if (nalezyDoVir > nalezyDoSet && nalezyDoVir > nalezyDoVer) {
                result = "Iris-virginica";
                System.out.print(" Iris-virginica \n");
            }
            if (nazwaMain.equals(result)) {
                poprawne++;
            }
        }
        System.out.println();
        System.out.println(poprawne/35);
        System.out.println();

        System.out.println("Prosze podac wektor, jezeli nie pan(i) chce podawac wektor prosze wipisac 1");
        String wektor = scanner.next();
        while(wektor.length() != 1){
            rToTrain = new FileReader(train);
            Scanner sToTrain = new Scanner(rToTrain);

            int x = 0, y = 3;
            String str = "";
            for (int i = 0; i < 4; i++) {
                while (x < y) {
                    str += wektor.charAt(x);
                    x++;
                }
                main[i] = Double.parseDouble(str);
                str = "";
                x += 1;
                y += 4;
            }

            double kTab[] = new double[k];
            String kTabNazwy[] = new String[k];
            int g = 0;
            double max = 0;

            while (sToTrain.hasNextLine()) {
                String iris = sToTrain.next();

                double por[] = new double[4];
                String nazwa = "";
                double sum = 0;

                int z = 0, d = 3;
                String gdr = "";
                for (int i = 0; i < 4; i++) {
                    while (z < d) {
                        gdr += iris.charAt(z);
                        z++;
                    }
                    por[i] = Double.parseDouble(gdr);
                    gdr = "";
                    z += 1;
                    d += 4;
                }
                for (int i = 16; i < iris.length(); i++) {
                    nazwa += iris.charAt(i);
                }

                sum = Math.sqrt(Math.pow((main[0] - por[0]), 2) + Math.pow((main[1] - por[1]), 2) + Math.pow((main[2] - por[2]), 2) + Math.pow((main[3] - por[3]), 2));

                if (g == 0) {
                    max = sum;
                }
                if (g < kTab.length) {
                    kTab[g] = sum;
                    kTabNazwy[g] = nazwa;
                    if (sum > max) {
                        max = sum;
                    }
                } else if (g >= kTab.length) {
                    if (sum < max) {
                        for (int i = 0; i < kTab.length; i++) {
                            if (kTab[i] == max) {
                                kTab[i] = sum;
                                kTabNazwy[i] = nazwa;
                                max = sum;
                            }
                        }
                        for (int i = 0; i < kTab.length; i++) {
                            if (kTab[i] > max) {
                                max = kTab[i];
                            }
                        }
                    }
                }
                g++;
            }

            int nalezyDoVir = 0;
            int nalezyDoVer = 0;
            int nalezyDoSet = 0;
            String result = "";

            System.out.println();
            for(int i = 0; i < main.length; i++){
                System.out.print(main[i] + ";");
            }
            System.out.print(" nalezy do");
            for (int i = 0; i < kTabNazwy.length; i++) {
                switch (kTabNazwy[i].length()) {
                    case 11: nalezyDoSet++;break;
                    case 15: nalezyDoVer++;break;
                    case 14: nalezyDoVir++;break;
                }
            }
            if (nalezyDoSet >= nalezyDoVer && nalezyDoSet >= nalezyDoVir) {
                result = "Iris-setosa";
                System.out.print(" Iris-setosa \n");
            } else if (nalezyDoVer > nalezyDoSet && nalezyDoVer >= nalezyDoVir) {
                result = "Iris-versicolor";
                System.out.print(" Iris-versicolor \n");
            } else if (nalezyDoVir > nalezyDoSet && nalezyDoVir > nalezyDoVer) {
                result = "Iris-virginica";
                System.out.print(" Iris-virginica \n");
            }
            System.out.println("Prosze podac wektor, jezeli nie pan(i) chce podawac wektor prosze wipisac 1");
            wektor = scanner.next();
        }
    }
}
