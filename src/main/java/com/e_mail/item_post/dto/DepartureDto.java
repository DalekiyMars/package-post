package com.e_mail.item_post.dto;

import com.e_mail.item_post.common.PackageType;
import com.e_mail.item_post.common.Status;
import com.e_mail.item_post.constants.Constants;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DepartureDto {
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Type must be not empty")
    private PackageType packageType;

    @NotBlank
    @Pattern(regexp = Constants.Patterns.INDEX_FORMAT, message = "Your index must be 6 characters")
    private String ownerIndex;

    @NotBlank(message = "Owner address should not be empty")
    private String ownerAddress;

    @NotBlank(message = "Owner name should not be empty")
    @Size(min = 2, max = 30, message = "Incorrect name format")
    private String ownerName;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status must be not empty")
    private Status status;
}
