package heima_data_structure.java.src.main.java.com.itheima.algorithm.greedy;

public enum Color {
    Black(30), Red(31), Green(32), Yellow(33), Blue(34),
    Purple(35), Cyan(36), White(37), Gray(90), DarkRed(91),
    LightGreen(92), LightYellow(93), LightBlue(94), LightPurple(95), Turquoise(96);
    final int code;

    Color(int code) {
        this.code = code;
    }

    public String get(String str) {
        return "\u001B[" + this.code + "m" + str + "\u001B[0m";
    }
}