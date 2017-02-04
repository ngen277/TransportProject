package transportproject.transportwebsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainPageController {

    /**
     * index page controller method
     *
     * @return index page template
     */
    @GetMapping("/")
    public String mainPage() {
        return "index";
    }
}