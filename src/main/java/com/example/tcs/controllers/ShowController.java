package com.example.tcs.controllers;

import com.example.tcs.models.Shows;
import com.example.tcs.services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShowController {
    @Autowired
    private ShowService showService;

    @PostMapping("/show/add")
    // ADMIN ACCESSIBLE ONLY  (IF NOT ADMIN - RETURN 403 WITH MESSAGE "YOU DON'T HAVE PERMISSION")
    public ResponseEntity<Object> postData(@RequestBody Shows show) {
        return showService.postShowData(show);
    }

    @GetMapping("/show/list")
    public ResponseEntity<Object> getData() {
        return showService.getShowData();
    }

    @GetMapping("/show/get/airing/{showTime}")   // USER ACCESSIBLE ONLY
    public ResponseEntity<Object> getShowByTiming(@PathVariable String showTime) {
        return showService.getShowsByTiming(showTime);
    }

    @GetMapping("/show/popularShow")  // USER ACCESSIBLE ONLY
    public ResponseEntity<List<Shows>> getPopularShow() {
        return showService.getPopularShow();
    }
}
