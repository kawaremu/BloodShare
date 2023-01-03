CREATE TABLE `table_users` (
    `username` VARCHAR(20) NOT NULL,
    `password` VARCHAR(45) NOT NULL,
    `is_admin` TINYINT(4) DEFAULT NULL,
    `nbre_notifs` INT(11) DEFAULT '0',
    PRIMARY KEY (`username`),
    KEY `username_idx` (`username`)
)  ENGINE=INNODB DEFAULT CHARSET=LATIN1;

CREATE TABLE `table_hopitaux` (
    `id_hopital` INT(11) NOT NULL AUTO_INCREMENT,
    `nom_hopital` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id_hopital`),
    KEY `index_hopital` (`nom_hopital`)
)  ENGINE=INNODB AUTO_INCREMENT=6 DEFAULT CHARSET=LATIN1;

CREATE TABLE `table_donneurs` (
    `username` VARCHAR(30) NOT NULL,
    `nom` VARCHAR(45) DEFAULT NULL,
    `adresse` VARCHAR(60) DEFAULT NULL,
    `email` VARCHAR(45) DEFAULT NULL,
    `date_naissance` INT(11) DEFAULT NULL,
    `genre` VARCHAR(10) DEFAULT NULL,
    `groupe_sanguin` VARCHAR(20) DEFAULT NULL,
    `remarques` TINYTEXT,
    `nombre_don` INT(11) DEFAULT '0',
    PRIMARY KEY (`username`),
    CONSTRAINT `username` FOREIGN KEY (`username`)
        REFERENCES `table_users` (`username`)
        ON DELETE CASCADE ON UPDATE NO ACTION
)  ENGINE=INNODB DEFAULT CHARSET=LATIN1;

CREATE TABLE `table_demandes` (
    `id_demande` INT(10) NOT NULL AUTO_INCREMENT,
    `cree_a` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `demande_par` VARCHAR(20) DEFAULT NULL,
    `date_demande` DATE DEFAULT NULL,
    `groupe_sanguin` VARCHAR(10) DEFAULT NULL,
    `objectifs` TINYTEXT,
    `type_sang` VARCHAR(25) DEFAULT NULL,
    PRIMARY KEY (`id_demande` , `cree_a`),
    KEY `demande_par_idx` (`demande_par`),
    CONSTRAINT `demande_par` FOREIGN KEY (`demande_par`)
        REFERENCES `table_users` (`username`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=INNODB AUTO_INCREMENT=7 DEFAULT CHARSET=LATIN1;

CREATE TABLE `table_dons` (
    `id_don` INT(11) NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(20) DEFAULT NULL,
    `nom_complet` VARCHAR(45) DEFAULT NULL,
    `groupe_sanguin` VARCHAR(10) DEFAULT NULL,
    `type_sang` VARCHAR(25) DEFAULT NULL,
    `date_collecte` DATE DEFAULT NULL,
    `medecin_responsable` VARCHAR(45) DEFAULT NULL,
    `hopital` VARCHAR(45) DEFAULT NULL,
    PRIMARY KEY (`id_don`),
    KEY `hopital_idx` (`hopital`)
)  ENGINE=INNODB AUTO_INCREMENT=29 DEFAULT CHARSET=LATIN1;

CREATE TABLE `table_transaction` (
    `id_transaction` INT(11) NOT NULL AUTO_INCREMENT,
    `delivre_par` VARCHAR(45) DEFAULT NULL,
    `delivre_a` VARCHAR(45) DEFAULT NULL,
    `date_validation` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id_transaction`)
)  ENGINE=INNODB DEFAULT CHARSET=LATIN1;

CREATE TABLE `table_activites` (
    `id_activite` INT(11) NOT NULL AUTO_INCREMENT,
    `date_activite` DATE DEFAULT NULL,
    `place_activite` VARCHAR(45)CHARACTER SET LATIN1 DEFAULT NULL,
    `type_activite` VARCHAR(20)CHARACTER SET LATIN1 DEFAULT NULL,
    `details_activites` VARCHAR(150)CHARACTER SET LATIN1 DEFAULT 'Masques svp',
    PRIMARY KEY (`id_activite`),
    KEY `place_activite_idx` (`place_activite`)
)  ENGINE=INNODB AUTO_INCREMENT=49 DEFAULT CHARSET=UTF8;

