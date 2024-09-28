use playa_market;

CREATE TABLE usuario (
	idusuario INT auto_increment NOT NULL,
	nomusuario varchar(100) NULL,
	email varchar(200) NULL,
	password varchar(300) NULL,
	nombres varchar(100) NULL,
	apellidos varchar(100) NULL,
	activo BOOL NULL,
	CONSTRAINT users_pk PRIMARY KEY (idusuario)
);	

CREATE TABLE rol (
	idrol INT auto_increment NOT NULL,
	nomrol varchar(300) NULL,
	CONSTRAINT roles_pk PRIMARY KEY (idrol)
);


CREATE TABLE usuario_rol (
	idusuario INT NOT NULL,
	idrol INT NOT NULL,
	CONSTRAINT user_role_pk PRIMARY KEY (idusuario, idrol),
	CONSTRAINT user_role_FK FOREIGN KEY (idusuario) REFERENCES usuario(idusuario),
	CONSTRAINT user_role_FK_1 FOREIGN KEY (idrol) REFERENCES rol(idrol)
);

Insert Into rol  Values(1,'Admin');
Insert Into rol  Values(2,'Employee');
Insert Into rol  Values(3,'User');

INSERT INTO usuario (nomusuario, email, password, nombres, apellidos, activo)
VALUES ('Junior', 'Junior_aybar27@hotmail.com', '$2a$12$0GMFBfN3Te5ctrgMC3i5GuZ0YcByQD5J.jS63dwMfuX1uiLltnBsO', 'Marcial', 'Aybar', 1);

INSERT INTO usuario (nomusuario, email, password, nombres, apellidos, activo)
VALUES ('employee', 'employee@hotmail.com', '$2a$12$0GMFBfN3Te5ctrgMC3i5GuZ0YcByQD5J.jS63dwMfuX1uiLltnBsO', 'Pedro', 'Suarez', 1);

INSERT INTO usuario (nomusuario, email, password, nombres, apellidos, activo)
VALUES ('user', 'user@hotmail.com', '$2a$12$0GMFBfN3Te5ctrgMC3i5GuZ0YcByQD5J.jS63dwMfuX1uiLltnBsO', 'Lucas', 'Paqueta', 1);
select * from usuario
select * from usuario_rol
Insert Into usuario_rol  Values(1,1);
Insert Into usuario_rol  Values(2,2);
Insert Into usuario_rol  Values(3,3);

ALTER TABLE usuario ADD CONSTRAINT unique_email UNIQUE (email);
ALTER TABLE usuario ADD CONSTRAINT unique_nomusuario UNIQUE (nomusuario);
DELIMITER $$

CREATE TRIGGER asignar_rol_usuario
AFTER INSERT ON usuario
FOR EACH ROW
BEGIN
   INSERT INTO usuario_rol (idusuario, idrol)
   VALUES (NEW.idusuario, 3); 
END $$

DELIMITER ;
DROP TABLE producto;
CREATE TABLE producto (
    idproducto INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    categoria TEXT NOT NULL,
    imagenurl TEXT,
    enoferta BOOLEAN DEFAULT FALSE,
    ofertaprecio DECIMAL(10, 2) NULL,
    cantidad INT NOT NULL,
    CONSTRAINT producto_pk PRIMARY KEY (idproducto)
);

INSERT INTO producto (nombre, descripcion, precio, categoria, imagenurl, enoferta, ofertaprecio, cantidad)
VALUES ('Camisa de Algodón', 'Camisa de algodón de alta calidad', 19.99, 'Camisas', 'https://i.ibb.co/DpFTZ2J/polo-blanco.webp', FALSE, NULL, 10);

INSERT INTO producto (nombre, descripcion, precio, categoria, imagenurl, enoferta, ofertaprecio, cantidad)
VALUES ('Zapatos de Cuero', 'Zapatos elegantes de cuero de todas las tallas.', 49.99, 'Zapatos', 'https://i.ibb.co/bRQZW2q/manzana.webp', TRUE, 39.99, 25);

INSERT INTO producto (nombre, descripcion, precio, categoria, imagenurl, enoferta, ofertaprecio, cantidad)
VALUES ('Zapatos de Cuero', 'Zapatos elegantes de cuero de todas las tallas.', 49.99, 'Zapatos', 'https://i.ibb.co/bRQZW2q/manzana.webp', TRUE, 39.99, 25);

INSERT INTO producto (nombre, descripcion, precio, categoria, imagenurl, enoferta, ofertaprecio, cantidad)
VALUES ('Zapatos de Cuero', 'Zapatos elegantes de cuero de todas las tallas.', 49.99, 'Zapatos', 'https://i.ibb.co/bRQZW2q/polo-blanco.webp', TRUE, 39.99, 25);

INSERT INTO producto (nombre, descripcion, precio, categoria, imagenurl, enoferta, ofertaprecio, cantidad)
VALUES ('Zapatos de Cuero', 'Zapatos elegantes de cuero de todas las tallas.', 49.99, 'Zapatos', 'https://i.ibb.co/bRQZW2q/polo-blanco.webp', TRUE, 39.99, 25);

INSERT INTO producto (nombre, descripcion, precio, categoria, imagenurl, enoferta, ofertaprecio, cantidad)
VALUES ('Gorra Deportiva', 'Gorra deportiva para actividades al aire libre.', 15.99, 'Gorras', 'https://i.ibb.co/QQFtF6w/b6608e05-c112-4c1c-ac1b-c52d7a998886-transparent-800-800.webp', FALSE, NULL, 17);

SELECT * FROM producto
