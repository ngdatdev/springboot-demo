package com.nguyendat.blog.payload.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class PageResponse<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElement;
    private int totalPage;
    private boolean last;

    public PageResponse(List<T> content, int page, int size, long totalElement, int totalPage, boolean last) {
        setContent(content);
        this.page = page;
        this.size = size;
        this.totalElement = totalElement;
        this.totalPage = totalPage;
        this.last = last;
    }

    public PageResponse() {
    }

    public List<T> getContent() {
        return content == null ? null : new ArrayList<>(content);
    }

    public final void setContent(List<T> content) {
        if(content == null) {
            this.content = null;
        } else {
            this.content = Collections.unmodifiableList(content);
        }
    }
}
