package com.e_mail.item_post.dto;

import com.e_mail.item_post.common.Status;
import com.e_mail.item_post.entity.Departure;
import com.e_mail.item_post.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class DeparturePostEnterprise {
    public DeparturePostEnterprise(Departure departure, Post post, Date whenArrived, Status status) {
        setDepartureDto(departure);
        setPostDto(post);
        this.whenArrived = new Timestamp(whenArrived.getTime()).toLocalDateTime();
        this.status = status;
    }

    public void setDepartureDto(Departure departure) {
        this.departureDto = new DepartureDto();
        this.departureDto.setPackageType(departure.getPackageType());
        this.departureDto.setStatus(departure.getStatus());
        this.departureDto.setOwnerAddress(departure.getOwnerAddress());
        this.departureDto.setOwnerName(departure.getOwnerName());
        this.departureDto.setOwnerIndex(departure.getOwnerIndex());
    }

    public void setPostDto(Post post) {
        this.postDto = new PostDto();
        this.postDto.setOwnerAddress(post.getOwnerAddress());
        this.postDto.setName(post.getName());
        this.postDto.setIndex(post.getIndex());
    }

    private DepartureDto departureDto;

    private PostDto postDto;

    private LocalDateTime whenArrived;

    private Status status;
}
