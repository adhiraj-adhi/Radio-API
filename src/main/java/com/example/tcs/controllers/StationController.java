package com.example.tcs.controllers;

import com.example.tcs.models.Station;
import com.example.tcs.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StationController {
    @Autowired
    private StationService stationService;

    @PostMapping("/station/add")
    // ADMIN ACCESSIBLE ONLY  (IF NOT ADMIN - RETURN 403 WITH MESSAGE "YOU DON'T HAVE PERMISSION")
    public ResponseEntity<Object> postData(@RequestBody Station station) {
        return stationService.postStationData(station);
    }

    @GetMapping("/station/list")
    public ResponseEntity<Object> getData() {
        return stationService.getData();
    }

    @PutMapping("/station/update/{station_id}")
    // ADMIN ACCESSIBLE ONLY (IF NOT ADMIN - RETURN 403 WITH MESSAGE "YOU DON'T HAVE PERMISSION")
    public ResponseEntity<Object> updateData(@PathVariable int station_id, @RequestBody Station station) {
        return stationService.updateData(station_id, station);
    }
}
