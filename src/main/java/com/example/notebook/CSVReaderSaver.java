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
            if (curArr.length != 7) {
                continue;
            }

            if (Integer.parseInt(curArr[5].strip()) == 1) {
                isDone = true;
            }
            else isDone = false;


            System.out.print(Integer.parseInt(curArr[5].strip()) == 1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(curArr[1].strip(), formatter);
            res.add(new TaskClass(curArr[0].strip(), curArr[3].strip(), curArr[2].strip(), date, curArr[4].strip(), isDone, curArr[6].strip()));
        }
        System.out.print("CSV reading done!\n");
        System.out.print(res);
        sc.close();
        return res;
    }
    public static String convertToCSV(TaskClass tc) {
        int isDone = 0;

        if (tc.isDone()) {
            isDone = 1;
        }

        return tc.getEvent2() + ", " + tc.getTime() + ", " + tc.getPlace() + ", " + tc.getDescription() + ", " + tc.getStatus()+ ", " + isDone+ ", " + tc.getComment() + "!";
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
