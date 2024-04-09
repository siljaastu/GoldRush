module vidmot.goldrush {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;


    opens vidmot to javafx.fxml, javafx.media;
    exports vidmot;
}