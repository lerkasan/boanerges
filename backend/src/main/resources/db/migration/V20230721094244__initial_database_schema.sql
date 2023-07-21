
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT NOT NULL,
    title VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (title)
);


CREATE TABLE IF NOT EXISTS categories (
    id BIGINT NOT NULL,
    name VARCHAR(500) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);


CREATE TABLE IF NOT EXISTS topics (
    id BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(500) NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);


CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT NOT NULL,
    username VARCHAR(25) NOT NULL,
    password_hash VARCHAR(60) NOT NULL,
    enabled BOOLEAN DEFAULT false NOT NULL,
    token VARCHAR(64) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    created_at DATETIME NOT NULL,
    email VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (username),
    UNIQUE(email)
);


CREATE TABLE IF NOT EXISTS users_roles (
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, user_id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS interviews (
    id BIGINT NOT NULL,
    created_at DATE NOT NULL,
    name VARCHAR(500) NOT NULL,
    user_id BIGINT NOT NULL,
    topic_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (topic_id) REFERENCES topics (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS questions (
    id BIGINT NOT NULL,
    text VARCHAR(5000) NOT NULL,
    audio_url VARCHAR(1500) NOT NULL,
    interview_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (interview_id) REFERENCES interviews (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS answers (
    id BIGINT NOT NULL,
    text VARCHAR(10000) NOT NULL,
    audio_url VARCHAR(1500) NOT NULL,
    question_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (question_id) REFERENCES questions (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS feedbacks (
    id BIGINT AUTO_INCREMENT NOT NULL,
    score INT NOT NULL,
    text VARCHAR(10000) NOT NULL,
    answer_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (answer_id) REFERENCES answers (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

