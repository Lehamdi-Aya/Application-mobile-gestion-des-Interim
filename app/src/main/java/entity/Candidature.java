package entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

@Entity(
        foreignKeys = {
                @ForeignKey(entity = Offer.class, parentColumns = "id", childColumns = "offreId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId", onDelete = ForeignKey.CASCADE)
        }
)
public class Candidature {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int offreId;
    public int userId;
    public String cvUri;
    public String coverLetterUri;

    public int getOffreId() {
        return offreId;
    }

    public void setOffreId(int offreId) {
        this.offreId = offreId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Candidature(int offreId, int userId, String cvUri, String coverLetterUri) {
        this.offreId = offreId;
        this.userId = userId;
        this.cvUri = cvUri;
        this.coverLetterUri = coverLetterUri;
    }
}

