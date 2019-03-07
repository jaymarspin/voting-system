/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package votingsystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 *
 */
public class AddCandidatesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML TextField name;
    @FXML ComboBox<String> position;
    private static String imgStr;
    @FXML TextField course;
    @FXML TextArea Platform;
    String positionValues[] = {"President","Vice President","Auditor","Secretary","Treasurer","Sargeant at Arms"};
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        position.getItems().addAll(positionValues);
    }    
    
    private StringBuilder build(String[] argss){
        StringBuilder sbMain = new StringBuilder();
        int no = 0;
        while (no < argss.length) {            
            no += 1;
        }
        for(int i=0;i< argss.length;i++){
            sbMain.append(argss[i]+"%20");
        }
        return sbMain;
    }
    private String appender(String arggs[]){
        StringBuilder sb = new StringBuilder();
       for(int i = 0;i<arggs.length;i++){
           sb.append(arggs[i]);
       }
       return sb.toString();
    }
    
    
    @FXML public void addCandidates(Event e) throws MalformedURLException, IOException{
        
        
        if(!name.getText().trim().equals("") && !position.getSelectionModel().getSelectedItem().equals("") && !course.getText().trim().equals("") && !Platform.getText().trim().equals("")){
            String nameval[] = name.getText().trim().split(" ");
        String positionval[] = position.getSelectionModel().getSelectedItem().trim().split(" ");
        
        StringBuilder sb = new StringBuilder();
        StringBuilder builder = new StringBuilder();
        String courseText[] = course.getText().trim().split(" ");
        String platformText[] = Platform.getText().trim().split(" ");
        
        int num = 0;
        
        while (num < nameval.length) {            
            sb.append(nameval[num]+"%20");
            num++;
        }
        for(int i=0;i< positionval.length;i++){
            builder.append(positionval[i]+"%20");
        }
       
            HttpURLConnection conn = (HttpURLConnection) new URL(FXMLDocumentController.baseURL+"addCandidates.php?name="+sb.toString()+"&position="+builder.toString()+"&electionId="+HomeController.electionId+"&img="+imgStr+"&course="+build(courseText)+"&platform="+build(platformText)).openConnection();
            
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            
            int responsecode = conn.getResponseCode();
            if(responsecode == HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb2 = new StringBuilder();
                String line;
                
                while ((line = br.readLine()) != null) {                    
                    sb2.append(line);
                }
                System.out.println(sb2.toString());
                if(sb2.toString().split("-")[0].equals("success!")){
                 
                    new Alert(Alert.AlertType.INFORMATION,sb2.toString().split("-")[0]).showAndWait();
                    
                    CandidatesController.candidateTableStatic.getItems().add(new globalBean(Integer.parseInt(sb2.toString().split("-")[1]), name.getText().trim(), position.getSelectionModel().getSelectedItem().trim(), num,appender(courseText),appender(platformText)));
                    Stage window = (Stage) ((Node)e.getSource()).getParent().getScene().getWindow();
                    window.close();
                }   
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"Please fill up all the fields Thank you!").showAndWait();
        }
        
        
        
       
            
            
    }
    
   
    
   
    
        @FXML public void close(MouseEvent e){
        Stage window = (Stage) ((Node) e.getSource()).getParent().getScene().getWindow();
        window.close();
    }
}
