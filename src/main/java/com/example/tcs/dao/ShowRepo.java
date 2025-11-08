package com.example.tcs.dao;

import com.example.tcs.models.Shows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepo extends JpaRepository<Shows, Integer> {
    @Query("SELECT s FROM Shows s WHERE s.popularityRating=5")
    public List<Shows> popularShows();

    @Query("SELECT s FROM Shows s WHERE s.showTime=?1")
    List<Shows> getShowsByTiming(String showTime);
}
