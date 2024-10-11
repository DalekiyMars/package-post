package com.e_mail.item_post.dto;

import com.e_mail.item_post.common.Status;
import com.e_mail.item_post.entity.Departure;
import com.e_mail.item_post.entity.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeparturePostEnterprise {
    public DeparturePostEnterprise(Departure departure, Post post, LocalDateTime whenArrived, Status status) {
        setDepartureDto(departure);
        setPostDto(post);
        this.whenArrived = whenArrived;
        this.status = status;
    }

    public void setDepartureDto(Departure departure) {
        this.departureDto.setPackageType(departure.getPackageType());
        this.departureDto.setStatus(departure.getStatus());
        this.departureDto.setOwnerAddress(departure.getOwnerAddress());
        this.departureDto.setOwnerName(departure.getOwnerName());
        this.departureDto.setOwnerIndex(departure.getOwnerIndex());
    }

    public void setPostDto(Post post) {
        this.postDto.setOwnerAddress(post.getOwnerAddress());
        this.postDto.setName(post.getName());
        this.postDto.setIndex(post.getIndex());
    }

    private DepartureDto departureDto;

    private PostDto postDto;

    private LocalDateTime whenArrived;

    private Status status;


}
