package com.backend.dream.service.imp;

import com.backend.dream.entity.Size;
import com.backend.dream.mapper.SizeMapper;
import com.backend.dream.repository.SizeRepository;
import com.backend.dream.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SizeServiceImp implements SizeService {
    private final SizeRepository sizeRepository;
    private final SizeMapper sizeMapper;

    @Autowired
    public SizeServiceImp(SizeRepository sizeRepository, SizeMapper sizeMapper){
        this.sizeRepository = sizeRepository;
        this.sizeMapper = sizeMapper;
    }
    @Override
    public List<Size> findAll() {
        return sizeRepository.findAll();
    }

    @Override
    public Size findById(Long id) {
        return sizeRepository.findById(id).get();
    }

    @Override
    public Size create(Size size) {
        return sizeRepository.save(size);
    }

    @Override
    public Size update(Size size) {
        return sizeRepository.save(size);
    }

    @Override
    public void delete(Long id) {
        sizeRepository.deleteById(id);
    }
}
