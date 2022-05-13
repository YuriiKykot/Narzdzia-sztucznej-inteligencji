import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static String katalog;
    public static double a = 0.05;
    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(System.in);
        //System.out.println("Proszze podac nazwe katalogu");
        //String katalog = scanner.next();

        katalog = "katalog";
        File dir = new File(katalog);
        ArrayList<String> nazwy = new ArrayList<>();
        for(String path : dir.list()) {
            nazwy.add(path);
        }

        ArrayList<List> lists = new ArrayList<>();
        for (int i = 0; i < nazwy.size(); i++) {
            lists.add(new List(katalog,nazwy.get(i)));
        }

        ArrayList<Perceptron> perceptrons = new ArrayList<>();
        for(int i = 0; i < nazwy.size(); i++){
            perceptrons.add(new Perceptron(nazwy.get(i)));
        }

//        System.out.println("po trwozeniy p");
//        for(int i = 0; i < perceptrons.size(); i++){
//            for(int j = 0; j < perceptrons.get(i).getWagi().length; j++){
//                System.out.print(perceptrons.get(i).getWagi()[j] + " ");
//            }
//            System.out.println();
//            System.out.println(perceptrons.get(i).getProg());
//        }

        for(int i = 0; i < 100; i++) {
            perceptrons = train(perceptrons, lists);
        }

//        System.out.println("po train");
//        for(int i = 0; i < perceptrons.size(); i++){
//            for(int j = 0; j < perceptrons.get(i).getWagi().length; j++){
//                System.out.print(perceptrons.get(i).getWagi()[j] + " ");
//            }
//            System.out.println();
//            System.out.println(perceptrons.get(i).getProg());
//        }

        for(int i = 0; i < perceptrons.size(); i++){
            double[] wagi = normalize(perceptrons.get(i).getWagi());
            perceptrons.get(i).setWagi(wagi);
        }


