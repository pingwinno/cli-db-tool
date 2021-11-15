package com.study.db;

import com.study.model.QueryResult;

import java.util.List;

public class TestData {
    public static final String ID_LABEL = "ID";
    public static final String NAME_LABEL = "NAME";
    public static final String MAIL_LABEL = "MAIL";
    public static final String UPDATED_NAME = "Jonathan";
    public static final List<String> COLUMN_NAMES = List.of(ID_LABEL, NAME_LABEL, MAIL_LABEL);
    public static final QueryResult TABLE_WITHOUT_ROWS = QueryResult.builder()
                                                                    .columnNames(COLUMN_NAMES)
                                                                    .build();
    public static final TestEntity JOHN_TEST_ENTITY = new TestEntity("1", "John", "john@mail.com");
    public static final QueryResult TABLE_WITH_ALL_FIELDS_AND_ONE_ROW = QueryResult.builder()
                                                                                   .columnNames(COLUMN_NAMES)
                                                                                   .rows(List.of(
                                                                                           JOHN_TEST_ENTITY.toRow()))
                                                                                   .build();
    public static final TestEntity JACK_TEST_ENTITY = new TestEntity("2", "Jack", "jack@mail.com");
    public static final QueryResult TABLE_WITH_ALL_FIELDS_AND_TWO_ROWS = QueryResult.builder()
                                                                                    .columnNames(COLUMN_NAMES)
                                                                                    .rows(List.of(
                                                                                            JOHN_TEST_ENTITY.toRow(),
                                                                                            JACK_TEST_ENTITY.toRow()))
                                                                                    .build();
    public static final TestEntity MARK_TEST_ENTITY = new TestEntity("3", "Mark", "mark@mail.com");
    public static final QueryResult TABLE_WITH_ALL_FIELDS_AND_THREE_ROWS = QueryResult.builder()
                                                                                      .columnNames(COLUMN_NAMES)
                                                                                      .rows(List.of(
                                                                                              JOHN_TEST_ENTITY.toRow(),
                                                                                              JACK_TEST_ENTITY.toRow(),
                                                                                              MARK_TEST_ENTITY.toRow()))
                                                                                      .build();
    public static final QueryResult EXPECTED_TABLE_ID_COLUMN_ONLY = QueryResult.builder()
                                                                               .columnNames(List.of(ID_LABEL))
                                                                               .rows(List.of(
                                                                                       List.of(JOHN_TEST_ENTITY.id()),
                                                                                       List.of(JACK_TEST_ENTITY.id())))
                                                                               .build();
    public static final String SELECT_QUERY_WITH_ASTERISK = "SELECT * FROM USER";
    public static final String SELECT_QUERY_WITH_ID = "SELECT ID FROM USER";
    public static final String SELECT_UPDATED_ROW_WITH_NAME_ONLY = "SELECT NAME FROM USER WHERE NAME ='Jonathan'";
    public static final String UPDATE_QUERY = "UPDATE USER SET NAME = 'Jonathan' WHERE MAIL = 'john@mail.com'";
    public static final String INSERT_QUERY = "INSERT INTO USER VALUES (3, 'Mark', 'mark@mail.com')";
    public static final String DELETE_QUERY = "DELETE FROM USER WHERE MAIL = 'john@mail.com'";

}
