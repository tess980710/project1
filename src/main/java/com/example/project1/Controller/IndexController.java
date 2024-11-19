package com.example.project1.Controller;

import com.example.project1.dto.ItemDto;
import com.example.project1.dto.UserDto;
import com.example.project1.repo.ItemRepo;
import com.example.project1.service.TaskService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {

    private final TaskService taskService;
    private final HttpSession session;

    private final ItemRepo itemRepo;

    @GetMapping
    public String index() {
        return "Index";
    }

    @GetMapping("login")
    public String getLogin() {
        return "tasks/login";
    }

    @PostMapping("login")
    public String postLogin(@Validated @ModelAttribute UserDto dto) {
        boolean success = taskService.login(dto.getId(), dto.getPassword());
        if (success) {
            if (dto.getId().equals("admin")) {
                session.setAttribute("admin", "admin");
            } else {
                session.removeAttribute("admin");
            }
            return "redirect:/";
        } else {
            return "tasks/login";
        }
    }

    @PostMapping("logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("list")
    public String itemList(Model model) {
        String admin = taskService.getAdmin();
        List<ItemDto> list;

        if (admin != null) {
            list = itemRepo.findAll();
        } else {
            UserDto user = (UserDto) session.getAttribute("user");
            list = itemRepo.findByUserid(user.getId());
        }
        model.addAttribute("admin", admin);
        model.addAttribute("list", list);
        return "tasks/list";
    }


    @GetMapping("item/detail/{id}")
    public String itemDetail(@PathVariable("id") Integer id, Model model) {
        ItemDto itemOptional = taskService.getItemById(id);
        model.addAttribute("item", itemOptional);
        return "tasks/detail";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Integer id) {
        taskService.deleteById(id);
        return "redirect:/list";
    }

    @GetMapping("/write")
    public String write() {
        return "tasks/write";
    }

    @PostMapping("/write")
    public String save(@Validated @ModelAttribute ItemDto itemDto) {
        taskService.write(itemDto);
        return "redirect:/list";
    }

    @GetMapping("/modify/{id}")
    public String modify(@PathVariable("id") Integer id, Model model) {
        ItemDto dto = taskService.getItemById(id);
        model.addAttribute("item", dto);
        return "tasks/modify";
    }

    @PostMapping("/modify")
    public String modify(@Validated @ModelAttribute ItemDto itemDto) {
        taskService.modify(itemDto);
        return "redirect:/item/detail/" + itemDto.getId();
    }
}
