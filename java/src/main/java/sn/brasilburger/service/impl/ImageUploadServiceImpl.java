package sn.brasilburger.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import sn.brasilburger.config.CloudinaryConfig;

import java.io.File;
import java.util.Map;

public class ImageUploadServiceImpl {

    public String uploadImage(String filePath) {
        try {
            Cloudinary cloudinary = CloudinaryConfig.getCloudinary();
            File file = new File(filePath);

            if (!file.exists()) {
                System.out.println("❌ Image introuvable : " + filePath);
                return null;
            }

            Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            return uploadResult.get("secure_url").toString();

        } catch (Exception e) {
            System.out.println("❌ Erreur upload Cloudinary : " + e.getMessage());
            return null;
        }
    }
}
