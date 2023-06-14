package com.example.notebook;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.Console;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

import static com.example.notebook.CSVReaderSaver.readCSV;
import static com.example.notebook.CSVReaderSaver.saveCSV;

public class HelloController {
    ArrayList<TaskClass> actualList;
    ObservableList<TaskClass> taskList;
    @FXML
    private TableView<TaskClass> main_table;

    @FXML
    private Button add_button;


    @FXML
    private TextArea eventTextField;
    @FXML
    private MenuButton menuButton;

    @FXML
    private DatePicker dateTextField;

    @FXML
    private TextArea placeTextField;
    @FXML
    private TextArea descriptionTextField;
    @FXML
    private TextArea doneComment;


    @FXML
    private TableColumn event2;
    @FXML
    private TableColumn description;
    @FXML
    private TableColumn time;
    @FXML
    private TableColumn place;
    @FXML
    private TableColumn status;
    @FXML
    private TableColumn comment;
    @FXML
    private VBox mainVBOX;

    @FXML
    public void initialize() throws FileNotFoundException {
        ArrayList<TaskClass> initList = readCSV();
        actualList = new ArrayList<>(initList);
        taskList = FXCollections.observableArrayList(actualList);
        main_table.setItems(taskList);

        dateTextField.setEditable(false);
        doneComment.setWrapText(true);




        event2.setCellValueFactory(new PropertyValueFactory<>("event2"));

        description.setCellValueFactory(new PropertyValueFactory<>("description"));

        time.setCellValueFactory(new PropertyValueFactory<>("time"));

        place.setCellValueFactory(new PropertyValueFactory<>("place"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        comment.setCellValueFactory(new PropertyValueFactory<>("comment"));


        event2.prefWidthProperty().bind(main_table.widthProperty().multiply(0.25));
        description.prefWidthProperty().bind(main_table.widthProperty().multiply(0.25));
        time.prefWidthProperty().bind(main_table.widthProperty().multiply(0.1));
        place.prefWidthProperty().bind(main_table.widthProperty().multiply(0.1));
        status.prefWidthProperty().bind(main_table.widthProperty().multiply(0.1));
        comment.prefWidthProperty().bind(main_table.widthProperty().multiply(0.2));


        mainVBOX.setVgrow(main_table, Priority.ALWAYS);
    }



    @FXML
    protected void on_click_add_button() {
        ArrayList<String> missingFields = CheckMissingFields();
        if (missingFields.contains("Date")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Not correct format for date");
            errorAlert.setContentText("Not correct format for date");
            errorAlert.showAndWait();
            return;
        }

        if (!missingFields.isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Missing fields");
            errorAlert.setContentText("Missing fields: " + missingFields.toString().replace("[", "").replace("]", ""));
            errorAlert.showAndWait();
            return;
        }
        System.out.println(eventTextField.getText());
        TaskClass task = new TaskClass(eventTextField.getText(), descriptionTextField.getText(), placeTextField.getText(), dateTextField.getValue(), "Not visited", false, "");

        taskList.add(task);
        actualList.add(task);
        main_table.setItems(taskList);

        eventTextField.clear();
        descriptionTextField.clear();
        placeTextField.clear();
        //deadlineTextField.clear();


    }

    protected ArrayList<String> CheckMissingFields() {
        ArrayList<String> missingFields = new ArrayList<>();
        if (eventTextField.getText().isEmpty()) {
            missingFields.add("Event");
        }
        if (descriptionTextField.getText().isEmpty()) {
            missingFields.add("Description");
        }
        if (placeTextField.getText().isEmpty()) {
            missingFields.add("Place");
        }
        if(dateTextField.getValue() == null) {
            missingFields.add("Date");
        }
        return missingFields;
    }







    @FXML
    protected void on_click_delete_button() {
        TaskClass selectedItem = main_table.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        actualList.remove(selectedItem);
        taskList.remove(selectedItem);

    }

    @FXML
    protected void on_click_done_button() {
        String status = "Visited";
        TaskClass selectedItem = main_table.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }

        if (selectedItem.isDone()) {
            return;
        }


        if (doneComment.getText() != "") {
            selectedItem.setComment(doneComment.getText());
        }


        selectedItem.setDone();
        selectedItem.setStatus(status);

        doneComment.clear();

        main_table.refresh();

    }


    @FXML
    protected void onClose() {
        saveCSV(actualList);
    }
}

