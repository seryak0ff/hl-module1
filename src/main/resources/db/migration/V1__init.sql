-- Создание таблицы пользователей
CREATE TABLE IF NOT EXISTS t_user (
    id UUID PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE,
    university VARCHAR(255) NOT NULL,
    subscription_end_date DATE NOT NULL
);

-- Создание таблицы статей
CREATE TABLE IF NOT EXISTS t_article (
    id UUID PRIMARY KEY,
    doi VARCHAR(255) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publication_year INTEGER NOT NULL
);

-- Создание таблицы загрузок
CREATE TABLE IF NOT EXISTS t_download (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES t_user(id),
    article_id UUID NOT NULL REFERENCES t_article(id),
    download_date TIMESTAMP NOT NULL,
    format VARCHAR(10) NOT NULL
); 