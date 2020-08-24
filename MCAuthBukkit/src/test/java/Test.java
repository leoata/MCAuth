import java.util.concurrent.ThreadLocalRandom;

public class Test {
    public static void main(String[] args) {
        char[] arr = new char[8];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (char) Math.round(ThreadLocalRandom.current().nextDouble() * (122-97) + 97);
        }
        System.out.println(new String(arr));
    }
}

