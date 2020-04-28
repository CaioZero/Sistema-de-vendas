CREATE TABLE cliente (
    id integer PRIMARY key auto_increment,
    nome VARCHAR(100)
);

CREATE TABLE produto (
    id integer PRIMARY key auto_increment,
    descricao VARCHAR(100),
    preco_unitario NUMERIC(20,2)
);

CREATE TABLE pedido (
    id integer PRIMARY key auto_increment,
    cliente_id INTEGER REFERENCES cliente(id),
    data_pedido TIMESTAMP,
    total NUMERIC (20,2)
);

CREATE TABLE item_pedido (
    id integer PRIMARY key auto_increment,
    pedido_id INTEGER REFERENCES pedido (id),
    produto_id INTEGER REFERENCES produto (id),
    quantitade INTEGER
);