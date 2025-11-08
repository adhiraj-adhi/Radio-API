package com.example.tcs.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int station_id;
    private String name;
    private String frequency;
    private String genre;
    private String language;
    private String country;
    private String streamingURL;
    private Boolean isLive;
    private String startTime;
    private String endTime;
    private int operatorId;
}
