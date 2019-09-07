package pl.sda.ownApp.events;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventController {
    @GetMapping("/eventForm")
    public String showEventForm() {

        return "user/eventForm";
    }
}
