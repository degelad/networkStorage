module org.clades.networkcloudclient {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.clades.networkcloudclient to javafx.fxml;
    exports org.clades.networkcloudclient;
}
