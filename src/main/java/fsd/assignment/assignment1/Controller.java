package fsd.assignment.assignment1;

import fsd.assignment.assignment1.datamodel.Student;
import fsd.assignment.assignment1.datamodel.StudentData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;

public class Controller {

    //used to add a student to the ArrayList for addStudentData()
    public Student studentToAdd;
    //these variables correspond to the <top> of main-view.fxml
    @FXML
    private TextField studId;
    @FXML
    private TextField yearStudy;
    @FXML
    private ChoiceBox<String> mod1Choice;
    @FXML
    private ChoiceBox<String> mod2Choice;
    @FXML
    private ChoiceBox<String> mod3Choice;
    private String choice1, choice2, choice3;
    private String modChoices[] = {"OOP", "Data Algo", "DS", "Maths", "AI", "Adv Programming", "Project"};

    //validateStudent is the last element corresponding to <top>
    @FXML
    private Label validateStudent; //remember this is the Label that you only see when there is an invalid "add"
    //these variables correspond to the <left> i.e. the studentListView
    @FXML
    private ListView<Student> studentListView;
    //these variables correspond to the <bottom> part of the border
    @FXML
    private Label yearStudyView;
    @FXML
    private Label mod1View;
    @FXML
    private Label mod2View;

    //mod3View is the last element for the bottom part of the border
    @FXML
    private Label mod3View;
    //the contextMenu is used for the right-click regarding Edit / Delete
    @FXML
    private ContextMenu listContextMenu;
    //this variable is used when switching windows from add to edit
    @FXML
    private BorderPane mainWindow;

