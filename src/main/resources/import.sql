-- Create the User table
CREATE TABLE app_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL
);

-- Create the Session table
CREATE TABLE Session (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL
);

-- Create the Restaurant table
CREATE TABLE Restaurant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    session_id BIGINT NOT NULL,
    submitted_by BIGINT NOT NULL,
    CONSTRAINT fk_session FOREIGN KEY (session_id) REFERENCES Session(id),
    CONSTRAINT fk_user FOREIGN KEY (submitted_by) REFERENCES User(id)
);

-- Create the join table for the many-to-many relationship between User and Session
CREATE TABLE session_participants (
    session_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (session_id, user_id),
    CONSTRAINT fk_session_participants_session FOREIGN KEY (session_id) REFERENCES Session(id),
    CONSTRAINT fk_session_participants_user FOREIGN KEY (user_id) REFERENCES User(id)
);
