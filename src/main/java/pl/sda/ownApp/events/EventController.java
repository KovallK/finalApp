package pl.sda.ownApp.events;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.ownApp.service.UserExistException;
import pl.sda.ownApp.user.RegisterForm;

import javax.validation.Valid;

@Controller
public class EventController {
    @GetMapping("/eventForm")
    public String showEventForm(Model model) { //trzeba utworzyc Model model,zebysmy mogli dodac w htmlu th:object,dzieki temu mozna tworzyc th:fieldy z obsluga błędów
        EventForm eventForm = new EventForm();
        model.addAttribute("eventForm", eventForm);
        return "user/eventForm";
    }

    @PostMapping("/eventForm")
    public String handleEventForm(@ModelAttribute @Valid EventForm eventForm,
                                  BindingResult bindingResult,
                                  Model model) throws RuntimeException {
        System.out.println(eventForm);

        if (bindingResult.hasErrors()) {
//            model.addAttribute("registerForm", registerForm);
            return "user/eventForm";
        }
        return "redirect:/thank-you-for-addingEvent";
    }
    @GetMapping("/thank-you-for-addingEvent")
    public String showAddEventThankYouPage() {
        return "user/eventFormSuccess";
    }
}