//        System.out.println("po norm");
//        for(int i = 0; i < perceptrons.size(); i++){
//            for(int j = 0; j < perceptrons.get(i).getWagi().length; j++){
//                System.out.print(perceptrons.get(i).getWagi()[j] + " ");
//            }
//            System.out.println();
//            System.out.println(perceptrons.get(i).getProg());
//        }

        List list = new List("toTest");
        test(perceptrons,list,"ToTest",nazwy);
    }

    public static double[] normalize(double[] wektor){
        double sum = 0;
        double lenght = 0;
        double[] newWektor = new double[wektor.length];
        for(int i = 0; i < wektor.length; i++){
            sum += wektor[i] * wektor[i];
        }
        lenght = Math.sqrt(sum);
        for(int i = 0; i < wektor.length; i++) {
            newWektor[i] = wektor[i]/lenght;
        }
        return newWektor;
    }

    public static void test(ArrayList<Perceptron> perceptrons, List list, String nazwa,ArrayList<String> nazwy) throws FileNotFoundException{
        for(int i = 0; i < list.getLeng().size(); i++){
            double max[] = new double[perceptrons.size()];
            double[] wektor = wektor2(list.getLeng().get(i).toString(),nazwa);
            for(int j = 0; j < perceptrons.size(); j++){
                double[] wagi = perceptrons.get(j).getWagi();
                double skalar = 0;
                for (int b = 0; b < wagi.length; b++) {
                    skalar += (wektor[b] * wagi[b]);
                }
                max[j] = 1/(1+Math.pow(Math.E,-skalar));
                //System.out.print(max[j] + " ");
            }
            System.out.println();
            int index = 0;
            double m = 0;
            m = max[0];
            for(int k = 0; k < max.length; k++){
                if(max[k] > m){
                    m = max[k];
                    index = k;
                }
            }
            System.out.println(nazwy.get(index));
        }
    }

    public static ArrayList<Perceptron> train(ArrayList<Perceptron> perceptrons, ArrayList<List> lists) throws FileNotFoundException {
        int size = 0;
        for(int i = 0; i < lists.size(); i++){
            size += lists.get(i).getLeng().size();
        }
        for(int i = 0; i < size/lists.size(); i++){
            for(int j = 0; j < lists.size(); j++){
                double wektor[] = wektor(lists.get(j).getLeng().get(i).toString(),lists.get(j).getNazwa());
                for(int k = 0; k < perceptrons.size(); k++){
                    double skalar = 0;
                    double[] wagi = perceptrons.get(k).getWagi();
                    for (int b = 0; b < wagi.length; b++) {
                        skalar += (wektor[b] * wagi[b]);
                    }
                    if(skalar < perceptrons.get(k).getProg() && lists.get(j).getNazwa() == perceptrons.get(k).getNazwa()){
                        //System.out.println(lists.get(j).getNazwa() + " " + perceptrons.get(k).getNazwa() + " 0");/////
                        wagi = regulaDelta2(wagi,wektor,0,1,a);
                        double prog = newProg(0,1,a,perceptrons.get(k).getProg());
                        perceptrons.get(k).setWagi(wagi);
                        perceptrons.get(k).setProg(prog);
                    }else if(skalar >= perceptrons.get(k).getProg() && lists.get(j).getNazwa() != perceptrons.get(k).getNazwa()){
                        //System.out.println(lists.get(j).getNazwa() + " " + perceptrons.get(k).getNazwa() + " 1");/////
                        wagi = regulaDelta2(wagi,wektor,1,0,a);
                        double prog = newProg(1,0,a,perceptrons.get(k).getProg());
                        perceptrons.get(k).setWagi(wagi);
                        perceptrons.get(k).setProg(prog);
                    }
                }
            }
        }
        return perceptrons;
    }

    public static double[] regulaDelta2(double[] w, double[] x, double y, double d, double a){
        double tabDeltaW[] = new double[26];
        double newTabX[] = new double[26];
        for(int i = 0; i < newTabX.length; i++){
            newTabX[i] = ((d - y) * a) * x[i];
        }
        for(int i = 0; i < 26; i++){
            tabDeltaW[i] = newTabX[i] + w[i];
        }
        return tabDeltaW;
    }

    public static double newProg(double y, double d, double a, double prog){
        return prog + (d-y) * a * (-1);
    }

    public static double[] wektor(String lang, String name) throws FileNotFoundException {
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        FileReader fileReader = new FileReader(katalog + "/" + name + "/" + lang);
        Scanner scanner = new Scanner(fileReader);
        while(scanner.hasNextLine()){
            String test = scanner.next().toLowerCase();
            char[] chars = test.toCharArray();

            for(int i=0; i<chars.length;i++) {
                if ( chars[i] >= 97 && chars[i] <= 122){
                    if (!map.containsKey(chars[i])) {
                        map.put(chars[i], 1);
                    }
                    map.put(chars[i], map.get(chars[i]) + 1);
                }
            }
        }
        double[] wektor = new double[26];
        ArrayList<Character> characters = new ArrayList<>();
        for(Map.Entry entry:map.entrySet()){
            characters.add((Character) entry.getKey());
        }
        //System.out.println(map.toString() + " " + lang + " " + name);
        int kalk = 0;
        int key = 0;
        int tmp = 97;
        while (kalk < 26 && kalk < characters.size()){
            if(characters.get(key) == tmp){
                wektor[kalk] = map.get(characters.get(key));
                tmp++;
                kalk++;
                key++;
            }else{
                tmp++;
                kalk++;
            }
            //System.out.print(wektor[kalk - 1] + " ");
        }
        double sum = 0;
        for(int i = 0; i < wektor.length; i++){
            sum += wektor[i];
        }
        for(int i = 0; i < wektor.length; i++){
            wektor[i] = wektor[i]/sum;
        }
//        for(int i = 0; i < wektor.length; i++){
//            System.out.print(wektor[i] + " ");
//        }
//        System.out.println();
        return wektor;
    }

    public static double[] wektor2(String lang, String name) throws FileNotFoundException {
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        FileReader fileReader = new FileReader(name + "/" + lang);
        Scanner scanner = new Scanner(fileReader);
        while(scanner.hasNextLine()){
            String test = scanner.next().toLowerCase();
            char[] chars = test.toCharArray();

            for(int i=0; i<chars.length;i++) {
                if ( chars[i] >= 97 && chars[i] <= 122){
                    if (!map.containsKey(chars[i])) {
                        map.put(chars[i], 1);
                    }
                    map.put(chars[i], map.get(chars[i]) + 1);
                }
            }
        }
        double[] wektor = new double[26];
        ArrayList<Character> characters = new ArrayList<>();
        for(Map.Entry entry:map.entrySet()){
            characters.add((Character) entry.getKey());
        }
        //System.out.println(map.toString());
        int kalk = 0;
        int key = 0;
        int tmp = 97;
        while (kalk < 26 && kalk < characters.size()){
            if(characters.get(key) == tmp){
                wektor[kalk] = map.get(characters.get(key));
                tmp++;
                kalk++;
                key++;
            }else{
                tmp++;
                kalk++;
            }
            //System.out.print(wektor[kalk - 1] + " ");
        }
        //System.out.println();
        return normalize(wektor);
    }
}
