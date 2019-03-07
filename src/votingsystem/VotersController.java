
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


public class VotersController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML HBox addHolder;
    @FXML TableView<globalBean> votersTable;
    public static TableView<globalBean> votersTableStatic;
    TableColumn<globalBean, Integer> idCol = new TableColumn<>("id");
    TableColumn<globalBean, String> fnameCol = new TableColumn<>("First Name");
    TableColumn<globalBean, String> lnameCol = new TableColumn<>("Last Name");
    TableColumn<globalBean, String> courseCol = new TableColumn<>("Course");
    TableColumn<globalBean, String> yearCol = new TableColumn<>("Year");
    TableColumn<globalBean, Integer> electionIdCol = new TableColumn<>("electionId");
    TableColumn<globalBean, String> votersIdCol = new TableColumn<>("Voters ID");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if(HomeController.limiter.equals("Concluded") || HomeController.limiter.equals("On")){
           addHolder.getChildren().remove(0);
        }
        votersTableStatic = votersTable;
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        fnameCol.setCellValueFactory(new PropertyValueFactory<>("fname"));
        lnameCol.setCellValueFactory(new PropertyValueFactory<>("lname"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("yearString"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("course"));
        votersIdCol.setCellValueFactory(new PropertyValueFactory<>("votersId"));
        
        try {
            tableLoad();
        } catch (IOException ex) {
            Logger.getLogger(VotersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        votersTable.getColumns().addAll(fnameCol,lnameCol,courseCol,yearCol,votersIdCol);
    }    
    
    
    @FXML public void addVoters() throws IOException{
        newStage("addVoters.fxml");
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
    
    
    public void tableLoad() throws MalformedURLException, IOException{
        
        HttpURLConnection conn = (HttpURLConnection) new URL(FXMLDocumentController.baseURL+"getVoters.php?id="+HomeController.electionId).openConnection();
        
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
       
        
        int respsonse = conn.getResponseCode();
        if(respsonse == HttpURLConnection.HTTP_OK){
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {                
                sb.append(line);
            }
            
            
            String replace = "["+sb.toString().substring(4);
           
            Gson gson = new Gson();
            try{
            globalBean[] type = gson.fromJson(replace, globalBean[].class);
            ObservableList<globalBean> apple = FXCollections.observableArrayList();
            for(int i= 0;i < type.length;i++){
                apple.add(new globalBean(type[i].getId(), type[i].getElectionid(), type[i].getFname(), type[i].getLname(), type[i].getYearString(), type[i].getCourse(), type[i].getVotersId()));
            }
            votersTable.setItems(apple);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            
        }
                
        
        
        
        
    }
    
}
