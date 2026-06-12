-- =====================================================
-- ProductCatalog
-- SQL-представление структуры базы данных проекта
-- Эквивалент Liquibase-миграций
-- =====================================================

CREATE TABLE app_users (
id BIGSERIAL PRIMARY KEY,
email VARCHAR(255) NOT NULL UNIQUE,
password_hash VARCHAR(255) NOT NULL,
full_name VARCHAR(150) NOT NULL,
role VARCHAR(30) NOT NULL,
created_at TIMESTAMP NOT NULL
);

CREATE TABLE products (
id BIGSERIAL PRIMARY KEY,
title VARCHAR(150) NOT NULL,
description TEXT NOT NULL,
price NUMERIC(12,2) NOT NULL,
image_url VARCHAR(500) NOT NULL,
available BOOLEAN NOT NULL,
created_at TIMESTAMP NOT NULL
);

CREATE TABLE feedback_requests (
id BIGSERIAL PRIMARY KEY,
user_id BIGINT NOT NULL,
product_id BIGINT,
contact_name VARCHAR(150) NOT NULL,
contact_phone VARCHAR(30) NOT NULL,
contact_email VARCHAR(255) NOT NULL,
message_text TEXT NOT NULL,
status VARCHAR(30) NOT NULL,
error_message TEXT,
created_at TIMESTAMP NOT NULL,
processed_at TIMESTAMP,

CONSTRAINT fk_feedback_user
    FOREIGN KEY (user_id)
    REFERENCES app_users(id),

CONSTRAINT fk_feedback_product
    FOREIGN KEY (product_id)
    REFERENCES products(id)
);

CREATE TABLE email_notification_logs (
id BIGSERIAL PRIMARY KEY,
feedback_request_id BIGINT NOT NULL,
recipient_email VARCHAR(255) NOT NULL,
subject VARCHAR(255) NOT NULL,
delivery_status VARCHAR(30) NOT NULL,
error_message TEXT,
created_at TIMESTAMP NOT NULL,
CONSTRAINT fk_email_log_feedback
    FOREIGN KEY (feedback_request_id)
    REFERENCES feedback_requests(id)
);

CREATE INDEX idx_app_users_email
ON app_users(email);

CREATE INDEX idx_feedback_requests_user_id
ON feedback_requests(user_id);

CREATE INDEX idx_feedback_requests_status
ON feedback_requests(status);

CREATE INDEX idx_email_logs_feedback_request_id
ON email_notification_logs(feedback_request_id);

-- =====================================================
-- Примеры тестовых данных
-- =====================================================

INSERT INTO app_users (
email,
password_hash,
full_name,
role,
created_at
)
VALUES (
'[admin@example.com](mailto:admin@example.com)',
'$2a$10$exampleHash',
'Администратор',
'ADMIN',
CURRENT_TIMESTAMP
);

INSERT INTO products (
title,
description,
price,
image_url,
available,
created_at
)
VALUES (
'Ноутбук Lenovo',
'Ноутбук для работы и обучения',
79990.00,
'/images/lenovo.jpg',
TRUE,
CURRENT_TIMESTAMP
);
