package com.example.tcs.services;

import com.example.tcs.dao.StationRepo;
import com.example.tcs.dao.UserRepository;
import com.example.tcs.models.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {
    @Autowired
    public StationRepo stationRepo;

    UserRepository userRepository;

    // Post Station Data and set the operatorId
    // Created - 201
    // Bad Request - 400
    public ResponseEntity<Object> postStationData(Station station) {
        try {
            Station savedStation = stationRepo.save(station);
            return new ResponseEntity<>(savedStation, HttpStatus.CREATED);
        } catch (Exception e) {
            return (ResponseEntity<Object>) ResponseEntity.badRequest();
        }
    }

    // GET Station Data
    // Ok - 200
    public ResponseEntity<Object> getData() {
        try {
            List<Station> stations = stationRepo.findAll();
            return new ResponseEntity<>(stations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Object>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Object> updateData(int station_id, Station station) {
        try{
            Station savedStation = stationRepo.findById(station_id).orElseThrow(() -> new Exception("Station Not Found"));
            savedStation.setName(station.getName());
            savedStation.setFrequency(station.getFrequency());
            savedStation.setGenre(station.getGenre());
            savedStation.setLanguage(station.getLanguage());
            savedStation.setCountry(station.getCountry());
            savedStation.setStreamingURL(station.getStreamingURL());
            savedStation.setStartTime(station.getStartTime());
            savedStation.setEndTime(station.getEndTime());
            savedStation.setOperatorId(station.getOperatorId());

            Station updatesStation = stationRepo.save(savedStation);
            return new ResponseEntity<>(updatesStation, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return (ResponseEntity<Object>) ResponseEntity.ok();
        }
    }
}
