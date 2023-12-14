package com.backend.dream.service.imp;

import com.backend.dream.dto.DiscountDTO;
import com.backend.dream.dto.ProductDTO;
import com.backend.dream.entity.Discount;
import com.backend.dream.entity.Product;
import com.backend.dream.mapper.DiscountMapper;
import com.backend.dream.repository.DiscountRepository;
import com.backend.dream.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

//    @Override
//    public void deleteDiscount(Long id) {
//        discountRepository.deleteById(id);
//    }

    @Override
    public List<DiscountDTO> findAll() {
        List<Discount> products = discountRepository.findAll();
        return products.stream().map(discountMapper::discountToDiscountDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DiscountDTO update(DiscountDTO discountDTO) {
        Discount discount = discountMapper.discountDTOToDiscount(discountDTO);
        Discount updatedDiscount = discountRepository.save(discount);
        return discountMapper.discountToDiscountDTO(updatedDiscount);
    }

    @Override
    public void delete(Long id) {
        discountRepository.deleteById(id);
    }

    @Override
    public DiscountDTO getDiscountByCategoryId(Long idProduct) {
        Optional<Discount> optionalDiscount = discountRepository.findByIDProduct(idProduct);
        return optionalDiscount.map(discountMapper::discountToDiscountDTO).orElse(null);
    }


    @Override
    public Double getDiscountPercentByProductId(Long idCategory) {
        DiscountDTO discountDTO = getDiscountByCategoryId(idCategory);
        if (discountDTO != null) {
            return discountDTO.getPercent();
        }
        return 0.0;
    }

    @Override
    public DiscountDTO getDiscountByID(Long id) {
        Optional<Discount> optionalDiscount = discountRepository.findById(id);
        return optionalDiscount.map(discountMapper::discountToDiscountDTO).orElse(null);
    }
}
