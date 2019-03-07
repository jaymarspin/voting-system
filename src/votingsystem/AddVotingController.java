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
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable; 
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.JSONException;


public class AddVotingController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML TextField electionName;
    
    @FXML DatePicker dateS;
    @FXML DatePicker dateE;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    
    public void send(String name,int year,String dateS,String dateE,Event e) throws IOException, JSONException{
        
        
        
        StringBuilder urlBuild = new StringBuilder();
        StringBuilder bf = new StringBuilder();
        String split[] = name.split(" ");
        int num = 0;
        while (num < split.length) {       
            bf.append(split[num]+"*_*");
            num += 1;
        }
        
        
        urlBuild.append("name="+bf.toString()+"&");
        urlBuild.append("year=2018&");
        urlBuild.append("dateS="+dateS+"&");
        urlBuild.append("dateE="+dateE+"&");
        String url = FXMLDocumentController.baseURL+"addElection.php?"+urlBuild.toString();
        HttpURLConnection con = new server().con(url);
      int response = con.getResponseCode();
        
        
      if(response == con.HTTP_OK){
            
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {        
                
                sb.append(line);
            }
            if(sb.toString().split("-")[0].equals("success!")){
               
                
                new Alert(Alert.AlertType.INFORMATION,sb.toString().split("-")[0]).showAndWait();
                HomeController.tabler.getItems().add(new globalBean(Integer.parseInt(sb.toString().split("-")[1]), name, year, dateS, dateE, 0,"pending"));
               
                Stage window = (Stage) ((Node) e.getSource()).getParent().getScene().getWindow();
                window.close();
            }else{
                new Alert(Alert.AlertType.ERROR,"Date input error!").showAndWait();
                
            }
            
     }else{
           new Alert(Alert.AlertType.ERROR,"error occured!").showAndWait();
       }
     }

    @FXML public void addEl(Event e) throws IOException, JSONException{
        if(!electionName.getText().trim().equals("") && !dateS.getEditor().getText().trim().equals("") && !dateE.getEditor().getText().trim().equals("")){
            send(electionName.getText().trim(),2018,dateS.getEditor().getText().trim(),dateE.getEditor().getText().trim(),e);
        }else{
            new Alert(Alert.AlertType.ERROR,"Please fill up all the fields!").showAndWait();
        }
        
        
      
    }
    
    @FXML public void close(MouseEvent e){
        Stage window = (Stage) ((Node) e.getSource()).getParent().getScene().getWindow();
        window.close();
    }
    

    
}
