package fsd.assignment.assignment1;

import fsd.assignment.assignment1.datamodel.Student;
import fsd.assignment.assignment1.datamodel.StudentData;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;


public class EditStudentController {
    //all of the variables declared below correspond with the edit-students.fxml
    @FXML
    private Label yearStudyDisplay;

    @FXML
    private Label mod1Edit;

    @FXML
    private Label mod2Edit;

    @FXML
    private Label mod3Edit;

    @FXML
    private ChoiceBox<String> mod1ChoiceEdit;

    @FXML
    private ChoiceBox<String> mod2ChoiceEdit;

    @FXML
    private ChoiceBox<String> mod3ChoiceEdit;

    //the modChoices variables correspond to the []
    private String mod1S, mod2S, mod3S;

    private String modChoices[] = {"OOP", "Data Algo", "DS", "Maths", "AI", "Adv Programming", "Project"};

    public void initialize() {

        /* TODO: add all the modChoices to each ChoiceBox
         */
        //insert 3 lines of code here
        mod1ChoiceEdit.getItems().setAll(modChoices);
        mod2ChoiceEdit.getItems().setAll(modChoices);
        mod3ChoiceEdit.getItems().setAll(modChoices);

        //these lines have been given to you includes the setOnAction if a ChoiceBox is selected
        mod1ChoiceEdit.setOnAction(this::getChoiceEdit);
        mod2ChoiceEdit.setOnAction(this::getChoiceEdit);
        mod3ChoiceEdit.setOnAction(this::getChoiceEdit);
    }

    //to ensure that detail pops up to edit
    public void setToEdit(Student stu) {
        /* TODO: display the student to be edited details
         */
        //insert 4 lines of code here
        yearStudyDisplay.setText(stu.getYearOfStudy());
        mod1Edit.setText(stu.getModule1());
        mod2Edit.setText(stu.getModule2());
        mod3Edit.setText(stu.getModule3());
        /* TODO: get the new module choices using mod1S, mod2S and mod3S
         */
        mod1S = mod1ChoiceEdit.getSelectionModel().getSelectedItem();
        mod2S = mod2ChoiceEdit.getSelectionModel().getSelectedItem();
        mod3S = mod3ChoiceEdit.getSelectionModel().getSelectedItem();
    }

    public Student processEdit(Student stu) {
        /* TODO: complete the observableIst statement by getting all students from StudentData
                 to be collected in allStudents
         */
        ObservableList<Student> allStudents = StudentData.getInstance().getStudents();
        /* TODO: get studId and year of study
         */
        String studIdS = stu.getStudId();
        String yearStudyS = stu.getYearOfStudy();
        /* TODO: remove the student to be edited from allStudents
         */
        //insert the line to remove the student here
        allStudents.remove(stu);
        /* TODO: add the student back by creating a new object reference and calling the addStudentData()
         */
        if (mod1S == null) mod1S = mod1Edit.getText();
        if (mod2S == null) mod2S = mod2Edit.getText();
        if (mod3S == null) mod3S = mod3Edit.getText();
        Student changedStu = new Student(studIdS, yearStudyS, mod1S, mod2S, mod3S);
        //call the addStudentData()
        StudentData.getInstance().addStudentData(changedStu);
        /* TODO: return the newly edited student back to the Controller class / editStudent()
         */
        return changedStu;
    }

    public void getChoiceEdit(ActionEvent event) {
        /* TODO: complete the 3 if... statements using event
         */
        if (event.getSource().equals(mod1ChoiceEdit)) mod1S = mod1ChoiceEdit.getSelectionModel().getSelectedItem();
        else if (event.getSource().equals(mod2ChoiceEdit)) mod2S = mod2ChoiceEdit.getSelectionModel().getSelectedItem();
        else if (event.getSource().equals(mod3ChoiceEdit)) mod3S = mod3ChoiceEdit.getSelectionModel().getSelectedItem();
    }
}
