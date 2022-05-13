import java.io.File;
import java.util.ArrayList;

public class List {
    private ArrayList leng;
    private String nazwa;

    public List(String katalog,String nazwa) {
        leng = readLeng(katalog,nazwa);
        this.nazwa = nazwa;
    }

    public List(String katalog) {
        leng = readLeng2(katalog);
    }

    public static ArrayList readLeng2(String katalog){
        ArrayList tmp = new ArrayList();
        File dir = new File(katalog);
        ArrayList<String> nazwy = new ArrayList<>();
        for(String path : dir.list()) {
            tmp.add(path);
        }
        return tmp;
    }

    public static ArrayList readLeng(String katalog1, String katalog2){
        ArrayList tmp = new ArrayList();
        File dir = new File(katalog1 + "/" + katalog2);
        for(String path : dir.list()) {
            tmp.add(path);
        }
        return tmp;
    }

    public String getNazwa() {
        return nazwa;
    }

    public ArrayList getLeng() {
        return leng;
    }
}
