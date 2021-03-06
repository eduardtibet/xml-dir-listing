package net.matthaynes.xml.dirlist;

import java.io.*;
import java.text.SimpleDateFormat;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;

/**
 * Command line interface for XML Directory Listing class.
 */

public class DirectoryListing {

	/** CLI options object */
	private static Options opt = new Options();
	
	/** XmlDirectoryListing object */
	private static XmlDirectoryListing lister = new XmlDirectoryListing();
	
	/** Outputstream for XmlDirectoryListing object */
	private static FileOutputStream out;
	
	/** The directory to list */
	private static File dir;

	// Main function
	public static void main(String aArguments[]) throws FileNotFoundException, IOException {
		
		try {
			
			// Define options
			opt.addOption("h", "help", false, "print this message");
			opt.addOption("s", "sort", true, "sort method");
			opt.addOption("r", "reverse", false, "sort reverse");
			opt.addOption("o", "output", true, "output file");
			opt.addOption("v", "verbose", false, "verbose logging");
			opt.addOption("f", "dateformat", true, "date format for listings");
			opt.addOption("d", "depth", true, "depth of directory listings");
			opt.addOption("i", "includes", true, "includes regEx for directory listings");
			opt.addOption("e", "excludes", true, "excludes regEx for directory listings");
			opt.addOption("c", "encoding", true, "sets character encoding definition for XML file");
						
			// Parse arguments
			BasicParser parser = new BasicParser();
			CommandLine cl = parser.parse(opt, aArguments);
			
			// If help or no args,
			if (cl.hasOption("h") || aArguments.length == 0 ) {
				
				// Display help and return (exit).
				HelpFormatter f = new HelpFormatter();
				f.printHelp("xml-dir-list [options] source", "\n options:", opt, "");
				return;
				
			}
			
			// Check for output option and and apply it to XmlDirectoryListing class, otherwise fail.
			if (cl.hasOption("o")) {
				
				// Set output stream for generated file
				out = new FileOutputStream(cl.getOptionValue("o"));
				
			} else {
				
				System.out.println("Please specify an output file.");
				return;
				
			}
			
			// Check for sort option and apply it to XmlDirectoryListing class
			if (cl.hasOption("s")) {
				lister.setSort(cl.getOptionValue("s"));
			}
			
			// Check for character encoding
			if (cl.hasOption("c")) {
				lister.setEncoding(cl.getOptionValue("c"));
			}
			
			// Check for reverse option and apply it to XmlDirectoryListing class
			if (cl.hasOption("r")) {
				lister.setSortReverse(true);
			}
			
			// Check for dateformat option and apply it to XmlDirectoryListing class
			if (cl.hasOption("f")) {
				lister.setDateFormat(cl.getOptionValue("f"));
			}
			
			// Check for dateformat option and apply it to XmlDirectoryListing class
			if (cl.hasOption("d")) {
				lister.setDepth(Integer.valueOf(cl.getOptionValue("d")).intValue());
			}	
			
			// Check for includes option and apply it to XmlDirectoryListing class
			if (cl.hasOption("i")) {
				lister.setIncluded(cl.getOptionValue("i"));
			}	
			
			// Check for excludes option and apply it to XmlDirectoryListing class
			if (cl.hasOption("e")) {
				lister.setExcluded(cl.getOptionValue("e"));
			}	
			
			// Check for verbose flag. Set logger accordingly.
			if (cl.hasOption("v")) {
				lister.log.setLevel(org.apache.log4j.Level.DEBUG);
			} else {
				lister.log.setLevel(org.apache.log4j.Level.INFO);
			}
			
			// Check for directory as last remaining argument 
			if (cl.getArgs().length == 1) {
				// Get specified directory
				dir = new File(cl.getArgs()[0]);
			} else if(cl.getArgs().length > 1) {
				System.out.println("Too many arguments specified.");
				return;
			} else {
				System.out.println("Please specify a directory to generate a listing for");
				return;
			}
			
			
			// Run Class ========================================================
			
			// Begin listing			
			lister.generateXmlDirectoryListing(dir, out);

			// Close output stream
			out.close();
			
			// ==================================================================
						
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		    
	}
	
}