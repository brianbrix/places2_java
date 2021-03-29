module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires android.json;
    requires com.google.gson;
    requires org.apache.commons.io;
    requires kotlin.stdlib;
    requires kotlinx.coroutines.core.jvm;


    opens org.example to javafx.fxml;
    exports org.example;
}