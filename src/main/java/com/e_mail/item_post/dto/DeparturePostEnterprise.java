package com.e_mail.item_post.dto;

import com.e_mail.item_post.common.Status;
import com.e_mail.item_post.entity.Departure;
import com.e_mail.item_post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class DeparturePostEnterprise {
    public DeparturePostEnterprise(Departure departure, Post post, Date whenArrived, Status status) {
        setDepartureDto(departure);
        setPostDto(post);
        this.whenArrived = whenArrived;
        this.status = status;
    }

    public void setDepartureDto(Departure departure) {
        this.departureDto = new DepartureDto();
        this.departureDto.setPackageType(departure.getPackageType());
        this.departureDto.setStatus(departure.getStatus());
        this.departureDto.setOwnerAddress(departure.getOwnerAddress());
        this.departureDto.setOwnerName(departure.getOwnerName());
        this.departureDto.setOwnerIndex(departure.getOwnerIndex());
        this.departureDto.setOwnerIndex(departure.getStatus().toString());
    }

    public void setPostDto(Post post) {
        this.postDto = new PostDto();
        this.postDto.setOwnerAddress(post.getOwnerAddress());
        this.postDto.setName(post.getName());
        this.postDto.setIndex(post.getIndex());
    }

    private DepartureDto departureDto;

    private PostDto postDto;

    private Date whenArrived;

    private Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeparturePostEnterprise that = (DeparturePostEnterprise) o;
        return Objects.equals(departureDto, that.departureDto) && Objects.equals(postDto, that.postDto) && Objects.equals(whenArrived, that.whenArrived) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(departureDto, postDto, whenArrived, status);
    }
}
