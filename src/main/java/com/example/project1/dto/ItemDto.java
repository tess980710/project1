package com.example.project1.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "items")
public class ItemDto {
    @Id
    private Integer id;
    private String name;
    private String description;

    private LocalDate createdtime;
    private LocalDate updatedtime;

    private String userid;
}
