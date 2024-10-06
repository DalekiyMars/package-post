package com.e_mail.item_post.dto;

import com.e_mail.item_post.common.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeparturePostDto {
    @NotNull(message = "Departure id could not be empty")
    private int departureId;

    @NotNull(message = "Post id could not be empty")
    private int postId;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "New status could not be this type")
    private Status updatedStatus;
}
