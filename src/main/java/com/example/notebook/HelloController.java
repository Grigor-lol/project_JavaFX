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


    private Map<String, CustomMenuItem> curMapOfMenuItems;
    private Map<String, CheckBox> curMapOfCheckBoxes;
    ArrayList<TaskClass> actualList;
    ObservableList<TaskClass> taskList;
    @FXML
    private TableView<TaskClass> main_table;

    @FXML
    private Button add_button;


    @FXML
    private TextArea taskTextField;
    @FXML
    private MenuButton menuButton;

    @FXML
    private DatePicker deadlineTextField;

    @FXML
    private TextArea executorTextField;
    @FXML
    private TextArea descriptionTextField;
    @FXML
    private TextArea doneComment;
    @FXML
    private TextArea notDoneComment;


    @FXML
    private TableColumn task;
    @FXML
    private TableColumn description;
    @FXML
    private TableColumn executor;
    @FXML
    private TableColumn deadline;
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

        deadlineTextField.setEditable(false);
        doneComment.setWrapText(true);
        notDoneComment.setWrapText(true);



        task.setCellValueFactory(new PropertyValueFactory<>("task"));

        description.setCellValueFactory(new PropertyValueFactory<>("description"));

        executor.setCellValueFactory(new PropertyValueFactory<>("executor"));

        deadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        comment.setCellValueFactory(new PropertyValueFactory<>("comment"));


        task.prefWidthProperty().bind(main_table.widthProperty().multiply(0.25));
        description.prefWidthProperty().bind(main_table.widthProperty().multiply(0.25));
        executor.prefWidthProperty().bind(main_table.widthProperty().multiply(0.1));
        deadline.prefWidthProperty().bind(main_table.widthProperty().multiply(0.1));
        status.prefWidthProperty().bind(main_table.widthProperty().multiply(0.1));
        comment.prefWidthProperty().bind(main_table.widthProperty().multiply(0.2));


        mainVBOX.setVgrow(main_table, Priority.ALWAYS);
    }



    @FXML
    protected void on_click_add_button() {
        ArrayList<String> missingFields = CheckMissingFields();
        if (missingFields.contains("Deadline")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Not correct format for data");
            errorAlert.setContentText("Not correct format for deadline data");
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

        TaskClass task = new TaskClass(taskTextField.getText(), descriptionTextField.getText(), executorTextField.getText(), deadlineTextField.getValue(), "In developing", false, "", true);

        taskList.add(task);
        actualList.add(task);
        main_table.setItems(taskList);

        taskTextField.clear();
        descriptionTextField.clear();
        executorTextField.clear();
        //deadlineTextField.clear();


    }

    protected ArrayList<String> CheckMissingFields() {
        ArrayList<String> missingFields = new ArrayList<>();
        if (taskTextField.getText().isEmpty()) {
            missingFields.add("Task");
        }
        if (descriptionTextField.getText().isEmpty()) {
            missingFields.add("Description");
        }
        if (executorTextField.getText().isEmpty()) {
            missingFields.add("Executor");
        }
        if(deadlineTextField.getValue() == null) {
            missingFields.add("Deadline");
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
        String status = "Done";
        TaskClass selectedItem = main_table.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            System.out.print("lol");
            return;
        }

        if (selectedItem.isDone()) {
            System.out.print("lol2");
            return;
        }


        if (doneComment.getText() != "") {
            //status += doneComment.getText();
            selectedItem.setComment(doneComment.getText());
        }

        if (!selectedItem.inDevelop()) {
            System.out.print(selectedItem.getStatus());

            return;
        }

        selectedItem.setDone();
        selectedItem.setNotInDevelope();
        selectedItem.setStatus(status);

        doneComment.clear();

        main_table.refresh();

    }

    @FXML
    protected void on_click_not_done_button() {
        String status = "Not done";
        TaskClass selectedItem = main_table.getSelectionModel().getSelectedItem();
        //System.out.print("lol3");
        if (selectedItem == null) {
            //System.out.print("lol");
            return;
        }

        if (selectedItem.inDevelop()) {
            //System.out.print("lol2");
            TaskClass newItem = new TaskClass(selectedItem.getTask(),selectedItem.getDescription(),selectedItem.getExecutor(),selectedItem.getDeadline(),"In developing",false, "", true);
            newItem.addWeek();

            selectedItem.setNotDone();
            if (notDoneComment.getText() != "") {
                //status += notDoneComment.getText();
                selectedItem.setComment(notDoneComment.getText());
            }
            selectedItem.setStatus(status);
            selectedItem.setNotInDevelope();

            taskList.add(newItem);
            actualList.add(newItem);

            notDoneComment.clear();
            main_table.refresh();
        }
    }

    @FXML
    protected void onClose() {
        saveCSV(actualList);
    }
}

