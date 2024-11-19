package com.example.project1.service;

import com.example.project1.dto.ItemDto;
import com.example.project1.dto.UserDto;
import com.example.project1.repo.ItemRepo;
import com.example.project1.repo.TaskRepo;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepo taskRepo;
    private final HttpSession session;
    private final ItemRepo itemRepo;

    public boolean login(String id, String password) {
        Optional<UserDto> result = taskRepo.findByIdAndPassword(id, password);
        if (result.isPresent()) {
            session.setAttribute("user", result.get());
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public String signUp(UserDto dto, String confirmPassword) {
        if (taskRepo.existsById(dto.getId())) {
            return "1";
        }
        if (!dto.getPassword().equals(confirmPassword)) {
            return "2";
        }
        taskRepo.save(dto);
        return "3";
    }

    public List<ItemDto> getAllItems() {
        UserDto user = (UserDto) session.getAttribute("user");
        return user != null ? itemRepo.findByUserid(user.getId()) : List.of();
    }

    public ItemDto getItemById(Integer id) {
        ItemDto item = itemRepo.findById(id).orElse(null);
        return item;
    }

    public void deleteById(Integer id) {
        itemRepo.deleteById(id);
    }

    public void write(ItemDto itemDto) {
        UserDto dto = (UserDto) session.getAttribute("user");
        itemDto.setCreatedtime(LocalDate.now());
        itemDto.setUserid(dto.getId());
        itemRepo.save(itemDto);
    }

    @Transactional
    public void modify(ItemDto itemDto) {
        UserDto dto = (UserDto) session.getAttribute("user");

        if (itemDto.getCreatedtime() == null) {
            itemDto.setCreatedtime(LocalDate.now());
        }

        itemDto.setUpdatedtime(LocalDate.now());

        itemDto.setUserid(dto.getId());
        itemRepo.save(itemDto);
    }





}

