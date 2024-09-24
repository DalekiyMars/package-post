package com.e_mail.item_post.dto;

import com.e_mail.item_post.common.PackageType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class DepartureDto {
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Type must be not empty")
    private PackageType packageType;

    @NotBlank
    @Size(min = 6, max = 6, message = "Your index must be 6 characters")
    private String ownerIndex;

    @NotBlank(message = "Address should not be empty")
    //@Pattern(regexp = Constants.Patterns.ADDRESS_FORMAT)
    private String ownerAddress;

    @NotBlank(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Incorrect name format")
    private String ownerName;
}
