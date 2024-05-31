package DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import entity.Employer;
import java.util.List;



@Dao
public interface EmployerDao {
    @Insert
    void insert(Employer employer);
    @Query("SELECT * FROM Employer WHERE email = :email AND password = :password LIMIT 1")
    Employer findByEmailAndPassword(String email, String password);

    @Query("SELECT * FROM Employer")
    List<Employer> getAllEmployers();
    @Query("SELECT * FROM Employer WHERE id = :id")
    Employer getEmployerById(int id);
    @Delete
    void delete(Employer employer);

    @Query("DELETE FROM Employer")
    void deleteAllEmployers();
}
