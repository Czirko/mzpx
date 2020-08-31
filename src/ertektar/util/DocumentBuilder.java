/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ertektar.util;

import ertektar.model.Ertek;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import static java.lang.System.out;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;


/**
 *
 * @author Cz.Csaba
 */
public class DocumentBuilder {

    Pane basePane;
    Ertek ertek;

    public DocumentBuilder(Pane basePane, Ertek ertek) {
        this.basePane = basePane;
        this.ertek = ertek;
    }

    public void setErtek(Ertek ertek) {
        this.ertek = ertek;
    }
    

    public File getFileChooser() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Válaszd ki a dokumentumot");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("DocX files (*.docx)", "*.docx");
        // chooser.setInitialDirectory(getClass());
        chooser.getExtensionFilters().add(extFilter);
        Stage stage = (Stage) basePane.getScene().getWindow();
        File file = chooser.showOpenDialog(stage);
        return file;
    }

    public Ertek filePrcess(File f) {
        String line = "";
         ertek = new Ertek();
       
            System.out.println(f.getAbsolutePath());

            XWPFDocument docx = null;
            try {
                docx = new XWPFDocument(new FileInputStream(f));
            } catch (IOException e) {
                System.out.println(e);
            }
            XWPFWordExtractor ex = new XWPFWordExtractor(docx);
            String full = ex.getText();

            String[] splittedText = full.split("\n");
            //System.out.println(splittedText.length);
            // System.out.println(ex.getText());
            String name = getNameByFileName(f);
            ertek.setName(name);

            String text = "";
            for (int i = 0; i < splittedText.length; i++) {
                if (i == 0) {
                    ertek.setTitle(splittedText[i]);
                } else {
                    text += splittedText[i] + System.lineSeparator();
                }
            }
            ertek.setText(text);
        return ertek;
    }

    private String getNameByFileName(File f) {
        String[] spdFileName = f.getName().split("\\.");
        return spdFileName[0];

    }

}
