package sn.brasilburger.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import sn.brasilburger.config.CloudinaryConfig;
import sn.brasilburger.service.ImageStorageService;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CloudinaryImageStorageService implements ImageStorageService {

    private final Cloudinary cloudinary;

    public CloudinaryImageStorageService() {
        this.cloudinary = CloudinaryConfig.getCloudinary();
    }

    @Override
    public String uploadImage(String localPath) {
        if (localPath == null || localPath.isBlank()) {
            throw new IllegalArgumentException("Le chemin de l'image ne peut pas être vide.");
        }

        File file = new File(localPath);
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("Fichier image introuvable : " + localPath);
        }

        try {
            Map<?, ?> result = cloudinary.uploader()
                    .upload(file, ObjectUtils.emptyMap());

            Object url = result.get("secure_url");
            if (url == null) {
                throw new RuntimeException("Upload Cloudinary sans URL retournée.");
            }
            return url.toString();
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'upload vers Cloudinary : " + e.getMessage(), e);
        }
    }
}
