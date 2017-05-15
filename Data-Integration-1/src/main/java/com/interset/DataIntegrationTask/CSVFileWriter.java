package com.interset.DataIntegrationTask;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Richard on 14/05/2017.
 */

public class CSVFileWriter {


    public CSVFileWriter() {
    }

    public void WriteCSVFile(HashSet<MetaData> parsedDataSet, Path csvFile) {


        //CSV file header
        final Object[] FILE_HEADER = {"TIMESTAMP", "ACTION", "USER", "FOLDER", "FILENAME", "IP"};
        //Delimiter used in CSV file
        final String NEW_LINE_SEPARATOR = "\n";


        File outputFile = csvFile.toFile();
        CSVPrinter csvFilePrinter = null;
        FileWriter fileWriter = null;

        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

        try {
            fileWriter = new FileWriter(outputFile);

            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

            // Print the header
            csvFilePrinter.printRecord(FILE_HEADER);

            // Variables for processing CSV data
            String action = null;
            String timeStamp = null;
            String offset = null;
            DateTimeZone dtOffset = null;
            DateTime dateTime = null;
            DateTimeFormatter patternFormat = new DateTimeFormatterBuilder()
                    .appendPattern("MM/dd/yyyy hh:mm:ssa")
                    .toFormatter();

            // Iterate through the data set,  process and print the data
            for (MetaData parsedData : parsedDataSet) {

                // create list to hold data for this line
                List<String> dataLine = new ArrayList<String>();

                // Add the data ...
                // Timestamp
                timeStamp = parsedData.getTimestamp();
                offset = parsedData.getTimeOffset();

                dtOffset = DateTimeZone.forID(offset);
                dateTime = patternFormat.withZone(dtOffset).parseDateTime(timeStamp.toString());

                dataLine.add(dateTime.toString());

                // Action
                action = parsedData.getActivity();
                if (Arrays.asList("createdDoc", "addedText", "changedText").contains(action)) {
                    dataLine.add("ADD");
                } else if (Arrays.asList("deletedDoc", "deletedText", "archived").contains(action)) {
                    dataLine.add("REMOVE");
                } else {
                    dataLine.add("ACCESSED");
                }

                // User -> separate from email address
                String email = parsedData.getUser();
                String[] parts = email.split("@");
                // User is in 1st part
                dataLine.add(parts[0]);

                // Folder & Filename
                String folderAndFile = parsedData.getFile();
                Path path = Paths.get(folderAndFile);

                dataLine.add(path.getParent().toString());     //Folder
                dataLine.add(path.getFileName().toString());   //File

                // IP
                dataLine.add(parsedData.getIpAddr());

                // Write data line to file
                csvFilePrinter.printRecord(dataLine);
            }

        } catch (Exception e) {

            System.out.println("Error in CsvFileWriter.");
            e.printStackTrace();

        } finally {

            // Do some cleanup
            try {

                fileWriter.flush();
                fileWriter.close();
                csvFilePrinter.close();
                System.out.println("Stop here for a sec");

            } catch (IOException e) {

                System.out.println("Error while flushing/closing fileWriter/csvPrinter.");
                e.printStackTrace();

            }
        }

    }
}
