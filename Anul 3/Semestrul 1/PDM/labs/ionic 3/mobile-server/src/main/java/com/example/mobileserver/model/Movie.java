package com.example.mobileserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "movie")
public class Movie {
  @Id
  private String title;
  private Integer rating;
  private boolean watched;
  private LocalDate date;
  private String username;
  @Column(name = "picture", length = 100000)
  private String picture;
  private Long lat;
  private Long lng;
}
