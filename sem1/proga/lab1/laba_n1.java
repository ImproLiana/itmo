package proga.my_labs.lab1;
import java.util.Random; 

public class laba_n1 {
    // первый метод для нахождения переменной
    public static double f1(double h) {
        double ans = Math.atan(Math.sin(Math.sin(h)));
        return ans;
    }
    // второй метод для нахождения переменной
    public static double f2(double h) {
        double ans = Math.pow(0.25/(1-(2.0/3.0)-Math.tan(h)), 2);
        return ans;
    }
    // третий метод для нахождения переменной
    public static double f3(double h) {
        double ans = Math.cos(Math.pow(((1.0/3.0)/Math.pow((((Math.pow(h,(1.0/3.0)))/1.0)/3.0),3.0)),3.0));
        return ans;
    }
    // метод для вывода
    public static void enter(double[][] name) {
        for (int i = 0; i < name.length; i++) {
            for (int k = 0; k < name[0].length; k++)  {
                System.out.printf("%.4f", name[i][k]);
                System.out.print("   ");
            } 
            System.out.println("");
        }   
    }
    public static void main (String[] args) {
        // создание первого массива
        short[] z = new short[10]; 
        // заполнение первого массива
        // int c = 0;
        for (short i = 6, c = 0; i <= 15; i ++, c++) {
            z[c] = i;
            // c++;
        }
        // создание второго массива
        double[] x = new double[13];
        // заполнение второго массива
        Random random = new Random();
        for (int i = 0; i < x.length; i ++) {
            x[i] = -5.0 + random.nextDouble(14.0);
        }
        // создание третьего массива
        double [][] z1 = new double [10][13];
        // заполнение третьего массива
        for (int i = 0; i < z.length; i ++) {
            for (int j = 0; j < x.length; j ++) {
                if (z[i] == 15) {
                    z1[i][j] = f1(x[j]);
                };
                if (z[i] == 6 || z[i] == 8 || z[i] == 11 || z[i] == 12 || z[i] == 13) {
                    z1[i][j] = f2(x[j]);
                }  
                else {
                    z1[i][j] = f3(x[j]);
                }

            }
        }
        // вывод
        enter(z1);

    }
    
}
