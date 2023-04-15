package ru.clevertec.ecl.util;

public class QueryData {

    public static final String ORDER_CERTIFICATE = "INSERT INTO order_certificate (order_id, certificate_id) VALUES(:orderId, :certificateId)";

    public static final String CREATE_ORDER = "INSERT INTO orders (number, user_id, order_price, buy_date) VALUES (random()*1000, :userId, :orderPrice, now()) RETURNING *";

    public static final String FIND_POPULAR_TAG_FROM_ORDER = "SELECT name FROM tags WHERE id =( " +
            "SELECT tagid FROM tag_certificate tc WHERE tc.certificate_id = (" +
                "SELECT certificate_id FROM order_certificate WHERE order_id = (" +
                    "SELECT id FROM orders WHERE userid = :userId ORDER BY order_price DESC LIMIT 1))" +
            "GROUP BY tag_id having count(tag_id) > 1 " +
            "ORDER BY count(tag_id) DESC LIMIT 1 )";

    public static final String FIND_BY_TAGS = "SELECT c.id, c.name, c.description, c.price, c.duration, c.create_date, c.last_update_date FROM certificates c " +
            "JOIN tag_certificate tc ON c.id=tc.certificate_id " +
            "JOIN tags t ON tc.tag_id = t.id " +
            "WHERE t.name IN (:tag)";
}
