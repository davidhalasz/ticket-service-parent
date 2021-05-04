package com.epam.training.ticketservice.domain;

public class Movie {

    private String title;
    private String genre;
    private int runtime;

    public Movie(String title, String genre, int runtime) {
        this.title = title;
        this.genre = genre;
        this.runtime = runtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    @Override
    public String toString() {
        return title + "(" + genre + ", " + runtime + " minutes" + ")" + '\n';
    }
}
