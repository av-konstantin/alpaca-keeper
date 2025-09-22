package dev.khie.alpaca.web;

import dev.khie.alpaca.service.AlpacaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;

@Controller
public class HomeController {

    private final AlpacaService alpacaService;

    public HomeController(AlpacaService alpacaService) {
        this.alpacaService = alpacaService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("alpacas", alpacaService.listAll());
        model.addAttribute("now", Instant.now());
        return "index";
    }

    @PostMapping("/alpaca")
    public String addAlpaca(@RequestParam("name") String name) {
        if (name != null && !name.isBlank()) {
            alpacaService.add(name.trim());
        }
        return "redirect:/";
    }
}
