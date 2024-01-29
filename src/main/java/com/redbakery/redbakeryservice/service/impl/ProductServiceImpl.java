package com.redbakery.redbakeryservice.service.impl;

import com.redbakery.redbakeryservice.common.WellKnownStatus;
import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.dto.request.ProductRequestDto;
import com.redbakery.redbakeryservice.dto.response.ProductResponseDto;
import com.redbakery.redbakeryservice.exception.BadRequestException;
import com.redbakery.redbakeryservice.model.Discount;
import com.redbakery.redbakeryservice.model.FoodType;
import com.redbakery.redbakeryservice.model.Product;
import com.redbakery.redbakeryservice.model.Role;
import com.redbakery.redbakeryservice.repository.DiscountRepository;
import com.redbakery.redbakeryservice.repository.FoodTypeRepository;
import com.redbakery.redbakeryservice.repository.ProductRepository;
import com.redbakery.redbakeryservice.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if (request.getIsDiscounted()) {
            //validate discount request
            discountRequestValidation(request);

            Discount discount = new Discount();

            discount = modelMapper.map(request, Discount.class);

            if (request.getNewPrice() != null)
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

        return mapProductToProductResponseDto(product, authTicket.getRole());
    }

    @Override
    public List<ProductResponseDto> getAllProducts(AuthenticationTicketDto authTicket) {
        String userRole = authTicket.getRole();

        List<Product> products = new ArrayList<>();

        if (userRole.equalsIgnoreCase(Role.ADMIN.name())) {
            products = productRepository.findAllByStatusIn(List.of(WellKnownStatus.ACTIVE.getValue(), WellKnownStatus.INACTIVE.getValue()));
        } else if (userRole.equalsIgnoreCase(Role.USER.name())) {
            products = productRepository.findAllByStatusIn(List.of(WellKnownStatus.ACTIVE.getValue()));
        }

        List<ProductResponseDto> productResponseDtos = new ArrayList<>();

        for (Product product : products) {
            productResponseDtos.add(mapProductToProductResponseDto(product, userRole));
        }

        return productResponseDtos;
    }

    @Override
    public ProductResponseDto getProductById(AuthenticationTicketDto authTicket, Long id) {
        return null;
    }


    private void discountRequestValidation(ProductRequestDto request) {
        if (request.getIsDiscounted()) {
            if (request.getDiscountPercentage() == null)
                throw new BadRequestException("Discount Percentage is required!");
            if (request.getDiscountPercentage() <= 0)
                throw new BadRequestException("Discount Percentage must be greater than 0!");
            if (request.getDiscountPercentage() > 100)
                throw new BadRequestException("Discount Percentage must be less than or equal to 100!");

            if (request.getStartDate() == null)
                throw new BadRequestException("Discount Start Date is required!");

            if (request.getEndDate() == null)
                throw new BadRequestException("Discount End Date is required!");

            if (request.getEndDate().before(new Date()))
                throw new BadRequestException("Discount End Date must be in the future!");

            if (request.getStartDate().after(request.getEndDate()))
                throw new BadRequestException("Discount Start Date must be before Discount End Date!");
        }
    }

    private ProductResponseDto mapProductToProductResponseDto(Product product, String role) {
        Date currentDate = new Date();
        ProductResponseDto productResponseDto = new ProductResponseDto();

        if(role.equalsIgnoreCase(Role.ADMIN.name())){
            setGeneraleProductData(product, productResponseDto);
            if (product.getIsDiscounted()) {
                setGeneralDiscountData(product, productResponseDto);
            }else{
                productResponseDto.setProductPrice(product.getProductPrice());
                productResponseDto.setDiscountPercentage(null);
                productResponseDto.setNewPrice(null);
                productResponseDto.setDiscountPrice(null);
                productResponseDto.setStartDate(null);
                productResponseDto.setEndDate(null);
            }
        }else{
            setGeneraleProductData(product, productResponseDto);
            ;
            if (product.getIsDiscounted()) {
                if (currentDate.after(product.getDiscount().getStartDate()) && currentDate.before(product.getDiscount().getEndDate())) {
                    setGeneralDiscountData(product, productResponseDto);
                } else {
                    productResponseDto.setProductPrice(product.getProductPrice());
                    productResponseDto.setDiscountPercentage(null);
                    productResponseDto.setNewPrice(null);
                    productResponseDto.setDiscountPrice(null);
                    productResponseDto.setStartDate(null);
                    productResponseDto.setEndDate(null);
                }
            } else {
                productResponseDto.setProductPrice(product.getProductPrice());
                productResponseDto.setDiscountPercentage(null);
                productResponseDto.setNewPrice(null);
                productResponseDto.setDiscountPrice(null);
                productResponseDto.setStartDate(null);
                productResponseDto.setEndDate(null);
            }
        }

        return productResponseDto;
    }

    private void setGeneralDiscountData(Product product, ProductResponseDto productResponseDto) {
        productResponseDto.setDiscountPercentage(product.getDiscount().getDiscountPercentage());
        productResponseDto.setProductPrice(product.getProductPrice());
        productResponseDto.setStartDate(product.getDiscount().getStartDate());
        productResponseDto.setEndDate(product.getDiscount().getEndDate());
        productResponseDto.setNewPrice(product.getDiscount().getNewPrice());
        productResponseDto.setDiscountPrice(product.getDiscount().getDiscountPrice());
    }

    private void setGeneraleProductData(Product product, ProductResponseDto productResponseDto) {
        productResponseDto.setProductId(product.getProductId());
        productResponseDto.setProductName(product.getProductName());
        productResponseDto.setProductDescription(product.getProductDescription());
        productResponseDto.setProductImage(product.getProductImage());
        productResponseDto.setFoodTypeId(product.getFoodType().getFoodTypeId());
        productResponseDto.setFoodTypeName(product.getFoodType().getFoodTypeName());
        productResponseDto.setStatus(product.getStatus());
        productResponseDto.setIsDiscounted(product.getIsDiscounted());
    }

}
