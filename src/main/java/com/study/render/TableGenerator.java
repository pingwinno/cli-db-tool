package com.study.render;

import com.study.factory.Render;
import com.study.model.QueryResult;

public abstract class TableGenerator {

    private final Render render;

    public TableGenerator(Render render) {
        this.render = render;
    }

    protected Render getRender() {
        return render;

    }

    public abstract void render(QueryResult table);
}
