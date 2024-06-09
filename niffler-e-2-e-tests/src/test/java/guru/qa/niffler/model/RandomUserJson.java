package guru.qa.niffler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javafaker.Faker;
import guru.qa.niffler.jupiter.annotation.User;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RandomUserJson(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("username")
        String username,
        @JsonProperty("firstname")
        String firstname,
        @JsonProperty("surname")
        String surname,
        @JsonProperty("currency")
        CurrencyValues currency,
        @JsonProperty("photo")
        String photo,
        @JsonProperty("photoSmall")
        String photoSmall,
        @JsonProperty("friendState")
        FriendState friendState,
        @JsonIgnore
        RandomTestData testData
) {

    public static RandomUserJson randomUser() {
        Faker faker = new Faker();
        return new RandomUserJson(
                null,
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                CurrencyValues.RUB,
                null,
                null,
                null,
                new RandomTestData(
                        faker.internet().password()
                )
        );
    }
}
