package DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import entity.Candidature;
import entity.CandidatureWithUserName;

@Dao
public interface CandidatureDao {
    @Insert
    void insert(Candidature candidature);



    @Query("SELECT EXISTS (SELECT 1 FROM Offer WHERE id = :offreId)")
    boolean isValidOffer(int offreId);

    @Query("SELECT EXISTS (SELECT 1 FROM users WHERE id = :userId)")
    boolean isValidUser(int userId);
    @Query("SELECT c.* FROM Candidature c JOIN Offer o ON c.offreId = o.id WHERE o.employerId = :employerId")
    List<Candidature> getCandidaturesByEmployerId(int employerId);
    @Query("SELECT c.*, u.nom AS userName, u.prenom AS userPrenom FROM Candidature c JOIN users u ON c.userId = u.id WHERE c.offreId = :offreId")
    List<CandidatureWithUserName> getCandidaturesWithUserNameByOfferId(int offreId);
    @Query("SELECT * FROM Candidature WHERE id = :candidatureId")
    Candidature getCandidatureById(int candidatureId);
    @Delete
    void delete(Candidature candidature);

    @Query("DELETE FROM Candidature")
    void deleteAllCandidatures();
}
