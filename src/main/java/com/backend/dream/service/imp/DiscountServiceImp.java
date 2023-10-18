package com.backend.dream.service.imp;

import com.backend.dream.dto.DiscountDTO;
import com.backend.dream.entity.Discount;
import com.backend.dream.mapper.DiscountMapper;
import com.backend.dream.repository.DiscountRepository;
import com.backend.dream.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiscountServiceImp implements DiscountService {
    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;

    @Autowired
    public DiscountServiceImp(DiscountRepository discountRepository, DiscountMapper discountMapper) {
        this.discountRepository = discountRepository;
        this.discountMapper = discountMapper;
    }


    @Override
    public DiscountDTO createDiscount(DiscountDTO discountDTO) {
        Discount discount = discountMapper.discountDTOToDiscount(discountDTO);
        Discount createdDiscount = discountRepository.save(discount);
        return discountMapper.discountToDiscountDTO(createdDiscount);
    }

    @Override
    public void deleteDiscount(Long discountId) {
        discountRepository.deleteById(discountId);
    }

    @Override
    public DiscountDTO getDiscountByProductId(Long idProduct) {
        Optional<Discount> optionalDiscount = discountRepository.findByIDProduct(idProduct);
        return optionalDiscount.map(discountMapper::discountToDiscountDTO).orElse(null);
    }

}
