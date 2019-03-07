/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package votingsystem;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException; 
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;


public class FurtherController implements Initializable {
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    @FXML private void goCandidates(Event e) throws IOException{
        changer("candidates.fxml", e);
        
    }
    @FXML private void goVoters(Event e) throws IOException{
        changer("voters.fxml", e);
    }
    @FXML private void starter(Event e){
        try {
            newStage("starter.fxml");
            Stage window = (Stage) ((Node) e.getSource()).getParent().getParent().getScene().getWindow();
        window.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private int count(HttpURLConnection conn){
        
        return 0;
    }
    
    public void sorter(int x[]){
        for(int i = 0;i < x.length;i++){
            if(x[i] == 0){
                x[i] = 0;
            }
        }
        int tmp = 0;
        for(int i = 0;i < x.length;i++){
            for(int z = 0;z < x.length - 1;z++){
                if(x[z + 1] > x[z]){
                    tmp = x[z+1];
                    x[z + 1] = x[z];
                    x[z] = tmp;
                }
            }
        }
        
        for(int i = 0;i < x.length;i++){
            System.out.println(x[i]);
        }
        
        
      
    }
    
    
    @FXML public void reult(Event e) throws MalformedURLException, IOException{
        
    String positionValues[] = {"President","Vice President","Auditor","Secretary","Treasurer","Sargeant at Arms"};
        List<Map<String, Object>> dataSource = new ArrayList<>();
        Map<String,Object> row2 = new HashMap<>();
        row2.put("election", HomeController.electionName);
        dataSource.add(row2);
        
        HttpURLConnection conn = (HttpURLConnection) new URL(FXMLDocumentController.baseURL+"result.php?id="+HomeController.electionId).openConnection();
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("accept", "application/json");
        
        int response = conn.getResponseCode();
        if(response == HttpURLConnection.HTTP_OK){
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = br.readLine()) != null){
                sb.append(line);
            }  
            
            
            try{
                   Gson gs = new Gson();
                   globalBean[] stash = gs.fromJson(sb.toString(), globalBean[].class);
                   
                   
                   int valsPres[] = new int[stash.length];
                       int valsvice[] = new int[stash.length];
                       int valsAudit[] = new int[stash.length];
                       int valsSecre[] = new int[stash.length];
                       int valsTreas[] = new int[stash.length];
                       int valsSer[] = new int[stash.length];
                       int limitVal[] = new int[stash.length];
                   for(int x = 0;x < positionValues.length;x++){
                      
                       for(int i = 0;i < stash.length;i++){
                           
                       Map<String,Object> row = new HashMap<>();
                       
                   
                       
                        if(stash[i].getPosition().equals(positionValues[x])){
                            row.put("presidentName", stash[i].getName());
                            row.put("position", stash[i].getPosition());
                            row.put("noVotes", stash[i].getNoVotes());
                            if(stash[i].getPosition().equals("President")){
                                valsPres[limitVal[0]] = Integer.parseInt(stash[i].getPersent());
                               limitVal[0] += 1;
                           }else if(stash[i].getPosition().equals("Vice President")){
                              valsvice[limitVal[1]] = Integer.parseInt(stash[i].getPersent());
                               limitVal[1] += 1;
                           }else if(stash[i].getPosition().equals("Auditor")){
                               valsAudit[limitVal[2]] = Integer.parseInt(stash[i].getPersent());
                               limitVal[2] += 1;
                           }else if(stash[i].getPosition().equals("Secretary")){
                               valsSecre[limitVal[3]] = Integer.parseInt(stash[i].getPersent());
                               limitVal[3] += 1;
                           }else if(stash[i].getPosition().equals("Treasurer")){
                               valsTreas[limitVal[4]] = Integer.parseInt(stash[i].getPersent());
                               limitVal[4] += 1;
                           }else if(stash[i].getPosition().equals("Sargeant at Arms")){
                               valsSer[limitVal[5]] = Integer.parseInt(stash[i].getPersent());
                               limitVal[5] += 1;
                           }
                            row.put("percentage", "________________");
                            
                            
                            dataSource.add(row);
                        }
                       
                       
                        }
                       
                     
                   }
                   
                   sorter(valsvice);
                     
                   
                    
            }catch(Exception x){
                System.out.println(x.getMessage());
            }

        }

        
         try {
             
             InputStream file = getClass().getResourceAsStream("/reports/result.jrxml");
             JRDataSource jrSource = new JRBeanCollectionDataSource(dataSource);
                    JasperReport jrReport = JasperCompileManager.compileReport(file);
                    JasperPrint jrPrint = JasperFillManager.fillReport(jrReport, null,jrSource);
                    JasperViewer jrView = new JasperViewer(jrPrint,false);
                    jrView.setVisible(true);
                    Stage window = (Stage) ((Node) e.getSource()).getParent().getParent().getScene().getWindow();
                    window.close();
                
            } catch (Exception z) {
                new Alert(Alert.AlertType.ERROR,z.getMessage()).showAndWait();
            } 
    }
    
    public void changer(String fxml,Event e) throws IOException{
        AnchorPane p = FXMLLoader.load(getClass().getResource(fxml));
        AdminPortalController.mPane.getChildren().clear();
        AdminPortalController.mPane.getChildren().add(p);
        Stage window = (Stage) ((Node) e.getSource()).getParent().getParent().getScene().getWindow();
        window.close();
    }
    
    @FXML public void close(Event e){
        Stage window = (Stage) ((Node) e.getSource()).getParent().getParent().getScene().getWindow();
        window.close();
    }
    
       private void newStage(String load) throws IOException{
        Stage primary = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(load));
        Scene s = new Scene(root);
        s.setFill(Color.TRANSPARENT);
        primary.setScene(s);
       
        primary.initModality(Modality.APPLICATION_MODAL);
        primary.initStyle(StageStyle.TRANSPARENT);
        primary.show();
    }
    
}
