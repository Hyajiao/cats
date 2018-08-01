import java.util.ArrayList;
import java.util.Collections;

/**
 * @author hanyajiao
 * @date 2018/7/12 9:12
 */
public class TestSort {


    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("westbook");
        list.add("tracy");
        list.add("taylor");
        list.add("ladygaga");
        list.add("jordan");
        list.add("adele");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        System.out.println("=============");
        for (int i = 0; i < list.size(); i++) {
            String str = "";
            ArrayList<Character> ch = new ArrayList<>();
            char[] chars = list.get(i).toCharArray();
            for (int a = 0; a < chars.length; a++) {
                ch.add(chars[a]);
            }
            Collections.sort(ch, (a, b) -> {
                return a - b;
            });
            for (int j = 0; j < ch.size(); j++) {
                //str = ch.get(j) + str;
                str = str + ch.get(j);
            }
            System.out.println(str);
        }
    }
}
