package com.works.dto;

import jakarta.validation.constraints.*;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.works.entity.Customer}
 */
@Value
public class CustomerRegisterDto implements Serializable {
    @NotNull
    @Size(min = 2, max = 100)
    @NotEmpty
    String name;
    @NotNull
    @Size(min = 2, max = 100)
    @NotEmpty
    String surname;
    @NotNull
    @NotEmpty
    @Pattern(
            regexp = "^(?!.*@(freefonts\\.com|gyknife\\.com)$)(?:[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+" +
                    "(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@" +
                    "(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+" +
                    "[a-zA-Z]{2,})$",
            message = "Email formatı doğru değil yada domainler freefonts.com veya gyknife.com olamaz."
    )
    private String email;
    @NotNull
    @NotEmpty
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[-=.?#]).{6,}$",
            message = "Password en 6 karakter, Büyük küçük harf, sayısal değer ve (-=.?) değerleri almalıdır."
    )
    private String password;
    @NotNull
    @Size(min = 11, max = 11)
    @NotEmpty
    private String tc;
}