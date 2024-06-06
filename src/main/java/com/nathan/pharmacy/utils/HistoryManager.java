package com.nathan.pharmacy.utils;

import com.nathan.pharmacy.models.History;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HistoryManager {

    private static HistoryManager instance = null;
    private final List<String> histories = new ArrayList<>();
    private String historyDate;
    private final String historyPath = "src/main/resources/com/nathan/pharmacy/history/";

    private HistoryManager() {
        historyDate = LocalDate.now().toString();
        loadHistory();
    }

    public static synchronized HistoryManager getInstance() {
        if (instance == null) {
            instance = new HistoryManager();
        }
        return instance;
    }

    public void push(String userName, String userAction) {
        String entry = LocalDateTime.now() + " - " + userName + ": " + userAction;
        histories.add(entry);
        saveHistory();
    }

    public void pushAt(LocalDate date,String userName, String userAction) {
        String entry = date + " - " + userName + ": " + userAction;
        histories.add(entry);
        saveHistory();
    }

    public void pop() {
        if (!histories.isEmpty()) {
            histories.remove(histories.size() - 1);
            saveHistory();
        }
    }

    public List<History> getHistory(LocalDate historyDate) {
        File historyFile = new File(historyPath, historyDate.toString() + ".txt");
        List<History> historyEntries = new ArrayList<>();
        if (historyFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(historyFile))) {
                String line;
                while ((line = reader.readLine()) != null) {

                    String[] parts = line.split(" - ");
                    if (parts.length == 2) {
                        String[] nameAction = parts[1].split(": ");
                        if (nameAction.length == 2) {
                            LocalDateTime date = LocalDateTime.parse(parts[0]);
                            String name = nameAction[0];
                            String action = nameAction[1];
                            historyEntries.add(new History(date, name, action));
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return historyEntries;
    }



    public void remove(LocalDate historyDate) {
        File historyFile = new File(historyPath, historyDate.toString() + ".txt");
        if (historyFile.exists()) {
            historyFile.delete();
        }
    }

    public void _new() {
        histories.clear();
        historyDate = LocalDate.now().toString();
        saveHistory();
    }

    private void saveHistory() {
        File historyFile = new File(historyPath, historyDate + ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(historyFile))) {
            for (String entry : histories) {
                writer.write(entry);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHistory() {
        File historyFile = new File(historyPath, historyDate + ".txt");
        if (historyFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(historyFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    histories.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}