package com.interset.DataIntegrationTask;

/**
 * Created by Richard on 14/05/2017.
 */

public class MetricsSingleton {
    private static final MetricsSingleton instance = new MetricsSingleton();

    private int linesRead;
    private int linesDropped_Duplicates;
    private int linesDropped_NoActionMapping;
    private int linesDropped_Total;
    private int numberOfUniqueUsers;
    private int numberOfUniqueFiles;
    private String startDate;
    private String endDate;
    private int numberOfEachActionType;

    // private constructor
    private MetricsSingleton() {
    }

    public static MetricsSingleton getInstance() {
        return instance;
    }

    public void incrementLinesRead(int val) {
        linesRead = +val;
    }

    public void incrementLinesDropped_NoActionMapping(int val) {
        linesDropped_NoActionMapping++;
    }

    public void incrementNumberOfUniqueUsers(int val) {
        numberOfUniqueUsers = +val;
    }

    public void incrementNumberOfUniqueFiles(int val) {
        numberOfUniqueFiles = +val;
    }

    public int getLinesRead() {
        return linesRead;
    }

    public void setLinesRead(int linesRead) {
        this.linesRead = linesRead;
    }


    public int getLinesDropped_Duplicates() {
        return linesDropped_Duplicates;
    }

    public void setLinesDropped_Duplicates(int linesDropped_Duplicates) {
        this.linesDropped_Duplicates = linesDropped_Duplicates;
    }

    public int getLinesDropped_NoActionMapping() {
        return linesDropped_NoActionMapping;
    }

    public void setLinesDropped_NoActionMapping(int linesDropped_NoActionMapping) {
        this.linesDropped_NoActionMapping = linesDropped_NoActionMapping;
    }

    public int getLinesDropped_Total() {
        return linesDropped_Total;
    }

    public void setLinesDropped_Total(int linesDropped_Total) {
        this.linesDropped_Total = linesDropped_Total;
    }

    public int getNumberOfUniqueUsers() {
        return numberOfUniqueUsers;
    }

    public void setNumberOfUniqueUsers(int numberOfUniqueUsers) {
        this.numberOfUniqueUsers = numberOfUniqueUsers;
    }


    public int getNumberOfUniqueFiles() {
        return numberOfUniqueFiles;
    }

    public void setNumberOfUniqueFiles(int numberOfUniqueFiles) {
        this.numberOfUniqueFiles = numberOfUniqueFiles;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public int getNumberOfEachActionType() {
        return numberOfEachActionType;
    }

    public void setNumberOfEachActionType(int numberOfEachActionType) {
        this.numberOfEachActionType = numberOfEachActionType;
    }


}
