package com.example.notebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVReaderSaver {
    public static ArrayList<TaskClass> readCSV() {
        ArrayList<TaskClass> res = new ArrayList<>();
        Scanner sc;
        Boolean isDone;
        Boolean inDev;
        try {
            sc = new Scanner(new File("tasks.csv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        sc.useDelimiter("!");
        System.out.print("Trying to read!\n");
        while (sc.hasNext())  //returns a boolean value
        {
            String[] curArr = sc.next().split(",");
            if (curArr.length != 8) {
                continue;
            }

            if (Integer.parseInt(curArr[5].strip()) == 1) {
                isDone = true;
            }
            else isDone = false;

            if (Integer.parseInt(curArr[7].strip()) == 1) {
                inDev = true;
            }
            else inDev = false;

            System.out.print(Integer.parseInt(curArr[5].strip()) == 1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(curArr[3].strip(), formatter);
            res.add(new TaskClass(curArr[0].strip(), curArr[1].strip(), curArr[2].strip(), date, curArr[4].strip(), isDone, curArr[6].strip(), inDev));
        }
        System.out.print("CSV reading done!\n");
        System.out.print(res);
        sc.close();
        return res;
    }
    public static String convertToCSV(TaskClass tc) {
        int isDone = 0;
        int inDev = 0;
        if (tc.isDone()) {
            isDone = 1;
        }

        if(tc.inDevelop()){
            inDev = 1;
        }
        return tc.getTask() + ", " + tc.getDescription() + ", " + tc.getExecutor() + ", " + tc.getDeadline() + ", " + tc.getStatus()+ ", " + isDone+ ", " + tc.getComment()+", " + inDev + "!";
    }
    public static void saveCSV(ArrayList<TaskClass> saveListOfLinks) {
        File csvOutputFile = new File("tasks.csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            saveListOfLinks.stream()
                    .map(CSVReaderSaver::convertToCSV)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
