package com.study.db;

import java.util.List;

record TestEntity(String id, String name, String mail) {
    public List<String> toRow() {
        return List.of(id, name, mail);
    }
}