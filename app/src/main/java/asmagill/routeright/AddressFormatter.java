package asmagill.routeright;

/**
 * Created by william on 3/9/16.
 */
public class AddressFormatter {

    public static String integerToInetAddress(int address, boolean ip) {
        int firstOctet;
        int secondOctet;
        int thirdOctet;
        int fourthOctet;


            firstOctet = address / 16777216;
            address -= firstOctet * 16777216;

            secondOctet = address / 65536;
            address -= secondOctet * 65536;

            thirdOctet = address / 256;
            address -= thirdOctet * 256;

            fourthOctet = address;


            return (fourthOctet + "." + thirdOctet + "." + secondOctet + "." + firstOctet);



    }

}
