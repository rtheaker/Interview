package com.interset.DataIntegrationTask;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class TaskRunner {

    public static void main(String args[]) {

        // Check arguments
        if (args.length != 2) {
            System.out.println("We currently only expect 2 arguments! A path to a JSON file to read, and a path for a CSV file to write.");
            System.exit(1);
        }

        // Read arguments
        Path jsonFile = null;

        try {
            jsonFile = Paths.get(args[0]);
        } catch (InvalidPathException e) {
            System.err.println("Couldn't convert JSON file argument [" + args[0] + "] into a path!");
            throw e;
        }

        Path csvFile = null;

        try {
            csvFile = Paths.get(args[1]);
        } catch (InvalidPathException e) {
            System.err.println("Couldn't convert CSV file argument [" + args[1] + "] into a path!");
            throw e;
        }

        // Validate arguments
        if (!Files.exists(jsonFile)) {
            System.err.println("JSON file [" + jsonFile.toString() + "] doesn't exist!");
            System.exit(1);
        }

        if (!Files.isWritable(csvFile.getParent())) {
            System.err.println("Can't write to the directory [" + csvFile.getParent().toString() + "] to create the CSV file! Does directory exist?");
            System.exit(1);
        }

        // Create the CSV file
        System.out.println("Reading file [" + jsonFile.toString() + "], and writing to file [" + csvFile.toString() + "].");

        parseJsonFileAndCreateCsvFile(jsonFile, csvFile);

    }

    public static void parseJsonFileAndCreateCsvFile(Path jsonFile, Path csvFile) {

        // System.out.println("JSON file: " + jsonFile.toString() + " csvFile: " + csvFile.toString());
        MetricsSingleton metrics = MetricsSingleton.getInstance();
        ActivityFileReader activityFileReader = new ActivityFileReader();
        HashSet<MetaData> parsedDataSet = new HashSet<MetaData>();


        try {
            ObjectMapper mapper = new ObjectMapper();
            MappingIterator<MetaData> metaDataIterator = mapper.readerFor(MetaData.class).readValues(jsonFile.toFile());

            // copy iterator to List
            List<MetaData> originalJsonDataList = metaDataIterator.readAll();

            // copy original list to a set which will remove duplicate MetaData objects
            parsedDataSet.addAll(originalJsonDataList);

            // Get some metrics...
            Set<String> uniqueUsers = new HashSet<String>();
            Set<String> uniqueFiles = new HashSet<String>();
            List<String> actions = new ArrayList<String>();
            List<String> times = new ArrayList<String>();
            for (MetaData mData : originalJsonDataList) {
                uniqueUsers.add(mData.getUser());
                uniqueFiles.add(mData.getFile());
                actions.add(mData.getActivity());
                times.add(mData.getTimestamp());
            }

            metrics.setStartDate(times.get(0));
            metrics.setEndDate(times.get(times.size() - 1));
            metrics.setNumberOfUniqueUsers(uniqueUsers.size());
            metrics.setNumberOfUniqueFiles(uniqueFiles.size());

            metrics.setLinesRead(originalJsonDataList.size());
            int numberOfLinesAfterDuplicatesRemoved = parsedDataSet.size();
            metrics.setLinesDropped_Duplicates(originalJsonDataList.size() - numberOfLinesAfterDuplicatesRemoved);

            // Reads file with valid activities and returns list of valid activities
            List<String> activities = activityFileReader.ReadActivityFile();


            // Identify and remove any MetaData objects with an activity that is not mapped
            Iterator iterator = parsedDataSet.iterator();

            while (iterator.hasNext()) {
                MetaData mData = (MetaData) iterator.next();

                if (!activities.contains(mData.getActivity().toLowerCase())) {
                    iterator.remove();

                    // metrics
                    metrics.incrementLinesDropped_NoActionMapping(1);
                }
            }

            // Record some more metrics
            metrics.setLinesDropped_Total(originalJsonDataList.size() - parsedDataSet.size());


        } catch (IOException ex) {
            System.out.println("Problems reading JSON file" + ex.toString());
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }


        // Produce a filtered CSV file
        CSVFileWriter csvFileWriter = new CSVFileWriter();

        csvFileWriter.WriteCSVFile(parsedDataSet, csvFile);


        // TODO: aggregate and print out statistics

        // Statistics are in MetricsSingleton -> variable metrics
        // I ran into some DateTime related problems and did not get a chance to finish

    }


}
