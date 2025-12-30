FROM php:8.2-apache

# Dépendances système
RUN apt-get update && apt-get install -y \
    git unzip libpq-dev libicu-dev libzip-dev \
    && docker-php-ext-install pdo pdo_pgsql intl zip opcache \
    && a2enmod rewrite

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

WORKDIR /var/www/html

# Copier TOUT le projet d'abord
COPY . .

# Installer les dépendances Symfony
RUN COMPOSER_ALLOW_SUPERUSER=1 \
    composer install --no-dev --optimize-autoloader --no-interaction

# Apache → dossier public
RUN sed -ri 's!/var/www/html!/var/www/html/public!g' /etc/apache2/sites-available/*.conf

# Permissions Symfony
RUN mkdir -p var/cache/prod var/log \
    && chown -R www-data:www-data var

# Warmup cache prod (IMPORTANT)
RUN APP_ENV=prod APP_DEBUG=0 php bin/console cache:clear --no-debug || true

EXPOSE 80
CMD ["apache2-foreground"]
