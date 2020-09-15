-- This script only contains the table creation statements and does not fully represent the table in database. It's still missing: indices, triggers. Do not use it as backup.

-- Table Definition
CREATE TABLE "public"."users" (
    "id" int4 NOT NULL,
    "username" varchar NOT NULL,
    "password" varchar NOT NULL,
    "first_name" varchar NOT NULL,
    "last_name" varchar NOT NULL,
    "created_at" timestamp,
    "enabled" bool NOT NULL,
    "roles" varchar NOT NULL,
    PRIMARY KEY ("id")
);

INSERT INTO "public"."users" ("id", "username", "password", "first_name", "last_name", "created_at", "enabled", "roles") VALUES
(11, 'Eva_Test', '$2a$10$Dg4KlNbW/W.sxPZNT.KRB.smedVW8cBrH0hTkRHAN4usvBxEgAVwy', 'Eva', 'Elderberry', '2020-08-22 21:12:34.856', 't', 'ROLE_USER');
INSERT INTO "public"."users" ("id", "username", "password", "first_name", "last_name", "created_at", "enabled", "roles") VALUES
(33, 'Victoria_Test', '$2a$10$eb9d8kZNTn8IlJAvoK262.Vvq19IIpAjafI6FJrvvxvvks6zOwpsu', 'Victoria', 'Vanilla', '2020-08-29 11:03:44.309', 't', 'ROLE_USER');
INSERT INTO "public"."users" ("id", "username", "password", "first_name", "last_name", "created_at", "enabled", "roles") VALUES
(19, 'William_Test', '$2a$10$.V/OEPqoTtYjN5ol81T/o.zzgCqkNmQUJHS0PETCQO3I3iNAy4Bva', 'William', 'Watermelon', '2020-08-24 23:07:08.528', 't', 'ROLE_ADMIN');
INSERT INTO "public"."users" ("id", "username", "password", "first_name", "last_name", "created_at", "enabled", "roles") VALUES
(18, 'Lilly_Test', '$2a$10$uhkJQPC3lqH/CzYze9cmze9sUFBOmMs7xt4jpQsJIDVLxYG.QEBvC', 'Lilly', 'Lemon', '2020-08-24 20:31:13.815', 't', 'ROLE_ADMIN'),
(8, 'Bob_Test', '$2a$10$vZp1sO6/D97m2NF4ejYStOtg8Ulz9nhSSfl5aw6ghskpuV6DgjOzG', 'Bob', 'Bean', '2020-08-20 19:30:11.114', 't', 'ROLE_USER'),
(22, 'Henry_Test', '$2a$10$hEvDzjEQAfbilVe9JKcEvOFy1OhfSj6bJJL2hPYNTjfjqAtsCrvu2', 'Henry', 'Honeyberry', '2020-08-25 14:40:35.251', 't', 'ROLE_USER'),
(38, 'Pamella_Test', '$2a$10$6Es/t3pbJ2S5poe8MHFlpOZ7qUEdwFwdb4t.70YcTbVDEX7I9BdqK', 'Pamella', 'Plum', '2020-09-03 18:37:08.757', 't', 'ROLE_USER'),
(12, 'Frank_Test', '$2a$10$AqBN88t81lJDaHHjuTeu7eMA6BGMZNnBLHLVgZXZQ8jRudQyRs46a', 'Frank', 'Fruit', '2020-08-22 21:30:36.387', 't', 'ROLE_USER'),
(17, 'Nancy_Test', '$2a$10$roRAF8nyeIYwBPTB/gJKheY1sMdriJ/eY9NpxniTp1RQsc0aBQemK', 'Nancy', 'Nectarine', '2020-08-24 20:25:03.246', 't', 'ROLE_USER'),
(7, 'Alice_Test', '$2y$12$fLfgFHA9ftOzpLnqPEcGAetCJaDVd6pdmE2U9BORK1Eqtl3go2qNy', 'Alice', 'Apple', '2020-08-22 21:11:10.4', 't', 'ROLE_ADMIN'),
(23, 'Ola_Test', '$2a$10$wBGf613uKl3ueihHvch/O.fO3xsKqlJudAuXWQd0aFDppl0q9O.sm', 'Ola', 'Orange', '2020-08-25 23:55:46.576', 't', 'ROLE_ADMIN'),
(10, 'David_Test', '$2a$10$ExpUjIymOjg2LhozamwO0eTY6qtYtlZTEdsLCAQnivA7DpbfudhZG', 'David', 'Dragonfruiter', '2020-08-22 21:12:23.125', 'f', 'ROLE_USER'),
(9, 'Charley_Test', '$2a$10$ogmpA1vZW3OCj/BwpZX0VOG/bMaArlHpWYgbVIPF2TOALO9c27OVi', 'Charley', 'Carrot', '2020-08-22 21:11:55.325', 't', 'ROLE_USER'),
(39, 'Elizabeth_Test', '$2a$10$lznQg6his2cjEadTqXgGeuIpeyxbKpbSvTuKfpoPchshxLIrFE47G', 'Elizabeth', 'Egg', '2020-09-13 14:49:01.737', 't', 'ROLE_ADMIN'),
(24, 'George_Test', '$2a$10$F0pMvlFjETTtVevzGZ6rbuSoqw6WvxtXnEUoqka0pv0zXsERNQVF6', 'George', 'Garlic', '2020-08-26 18:26:40.19', 'f', 'ROLE_USER'),
(21, 'Pete_Test', '$2a$10$Hyohn8Wt5KuxfiiRcYugP.g2COAP4GIUa/p1gCru2qiy4oF0UotWu', 'Pete', 'Pear', '2020-08-25 14:02:27.066', 't', 'ROLE_USER'),
(25, 'Jack_Test', '$2a$10$ocfFmqENae8oH5pdV0s5dOeiZe2um8ImzyrUf8jZ5KIlVtSx.gU.K', 'Jack', 'Jackfruit', '2020-08-29 10:53:07.774', 't', 'ROLE_ADMIN'),
(13, 'Ron_Test', '$2a$10$j5mv9EAOQOeCg09bUO/hlOk9Od1M5wxPP1h20NDElIlJwS8xOoYlm', 'Ron', 'Raspberry', '2020-08-23 10:03:02.36', 't', 'ROLE_USER'),
(28, 'Queen_Test', '$2a$10$Ct30jL5rhtye1kRlEG8fP.uQ0T14VVUOLzyszynPU531wlxbdQrsi', 'Queen', 'Quetsch', '2020-08-29 10:57:14.449', 't', 'ROLE_ADMIN'),
(29, 'Selena_Test', '$2a$10$HppaycDCtPZAa4.cbZZQvO5RP5CS47M86cua5EcadK6GQGT6Gwz2O', 'Selena', 'Strawberry', '2020-08-29 10:58:03.265', 'f', 'ROLE_USER'),
(30, 'Tom_Test', '$2a$10$FLo1hKGrtTtdKTbYAr2C1uNbThwHZ2U/i/4n1MPs8yoc5fNniDvuO', 'Tom', 'Tomato', '2020-08-29 10:58:27.823', 't', 'ROLE_ADMIN'),
(31, 'Maple_Test', '$2a$10$8XbDTk8qxCreHxiQiZGnY.p4ef5XB7upIy9M7D2te3Dclcgmqy5rq', 'Maple', 'Mango', '2020-08-29 11:00:34.233', 't', 'ROLE_USER');