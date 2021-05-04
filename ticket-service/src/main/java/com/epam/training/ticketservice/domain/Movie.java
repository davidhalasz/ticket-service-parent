package com.epam.training.ticketservice.domain;

import lombok.Getter;

@Getter
public class Movie {

    private String title;
    private String genre;
    private int runtime;

    public Movie(String title, String genre, int runtime) {
        this.title = title;
        this.genre = genre;
        this.runtime = runtime;
    }

    @Override
    public String toString() {
        return title + "(" + genre + ", " + runtime + " minutes" + ")" + '\n';
    }
}
