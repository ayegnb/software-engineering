package kz.edu.nu.cs.cliexample;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * 
 */
public class App {
    public static void main(String[] args) {
        
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption("help", false, "print this help message");
        options.addOption("s", true, "input string");
        
        CommandLine cmd;
        
        try {
            cmd = parser.parse(options, args);
            
            if (cmd.hasOption("help")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("App", options);
                System.exit(0);
            }
            
            if (cmd.hasOption("s")) {
                String s = cmd.getOptionValue("s");
                System.out.println(wordcount(s));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static int wordcount(String s) {
        // Assume s is a sentence and count the number of words
        // Complete implementation
        return s.split(" ").length;
   }
}
