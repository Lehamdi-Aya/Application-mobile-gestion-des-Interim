package DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import entity.Offer;

@Dao
public interface OfferDao {
    @Insert
    void insert(Offer offer);

    @Update
    void update(Offer offer);

    @Query("SELECT * FROM Offer")
    List<Offer> getAllOffers();

    @Query("SELECT * FROM Offer WHERE employerId = :employerId")
    List<Offer> getOffersByEmployer(int employerId);
    @Delete
    void delete(Offer offer);

    @Query("DELETE FROM Offer")
    void deleteAllOffers();
    @Query("SELECT * FROM Offer WHERE city = :city")
    List<Offer> getOffersByCity(String city);
    @Query("SELECT * FROM Offer WHERE title LIKE :query OR description LIKE :query")
    List<Offer> searchOffers(String query);

}
