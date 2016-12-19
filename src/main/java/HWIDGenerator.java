import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * Created by phamm on 17.12.2016.
 */
public class HWIDGenerator
{

    public String getHWID() throws Exception
    {
        return bytesToString(generateHWID(getUservariables())).substring(0, 30).toUpperCase();
    }

    private byte[] generateHWID(String toencrypt) throws Exception
    {
        String salt = getNetworkMAC().substring(0,16);
        SecretKeySpec key = new SecretKeySpec(salt.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(toencrypt.getBytes());
    }


    private String getUservariables()
    {
        HashMap<String, String> uservars = new HashMap<String, String>();
        uservars.put("PROCESSOR_LEVEL", System.getenv("PROCESSOR_LEVEL"));
        uservars.put("PROCESSOR_IDENTIFIER", System.getenv("PROCESSOR_IDENTIFIER"));
        uservars.put("PROCESSOR_REVISION", System.getenv("PROCESSOR_REVISION"));
        uservars.put("NUMBER_OF_PROCESSORS", System.getenv("NUMBER_OF_PROCESSORS"));
        uservars.put("COMPUTERNAME", System.getenv("COMPUTERNAME"));
        uservars.put("OS", System.getenv("OS"));
        uservars.put("USERNAME", System.getenv("USERNAME"));
        uservars.put("PROCESSOR_ARCHITECTURE", System.getenv("PROCESSOR_ARCHITECTURE"));
        uservars.put("USERDOMAIN", System.getenv("USERDOMAIN"));
        uservars.put("os.arch", System.getProperty("os.arch"));
        uservars.put("os.name", System.getProperty("os.name"));
        uservars.put("os.version", System.getProperty("os.version"));
        uservars.put("NETWORK_MAC", getNetworkMAC());
        StringBuilder sb = new StringBuilder();
        for(String mapkey : uservars.keySet())
        {
            sb.append(uservars.get(mapkey));
        }

        return sb.toString();
    }

    private String bytesToString(byte[] b)
    {
        byte[] b2 = new byte[b.length + 1];
        b2[0] = 1;
        System.arraycopy(b, 0, b2, 1, b.length);
        return new BigInteger(b2).toString(36);
    }


    private String getNetworkMAC()
    {

        try
        {
            NetworkInterface netint = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            byte[] mac = netint.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < mac.length; i++)
            {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length -1) ? ":" : ""));
            }
            return sb.toString();
        }
        catch(UnknownHostException uhe)
        {
            System.err.println("Unable to Resovle local IP");
            //return "";
        }
        catch (SocketException se)
        {
            System.err.println("Unable to create Socket");
            //return "";
        }
        return "-1";
    }
}
