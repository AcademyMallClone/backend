package com.korea.it.shopping.product.service;

import com.korea.it.shopping.product.entity.Product;
import com.korea.it.shopping.product.repo.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    // 이미지 파일이 저장될 경로 (서버 내 특정 폴더)
    private static final String IMAGE_DIR = "src/main/resources/static/images/";

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 모든 상품 조회
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 특정 상품 조회
    public Product getProductById(Integer productId) {
        return productRepository.findByProductId(productId).orElse(null);
    }

    // 상품 등록 (이미지 포함)
    public void saveProduct(String name, double price, String description, MultipartFile image) throws IOException {
        // 이미지 파일 저장
        String imageUrl = saveImage(image);

        // Product 엔티티 생성 후 데이터 저장
        Product product = new Product();
        product.setProductName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setImageUrl(imageUrl); // 저장된 이미지 경로를 설정

        // 상품을 데이터베이스에 저장
        productRepository.save(product);
    }

    // 이미지 파일을 서버에 저장하는 메서드
    private String saveImage(MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            return null; // 이미지가 없으면 null 반환
        }

        // 이미지 파일 저장 경로 설정 및 파일 저장
        byte[] bytes = image.getBytes();
        Path path = Paths.get(IMAGE_DIR + image.getOriginalFilename());
        Files.write(path, bytes);

        // 저장된 이미지 파일 경로를 반환 (웹에서 접근 가능한 경로)
        return "/images/" + image.getOriginalFilename();
    }

    // 상품 검색 메서드 추가
    public List<Product> searchProducts(String query) {
        return productRepository.findByProductNameContainingIgnoreCase(query); // 이름에 검색어가 포함된 상품 찾기
    }

}
