package com.codekeys.saas.services;

import com.codekeys.saas.common.PageResponse;

public interface BasicService <I, O>{
    void create(final I request);

    void update(final String id, final I request);

    PageResponse<O> findAll(final int page, final int size);

    O findById(final String id);

    void delete(final String id);
}
