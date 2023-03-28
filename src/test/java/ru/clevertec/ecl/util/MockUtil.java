package ru.clevertec.ecl.util;

import ru.clevertec.ecl.entities.Certificate;
import ru.clevertec.ecl.entities.Tag;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MockUtil {

    public static List<Tag> getTags() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1, "tag_1"));
        tags.add(new Tag(2, "tag_2"));
        return tags;
    }

    public static List<Certificate> certificateList() {
        List<Certificate> certificateList = new ArrayList<>();
        certificateList.add(new Certificate(1, "demo", "description", 12.23, Duration.ZERO, LocalDateTime.now(), LocalDateTime.now(), getTags()));
        return certificateList;
    }
}
