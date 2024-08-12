module com.example.mathquiz {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mathquiz to javafx.fxml;
    exports com.example.mathquiz;
}