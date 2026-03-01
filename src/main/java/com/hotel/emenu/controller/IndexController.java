package com.hotel.emenu.controller;

import com.hotel.emenu.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final CategoryRepository categoryRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("categories", categoryRepository.findByIsActiveTrueOrderByDisplayOrderAsc());
        return "index";
    }
}
