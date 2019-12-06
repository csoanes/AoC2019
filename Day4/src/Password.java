public class Password {
    public static boolean isValid(int number) {
        boolean retval = false;
        // test length
        //.out.print ("Testing "+number + " for length");
        if (number < 100000 || number > 999999) return false;
        //System.out.println(" Passed");

        String asStr = String.valueOf(number);
        char[] asCharArray = asStr.toCharArray();
        for (int i = 0; i < 5; i++) {
            //System.out.print ("Testing sequence ")  ;
            if (asCharArray[i + 1] < asCharArray[i]) {
                //     System.out.println("Failed sequence test ");
                return false;
            }
        }

        if ((asCharArray[0] == asCharArray[1]) && (asCharArray[1] != asCharArray[2]))  retval = true;
        if ((asCharArray[1] == asCharArray[2]) && (asCharArray[0] != asCharArray[1]) && asCharArray[2]!=asCharArray[3]) retval = true;
        if ((asCharArray[2] == asCharArray[3]) && (asCharArray[1] != asCharArray[2]) && asCharArray[3]!=asCharArray[4]) retval = true;
        if ((asCharArray[3] == asCharArray[4]) && (asCharArray[2] != asCharArray[3]) && asCharArray[4]!=asCharArray[5]) retval = true;
        if ((asCharArray[4] == asCharArray[5]) && (asCharArray[3] != asCharArray[4]))  retval = true;
        return retval;
    }
}
