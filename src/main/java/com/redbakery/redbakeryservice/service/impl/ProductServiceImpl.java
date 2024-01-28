package com.redbakery.redbakeryservice.service.impl;

import com.redbakery.redbakeryservice.common.WellKnownStatus;
import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.dto.request.ProductRequestDto;
import com.redbakery.redbakeryservice.dto.response.ProductResponseDto;
import com.redbakery.redbakeryservice.exception.BadRequestException;
import com.redbakery.redbakeryservice.model.Discount;
import com.redbakery.redbakeryservice.model.FoodType;
import com.redbakery.redbakeryservice.model.Product;
import com.redbakery.redbakeryservice.repository.DiscountRepository;
import com.redbakery.redbakeryservice.repository.FoodTypeRepository;
import com.redbakery.redbakeryservice.repository.ProductRepository;
import com.redbakery.redbakeryservice.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    private final FoodTypeRepository foodTypeRepository;

    private final DiscountRepository discountRepository;

    @Override
    @Transactional
    public ProductResponseDto saveProduct(AuthenticationTicketDto authTicket, ProductRequestDto request) {
        FoodType foodType = foodTypeRepository.findByFoodTypeIdAndStatusIn(request.getFoodTypeId(), List.of(WellKnownStatus.ACTIVE.getValue()));

        if (foodType == null)
            throw new BadRequestException("Food Type Not Found!");

        Discount createdDiscount = null;
        if(request.getIsDiscounted()){
            //validate discount request
            discountRequestValidation(request);

            Discount discount = new Discount();

            discount = modelMapper.map(request, Discount.class);

            if(request.getNewPrice() != null)
                discount.setDiscountPrice(request.getNewPrice() - (request.getNewPrice() * request.getDiscountPercentage() / 100));
            else
                discount.setDiscountPrice(request.getProductPrice() - (request.getProductPrice() * request.getDiscountPercentage() / 100));

            discount.setAddedBy(authTicket.getUserId());
            discount.setStatus(WellKnownStatus.ACTIVE.getValue());

            createdDiscount = discountRepository.save(discount);
        }

        Product product = modelMapper.map(request, Product.class);
        product.setFoodType(foodType);
        product.setAddedBy(authTicket.getUserId());
        product.setStatus(WellKnownStatus.ACTIVE.getValue());
        product.setDiscount(createdDiscount);
        product.setUpdatedBy(authTicket.getUserId());

        product = productRepository.save(product);

        return mapProductToProductResponseDto(product);
    }

    private void discountRequestValidation(ProductRequestDto request){
        if(request.getIsDiscounted()){
            if(request.getDiscountPercentage() == null)
                throw new BadRequestException("Discount Percentage is required!");
            if(request.getDiscountPercentage() <= 0)
                throw new BadRequestException("Discount Percentage must be greater than 0!");
            if(request.getDiscountPercentage() > 100)
                throw new BadRequestException("Discount Percentage must be less than or equal to 100!");

            if(request.getStartDate() == null)
                throw new BadRequestException("Discount Start Date is required!");

            if(request.getEndDate() == null)
                throw new BadRequestException("Discount End Date is required!");

            if(request.getStartDate().before(new Date()) || request.getEndDate().before(new Date()))
                throw new BadRequestException("Discount Start Date and Discount End Date must be in the future!");

            if(request.getStartDate().after(request.getEndDate()))
                throw new BadRequestException("Discount Start Date must be before Discount End Date!");
        }
    }

    private ProductResponseDto mapProductToProductResponseDto(Product product){
        ProductResponseDto productResponseDto = modelMapper.map(product, ProductResponseDto.class);

        if(product.getIsDiscounted()){
            productResponseDto.setDiscountPercentage(product.getDiscount().getDiscountPercentage());
            productResponseDto.setProductPrice(product.getDiscount().getDiscountPrice());
            productResponseDto.setStartDate(product.getDiscount().getStartDate());
            productResponseDto.setEndDate(product.getDiscount().getEndDate());
        }else {
            productResponseDto.setProductPrice(product.getProductPrice());
            productResponseDto.setDiscountPercentage(null);
            productResponseDto.setStartDate(null);
            productResponseDto.setEndDate(null);
        }
        return productResponseDto;
    }
}
