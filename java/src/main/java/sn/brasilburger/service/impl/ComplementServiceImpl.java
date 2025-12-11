package sn.brasilburger.service.impl;

import sn.brasilburger.entity.Complement;
import sn.brasilburger.entity.enums.TypeComplement;
import sn.brasilburger.repository.ComplementRepository;
import sn.brasilburger.service.ComplementService;
import sn.brasilburger.service.ImageStorageService;

import java.util.List;

public class ComplementServiceImpl implements ComplementService {

    private final ComplementRepository complementRepository;
    private final ImageStorageService imageStorageService;

    public ComplementServiceImpl(
            ComplementRepository complementRepository,
            ImageStorageService imageStorageService
    ) {
        this.complementRepository = complementRepository;
        this.imageStorageService = imageStorageService;
    }

    @Override
    public void ajouterComplement(String nom, double prix, TypeComplement type, String cheminImage) {

        // 🔒 VALIDATIONS
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom est obligatoire");
        }

        if (prix <= 0) {
            throw new IllegalArgumentException("Le prix doit être supérieur à 0");
        }

        // ☁️ Upload image Cloudinary
        String imageUrl = null;
        if (cheminImage != null && !cheminImage.isBlank()) {
            imageUrl = imageStorageService.uploadImage(cheminImage);
        }

        Complement complement = new Complement();
        complement.setNom(nom.trim());
        complement.setPrix(prix);
        complement.setTypeComplement(type);
        complement.setImage(imageUrl);
        complement.setActif(true);

        complementRepository.save(complement);
    }

    @Override
    public List<Complement> listerComplements() {
        return complementRepository.findAll();
    }

    @Override
    public void supprimerComplement(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID invalide");
        }
        complementRepository.deleteById(id);
    }
}
