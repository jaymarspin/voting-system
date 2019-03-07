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
import java.io.OutputStream;
import java.net.HttpURLConnection;
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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONException;
import org.json.JSONObject;


public class HomeController implements Initializable {

    @FXML AnchorPane home;
    public static String electionName;
    @FXML TableView<globalBean> electionTable;
    public static String limiter;
    public static int electionId;
    public static TableView<globalBean> tabler;
    TableColumn<globalBean, Integer> idCol = new TableColumn<>("ID");
    TableColumn<globalBean, String> electionNameCol = new TableColumn<>("Election Name");
    TableColumn<globalBean, String> year = new TableColumn<>("Year");
    TableColumn<globalBean, String> dateS = new TableColumn<>("Date Started");
    TableColumn<globalBean, String> dateE = new TableColumn<>("Expected End Date");
    TableColumn<globalBean, Integer> votersNo = new TableColumn<>("# of Voters");
    TableColumn<globalBean, String> statusCol = new TableColumn<>("Status");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabler = electionTable;
        electionId = -1;
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        electionNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        dateS.setCellValueFactory(new PropertyValueFactory<>("dateS"));
        dateE.setCellValueFactory(new PropertyValueFactory<>("dateE"));
        votersNo.setCellValueFactory(new PropertyValueFactory<>("voters"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("concluded"));
        
        
       electionTable.getColumns().addAll(electionNameCol,year,dateS,dateE,votersNo,statusCol);
        
       
        
        electionTable.setRowFactory(e ->{
            
            TableRow<globalBean> row = new TableRow<>();
            row.setOnMouseClicked(z -> {
                
            if(z.getClickCount() == 2){
                try {
                    electionId = row.getItem().getId();
                    electionName = row.getItem().getName();
                    limiter = row.getItem().getConcluded().split(" ")[0];
                    newStage("further.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            });
            return row;
    });
        
        try {
            getElections(FXMLDocumentController.baseURL+"getElections.php");
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public static void getElections(String link) throws IOException, JSONException{
        HttpURLConnection conn = new server().con(link);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        
        JSONObject ob = new JSONObject();
        
        
        OutputStream os = conn.getOutputStream();
        byte[] outputBytes = ob.toString().getBytes("UTF-8");
        os.write(outputBytes);
        os.flush();
       
        int response = conn.getResponseCode();
        try{
        if(response == conn.HTTP_OK){
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {        
                
                sb.append(line);
            }
            System.out.println(sb.toString());
            Gson gson = new Gson();
            globalBean[] type = gson.fromJson(sb.toString(), globalBean[].class);
            ObservableList<globalBean> stash = FXCollections.observableArrayList();
            
            for(int i = 0;i < type.length;i++){
                
                stash.add(new globalBean(type[i].getId(), type[i].getName(), type[i].getYear(), type[i].getDateS(), type[i].getDateE(), type[i].getVoters(),type[i].getConcluded()));
                
            }
            tabler.setItems(stash);
         
           
            
        }
            
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }
    
    @FXML public void addVotings(Event e) throws IOException{
        
        newStage("addVoting.fxml");
    }
    
}
