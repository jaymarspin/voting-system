/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package votingsystem;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.PopupControl;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;


public class VoterInsideController implements Initializable,EventHandler<ActionEvent> {
    
    @FXML VBox vertical;
    StringBuilder sb = new StringBuilder();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vertical.setAlignment(Pos.TOP_CENTER);
        try {
            getCandidates();
        } catch (IOException ex) {
            Logger.getLogger(VoterInsideController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    public void getCandidates() throws MalformedURLException, IOException{
      
        
        HttpURLConnection conn = (HttpURLConnection) new URL(FXMLDocumentController.baseURL+"getCandidatesVotes.php?id="+FXMLDocumentController.election_id).openConnection();
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        int response = conn.getResponseCode();
        if(response == HttpURLConnection.HTTP_OK){
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb =new StringBuilder();
            String line;
            while((line = br.readLine())!= null){
                sb.append(line);
            }
            System.out.println(sb.toString());
            Gson gs = new Gson();
            globalBean[] type = gs.fromJson(sb.toString(), globalBean[].class);
            String pos[] = {"President","Vice President","Auditor","Secretary","Treasurer","Sargeant at Arms"};
         
            for(int x = 0;x < pos.length;x++){
                
                HBox h = new HBox(15);
                
                h.getChildren().clear();
                
                
               for(int i = 0;i< type.length;i++){
                   
                   if(type[i].getPosition().equals(pos[x]+" ")){
                       VBox b = new VBox(5);
                       b.setPadding(new Insets(10));
                       b.setStyle("-fx-border-width: 1px;-fx-border-color:#dedede;-fx-border-style: solid;-fx-background-color: #fff");
                       Label nameLabel = new Label(type[i].getName());
                       nameLabel.setStyle("-fx-text-fill: #707070");
                       nameLabel.setFont(Font.font(18));
                       b.getChildren().add(nameLabel);
                       Label posLabel = new Label(type[i].getPosition());
                       posLabel.setVisible(false);
                       b.getChildren().add(posLabel);
                       b.getChildren().add(new Label(type[i].getCourseCandidates()));
                       b.getChildren().add(new Label(type[i].getPlatform()));
                       Label l = new Label();
                       l.setText(type[i].getId()+"");
                       l.setVisible(false);
                       b.getChildren().add(l);
                       b.setAlignment(Pos.TOP_CENTER);
                       h.getChildren().add(b);
                       
                
                   }
                   
               }
                
               if(!h.getChildren().isEmpty()){
                   
                        
                        Label labelPostion = new Label(pos[x]);
                        labelPostion.setFont(Font.font(20));
                        vertical.getChildren().add(labelPostion);
                        
                        h.setAlignment(Pos.CENTER);
                       vertical.getChildren().add(h);
                       for(int i = 0;i < h.getChildren().size();i++){
                           
                           CheckBox c = new CheckBox();
                           c.setOnAction(this);
                           
                           VBox v = (VBox) h.getChildren().get(i);
                           v.getChildren().add(c);
                       }
                       Separator s = new Separator(Orientation.HORIZONTAL);
                       s.setPrefWidth(830);
                       vertical.getChildren().add(s);
                   }
              
            }
            
            JFXButton done = new JFXButton("Done");
            FontAwesomeIconView view = new FontAwesomeIconView(FontAwesomeIcon.CHECK);
            view.setSize("35");
            done.setGraphic(view);
            view.setFill(Paint.valueOf("#f5f5f5"));
            done.setContentDisplay(ContentDisplay.TOP);
            done.setTextFill(Paint.valueOf("#f5f5f5"));
            done.setStyle("-fx-background-color: #333;");
            done.setPrefSize(100, 100);
            done.setFocusTraversable(false);
            
            vertical.getChildren().add(done);
            
            
            done.setOnAction((ActionEvent x) -> {
                
                done.setDisable(true);
                
                Node n = (Node)x.getSource();
        VBox c = (VBox)n.getParent();
        int count = 0;
                StringBuilder mbuild = new StringBuilder();
                for(int i = 0;i< c.getChildren().size();i++){
                    if(c.getChildren().get(i) instanceof HBox){
                        count += 1;
                        HBox h = (HBox)c.getChildren().get(i);
                        for(int xx = 0;xx < h.getChildren().size();xx++){
                            VBox v = (VBox)h.getChildren().get(xx);
                            CheckBox cc = (CheckBox) v.getChildren().get(5);
                            
                            if(cc.isSelected()){
                                 String nameId = ((Label) v.getChildren().get(4)).getText().trim();
                                    String position = ((Label) v.getChildren().get(1)).getText().trim();
                                    String tmp = nameId+"->";
                                    mbuild.append(tmp);
                            }
                            
                        }
                        
                        
                    }
                }
                String toSend = mbuild.substring(0,mbuild.toString().length() - 2);
                System.out.println(toSend);
        
                
                if(toSend.split("->").length != count){
                    new Alert(Alert.AlertType.ERROR,"You may wanna count your votes").show();
                    done.setDisable(false);
                }else{
                    try {
                    HttpURLConnection conn1 = (HttpURLConnection) new URL(FXMLDocumentController.baseURL+"addVotes.php?id="+FXMLDocumentController.myId+"&votes="+toSend+"&election_id="+FXMLDocumentController.election_id).openConnection();
                    System.out.println(conn1.getURL());
                    conn1.setRequestMethod("GET");
                    conn1.setDoInput(true);
                    conn1.setDoOutput(true);
                    
                    if(conn1.getResponseCode() == HttpURLConnection.HTTP_OK){
                    
                        BufferedReader breader = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
                        StringBuilder build = new StringBuilder();
                        String line2;
                        while ((line2 = breader.readLine()) != null) {                            
                            build.append(line2);
                        }
                        System.out.println(build.toString());
                       new Alert(Alert.AlertType.INFORMATION,"Succes!").show();
                       
                       
                        
                    }
                }catch (MalformedURLException ex) {
                    Logger.getLogger(VoterInsideController.class.getName()).log(Level.SEVERE, null, ex);
                }catch (IOException ex) {
                    Logger.getLogger(VoterInsideController.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                
                
                
            });
        }
        
       
        
        
        
    }
    StringBuilder builder = new StringBuilder();
    
    @Override
    public void handle(ActionEvent event) {
        Node n = (Node)event.getSource();
        VBox c = (VBox)n.getParent();
        HBox h = (HBox)n.getParent().getParent();
        
        String nameId = ((Label) c.getChildren().get(4)).getText().trim();
        String position = ((Label) c.getChildren().get(1)).getText().trim();
        String tmp = nameId+"->"+position+"@";
        int num = 0;
        for(int i = 0;i < h.getChildren().size();i++){
            VBox virtual = (VBox) h.getChildren().get(i);
           
            
                
                CheckBox b = (CheckBox) virtual.getChildren().get(5);
                 
                
                
                
                if(b.isSelected()){
                    b.setDisable(false);
                    num += 1;
                    builder.append(tmp);
                    
                }else{
                    b.setDisable(true);
                }
                
                
                
            
        }
        int lenter = 0;
        lenter += builder.toString().split("@").length;
        if(num < 1){
                    for(int z = 0;z < h.getChildren().size();z++){
                        VBox virtual = (VBox) h.getChildren().get(z);
                       CheckBox bb = (CheckBox) virtual.getChildren().get(5);
                        bb.setDisable(false);
                        
                   }
                    
                    try{
                    for(int z = 0;z < builder.toString().split("@").length;z++){
                        
                        System.out.println(lenter);
                        if(builder.toString().split("@")[z].equals(tmp.substring(0, tmp.length() - 1))){
                            builder.delete(lenter - 1, tmp.length());
                        }
                    }
                    
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                    }
        
        
        
        
        
    }
    
    
}
