package com.interset.DataIntegrationTask;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Richard on 14/05/2017.
 */


public class ActivityFileReader {

    public List<String> ReadActivityFile() {

        Path activityFile = Paths.get("C:\\Users\\Richard\\IdeaProjects\\Interview2\\Data-Integration-1\\src\\main\\java\\com\\interset\\DataIntegrationTask\\ActivityTypes.txt");
        List<String> activitiesList = null;

        try (Stream<String> lines = Files.lines(activityFile)) {

            activitiesList = lines.map(String::toLowerCase).collect(Collectors.toList());


        } catch (Exception ex) {
            System.err.println("txt file [" + activityFile.toString() + "] doesn't exist!");
            System.exit(1);
        }

        return activitiesList;

    }
}
