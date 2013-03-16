package mtgscraper;

import java.io.File;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import mtgscraper.entities.CardSet;
import mtgscraper.entities.Catalog;
import mtgscraper.http.HttpDataProvider;
import mtgscraper.output.SetJsonGenerator;

public class App {
	
	public static void main(String[] args) throws Exception {
		Options options = new Options();
		options.addOption("l", "language", true, "the language of the set to download. default is en");
		options.addOption("o", "output", true, "the directory to output the downloaded files to.");
		options.addOption("f", "format", true, "the format to output in. default is json");
		
		CommandLineParser parser = new BasicParser();
		CommandLine commandLine = parser.parse(options, args);
		
		String languageCode = commandLine.getOptionValue("language", "en");
		
		File rootDir = null;
		if(commandLine.hasOption("output")) {
			rootDir = new File(commandLine.getOptionValue("output"));
		} else {
			System.out.println("Output directory is required.");
			printHelp(options);
			return;
		}
		
		String[] setCodes = commandLine.getArgs();
		if(setCodes.length == 0) {
			System.out.println("At least one set must be specified.");
			printHelp(options);
			return;
		}
		
		if(commandLine.hasOption("f") && !"json".equalsIgnoreCase(commandLine.getOptionValue("f"))) {
			System.out.println("Invalid format specified: " + commandLine.getOptionValue("f"));
			System.out.println("Valid formats are: json");
			printHelp(options);
			return;
		}
		
		new App().saveSets(languageCode, setCodes, rootDir);
	}
	
	private static void printHelp(Options options) {
		(new HelpFormatter()).printHelp(App.class.getName() + " <set>... [option]", options);
	}
	
	public void saveSets(String languageCode, String[] setCodes, File rootDir) throws Exception {
		HttpDataProvider dataProvider = null;
		try {
			dataProvider = new HttpDataProvider();
			SetJsonGenerator setOutput = new SetJsonGenerator();
			
			//get the catalog from the data provider
			Catalog catalog = dataProvider.requestCatalog();
			
			for(String setCode : setCodes) {
				
				try {
					//get the set with the specified language and code
					CardSet set = catalog.getSetByAbbr(languageCode, setCode);
					
					//output each of the sets as a file to the directory
					File file = new File(rootDir, set.getAbbr().toUpperCase() + ".json");
					setOutput.saveSet(set, file);
					
				} catch(Exception exc) {
					System.out.println("Error while downloading " + setCode + ":\t" + exc.getMessage());
				}
				
			}
			
		} finally {
			if(dataProvider != null) {
				dataProvider.shutdown();
			}
		}
	}
}
