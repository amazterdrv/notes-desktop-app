package com.amazter.gui;

import com.amazter.enums.SceneType;
import com.amazter.model.Note;
import com.amazter.service.PDFService;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class SceneBuilder {

    public Scene buildScene(SceneType sceneType, Stage stage) {
        Scene scene;
        switch (sceneType) {
            case CREATE_NOTE: {
                scene = buildCreateNoteScene(stage);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + sceneType);
        }
        return scene;
    }

    public Scene buildCreateNoteScene(Stage stage) {
        BorderPane border = new BorderPane();
        Alert a = new Alert(Alert.AlertType.NONE);
        DirectoryChooser directoryChooser = new DirectoryChooser();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(

                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")

        );
        VBox vboxTop = new VBox();
        vboxTop.setPadding(new Insets(15, 12, 15, 12));
        vboxTop.setSpacing(10);
        vboxTop.setStyle("-fx-background-color: rgba(92,153,150,0.27);");
        TextField title = new TextField("Title");
        TextField author = new TextField("Author");
        vboxTop.getChildren().addAll(title, author);
        VBox vboxCenter = new VBox();
        vboxCenter.setPadding(new Insets(15, 12, 15, 12));
        vboxCenter.setSpacing(10);
        vboxCenter.setStyle("-fx-background-color: rgba(92,153,150,0.27);");
        TextArea body = new TextArea("Body");
        body.setPrefHeight(200);
        vboxCenter.getChildren().addAll(body);
        HBox hboxBottom = new HBox();
        hboxBottom.setPadding(new Insets(15, 12, 15, 12));
        hboxBottom.setSpacing(10);
        hboxBottom.setStyle("-fx-background-color: #336699;");
        TextField fileName = new TextField("Enter File Name");
        Button buttonCreate = new Button("Create PDF");
        buttonCreate.setPrefSize(100, 20);
        Button buttonAppend = new Button("Append To Existing PDF");
        buttonAppend.setPrefSize(200, 20);
        hboxBottom.getChildren().addAll(fileName, buttonCreate, buttonAppend);
        border.setBottom(hboxBottom);
        border.setTop(vboxTop);
        border.setCenter(vboxCenter);
        buttonCreate.setOnAction((ActionEvent e) -> {
            directoryChooser.setTitle("Select directory to save");
            File selectedDirectory = directoryChooser.showDialog(stage);
            if (fileName.getText() == null || fileName.getText().equals("")) {
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Please enter a valid file name");
                a.show();
                return;
            }
            String filename = selectedDirectory.getAbsolutePath() + "/" + fileName.getText() + ".pdf";
            Note note = new Note(title.getText(), body.getText(), author.getText());
            PDFService.createPDF(filename, note.toText());
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setContentText("Your file has been saved at " + filename);
            a.show();

        });
        buttonAppend.setOnAction((ActionEvent e) -> {
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                String tempFileName = "temp.pdf";
                Note note = new Note(title.getText(), body.getText(), author.getText());
                PDFService.createPDF(tempFileName, note.toText());
                PDFService.mergePDfFiles(file.getAbsolutePath(), file.getAbsolutePath(), tempFileName);
                new File(tempFileName).delete();
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Your note has been appended to " + file.getAbsoluteFile());
                a.show();
            }

        });

        return new Scene(border, 640, 480);
    }

}

