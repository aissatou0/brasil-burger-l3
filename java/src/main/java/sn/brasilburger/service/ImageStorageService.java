package sn.brasilburger.service;

public interface ImageStorageService {

    /**
     * Upload une image depuis un chemin local
     * et retourne l’URL publique (ex: URL Cloudinary).
     */
    String uploadImage(String localPath);
}
