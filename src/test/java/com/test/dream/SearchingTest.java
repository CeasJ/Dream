//package com.test.dream;
//
//import com.backend.dream.dto.ProductDTO;
//import com.backend.dream.entity.Product;
//import com.backend.dream.repository.ProductRepository;
//import com.backend.dream.service.ProductService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//
//public class SearchingTest {
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @InjectMocks
//    private ProductService productService;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testSearchProductByName() {
//        List<Product> mockProductList = new ArrayList<>();
//        Product product1 = new Product();
//        product1.setId(1L);
//        product1.setName("Bạc xỉu");
//        // Thêm các thuộc tính khác của sản phẩm nếu cần
//        mockProductList.add(product1);
//
//        // Tạo Pageable object
//        Pageable pageable = Pageable.unpaged();
//
//        // Giả định kết quả trả về từ repository khi tìm kiếm theo tên "Bạc xỉu"
//        Page<Product> mockProductPage = new PageImpl<>(mockProductList);
//
//        // Mock behavior của productRepository khi gọi method findByNameContainingIgnoreCase
//        when(productRepository.findByNameContainingIgnoreCase(eq("Bạc xỉu"), any(Pageable.class)))
//                .thenReturn(mockProductPage);
//
//        // Thực hiện chức năng tìm kiếm sản phẩm theo tên
//        Page<ProductDTO> result = productService.findByNamePaged("Bạc xỉu", pageable);
//
//        // Kiểm tra kết quả trả về có chứa sản phẩm "Bạc xỉu" hay không
//        List<ProductDTO> productList = result.getContent();
//        boolean productFound = productList.stream().anyMatch(productDTO -> "Bạc xỉu".equals(productDTO.getName()));
//
//        // Asserts
//        assert(productFound); // Kiểm tra sản phẩm "Bạc xỉu" có trong danh sách sản phẩm kết quả hay không
//        verify(productRepository, times(1)).findByNameContainingIgnoreCase(eq("Bạc xỉu"), any(Pageable.class)); // Kiểm tra xem repository đã được gọi với đúng tham số không
//    }
//}
