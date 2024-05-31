package entity;

public class CandidatureWithUserName {
    public int id; // This is the candidatureId
    public int offreId;
    public int userId;
    public String cvUri;
    public String coverLetterUri;
    public String userName;
    public String userPrenom;

    public String getUserPrenom() {
        return userPrenom;
    }

    public void setUserPrenom(String userPrenom) {
        this.userPrenom = userPrenom;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCvUri() {
        return cvUri;
    }

    public void setCvUri(String cvUri) {
        this.cvUri = cvUri;
    }

    public String getCoverLetterUri() {
        return coverLetterUri;
    }

    public void setCoverLetterUri(String coverLetterUri) {
        this.coverLetterUri = coverLetterUri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOffreId() {
        return offreId;
    }

    public void setOffreId(int offreId) {
        this.offreId = offreId;
    }
}
