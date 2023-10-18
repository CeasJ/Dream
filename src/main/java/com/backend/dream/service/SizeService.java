package com.backend.dream.service;



import com.backend.dream.entity.Size;

import java.util.List;

public interface SizeService {
    List<Size> findAll();
    Size findById(Long id);
    Size create(Size size);
    Size update(Size size);
    void delete(Long id);
}
