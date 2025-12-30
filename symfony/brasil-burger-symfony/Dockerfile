FROM php:8.4-apache

# Install system dependencies
RUN apt-get update && apt-get install -y \
    git \
    unzip \
    libpq-dev \
    libicu-dev \
    libzip-dev \
    && docker-php-ext-install pdo pdo_pgsql intl zip opcache \
    && a2enmod rewrite

# Install Composer
COPY --from=composer:latest /usr/bin/composer /usr/bin/composer

# Set working directory
WORKDIR /var/www/html

# Copy application files
COPY . .

# Create .env.local for production
RUN echo "APP_ENV=prod" > .env.local && echo "APP_DEBUG=0" >> .env.local

# Install dependencies without scripts to avoid cache:clear error
RUN COMPOSER_ALLOW_SUPERUSER=1 composer install --no-dev --optimize-autoloader --no-scripts --no-interaction

# Install JavaScript assets (importmap)
RUN APP_ENV=prod php bin/console importmap:install || true

# Configure Apache with proper rewrite rules
RUN sed -i 's!/var/www/html!/var/www/html/public!g' /etc/apache2/sites-available/000-default.conf
RUN sed -i '/<\/VirtualHost>/i \
    <Directory /var/www/html/public>\n\
        AllowOverride All\n\
        Require all granted\n\
        FallbackResource /index.php\n\
    </Directory>' /etc/apache2/sites-available/000-default.conf

# Set permissions and create directories
RUN mkdir -p /var/www/html/var/cache/prod /var/www/html/var/log
RUN chown -R www-data:www-data /var/www/html/var

# Warm up cache for production
RUN APP_ENV=prod APP_DEBUG=0 php bin/console cache:warmup --no-debug || true

EXPOSE 80

CMD ["apache2-foreground"]