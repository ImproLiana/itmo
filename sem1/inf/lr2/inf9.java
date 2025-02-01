package inf.lr2;
import java.util.Scanner;

public class inf9 {
    public static int s1 (int r1, int i1, int i2, int i4) {
        int s1 = r1 + i1 + i2 + i4;
        return s1%2;
    }

    public static int s2 (int r2, int i1, int i3, int i4) {
        int s2 = r2 + i1 + i3 + i4;
        return s2%2;
    }

    public static int s3 (int r3, int i2, int i3, int i4) {
        int s3 = r3 + i2 + i3 + i4;
        return s3%2;
    }

    public static String error (String err) {
        switch (err) {
            case "100":
                return "r1";
            case "010":
                return "r2";
            case "110":
                return "i1";
            case "001":
                return "r3";
            case "101":
                return "i2";
            case "011":
                return "i3";
            case "111":
                return "i4";
            default:
                return "";
        }
    }
    
    public static String new_num (String num_to_ch) {
        switch (num_to_ch) {
            case "1":
                return "0";
            case "0":
                return "1";
            default:
                return "error404";
        }
    }

    public static boolean good (int i1, int i2, int i3, int i4, int r1, int r2, int r3){
        if ((i1 == 0 || i1 == 1) && (i2 == 0 || i2 == 1) && (i3 == 0 || i3 == 1)
        && (i4 == 0 || i4 == 1) && (r1 == 0 || r1 == 1) && (r2 == 0 || r2 == 1)
        && (r3 == 0 || r3 == 1)) {
            return true;
        } else {
            return false;
        }
    }


    
    public static void main (String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Сообщение: ");
        String num = in.nextLine();
        if (num.length() == 7){
            int r1 = Integer.parseInt(num.substring(0, 1));
            int r2 = Integer.parseInt(num.substring(1, 2));
            int i1 = Integer.parseInt(num.substring(2, 3));
            int r3 = Integer.parseInt(num.substring(3, 4));
            int i2 = Integer.parseInt(num.substring(4, 5));
            int i3 = Integer.parseInt(num.substring(5, 6));
            int i4 = Integer.parseInt(num.substring(6, 7));
            if (good(i1, i2, i3, i4, r1, r2, r3)){
                int s1 = s1(r1, i1, i2, i4);
                int s2 = s2(r2, i1, i3, i4);
                int s3 = s3(r3, i2, i3, i4);
                String err = "" + s1 + s2 + s3;
                if (error(err) == "") {
                    System.out.println("Ошибок нет!" + i1 + i2 + i3 + i4);
                }else {
                    String err_bit = error(err);
                    System.out.println("Ошибка в бите: " + err_bit);
                    // int change = Integer.parseInt(error(err).substring(2, 3));
                    switch (err_bit) {
                        case "i1":
                            System.out.println("Правильное сообщение: " + new_num("" + i1) + i2 + i3 + i4);
                            break;
                        case "i2":
                            System.out.println("Правильное сообщение: " + i1 + new_num("" + i2) + i3 + i4);
                            break;
                        case "i3":
                            System.out.println("Правильное сообщение: " + i1 + i2 + new_num("" + i3) + i4);
                            break;
                        case "i4":
                            System.out.println("Правильное сообщение: " + i1 + i2 + i3 + new_num("" + i4));
                            break;
                        default:
                            System.out.println("Правильное сообщение(ошибка была в проверочном разряде): " + i1 + i2 + i3 + i4);
                            break;
                    }
                }
            }else {
                System.out.println("Вы неверно ввели значение");
            }
            // System.out.println(num.substring(0, change) + new_num(num.substring(change, change+1)) + num.substring(change+1, 7));
        }else{
            System.out.println("Вы неверно ввели значение");
        }
        in.close();

    }
}
