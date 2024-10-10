package com.pascal.ecommerce.backend.service.image;

import com.pascal.ecommerce.backend.dto.ImageDto;
import com.pascal.ecommerce.backend.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(Long productId, List<MultipartFile> files);
    void updateImage(MultipartFile file,  Long imageId);
}
