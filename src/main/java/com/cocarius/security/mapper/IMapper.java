package com.cocarius.security.mapper;

import com.cocarius.security.payloads.ParentDTO;

/**
 * @author LuongTDT
 */
public interface IMapper<T,E extends ParentDTO > {
    E toDTO (T obj);
    T toEntity(E obj);
}
