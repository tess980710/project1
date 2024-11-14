package com.example.project1.Controller;

import com.example.project1.dto.ItemDto;
import com.example.project1.dto.UserDto;
import com.example.project1.service.TaskService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {

    private final TaskService taskService;
    private final HttpSession session;
    @GetMapping
    public String index(Model model) {
        return "Index";
    }

    @GetMapping("login")
    public String getLogin() {
        return "tasks/login";
    }
    @PostMapping("login")
    public String postLogin(@Validated UserDto dto){
       boolean A = taskService.login(dto.getId(),dto.getPassword());

       if(A == true){
           return "redirect:/";
       }else{
           return "tasks/login";
       }
    }

    @PostMapping("logout")
    public String Logout(){
        session.invalidate(); //세션 정보 지우기
        return"redirect:/";
    }
    @GetMapping("list")
    public String ItemList(Model model)
    {
        List<ItemDto> list = taskService.getAllItems();
                model.addAttribute("list", list);
        return"tasks/list";

    }
}
