/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package votingsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class AddVotersController implements Initializable {

    @FXML TextField fname;
    @FXML TextField lname;
    @FXML ComboBox<String> course;
    @FXML ComboBox<String> year;
    String yearValues[] = {"notyet","Grade11-SMAW","Grade11-COOKERY","Grade11-HUMS","Grade11-EIM","Grade12-SMAW","Grade12-COOKERY","Grade12-HUMS","Grade12-EIM"};
    String courseValues[] = {"Grade7","Grade8","Gradw9","Grade10","Grade11","Grade12"};
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        course.getItems().addAll(courseValues);
        year.getItems().addAll(yearValues);
        
    }    
    
    @FXML public void addVoters(Event e) throws MalformedURLException, IOException{
        if(!fname.getText().trim().equals("") && !lname.getText().trim().equals("") && !course.getSelectionModel().getSelectedItem().equals("") && !year.getSelectionModel().getSelectedItem().trim().equals("")){
            UUID id = UUID.randomUUID();
        
        String vId = id.toString().substring(0, 5);
        
        String tmpVid = vId;
        String fnameVal = fname.getText().trim();
        String lnameVal = lname.getText().trim();
        String courseVal = course.getSelectionModel().getSelectedItem();
        String yearVal = year.getSelectionModel().getSelectedItem();
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        String strinner1[] = fnameVal.split(" ");
        String strinner2[] = lnameVal.split(" ");
    
        for(int i = 0;i < strinner1.length;i++){
            if(strinner1.length < 2){
                sb.append(strinner1[i]);
            }else{
                sb.append(strinner1[i]+"%20");
            }
            
        }
        
        for(int i = 0;i < strinner2.length;i++){
            if(strinner2.length < 2){
                sb2.append(strinner2[i]);
            }else{
                sb2.append(strinner2[i]+"%20");
            }
            
        }
        vId = sb.toString()+"-"+vId;
        
        HttpURLConnection conn = (HttpURLConnection) new URL(FXMLDocumentController.baseURL+"addVoters.php?fname="+sb.toString().toString().substring(0,sb.toString().length() - 3)+"&lname="+sb2.toString()+"&course="+courseVal+"&year="+yearVal+"&vId="+vId+"&electionId="+HomeController.electionId).openConnection();
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("accept", "application/json");
            
        
        int response = conn.getResponseCode();
        
        if(response == HttpURLConnection.HTTP_OK){
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {                
                builder.append(line);
                
            }
            System.out.println(builder.toString());
            if(builder.toString().split("-")[0].equals("success!")){
                new Alert(Alert.AlertType.INFORMATION,"Success!").showAndWait();
                Stage window = (Stage) ((Node)e.getSource()).getParent().getScene().getWindow();
                window.close();
                
                VotersController.votersTableStatic.getItems().add(new globalBean(Integer.parseInt(builder.toString().split("-")[1]), HomeController.electionId, fnameVal, lnameVal, yearVal, courseVal,tmpVid));
            }
            
        }
        }else{
            new Alert(Alert.AlertType.ERROR,"Please Fill up all the fields!").showAndWait();
        }
        
        
        
        
    
    
    }
    @FXML public void close(MouseEvent e){
        Stage window = (Stage) ((Node) e.getSource()).getParent().getScene().getWindow();
        window.close();
    }
    
}
