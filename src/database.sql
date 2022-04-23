/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  lipe1
 * Created: 23/04/2022
 */

CREATE DATABASE db_pizza;

CREATE TABLE `tb_cliente` (
  `id_cliente` int(11) NOT NULL AUTO_INCREMENT,
  `nome_cliente` varchar(50) NOT NULL,
  `sobrenome_cliente` varchar(50) NOT NULL,
  `telefone_cliente` varchar(20) NOT NULL,
  `endereco_cliente` varchar(255) NOT NULL,
  PRIMARY KEY (`id_cliente`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

CREATE TABLE `tb_forma_pizza` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

CREATE TABLE `tb_idstatus` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) DEFAULT NULL,
  KEY `id` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

CREATE TABLE `tb_pedido` (
  `id_pedido` int(11) NOT NULL AUTO_INCREMENT,
  `id_status_pedido` int(11) DEFAULT NULL,
  `id_cliente` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_pedido`),
  UNIQUE KEY `id_cliente` (`id_cliente`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

CREATE TABLE `tb_pedido_pizza` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_pedido` int(11) DEFAULT NULL,
  `id_sabor` int(11) DEFAULT NULL,
  `id_forma` int(11) DEFAULT NULL,
  `id_sabor2` int(11) DEFAULT NULL,
  `valor_pedido_item` double(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_pedido` (`id_pedido`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

CREATE TABLE `tb_sabor` (
  `id_sabor` int(11) NOT NULL AUTO_INCREMENT,
  `nome_sabor` varchar(50) NOT NULL,
  `tipo_sabor` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_sabor`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1

CREATE TABLE `tb_tipopizza` (
  `id_tipo` int(11) NOT NULL,
  `nome_tipo` varchar(50) NOT NULL,
  `preco_tipo` float(10,2) DEFAULT NULL,
  PRIMARY KEY (`id_tipo`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1

INSERT INTO tb_tipopizza (id_tipo, nome_tipo, preco_tipo) VALUES ('1','Simples','0.0');
INSERT INTO tb_tipopizza (id_tipo, nome_tipo, preco_tipo) VALUES ('2','Especial','0.0');
INSERT INTO tb_tipopizza (id_tipo, nome_tipo, preco_tipo) VALUES ('3','Premium','0.0');

INSERT INTO tb_idstatus (nome) VALUES ('ABERTO');
INSERT INTO tb_idstatus (nome) VALUES ('A CAMINHO');
INSERT INTO tb_idstatus (nome) VALUES ('ENTREGUE');

INSERT INTO tb_forma_pizza (nome) VALUES ('CIRCULO');
INSERT INTO tb_forma_pizza (nome) VALUES ('QUADRADO');  
INSERT INTO tb_forma_pizza (nome) VALUES ('TRIÂNGULO');