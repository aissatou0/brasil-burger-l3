FROM php:8.2-apache

# Dépendances système
RUN apt-get update && apt-get install -y \
    git unzip libpq-dev libicu-dev libzip-dev \
    && docker-php-ext-install pdo pdo_pgsql intl zip opcache \
    && a2enmod rewrite

# Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

WORKDIR /var/www/html

# Copier SEULEMENT composer files
COPY composer.json composer.lock ./

# Installer les dépendances (sans scripts)
RUN COMPOSER_ALLOW_SUPERUSER=1 \
    composer install --no-dev --optimize-autoloader --no-scripts --no-interaction

# Copier le reste du projet
COPY . .

# Apache public/
RUN sed -ri 's!/var/www/html!/var/www/html/public!g' /etc/apache2/sites-available/*.conf

# Permissions
RUN mkdir -p var/cache var/log \
    && chown -R www-data:www-data var

EXPOSE 80
CMD ["apache2-foreground"]
