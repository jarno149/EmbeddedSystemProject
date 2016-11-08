/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.bluetoothserver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

/**
 *
 * @author Jarno
 */
public class Logger
{
    private static PrintWriter writer;
    
    public static void Init(String filePath)
    {
        try
        {
            writer = new PrintWriter(new FileWriter(filePath, true));
            
        } catch (IOException ex)
        {
            ANSI.printRed("Logging-file does not exist!");
        }
    }
    
    public static void WriteToLog(String line)
    {
        if(writer != null)
            writer.write(line);
    }
}
