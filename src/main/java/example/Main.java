package example;

public class Main
{
    public static void main(String[] args) throws InterruptedException
    {
        aMethod(true);
    }

    /**
     * If arg = true, then works first "return" inside operator "if" and duration compute correctly (report out/outTrue.txt)
     * If arg = false, then works second "return" and duration is zero (report out/outFalse.txt)
     */
    public static boolean aMethod(boolean arg) throws InterruptedException
    {
        Thread.sleep(1000);
        System.out.println("Slept 1 sec");

        if (arg)
        {
            System.out.println("TRUE");
            Thread.sleep(2000);
            return true;
        }
        System.out.println("FALSE");
        return false;
    }
}
