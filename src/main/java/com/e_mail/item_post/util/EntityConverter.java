package com.e_mail.item_post.util;

import com.e_mail.item_post.dto.DeparturePostEnterprise;
import com.e_mail.item_post.entity.DeparturePost;
import org.springframework.stereotype.Component;

@Component
public class EntityConverter {
    public DeparturePostEnterprise convertToEnterprise(DeparturePost departurePost){
        return new DeparturePostEnterprise(
                departurePost.getDeparture(),
                departurePost.getPost(),
                departurePost.getWhenArrived(),
                departurePost.getStatus());
    }
}