    public void initialize() {

        studentListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                /* TODO: complete the code so that if a studId is selected then the changed()
                         should ensure that the other fields related to the selected item appear at the bottom of the window
                */
                Student temp = studentListView.getSelectionModel().getSelectedItem();

                if (temp != null) {
                    yearStudyView.setText(temp.getYearOfStudy());
                    mod1View.setText(temp.getModule1());
                    mod2View.setText(temp.getModule2());
                    mod3View.setText(temp.getModule3());
                }
            }
        });
        //the setOnAction ensures that when a ChoiceBox is selected the getChoice() grabs the selected choice
        mod1Choice.setOnAction(this::getChoice);
        mod2Choice.setOnAction(this::getChoice);
        mod3Choice.setOnAction(this::getChoice);

        /* TODO: the array declared above for modChoices must be added to each Choicebox
                 include the code here to addAll()
        */
        //insert the code to addAll() the modChoices [] to each ChoiceBox here
        if (mod1Choice.getItems().size() == 0) {
            mod1Choice.getItems().addAll(modChoices);
            mod2Choice.getItems().addAll(modChoices);
            mod3Choice.getItems().addAll(modChoices);
        }

        //Remove edit??
        //Error when the entry areas are empty
        //Order??


        //deleting a student
        /* TODO: create a new listContextMenu -> defined above in the variables
         */
        listContextMenu = new ContextMenu();
        /* TODO: create a MenuItem object so that when the user right-clicks a studId
                 the word Delete? appears
         */
        MenuItem deleteStudent = new MenuItem("Delete?");

        deleteStudent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /* TODO: get the item to be deleted and call the deleteStudent()
                 */
                Student temp = studentListView.getSelectionModel().getSelectedItem();
                deleteStudent(temp);
            }
        });

        //editing a student
        /* TODO: create a new listContextMenu -> defined above in the variables
         */
        listContextMenu = new ContextMenu();

        /* TODO: create a MenuItem object so that when the user right-clicks a studId
                 the word Edit?? appears
         */
        MenuItem editStudent = new MenuItem("Edit?");

        editStudent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /* TODO: get the item to be edited and call the editStudent()
                 */
                Student temp = studentListView.getSelectionModel().getSelectedItem();
                editStudent(temp);
            }
        });

        //code provided to ensure that contextMenu appears as part of the above actions
        listContextMenu.getItems().add(deleteStudent);
        listContextMenu.getItems().add(editStudent);

        //to ensure access to a particular cell in the studentListView
        studentListView.setCellFactory(new Callback<ListView<Student>, ListCell<Student>>() {
            public ListCell<Student> call(ListView<Student> param) {
                ListCell<Student> cell = new ListCell<Student>() {
                    @Override
                    protected void updateItem(Student stu, boolean empty) {
                        super.updateItem(stu, empty);
                        /* TODO: ensure that the studentListView has studId's or not when
                                 the delete a student takes place
                         */
                        if (empty == true) {
                            setText(null);
                        } else {
                            setText(stu.getStudId());
                        }

                    }//end of update()
                };
                //code included as part of the delete
                cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                    if (isNowEmpty) {
                        cell.setContextMenu(null);
                    } else {
                        cell.setContextMenu(listContextMenu);
                    }
                });
                return cell;
            }
        }); //end of setting the cell factory

        /* TODO: ensure that the studId's are sorted according to year of study in ascending order
         */

        SortedList<Student> sortedByYear = new SortedList<Student>(StudentData.getInstance().getStudents(), (Comparator<? super Student>) new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return Integer.compare(Integer.parseInt(o1.getYearOfStudy()), Integer.parseInt(o2.getYearOfStudy()));
            }
        });


        /* TODO: step 1 - set items using the sorted list
                 step 2 - ensure that only one studId can be selected at one time
                 step 3 - ensure that the first studId is highlighted when the application commences
         */
        studentListView.getItems().setAll(sortedByYear);
        studentListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        studentListView.getSelectionModel().selectFirst();

    }

    public void getChoice(ActionEvent event) {
        /* TODO: make use of event to determine each choice and assign each module choice to
                 choice1, choice2 and choice3
         */
        if (event.getSource().equals(mod1Choice)) choice1 = mod1Choice.getSelectionModel().getSelectedItem();
        else if (event.getSource().equals(mod2Choice)) choice2 = mod2Choice.getSelectionModel().getSelectedItem();
        else if (event.getSource().equals(mod3Choice)) choice3 = mod3Choice.getSelectionModel().getSelectedItem();
    }


    @FXML
    public void addStudentData() {
        /* TODO: get the values from the textfields
         */
        String studIdS = studId.getText();
        String yearStudyS = yearStudy.getText();
        /* TODO: validate whether the studIdS and yearStudyS are occupied, BOTH have to be occupied
                 for the add to take place, if one or both are unoccupied print the following message
                 in the validateStudent label -> message to be printed is
                 "Error: cannot add student if studId or year of study not filled in"
                 If both are occupied then go ahead with the addStudentData()
         */
        //do the if...here
        if (studIdS.strip().length() == 0 || yearStudyS.strip().length() == 0)
            validateStudent.setText("Error: cannot add student if studId or year of study not filled in");
            //do the else...here, first ensure that the validateStudent label is clear of any text
        else {
            validateStudent.setText("");
            studentToAdd = new Student(studIdS, yearStudyS, choice1, choice2, choice3);
            StudentData.getInstance().addStudentData(studentToAdd);
            for (int i = 0; i < studentListView.getItems().size(); i++) {
                if (Integer.parseInt(studentListView.getItems().get(i).getYearOfStudy()) >= Integer.parseInt(studentToAdd.getYearOfStudy()) || studentListView.getItems().size() == i + 1) {
                    // if (studentListView.getItems().size() == i + 1 && Integer.parseInt(studentListView.getItems().get(i).getYearOfStudy()) < Integer.parseInt(studentToAdd.getYearOfStudy()))
                    //    i++;
                    studentListView.getItems().add(i + 1, studentToAdd);
                    break;

                }
            }

            //studentListView.getItems().add(studentToAdd);
            //initialize();
            studentListView.getSelectionModel().select(studentToAdd);

        }
        //use the getInstance() to addStudentData()
        //select the student that has been added so that it is highlighted on the list
    }

    public void deleteStudent(Student stu) {
        /* TODO: create an alert object to confirm that a user wants to delete
         */
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        /* TODO: set the title with "Delete a student from the list"
         */
        //insert the line of code here
        alert.setTitle("Delete a student from the list");
        /* TODO: set the header text with "Deleting student " xxx - where xxx is the studId
         */
        //insert the line of code here
        alert.setHeaderText("Deleting student " + stu.getStudId());
        /* TODO: have a message that asks the user "Are you sure you want to delete the student?"
         */
        //insert the line of code here
        alert.setContentText("Are you sure you want to delete the student?");
        //the result object with the showAndWait() has been completed for you
        Optional<ButtonType> result = alert.showAndWait();
        /* TODO: include a test to verify if the result is present and whether the OK button was
                 pressed, if so go ahead and call the deleteStudent()
         */
        //insert the 2 lines of code here
        if (result.isPresent() && result.get() == ButtonType.OK) {
            StudentData.getInstance().deleteStudent(stu);
            studentListView.getItems().remove(stu);
        }
    }

    public void editStudent(Student stu) {
        //the dialog object has been completed for you
        Dialog<ButtonType> dialog = new Dialog<>();
        /* TODO: use the dialog object to set the owner, the title and the header text
                 the title must state "Edit a student's details"
                 the header text must state: Editing student Id: xxx - where xxx is the studId
         */
        //insert the 3 lines of code here
        dialog.initOwner(mainWindow.getScene().getWindow());
        dialog.setTitle("Edit a student's details");
        dialog.setHeaderText("Editing student Id: " + stu.getStudId());
        /* TODO: complete the FXMLLoader statement
         */
        FXMLLoader fxmlLoader = new FXMLLoader();
        /* TODO: use the fxmlLoader to set the edit-students.fxml
         */
        fxmlLoader.setLocation(getClass().getResource("edit-students.fxml"));
        //insert the line of code here
        //remove the comments and complete the try...catch
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
            /* TODO: load the fxml
             */
        } catch (IOException event) {
            System.out.println(event.getMessage());
            /* TODO: print an appropriate message if it cannot be loaded
                     print stacktrace
             */
            return;
        }
        /* TODO: complete the ec controller statement
         */
        EditStudentController ec = fxmlLoader.getController();
        /* TODO: use the ec object to call setToEdit()
         */
        //insert the line of code here
        ec.setToEdit(stu);
        /* TODO: create the OK and CANCEL buttons using dialog
         */
        //insert the 2 lines of code here
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        //the result object with the showAndWait() has been completed for you
        Optional<ButtonType> result = dialog.showAndWait();

         /* TODO: verify if there is an edit to complete, complete the editStudent call processEdit()
                  ensure that the student edited is selected
         */
        //remove the comments and complete the if...
        if (result.get() == ButtonType.OK && result.isPresent()) {
            Student editStudent = ec.processEdit(stu);
            studentListView.getSelectionModel().select(editStudent);
            //select the edited studId here
        }
    }
}