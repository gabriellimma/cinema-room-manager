import java.util.*;

class FixingStringIndexOutOfBoundsException {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String str = scanner.nextLine();
        int index = scanner.nextInt();
        int stringLength = str.length();

        try {
            System.out.println(str.charAt(index));
        } catch(StringIndexOutOfBoundsException e) {
            System.out.println("Out of bounds!");
        }
    }
}
