package com.lld.order_ms.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lld.order_ms.Configurations.WebClientConfig;
import com.lld.order_ms.Dtos.ProductDTO;
import com.lld.order_ms.Dtos.RequestProductIdsDTO;
import com.lld.order_ms.Dtos.UpdateQuantityReqDTO;
import com.lld.order_ms.Dtos.UpdateQuantityResDTO;
import com.lld.order_ms.Exceptions.ProductNotFoundException;
import com.lld.order_ms.Models.Order;
import com.lld.order_ms.Models.OrderProduct;
import com.lld.order_ms.Models.OrderStatus;
import com.lld.order_ms.Repositories.OrderRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private WebClient webClient;
    private OrderProductService orderProductService;
    private OrderRepository orderRepository;
    private final String BASE_URL = "http://localhost:8080/product";

    @Autowired
    public OrderServiceImpl(WebClient webClient, OrderProductService orderProductService, OrderRepository orderRepository) {
        this.webClient = webClient;
        this.orderProductService = orderProductService;
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(long userId, List<OrderProduct> orderProductList) throws ProductNotFoundException {
        // Take productId's from orderProduct list, get all product from product MS.
        // Check available quantity, if yes make order else throw exception
        List<Long> productIds = orderProductList.stream().map(OrderProduct::getProduct_id).toList();
        List<ProductDTO> productDTOS = getAllProducts(productIds);
        if(productDTOS == null || productDTOS.size() != productIds.size()){
            throw new ProductNotFoundException("Some of products are not available");
        }
        Map<Long, ProductDTO> productDTOMap = getProductDtoMap(productDTOS);

        if(!checkAvailability(orderProductList, productDTOMap)){
            throw new ProductNotFoundException("Stock Not Available");
        }
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderStatus(OrderStatus.PLACED);
        order.setOrderedDate(new Date());
        List<OrderProduct> orderProductIds = this.orderProductService.createOrder(orderProductList);
        order.setOrderProducts(orderProductIds);
        double totalAmount = getTotalAmount(orderProductList, productDTOMap);
        order.setAmount(totalAmount);
        updateProductsQuantity(orderProductList, productDTOMap);
        return this.orderRepository.save(order);
    }
    public List<ProductDTO> getAllProducts(List<Long> productIds){
        RequestProductIdsDTO productIdsDTO = new RequestProductIdsDTO();
        productIdsDTO.setProductIds(productIds);
        List<ProductDTO> productDTOS = this.webClient.post()
                .uri(BASE_URL+"/details")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(productIdsDTO))
                .retrieve()
                .bodyToFlux(ProductDTO.class)
                .collectList()
                .block();
        return productDTOS;
    }
    public Map<Long, ProductDTO> getProductDtoMap(List<ProductDTO> productDTOS){
        Map<Long, ProductDTO> productDTOMap = new HashMap<>();
        for (ProductDTO product : productDTOS) {
            long id = product.getId();
            productDTOMap.put(id, product);
        }
        return productDTOMap;
    }
    public boolean checkAvailability(List<OrderProduct> orderProducts, Map<Long, ProductDTO> productMap){
        for (OrderProduct orderProduct : orderProducts) {
            long productId = orderProduct.getProduct_id();
            ProductDTO productDTO = productMap.get(productId);
            if(productDTO.getAvailableQuantity() < orderProduct.getQuantity()) return false;
        }
        return true;
    }
    public double getTotalAmount(List<OrderProduct> orderProducts, Map<Long, ProductDTO> productDTOMap){
        double totalAmount = 0;
        for (OrderProduct orderProduct : orderProducts) {
            long productId = orderProduct.getProduct_id();
            int quantity = orderProduct.getQuantity();
            double price = productDTOMap.get(productId).getPrice();
            totalAmount += quantity * price;
        }
        return totalAmount;
    }
    public void updateProductsQuantity(List<OrderProduct> orderProducts, Map<Long, ProductDTO> productDTOMap){
        for (OrderProduct orderProduct : orderProducts) {
            long productId = orderProduct.getProduct_id();
            int quantity = orderProduct.getQuantity();
            int availableQuantity = productDTOMap.get(productId).getAvailableQuantity();
            int updateQuantity = availableQuantity - quantity;

            UpdateQuantityReqDTO dto = new UpdateQuantityReqDTO();
            dto.setId(productId);
            dto.setQuantity(updateQuantity);
            UpdateQuantityResDTO updateQuantityResDTO = this.webClient.patch().uri(BASE_URL + "/updateQuantity")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(dto))
                    .retrieve()
                    .bodyToMono(UpdateQuantityResDTO.class)
                    .block();

            assert updateQuantityResDTO != null;
            System.out.println(updateQuantityResDTO.getMessage());
        }
    }
    public List<ProductDTO> trendingProducts(){
        return null;
    }

}

