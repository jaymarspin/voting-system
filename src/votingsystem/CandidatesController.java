/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package votingsystem;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class CandidatesController implements Initializable {
    @FXML TableView<globalBean> candidateTable;
    public static TableView<globalBean> candidateTableStatic;
    @FXML HBox addHolder;
    TableColumn<globalBean,Integer> idCol = new TableColumn<>("id");
    TableColumn<globalBean,String> nameCol = new TableColumn<>("Candidates Names");
    TableColumn<globalBean,String> positionCol = new TableColumn<>("Positions");
    TableColumn<globalBean,Integer> electionCol = new TableColumn<>("elections id");
    TableColumn<globalBean,String> courseCol = new TableColumn<>("Course");
    TableColumn<globalBean,String> platformCol = new TableColumn<>("Platform");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if(HomeController.limiter.equals("Concluded") || HomeController.limiter.equals("On")){
           addHolder.getChildren().remove(0);
        }
        
        
        candidateTableStatic = candidateTable;
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        electionCol.setCellValueFactory(new PropertyValueFactory<>("electionid"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("courseCandidates"));
        platformCol.setCellValueFactory(new PropertyValueFactory<>("platform"));
        platformCol.setPrefWidth(250);
        
        candidateTable.getColumns().addAll(nameCol,courseCol,platformCol,positionCol);
        
        try {
            tableLoad();
        } catch (IOException ex) {
            Logger.getLogger(CandidatesController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
       
           @FXML public void addCandidates(Event e) throws IOException{
        
        newStage("addCandidates.fxml");
    }
           
           
            public void tableLoad() throws IOException{
                System.out.println(FXMLDocumentController.election_id);
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(FXMLDocumentController.baseURL+"geCandidates.php?id="+HomeController.electionId).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            int responseCode = conn.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine())!=null) {                    
                    sb.append(line);
                }
                Gson gson = new Gson();
                ObservableList<globalBean> apple = FXCollections.observableArrayList();
                globalBean[] type = gson.fromJson(sb.toString(), globalBean[].class);
                for(int i = 0;i < type.length;i++){
                    apple.add(new globalBean(type[i].getId(), type[i].getName(), type[i].getPosition(), type[i].getElectionid(),type[i].getCourseCandidates(),type[i].getPlatform())); 
                }
                candidateTable.setItems(apple);
            }
            
            
            
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(AddCandidatesController.class.getName()).log(Level.SEVERE, null, ex);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    
    
}
