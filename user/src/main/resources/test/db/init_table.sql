DROP TABLE IF EXISTS student;
CREATE TABLE IF NOT EXISTS `student`
(
    `id`         int(11)     NOT NULL AUTO_INCREMENT,
    `name`       varchar(50) NOT NULL,
    `age`        int(11)     NOT NULL,
    `gender`     varchar(10) NOT NULL,
    `class_info` varchar(50) NOT NULL,
    `created_at` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);
