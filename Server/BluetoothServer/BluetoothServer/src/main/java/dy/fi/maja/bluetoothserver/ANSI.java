/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.bluetoothserver;

/**
 *
 * @author Jarno
 */
public class ANSI
{
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    
    public static void printRed(String message)
    {
        System.out.println(RED + message + RESET);
    }
    
    public static void printGreen(String message)
    {
        System.out.println(GREEN + message + RESET);
    }
    
    public static void printYellow(String message)
    {
        System.out.println(YELLOW + message + RESET);
    }
    
    public static void printBlue(String message)
    {
        System.out.println(BLUE + message + RESET);
    }
    
    public static void printPurple(String message)
    {
        System.out.println(PURPLE + message + RESET);
    }
    
    public static void printCyan(String message)
    {
        System.out.println(CYAN + message + RESET);
    }
    
    public static void nextLine()
    {
        System.out.println("");
    }
}
