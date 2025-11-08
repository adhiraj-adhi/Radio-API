package com.example.tcs.services;

import com.example.tcs.dao.ShowRepo;
import com.example.tcs.dao.UserRepository;
import com.example.tcs.models.Shows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {
    @Autowired
    private ShowRepo showRepo;
    @Autowired
    UserRepository userRepository;

    // POST Show Data
    // Created - 201
    // Bad Request - 400
    public ResponseEntity<Object> postShowData(Shows show) {
        try{
            Shows savedShow = showRepo.save(show);
            return new ResponseEntity<>(savedShow, HttpStatus.CREATED);
        } catch (Exception e) {
            return (ResponseEntity<Object>) ResponseEntity.badRequest();
        }
    }

    // GET Show Data
    // Ok - 200
    public ResponseEntity<Object> getShowData() {
        try {
            List<Shows> shows = showRepo.findAll();
            return new ResponseEntity<>(shows, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<Object> getShowsByTiming(String showTime) {
        try {
            List<Shows> showsByTiming = showRepo.getShowsByTiming(showTime);
            return new ResponseEntity<>(showsByTiming, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    // GET Most Popular Show
    public ResponseEntity<List<Shows>> getPopularShow() {
        try{
            List<Shows> popularShows = showRepo.popularShows();
            return new ResponseEntity<>(popularShows, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }
}
