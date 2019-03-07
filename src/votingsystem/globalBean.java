/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package votingsystem;


public class globalBean {
    private int id;
    private String name;
    private int year;
    private String dateS;
    private String dateE;
    private int voters;
    private String concluded;

    
    //result
    private int voteCount;
    private String persent;
    private String noVotes;
    //result
    
    //candidate bean
   private String position;
   private int electionid;
   private String courseCandidates;
   private String platform;
   //candidate bean
   
   //voters bean
   private String fname;
   private String lname;
   private String yearString;
   private String course;
   private String votersId;
   //voters bean

  

    

    public globalBean(String name, int voteCount, String persent,String noVotes) {
        this.name = name;
        this.voteCount = voteCount;
        this.persent = persent;
        this.noVotes = noVotes;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getPersent() {
        return persent;
    }

    public void setPersent(String persent) {
        this.persent = persent;
    }

    public String getNoVotes() {
        return noVotes;
    }

    public void setNoVotes(String noVotes) {
        this.noVotes = noVotes;
    }

   

    public String getCourseCandidates() {
        return courseCandidates;
    }

    public void setCourseCandidates(String courseCandidates) {
        this.courseCandidates = courseCandidates;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getVotersId() {
        return votersId;
    }

    public void setVotersId(String votersId) {
        this.votersId = votersId;
    }

    public globalBean(int id, String name, String position) {
        this.id = id;
        this.name = name;
        this.position = position;
        
    }
    
    

    public globalBean(int id, int electionid, String fname, String lname, String yearString, String course,String votersId) {
        this.id = id;
        this.electionid = electionid;
        this.fname = fname;
        this.lname = lname;
        this.yearString = yearString;
        this.course = course;
        this.votersId = votersId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getYearString() {
        return yearString;
    }

    public void setYearString(String yearString) {
        this.yearString = yearString;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
   
    public globalBean(int id, String name, String position, int electionid,String courseCandidates,String platform) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.electionid = electionid;
        this.courseCandidates = courseCandidates;
        this.platform = platform;
    }
    
    
    public String getPosition() {    
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getElectionid() {
        return electionid;
    }

    //candidate bean
    public void setElectionid(int electionid) {
        this.electionid = electionid;
    }

    public String getConcluded() {
        return concluded;
    }

    public void setConcluded(String concluded) {
        this.concluded = concluded;
    }

    public globalBean(int id, String name, int year, String dateS, String dateE, int voters,String concluded) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.dateS = dateS;
        this.dateE = dateE;
        this.voters = voters;
        this.concluded = concluded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDateS() {
        return dateS;
    }

    public void setDateS(String dateS) {
        this.dateS = dateS;
    }

    public String getDateE() {
        return dateE;
    }

    public void setDateE(String dateE) {
        this.dateE = dateE;
    }

    public int getVoters() {
        return voters;
    }

    public void setVoters(int voters) {
        this.voters = voters;
    }

  
    
    
}
