package com.e_mail.item_post.util;

import com.e_mail.item_post.dto.DeparturePostEnterprise;
import com.e_mail.item_post.entity.DeparturePost;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class EntityConverterTest {

    @Test
    void convertToEnterprise() throws IOException {
        DeparturePost a = JsonUtils.convertJsonFromFileToObject("src/test/resources/entities/DeparturePost.json", DeparturePost.class);
        DeparturePostEnterprise expectedEntity = JsonUtils.convertJsonFromFileToObject("src/test/resources/entities/DeparturePostEnterprice.json", DeparturePostEnterprise.class);

        var entity = new EntityConverter().convertToEnterprise(a);

        assertThat(JsonUtils.convertJsonFromObjectToString(entity)).isEqualTo(JsonUtils.convertJsonFromObjectToString(expectedEntity));
    }
}