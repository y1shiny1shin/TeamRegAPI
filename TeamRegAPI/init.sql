SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `TeamInfo` ;
CREATE TABLE TeamInfo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    Academy VARCHAR(50) NOT NULL,
    Username VARCHAR(50) NOT NULL,
    UserId VARCHAR(50) NOT NULL UNIQUE,
    Password VARCHAR(50) NOT NULL,
    team_name VARCHAR(90),
    is_captain INT(1),
    team_name2 VARCHAR(90),
    is_captain2 INT(1),
    team_name3 VARCHAR(90),
    is_captain3 INT(1)
);

DROP TABLE IF EXISTS `TeamGroup`;
CREATE TABLE TeamGroup(
    team_name VARCHAR(100) PRIMARY KEY ,
    group_type VARCHAR(100),
    group_type2 VARCHAR(100),
    group_type3 VARCHAR(100)
);

DROP TABLE IF EXISTS `Admin`;

CREATE TABLE Admin(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(90) NOT NULL UNIQUE,
    password VARCHAR(300) NOT NULL
);

INSERT INTO Admin (name ,password) VALUES ("superadmin","123456") ,("admin","123456");

DROP TABLE IF EXISTS `PmsjStu`;
CREATE TABLE PmsjStu(
    id int AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(50) NOT NULL,
    UserId VARCHAR(50) NOT NULL UNIQUE,
    ArtWorkPath VARCHAR(300)
)