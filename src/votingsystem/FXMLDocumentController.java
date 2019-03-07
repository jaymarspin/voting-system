/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package votingsystem;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLDocumentController implements Initializable {
    
    public static String role ="";
    public static String staticId;
    public static int election_id;
    public static int myId;
    public static String baseURL = "http://localhost:8300/votingServer/";
    @FXML JFXTextField username;
    @FXML JFXPasswordField password;
    @FXML TextField voters;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    
    
    
    @FXML private void eraserOne(KeyEvent e)throws IOException{
        if(!username.getText().trim().equals("") || !password.getText().trim().equals("")){
            voters.setText("");
            
        }
        String key = e.getCode().toString();
        if(key.equals("ENTER")){
            log(e);
        }
    }
    @FXML private void eraserTwo(KeyEvent e)throws IOException{
        if(!voters.getText().trim().equals("")){
            username.setText("");
            password.setText("");
        }
        String key = e.getCode().toString();
        if(key.equals("ENTER")){
            log(e);
        }
    }
    
    
    @FXML public void close(MouseEvent event){
        Stage window = (Stage) ((Node)event.getSource()).getParent().getScene().getWindow();
        window.close();
    } 
    
    @FXML public void getKeyCode(KeyEvent e){
        
    }
    
    @FXML public void log(Event e) throws IOException{
        String fxlink = "adminPortal.fxml";
        try{
            if(!voters.getText().trim().equals("")){
                String value = voters.getText().trim();
        String url;
        StringBuilder sb = new StringBuilder();
        if(!value.equals("")){
            sb.append("role=voter&id=");
            sb.append(value);
        }
        url = sb.toString();
            
        
        HttpURLConnection conn = (HttpURLConnection) new URL(baseURL+"login.php?"+url).openConnection();
        conn.setRequestMethod("GET");
        
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            int response = conn.getResponseCode();
            if(response == HttpURLConnection.HTTP_OK){
                
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {                    
                    builder.append(line);
                }
                String message = builder.toString().split("---")[0];
                if(message.equals("success")){
                   
                    role = "voters";
                    staticId = builder.toString().split("---")[1];
                    election_id = Integer.parseInt(builder.toString().split("---")[2]);
                    
                    myId = Integer.parseInt(builder.toString().split("---")[3]);
                    AnchorPane p = FXMLLoader.load(getClass().getResource(fxlink));
        
        Stage loggy = new Stage();
        loggy.setScene(new Scene(p));
        loggy.initStyle(StageStyle.UNDECORATED);
        loggy.centerOnScreen();
        loggy.show();
        Stage prime = (Stage) ((Node)e.getSource()).getParent().getParent().getScene().getWindow();
        prime.close();
                    
                }else{
                    new Alert(Alert.AlertType.ERROR,builder.toString()).showAndWait();
                }
            }
        
        
        
        
        
        
        
        
        
        
        
            }else{
                if(username.getText().trim().equals("admin") && password.getText().trim().equals("12345")){
                    AnchorPane p = FXMLLoader.load(getClass().getResource(fxlink));
        role = "admin";
        Stage loggy = new Stage();
        loggy.setScene(new Scene(p));
        loggy.initStyle(StageStyle.UNDECORATED);
        loggy.centerOnScreen();
        loggy.show();
        Stage prime = (Stage) ((Node)e.getSource()).getParent().getParent().getScene().getWindow();
        prime.close();
                }else{
                    new Alert(Alert.AlertType.ERROR,"Invalid Account!").showAndWait();
                }
                
                
            }
            
        
        }catch(Exception z){
            System.out.println(z.getMessage());
        }
    }
  
    
}
