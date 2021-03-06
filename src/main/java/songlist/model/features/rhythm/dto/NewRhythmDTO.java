package songlist.model.features.rhythm.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewRhythmDTO {

    @NotNull
    @NotBlank
    private String name;

    private String meter;

    public String getName() {
        return name;
    }

    public String getMeter() {
        return meter;
    }
}
