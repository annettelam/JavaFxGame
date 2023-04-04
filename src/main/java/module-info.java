module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires junit;
    requires org.mockito;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}