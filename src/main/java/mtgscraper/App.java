package mtgscraper;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import mtgscraper.entities.CardSet;
import mtgscraper.entities.CardSetReference;
import mtgscraper.http.HttpDataProvider;
import mtgscraper.output.SetJsonGenerator;

public class App {
	
	public static void main(@Nonnull final String[] args) throws Exception {
		
		// preparse the command key and args from the args
		final String commandKey;
		final String[] commandArgs;
		if (args.length == 0) {
			commandKey = "list";
			commandArgs = new String[0];
		} else {
			commandKey = args[0];
			commandArgs = Arrays.copyOfRange(args, 1, args.length);
		}
		
		// get the command corresponding to the key
		final Command command;
		if (commandKey.equals("set")) {
			command = new DownloadSetCommand();
		} else {
			command = new ListSetsCommand();
		}
		
		// run the command 
		command.run(commandArgs);
	}
	
	private static interface Command {
		public void run(String[] args) throws Exception;
	}
	
	private static class ListSetsCommand implements Command {
		@Override
		public void run(String[] args) throws Exception {
			Options options = new Options();
			options.addOption("l", "language", true, "the language of the set to download. default is en");
			
			CommandLineParser parser = new BasicParser();
			CommandLine commandLine = parser.parse(options, args);
			
			String languageCode = commandLine.getOptionValue("language", "en");
			
			listSets(languageCode);
		}
		
		private static void listSets(String languageCode) throws Exception {
			HttpDataProvider dataProvider = null;
			try {
				dataProvider = new HttpDataProvider();
				
				for(CardSetReference set : dataProvider.requestCardSetReferences(languageCode)) {
					System.out.println(set.getAbbr() + ": " + set.getName());
				}
				
			} finally {
				if(dataProvider != null) {
					dataProvider.shutdown();
				}
			}
		}
	}
	
	private static class DownloadSetCommand implements Command {
		@Override
		public void run(String[] args) throws Exception {
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
			
			saveSets(languageCode, setCodes, rootDir);
		}
		
		private static void printHelp(@Nonnull final Options options) {
			(new HelpFormatter()).printHelp(App.class.getName() + " set <set>... [option]", options);
		}
		
		private void saveSets(@Nonnull final String languageCode, @Nonnull final String[] setCodes, 
				@Nonnull final File rootDir) throws Exception {
			HttpDataProvider dataProvider = null;
			try {
				dataProvider = new HttpDataProvider();
				SetJsonGenerator setOutput = new SetJsonGenerator();
				
				//get the catalog from the data provider
				List<? extends CardSet> cardSets = dataProvider.requestCardSetsByAbbrAllOrNone(languageCode, setCodes);
				
				for(CardSet cardSet : cardSets) {
					
					try {
						//output each of the sets as a file to the directory
						final File file = new File(rootDir, cardSet.getAbbr().toUpperCase() + ".json");
						setOutput.saveSet(cardSet, file);
						
					} catch(Exception exc) {
						System.out.println("Error while downloading " + cardSet.getAbbr() + ":\t" + exc.getMessage());
					}
					
				}
				
			} finally {
				if(dataProvider != null) {
					dataProvider.shutdown();
				}
			}
		}
	}
	
}
