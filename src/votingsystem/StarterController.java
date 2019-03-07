/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package votingsystem;

import com.jfoenix.controls.JFXDatePicker;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jfxtras.scene.control.LocalTimeTextField;


public class StarterController implements Initializable {

    @FXML Label electionLabel;
    
    
    @FXML JFXDatePicker endDate;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       electionLabel.setText(HomeController.electionName);
    }    
    
     @FXML public void close(MouseEvent e){
        Stage window = (Stage) ((Node) e.getSource()).getParent().getScene().getWindow();
        window.close();
    }
     
     @FXML private void done(Event e) throws ParseException, MalformedURLException, IOException{
         try{
         String dater = endDate.getValue().toString();
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         Date d = sdf.parse(dater);
         HttpURLConnection conn = (HttpURLConnection) new URL(FXMLDocumentController.baseURL+"startVoting.php?id="+HomeController.electionId+"&date="+dater).openConnection();
         conn.setRequestMethod("GET");
         conn.setRequestProperty("accept", "application/json");
         conn.setDoInput(true);
         conn.setDoOutput(true);
         
         
         
         int response = conn.getResponseCode();
         if(response == HttpURLConnection.HTTP_OK){
             BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
             StringBuilder sb = new StringBuilder();
             String line;
             while ((line = br.readLine()) != null) {                 
                 sb.append(line);
             }
             if(sb.toString().equals("success!")){
                 new Alert(Alert.AlertType.INFORMATION,sb.toString()).showAndWait();
                 AnchorPane p = (AnchorPane) FXMLLoader.load(getClass().getResource("home.fxml"));
                 AdminPortalController.mPane.getChildren().clear();
                 AdminPortalController.mPane.getChildren().add(p);
                 Stage window = (Stage) ((Node) e.getSource()).getParent().getScene().getWindow();
                 window.close();
             }else{
                 new Alert(Alert.AlertType.ERROR,sb.toString()).showAndWait();
             }
         }
         }catch(Exception z){
             System.out.println(z.getMessage());
         }
     }
    
}