DELIMITER |
CREATE DEFINER=`hopital`@`%` TRIGGER generer_notif AFTER INSERT ON `table_activites` FOR EACH ROW
BEGIN
DECLARE last_id INT;
INSERT INTO table_notifs(type_notif) VALUES ('Activités à venir');
SELECT LAST_INSERT_ID() INTO last_id;
INSERT INTO table_infos_notif VALUES (last_id,NEW.id_activite);
END
|DELIMITER ;

CREATE TABLE `table_notifs` (
    `id_notif` INT(11) NOT NULL AUTO_INCREMENT,
    `type_notif` VARCHAR(20) COLLATE UTF8_BIN NOT NULL,
    `arrivee_a` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id_notif`)
)  ENGINE=INNODB AUTO_INCREMENT=23 DEFAULT CHARSET=UTF8 COLLATE = UTF8_BIN;

DELIMITER |
CREATE DEFINER=`hopital`@`%` TRIGGER maj_notif AFTER INSERT ON `table_notifs` FOR EACH ROW
BEGIN
-- Declaration de variables
    DECLARE finished INTEGER DEFAULT 0;
    DECLARE username_data VARCHAR(20) DEFAULT '';
	DECLARE cr_username CURSOR FOR SELECT username from table_users;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;
    
    OPEN cr_username;
    
    getUsername: 
    LOOP
		FETCH cr_username INTO username_data;
        IF finished = 1 THEN 
			LEAVE getUsername;
		END IF;
        -- Ici on insert dans la table
        INSERT INTO table_notifications_user(username,id_notif) VALUES (username_data,NEW.id_notif);
	END LOOP getUsername;
    CLOSE cr_username;
    UPDATE table_users SET nbre_notifs = nbre_notifs + 1;
END
|DELIMITER ;



CREATE TABLE `table_notifications_user` (
    `username` VARCHAR(20) NOT NULL,
    `id_notif` INT(11) NOT NULL,
    PRIMARY KEY (`username` , `id_notif`)
)  ENGINE=INNODB DEFAULT CHARSET=LATIN1;

CREATE TABLE `table_infos_notif` (
    `id_notif` INT(11) NOT NULL,
    `id_activite` INT(11) NOT NULL,
    PRIMARY KEY (`id_notif` , `id_activite`)
)  ENGINE=INNODB DEFAULT CHARSET=LATIN1;

CREATE TABLE `table_forum` (
    `id_message` INT(11) NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(20) NOT NULL,
    `contenu_message` TEXT NOT NULL,
    `envoye_a` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id_message`)
)  ENGINE=INNODB AUTO_INCREMENT=8 DEFAULT CHARSET=LATIN1;


CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `hopital`@`%` 
    SQL SECURITY DEFINER
VIEW `banque_sang`.`historique_don` AS
    SELECT 
        `banque_sang`.`table_dons`.`date_collecte` AS `date_don`,
        `banque_sang`.`table_dons`.`groupe_sanguin` AS `GS`,
        `banque_sang`.`table_dons`.`type_sang` AS `type_sang`
    FROM
        `banque_sang`.`table_dons`
    WHERE
        ((TO_DAYS(UTC_DATE()) - TO_DAYS(`banque_sang`.`table_dons`.`date_collecte`)) > 0);
CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `hopital`@`%` 
    SQL SECURITY DEFINER
VIEW `banque_sang`.`infos_notification` AS
    SELECT 
        `n`.`arrivee_a` AS `arrivee_a`,
        `n`.`type_notif` AS `type_notif`,
        `a`.`date_activite` AS `date_activite`,
        `a`.`place_activite` AS `place_activite`
    FROM
        (((`banque_sang`.`table_notifs` `n`
        JOIN `banque_sang`.`table_activites` `a`)
        JOIN `banque_sang`.`table_infos_notif` `info_activite`)
        JOIN `banque_sang`.`table_notifications_user` `nu`)
    WHERE
        ((`nu`.`id_notif` = `n`.`id_notif`)
            AND (`info_activite`.`id_notif` = `n`.`id_notif`)
            AND (`info_activite`.`id_activite` = `a`.`id_activite`));

