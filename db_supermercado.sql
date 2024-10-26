use db_supermercado_perla;

CREATE TABLE Metodo_de_pago (
    id_metodo_de_pago int PRIMARY KEY AUTO_INCREMENT,
    categoria_de_pago VARCHAR(50)
);

INSERT INTO Metodo_de_pago (categoria_de_pago) VALUES ('Tarjeta de credito');
INSERT INTO Metodo_de_pago (categoria_de_pago) VALUES ('Efectivo');

CREATE TABLE Cliente (
    id_cliente int PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    direccion VARCHAR(255),
    telefono VARCHAR(20),
    correo_electronico VARCHAR(100)
);

CREATE TABLE Rol (
    id_rol int PRIMARY KEY AUTO_INCREMENT,
    rol_del_personal VARCHAR(50)
);

INSERT INTO Rol (rol_del_personal) VALUES ('vendedor');
INSERT INTO Rol (rol_del_personal) VALUES ('Supervisor');

CREATE TABLE Usuario (
    id_usuario int PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    id_rol int NOT NULL,
    contraseña VARCHAR(255)
);
ALTER TABLE Usuario ADD FOREIGN KEY (id_rol) REFERENCES Rol (id_rol);



CREATE TABLE Categoria (
    id_categoria int PRIMARY KEY AUTO_INCREMENT,
    nombre_categoria VARCHAR(100)
);

CREATE TABLE Producto(
    id_producto int PRIMARY KEY AUTO_INCREMENT,
    codigo VARCHAR(50),
    codigo_de_barras VARCHAR(50),
    nombre VARCHAR(100),
    descripcion TEXT,
    id_categoria int NOT NULL,
    imagen VARCHAR(255),
    precio_normal DECIMAL(10, 2),
    precio_promocion DECIMAL(10, 2)
);

ALTER TABLE Producto ADD FOREIGN KEY (id_categoria) REFERENCES Categoria (id_categoria);


CREATE TABLE Inventario (
    id_inventario int PRIMARY KEY AUTO_INCREMENT,
    id_producto int NOT NULL,
    cantidad INT DEFAULT 0,
    fecha_modificacion DATE,
    movimiento_inventario VARCHAR(255)
);

ALTER TABLE Inventario ADD FOREIGN KEY (id_producto) REFERENCES Producto (id_producto);

CREATE TABLE Venta (
    id_venta int PRIMARY KEY AUTO_INCREMENT,
    id_cliente int NOT NULL,
    id_usuario int NOT NULL,
    fecha_hora DATETIME,
    id_metodo_de_pago int NOT NULL
);

ALTER TABLE Venta ADD FOREIGN KEY (id_cliente) REFERENCES Cliente (id_cliente);
ALTER TABLE Venta ADD FOREIGN KEY (id_usuario) REFERENCES Usuario (id_usuario);
ALTER TABLE venta ADD FOREIGN KEY (id_metodo_de_pago) REFERENCES Metodo_de_pago (id_metodo_de_pago);


CREATE TABLE Detalle (
    id_detalle int PRIMARY KEY AUTO_INCREMENT,
    id_venta int NOT NULL,
    id_producto int NOT NULL,
    cantidad INT
);

ALTER TABLE Detalle ADD FOREIGN KEY (id_venta) REFERENCES Venta (id_venta);
ALTER TABLE Detalle ADD FOREIGN KEY (id_producto) REFERENCES Producto (id_producto);

INSERT INTO Categoria (nombre_categoria) VALUES ('Lácteos');
ALTER TABLE venta DROP FOREIGN KEY venta_ibfk_1;

select * from Producto;
select * from inventario;
select * from Cliente;
select * from Rol;
select * from Usuario; 
select * from Metodo_de_pago;
select * from Venta;