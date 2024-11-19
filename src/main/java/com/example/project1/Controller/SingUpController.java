package com.example.project1.Controller;

import com.example.project1.dto.UserDto;
import com.example.project1.repo.TaskRepo;
import com.example.project1.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SingUpController {

    private final TaskService taskService;

    @GetMapping
    public String getSignUp() {
        return "tasks/signup";
    }

    @PostMapping
    public String postSignUp(@ModelAttribute UserDto dto,
                             @RequestParam("confirmPassword") String confirmPassword,
                             Model model) {
        String msg = taskService.signUp(dto, confirmPassword);

        if (msg.equals("1")) {
            model.addAttribute("message", "아이디가 중복됩니다.");
            return "tasks/signup";
        } else if (msg.equals("2")) {
            model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
            return "tasks/signup";
        } else {
            model.addAttribute("message", "회원가입 성공!");
            return "tasks/login";
        }
    }
}
