package com.study.db;

import java.util.List;
import java.util.Map;

public class TestData {
    public static final String ID_LABEL = "ID";
    public static final String NAME_LABEL = "NAME";
    public static final String MAIL_LABEL = "MAIL";
    public static final TestEntity JOHN_TEST_ENTITY = new TestEntity("1", "John", "john@mail.com");
    public static final TestEntity JACK_TEST_ENTITY = new TestEntity("2", "Jack", "jack@mail.com");
    public static final String SELECT_QUERY_WITH_ASTERISK = "SELECT * FROM USER";
    public static final String SELECT_QUERY_WITH_ID = "SELECT ID FROM USER";
    public static final String UPDATE_QUERY = "UPDATE USER SET NAME = 'Jonathan' WHERE MAIL = 'john@mail.com'";
    public static final String INSERT_QUERY = "INSERT INTO USER VALUES ('John', 'john@mail.com')";
    public static final String DELETE_QUERY = "DELETE FROM USER WHERE  WHERE MAIL = 'john@mail.com'";
    public static final Map<String, List<String>> EXPECTED_TABLE_ALL_COLUMNS =
            Map.of(ID_LABEL, List.of(JOHN_TEST_ENTITY.id(), JACK_TEST_ENTITY.id()),
                    NAME_LABEL, List.of(JOHN_TEST_ENTITY.name(), JACK_TEST_ENTITY.name()),
                    MAIL_LABEL, List.of(JOHN_TEST_ENTITY.mail(), JACK_TEST_ENTITY.mail()));
    public static final Map<String, List<String>> EXPECTED_TABLE_ID_COLUMN_ONLY =
            Map.of(ID_LABEL, List.of(JOHN_TEST_ENTITY.id(), JACK_TEST_ENTITY.id()));
}
