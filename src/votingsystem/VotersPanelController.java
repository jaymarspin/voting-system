/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package votingsystem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;


public class VotersPanelController implements Initializable {

    @FXML ScrollPane scroller;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            AnchorPane p = (AnchorPane) FXMLLoader.load(getClass().getResource("voterInside.fxml"));
            scroller.setContent(p);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }    
    
}
