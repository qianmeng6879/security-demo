/*
 Navicat Premium Data Transfer

 Source Server         : localhost_pgsql_5432
 Source Server Type    : PostgreSQL
 Source Server Version : 150007
 Source Host           : localhost:5432
 Source Catalog        : security_db
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 150007
 File Encoding         : 65001

 Date: 20/08/2024 11:35:00
*/


-- ----------------------------
-- Sequence structure for user_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."user_id_seq";
CREATE SEQUENCE "public"."user_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Table structure for t_member
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_member";
CREATE TABLE "public"."t_member" (
  "id" int8 NOT NULL,
  "username" varchar(12) COLLATE "pg_catalog"."default",
  "password" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "deleted" int2,
  "nickname" varchar(255) COLLATE "pg_catalog"."default",
  "avatar" varchar(255) COLLATE "pg_catalog"."default",
  "last_login_time" timestamp(6),
  "is_superuser" int2 NOT NULL DEFAULT 0,
  "phone" varchar(255) COLLATE "pg_catalog"."default",
  "email" varchar(255) COLLATE "pg_catalog"."default",
  "enable2fa" int2 DEFAULT 0,
  "locked" int2 DEFAULT 0
)
;

-- ----------------------------
-- Records of t_member
-- ----------------------------
INSERT INTO "public"."t_member" VALUES (1818563452052160514, 'zero', '$2a$10$pC37xr2.RMfe1ZgKbAAZoOoedzBDeV/rhTfkutkQt77vNpxRZcZ2W', '2024-07-31 16:24:48.906', NULL, 0, 'zero', NULL, '2024-08-20 11:33:29.575', 0, NULL, NULL, 0, 0);
INSERT INTO "public"."t_member" VALUES (1818569534283698177, 'admin', '$2a$10$pC37xr2.RMfe1ZgKbAAZoOoedzBDeV/rhTfkutkQt77vNpxRZcZ2W', '2024-07-31 16:48:59.023', NULL, 0, 'admin', NULL, '2024-08-20 11:33:50.539', 0, NULL, 'qianmeng6879@163.com', 1, 0);

-- ----------------------------
-- Table structure for t_member_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_member_role";
CREATE TABLE "public"."t_member_role" (
  "id" int8 NOT NULL,
  "member_id" int8,
  "role_id" int8
)
;

-- ----------------------------
-- Records of t_member_role
-- ----------------------------
INSERT INTO "public"."t_member_role" VALUES (1818563452157018114, 1818563452052160514, 1818563452157018113);
INSERT INTO "public"."t_member_role" VALUES (1818569534329835521, 1818569534283698177, 1818563452157018113);
INSERT INTO "public"."t_member_role" VALUES (1, 1818569534283698177, 1);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_role";
CREATE TABLE "public"."t_role" (
  "id" int8 NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO "public"."t_role" VALUES (1818563452157018113, 'ROLE_USER');
INSERT INTO "public"."t_role" VALUES (1, 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for user_session
-- ----------------------------
DROP TABLE IF EXISTS "public"."user_session";
CREATE TABLE "public"."user_session" (
  "session_id" varchar COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_at" timestamp(6),
  "last_access_at" timestamp(6),
  "device_type" varchar(255) COLLATE "pg_catalog"."default",
  "token" varchar(255) COLLATE "pg_catalog"."default",
  "expire" int8,
  "device_flag" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of user_session
-- ----------------------------
INSERT INTO "public"."user_session" VALUES ('529ed8d6-e792-49be-bb15-46cd439c974b', 'zero', '2024-08-20 11:33:29.401', '2024-08-20 11:33:29.401', 'web', NULL, NULL, NULL);
INSERT INTO "public"."user_session" VALUES ('03fa8a64-ba4c-4467-a4e1-3b9370cdd009', 'admin', '2024-08-20 11:33:50.368', '2024-08-20 11:33:50.368', 'web', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for user_token
-- ----------------------------
DROP TABLE IF EXISTS "public"."user_token";
CREATE TABLE "public"."user_token" (
  "created_at" timestamp(6) NOT NULL,
  "last_access_at" timestamp(6),
  "device_type" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "token" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "expire" int8 NOT NULL DEFAULT 7200,
  "device_flag" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "id" int8 NOT NULL,
  "state" int4 NOT NULL DEFAULT 1,
  "user_id" int8 NOT NULL,
  "username" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of user_token
-- ----------------------------
INSERT INTO "public"."user_token" VALUES ('2024-08-20 11:15:44.196', NULL, 'phone', 'dc865dd09c814f41b969cd8ec4590518', 7200, '123', 1825733427267682305, 3, 1818569534283698177, 'admin');
INSERT INTO "public"."user_token" VALUES ('2024-08-20 11:18:09.702', NULL, 'phone', 'd2484c404dd040dc93e7d383c7942397', 7200, '123', 1825734037564100609, 3, 1818569534283698177, 'admin');
INSERT INTO "public"."user_token" VALUES ('2024-08-20 11:21:10.877', '2024-08-20 11:21:49.34', 'phone', '8af2fb8abd9a4aaf83375c45e682bbf4', 7200, '123', 1825734797446103042, 0, 1818569534283698177, 'admin');
INSERT INTO "public"."user_token" VALUES ('2024-08-20 11:22:14.617', '2024-08-20 11:22:18.267', 'phone', '7adf318d7b124dd8a36bf436dc1eeb0a', 7200, '123', 1825735064770068481, 2, 1818569534283698177, 'admin');
INSERT INTO "public"."user_token" VALUES ('2024-08-20 11:22:20.013', '2024-08-20 11:22:29.241', 'phone', 'b31a0928c3ea4ab5a7f119733b6d4265', 7200, '123', 1825735087356395521, 1, 1818569534283698177, 'admin');

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."user_id_seq"', 2, false);

-- ----------------------------
-- Primary Key structure for table t_member
-- ----------------------------
ALTER TABLE "public"."t_member" ADD CONSTRAINT "t_member_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_member_role
-- ----------------------------
ALTER TABLE "public"."t_member_role" ADD CONSTRAINT "member_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_role
-- ----------------------------
ALTER TABLE "public"."t_role" ADD CONSTRAINT "t_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table user_session
-- ----------------------------
ALTER TABLE "public"."user_session" ADD CONSTRAINT "user_session_pkey" PRIMARY KEY ("session_id");

-- ----------------------------
-- Primary Key structure for table user_token
-- ----------------------------
ALTER TABLE "public"."user_token" ADD CONSTRAINT "user_token_pkey" PRIMARY KEY ("id");
