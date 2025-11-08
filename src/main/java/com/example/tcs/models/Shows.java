package com.example.tcs.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shows {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int showId;

    private String title;
    private String description;
    private String showTime;
    private String category;
    private String host;
    private int duration;
    private int popularityRating;
    private int station_id;
}
