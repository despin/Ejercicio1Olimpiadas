-- http server : 192.168.37.173
-- server sql:
Drop database if exists Insumosxd; 
Create Database Insumosxd;

Use Insumosxd;

Create Table Materia (
  id_Materia INT,
  nombre_Materia VARCHAR(20)
);

Create Table Practica (
  id_Practica INT,
  nombre_Practica VARCHAR(20),
  id_Practica_Materia INT
);

Create Table Insumo (
  id_Insumo INT,
  nombre_Insumo VARCHAR(20),
  cantidad_Insumo INT,
  id_Insumo_Practica INT
);

INSERT INTO Materia VALUES
(1,"Redes");

INSERT INTO Practica VALUES 
(1,"Cable de red",1),
(2,"yo q se xd",1);

INSERT INTO Insumo VALUES 
(1,"Cable UTP", 200,1),
(2,"Ficha RJ45",201,1),
(3,"Roseta RJ45",202,1),
(4,"cosa1", 200,2),
(5,"cosa2",201,2),
(6,"cosa3",202,2);

create view Datos as
select * from Materia 
inner join Practica on Practica.id_Practica_Materia = Materia.id_Materia
inner join Insumo on Insumo.id_Insumo_Practica = Practica.id_Practica;
