public class Day4 {
    public static void main(String [] args) {
        //test
        System.out.println("256667 "+Password.isValid(256667));
        System.out.println("111111 "+ Password.isValid(111111));
        System.out.println("11111 " + Password.isValid(11111));
        System.out.println("1111111 " + Password.isValid(1111111));
        System.out.println("223450 " +Password.isValid(223450));
        System.out.println("123789 "+Password.isValid(123789));
        System.out.println("112233 " +Password.isValid(112233));
        System.out.println("123444 "+Password.isValid(123444));
        System.out.println("111122 "+Password.isValid(111122));
        System.out.println("256667 "+Password.isValid(256667));
        int valid =0;
        for (int i=256310; i <=732736; i++) {
            if (Password.isValid(i)) {
                System.out.println(i+ " is valid");
                valid++;
            }
//            else {
//                System.out.println (i + " is invalid");
//            }
        }
        System.out.println("valid passowrds in range: " + valid);
    }
}
