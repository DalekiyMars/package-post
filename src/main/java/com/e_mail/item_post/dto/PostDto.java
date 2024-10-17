package com.e_mail.item_post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostDto {
    @NotBlank(message = "Index could not be empty")
    private String index;

    @NotBlank(message = "Name could not be empty")
    private String name;

    @NotBlank(message = "Owner address must be not empty")
    private String ownerAddress;
}
