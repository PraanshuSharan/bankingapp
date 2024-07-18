-- src/main/resources/schema.sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    accounts VARCHAR(255) -- Adjust this according to your schema requirements
);

-- Create the accounts table to store account numbers and balances
CREATE TABLE user_accounts (
    user_id BIGINT NOT NULL,
    account VARCHAR(255) NOT NULL,
    balance DECIMAL(19, 2) DEFAULT 0,
    PRIMARY KEY (user_id, account),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
