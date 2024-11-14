package com.example.project1.service;

import com.example.project1.dto.ItemDto;
import com.example.project1.dto.UserDto;
import com.example.project1.repo.ItemRepo;
import com.example.project1.repo.TaskRepo;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {


    private final TaskRepo taskRepo;

    private  final HttpSession session;
    private final ItemRepo itemRepo;
    public boolean login(String id, String password) {

     Optional<UserDto> result = taskRepo.findByIdAndPassword(id, password);
        boolean loginS;
     if(result.isEmpty()){
        loginS = false;
     }else{
         session.setAttribute("user",result.get());
         loginS=true;
         session.getId();
     }
     return loginS;
    }
    @Transactional
    public String SignUp(UserDto dto, String confirmPassword){
        if(taskRepo.existsById(dto.getId())){
            return "1";
        }
        if (!dto.getPassword().equals(confirmPassword)){
            return "2";
        }
//        UserDto newUser = new UserDto();
//        newUser.setId(dto.getId());
//        newUser.setPassword(dto.getPassword());
//        newUser.setUsername(dto.getUsername());
        taskRepo.save(dto);
        return "3";
    }

    public List<ItemDto> getAllItems() {

        UserDto user = (UserDto) session.getAttribute("user");

        if (user != null) {

            return itemRepo.findByUserid(user.getId());
        } else {
            return List.of();
        } 
    }

}

