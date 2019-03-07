/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package votingsystem;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Jaymar
 */
public class AdminPortalController implements Initializable {
    double xOffset,yOffset;
    @FXML VBox menuHolder;
    public static VBox mMenu;
    @FXML AnchorPane holder;
    public static AnchorPane mPane;
    public static Event masterClick;
    @FXML Label dater;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mMenu = menuHolder;
        mPane = holder;
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        dater.setText(sdf.format(new Date()));
        dater.setAlignment(Pos.CENTER);
        try {
            if(FXMLDocumentController.role.equals("voters")){
             
                menuHolder.getChildren().remove(1);
                menuHolder.getChildren().remove(0);
                votersPanel();
            }else{
                mMenu.getChildren().remove(1);
                home();
            }
             
           
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(AdminPortalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    @FXML public void signOut(Event e) throws IOException{
        Stage prime = (Stage)((Node) e.getSource()).getParent().getParent().getScene().getWindow();
        prime.close();
        
        Stage window = new Stage();
        AnchorPane p = (AnchorPane) FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        window.setScene(new Scene(p));
        window.initStyle(StageStyle.UNDECORATED);
        window.show();
    }
    
    @FXML public void home() throws IOException{
        AnchorPane p = FXMLLoader.load(getClass().getResource("home.fxml"));
        
        holder.getChildren().clear();
        holder.getChildren().add(p);
    }
    @FXML public void votersPanel() throws IOException{
                AnchorPane p = FXMLLoader.load(getClass().getResource("votersPanel.fxml"));
        
        holder.getChildren().clear();
        holder.getChildren().add(p);
    }
    @FXML public void mousePressed(MouseEvent event){
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
    }
    @FXML public void dragged(MouseEvent event){
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setX(event.getScreenX() - xOffset);
            window.setY(event.getScreenY() - yOffset);
    }
    
    
    public void newStage(String fxml) throws IOException{
        Stage prime = new Stage();
        AnchorPane p = FXMLLoader.load(getClass().getResource(fxml));
        prime.sizeToScene();
        prime.initModality(Modality.APPLICATION_MODAL);
        prime.initStyle(StageStyle.UNDECORATED);
        prime.setScene(new Scene(p));
        prime.show();
    }
    
    
    
      @FXML public void close(MouseEvent event){
        Stage window = (Stage) ((Node)event.getSource()).getParent().getScene().getWindow();
        window.close();
    } 
    
}
