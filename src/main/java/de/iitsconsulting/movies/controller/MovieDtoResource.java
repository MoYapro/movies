package de.iitsconsulting.movies.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class MovieDtoResource {

    @NotBlank(message = "Title is mandatory")
    public String title;

    @Min(value = 1900, message = "Invalid year")
    public int year;

    @NotBlank(message = "Name is mandatory")
    public String directorFirstName;

    @NotBlank(message = "Name is mandatory")
    public String directorLastName;
}
