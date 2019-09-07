package pl.sda.ownApp.events;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
public class EventForm {

    @NotBlank
    private String title;
    @Size(min = 20,message = "opis nie może być krótszy niż 20 znaków")
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
