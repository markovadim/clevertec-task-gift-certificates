package ru.clevertec.ecl.util;

import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.entities.Certificate;
import ru.clevertec.ecl.entities.Order;
import ru.clevertec.ecl.entities.Tag;
import ru.clevertec.ecl.entities.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

public class MockUtil {

    public static List<Tag> tagList() {
        Tag tag1 = new Tag("tag_1");
        Tag tag2 = new Tag("tag_2");
        return List.of(tag1, tag2);
    }

    public static List<TagDto> tagDtoList() {
        return List.of(
                new TagDto(1L, "tag_dto_1"),
                new TagDto(2L, "tag_dto_2")
        );
    }

    public static List<Certificate> certificateList() {
        Certificate certificate = certificate();
        certificate.setTags(tagList());
        return List.of(certificate);
    }

    public static List<CertificateDto> certificateDtoList() {
        return List.of(certificateDto());
    }

    public static Certificate certificate() {
        return new Certificate("demo", "description", 12.23, Duration.ZERO, LocalDateTime.now(), LocalDateTime.now());
    }

    public static CertificateDto certificateDto() {
        return CertificateDto.builder()
                .name("Certificate")
                .description("unknown")
                .price(12.32)
                .duration(11)
                .createDate("11.02.2023")
                .build();
    }

    public static List<Order> orderList() {
        Order order = new Order();
        order.setCertificates(new HashSet<>(certificateList()));
        return List.of(order, new Order());
    }

    public static List<User> userList() {
        User user = new User("user_1", "milomilo@mail.ru");
        user.setOrders(new HashSet<>(orderList()));
        return List.of(
                user,
                new User("user_2", "emailaddress@mail.ru")
        );
    }

    public static List<UserDto> userDtoList() {
        return List.of(
                UserDto.builder()
                        .name("User_1")
                        .email("user1mail.ru")
                        .build(),
                UserDto.builder()
                        .name("User_2")
                        .email("user2mail.ru")
                        .build()
        );
    }
}
