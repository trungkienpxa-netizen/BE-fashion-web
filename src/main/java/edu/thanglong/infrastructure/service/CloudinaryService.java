package edu.thanglong.infrastructure.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    
    private final Cloudinary cloudinary;
    
    // ✅ Upload file và trả về secure_url
    public String uploadFile(MultipartFile file) throws IOException {
        try {
            System.out.println("📤 Uploading to Cloudinary...");
            System.out.println("📄 File: " + file.getOriginalFilename());
            System.out.println("📊 Size: " + file.getSize() + " bytes");
            
            // ✅ Upload với folder "vehicles"
            Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                    "folder", "vehicles",
                    "resource_type", "auto"
                )
            );
            
            // ✅ Lấy secure_url (HTTPS)
            String imageUrl = uploadResult.get("secure_url").toString();
            
            System.out.println("✅ Upload successful!");
            System.out.println("🔗 Image URL: " + imageUrl);
            
            return imageUrl;
            
        } catch (IOException e) {
            System.err.println("❌ Cloudinary upload failed: " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Failed to upload to Cloudinary: " + e.getMessage());
        }
    }
    
    // ✅ Xóa ảnh từ Cloudinary
    public void deleteImage(String imageUrl) {
        try {
            if (imageUrl == null || imageUrl.isEmpty()) {
                return;
            }
            
            // Extract public_id from URL
            String publicId = extractPublicId(imageUrl);
            
            System.out.println("🗑️ Deleting image from Cloudinary: " + publicId);
            
            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            
            System.out.println("✅ Delete result: " + result.get("result"));
            
        } catch (Exception e) {
            System.err.println("❌ Failed to delete image: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // ✅ Extract public_id from Cloudinary URL
    private String extractPublicId(String imageUrl) {
        try {
            // URL format: https://res.cloudinary.com/{cloud_name}/image/upload/v{version}/{folder}/{public_id}.{format}
            // hoặc: https://res.cloudinary.com/{cloud_name}/image/upload/{folder}/{public_id}.{format}
            
            String[] parts = imageUrl.split("/upload/");
            if (parts.length < 2) {
                throw new IllegalArgumentException("Invalid Cloudinary URL");
            }
            
            String pathAfterUpload = parts[1];
            
            // Remove version if exists (v1234567890/)
            pathAfterUpload = pathAfterUpload.replaceFirst("v\\d+/", "");
            
            // Remove file extension
            int lastDotIndex = pathAfterUpload.lastIndexOf('.');
            if (lastDotIndex > 0) {
                pathAfterUpload = pathAfterUpload.substring(0, lastDotIndex);
            }
            
            return pathAfterUpload;
            
        } catch (Exception e) {
            System.err.println("❌ Failed to extract public_id from URL: " + imageUrl);
            throw new IllegalArgumentException("Invalid Cloudinary URL: " + imageUrl);
        }
    }
}