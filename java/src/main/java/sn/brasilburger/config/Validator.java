package sn.brasilburger.config;

import java.util.regex.Pattern;

public final class Validator {

    // Téléphone sénégalais : commence par 70, 75, 76, 77, 78 ou 71 + 7 chiffres = 9 chiffres au total
    private static final Pattern PHONE_SN_PATTERN =
            Pattern.compile("^(70|71|75|76|77|78)\\d{7}$");

    // Email simple mais correct
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$");

    private Validator() {
        // Classe utilitaire, pas d'instanciation
    }

    public static boolean isNonEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isValidPhoneSn(String phone) {
        if (phone == null) return false;
        String cleaned = phone.trim().replaceAll("\\s+", "");
        return PHONE_SN_PATTERN.matcher(cleaned).matches();
    }

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static boolean isPositive(double value) {
        return value > 0;
    }

    public static boolean isPositiveOrZero(double value) {
        return value >= 0;
    }

    public static boolean isStrictlyPositiveInt(int value) {
        return value > 0;
    }
}
