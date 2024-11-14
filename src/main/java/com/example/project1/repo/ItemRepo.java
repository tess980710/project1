package com.example.project1.repo;



import com.example.project1.dto.ItemDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ItemRepo extends JpaRepository<ItemDto, Integer> {


    List<ItemDto> findByUserid(String id);
}
