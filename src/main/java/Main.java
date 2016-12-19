/**
 * Created by phamm on 17.12.2016.
 */
public class Main
{
    public static void main(String[]args)
    {
        SystemIDGenerator generator = new SystemIDGenerator();
        String hwid;
        try
        {
            hwid = generator.getSID();
            System.out.println(hwid);
        }
        catch (Exception e)
        {
            System.err.println("Cannot create HWID");
        }


    }
}
