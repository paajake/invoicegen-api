CREATE TABLE IF NOT EXISTS clients(
    id BIGINT(19) AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    email      VARCHAR(155) NOT NULL,
    phone      VARCHAR(25) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO clients (name, email, phone)
VALUES
    ('Kris-Dooley', 'kimberly04@kuhic.com', '+9017109172969'),
    ('Jast and Sons', 'dschmeler@raynor.org', '+9553389284897'),
    ('Wyman, Parisian and Bradtke', 'mschinner@blanda.com', '+6453458133345'),
    ('Grady-Ebert', 'mathilde94@goldner.org', '+9479289345662'),
    ('Lindgren PLC', 'holden.buckridge@kunde.com', '+7057856442407'),
    ('Bosco Group', 'emiliano.kutch@hagenes.com', '+8227902395304'),
    ('Green, Franecki and Cummerata', 'zula60@greenfelder.com', '+3794279939753'),
    ('Predovic, Kreiger and Carter', 'berniece.wehner@rodriguez.com', '+9304656289425'),
    ('McKenzie, Schumm and Howe', 'zhagenes@rogahn.org', '+4483641977389'),
    ('Pfannerstill, Lemke and Cormier', 'maegan88@hilpert.net', '+233244419419');