package guru.qa.niffler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import guru.qa.niffler.jupiter.annotation.User;

public record RandomTestData(
        @JsonIgnore String password
) {
}
