package com.rnd;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movie")
public class Movie extends PanacheEntity {

    @Column(name = "title")
    public String title;
    @Column(name = "description")
    public String description;
    @Column(name = "director")
    public String director;
    @Column(name = "country")
    public String country;
}
