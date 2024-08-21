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

 Date: 21/08/2024 20:55:29
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
INSERT INTO "public"."t_member" VALUES (1818563452052160514, 'zero', '$2a$10$pC37xr2.RMfe1ZgKbAAZoOoedzBDeV/rhTfkutkQt77vNpxRZcZ2W', '2024-07-31 16:24:48.906', NULL, 0, 'zero', NULL, '2024-08-21 20:36:40.527', 0, NULL, NULL, 0, 0);
INSERT INTO "public"."t_member" VALUES (1818569534283698177, 'admin', '$2a$10$pC37xr2.RMfe1ZgKbAAZoOoedzBDeV/rhTfkutkQt77vNpxRZcZ2W', '2024-07-31 16:48:59.023', NULL, 0, 'admin', NULL, '2024-08-21 20:37:00.952', 0, NULL, 'qianmeng6879@163.com', 0, 0);

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
INSERT INTO "public"."t_member_role" VALUES (1, 1818569534283698177, 1);
INSERT INTO "public"."t_member_role" VALUES (1818563452157018114, 1818563452052160514, 2);
INSERT INTO "public"."t_member_role" VALUES (1818569534329835521, 1818569534283698177, 2);

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_permission";
CREATE TABLE "public"."t_permission" (
  "id" int8 NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO "public"."t_permission" VALUES (1, 'user:add');
INSERT INTO "public"."t_permission" VALUES (2, 'user:remove');
INSERT INTO "public"."t_permission" VALUES (3, 'user:edit');
INSERT INTO "public"."t_permission" VALUES (4, 'user:view');
INSERT INTO "public"."t_permission" VALUES (5, 'user:list');
INSERT INTO "public"."t_permission" VALUES (6, 'role:add');
INSERT INTO "public"."t_permission" VALUES (7, 'role:remove');
INSERT INTO "public"."t_permission" VALUES (8, 'role:edit');
INSERT INTO "public"."t_permission" VALUES (9, 'role:view');
INSERT INTO "public"."t_permission" VALUES (10, 'role:list');
INSERT INTO "public"."t_permission" VALUES (11, 'permission:add');
INSERT INTO "public"."t_permission" VALUES (12, 'permission:remove');
INSERT INTO "public"."t_permission" VALUES (13, 'permission:edit');
INSERT INTO "public"."t_permission" VALUES (14, 'permission:view');
INSERT INTO "public"."t_permission" VALUES (15, 'permission:list');
INSERT INTO "public"."t_permission" VALUES (16, 'device_template:add');
INSERT INTO "public"."t_permission" VALUES (17, 'device_template:remove');
INSERT INTO "public"."t_permission" VALUES (18, 'device_template:edit');
INSERT INTO "public"."t_permission" VALUES (19, 'device_template:view');
INSERT INTO "public"."t_permission" VALUES (20, 'device_template:list');
INSERT INTO "public"."t_permission" VALUES (21, 'device:add');
INSERT INTO "public"."t_permission" VALUES (22, 'device:remove');
INSERT INTO "public"."t_permission" VALUES (23, 'device:edit');
INSERT INTO "public"."t_permission" VALUES (24, 'device:view');
INSERT INTO "public"."t_permission" VALUES (25, 'device:list');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_role";
CREATE TABLE "public"."t_role" (
  "id" int8 NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "readonly" int2 DEFAULT 0
)
;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO "public"."t_role" VALUES (1, 'ROLE_ADMIN', 1);
INSERT INTO "public"."t_role" VALUES (2, 'ROLE_USER', 1);

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_role_permission";
CREATE TABLE "public"."t_role_permission" (
  "id" int8 NOT NULL,
  "role_id" int8,
  "permission_id" int8
)
;

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
INSERT INTO "public"."t_role_permission" VALUES (1, 1, 1);
INSERT INTO "public"."t_role_permission" VALUES (2, 1, 2);
INSERT INTO "public"."t_role_permission" VALUES (3, 1, 3);
INSERT INTO "public"."t_role_permission" VALUES (4, 1, 4);
INSERT INTO "public"."t_role_permission" VALUES (5, 1, 5);
INSERT INTO "public"."t_role_permission" VALUES (6, 1, 6);
INSERT INTO "public"."t_role_permission" VALUES (7, 1, 7);
INSERT INTO "public"."t_role_permission" VALUES (8, 1, 8);
INSERT INTO "public"."t_role_permission" VALUES (9, 1, 9);
INSERT INTO "public"."t_role_permission" VALUES (10, 1, 10);
INSERT INTO "public"."t_role_permission" VALUES (11, 1, 11);
INSERT INTO "public"."t_role_permission" VALUES (12, 1, 12);
INSERT INTO "public"."t_role_permission" VALUES (13, 1, 13);
INSERT INTO "public"."t_role_permission" VALUES (14, 1, 14);
INSERT INTO "public"."t_role_permission" VALUES (15, 1, 15);
INSERT INTO "public"."t_role_permission" VALUES (16, 1, 16);
INSERT INTO "public"."t_role_permission" VALUES (17, 1, 17);
INSERT INTO "public"."t_role_permission" VALUES (18, 1, 18);
INSERT INTO "public"."t_role_permission" VALUES (19, 1, 19);
INSERT INTO "public"."t_role_permission" VALUES (20, 1, 20);
INSERT INTO "public"."t_role_permission" VALUES (21, 1, 21);
INSERT INTO "public"."t_role_permission" VALUES (22, 1, 22);
INSERT INTO "public"."t_role_permission" VALUES (23, 1, 23);
INSERT INTO "public"."t_role_permission" VALUES (24, 1, 24);
INSERT INTO "public"."t_role_permission" VALUES (25, 1, 25);
INSERT INTO "public"."t_role_permission" VALUES (1826231348094337026, 2, 4);
INSERT INTO "public"."t_role_permission" VALUES (1826231348094337027, 2, 5);

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
INSERT INTO "public"."user_session" VALUES ('54d32b19-5ee7-452b-8688-b3e509d0e309', 'admin', '2024-08-21 10:52:37.802', '2024-08-21 10:52:37.802', 'web', NULL, NULL, NULL);

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
INSERT INTO "public"."user_token" VALUES ('2024-08-20 11:22:20.013', '2024-08-20 11:22:29.241', 'phone', 'b31a0928c3ea4ab5a7f119733b6d4265', 7200, '123', 1825735087356395521, 3, 1818569534283698177, 'admin');
INSERT INTO "public"."user_token" VALUES ('2024-08-20 11:22:14.617', '2024-08-20 11:22:18.267', 'phone', '7adf318d7b124dd8a36bf436dc1eeb0a', 7200, '123', 1825735064770068481, 3, 1818569534283698177, 'admin');
INSERT INTO "public"."user_token" VALUES ('2024-08-20 23:08:33.073', NULL, 'web', 'cd833aa1d18842f9b83a3b49cd1f3444', 7200, '-1809620334', 1825912813015035906, 2, 1818563452052160514, 'zero');
INSERT INTO "public"."user_token" VALUES ('2024-08-20 23:19:25.297', NULL, 'web', 'f9bf67f6b9014b45a04c468315d66aa5', 7200, '-1809620334', 1825915548540121090, 2, 1818563452052160514, 'zero');
INSERT INTO "public"."user_token" VALUES ('2024-08-20 23:19:43.583', NULL, 'web', 'a90fcfcef6a340da844e9c460f1b94e0', 7200, '-1809620334', 1825915625216192514, 2, 1818563452052160514, 'zero');
INSERT INTO "public"."user_token" VALUES ('2024-08-20 23:29:01.676', NULL, 'web', '124b2ce0930a4a75b8b3b93464bad7f7', 7200, '-1809620334', 1825917966061420545, 2, 1818563452052160514, 'zero');
INSERT INTO "public"."user_token" VALUES ('2024-08-20 23:29:32.035', NULL, 'web', '1300ab154c6a4e25bf23cd3d9e6f3e98', 7200, '-1809620334', 1825918093337575425, 2, 1818563452052160514, 'zero');
INSERT INTO "public"."user_token" VALUES ('2024-08-20 23:30:03.652', NULL, 'web', '4a01419e01844c5a9096c5f4a3751185', 7200, '-1809620334', 1825918225974050818, 2, 1818563452052160514, 'zero');
INSERT INTO "public"."user_token" VALUES ('2024-08-20 23:33:37.942', NULL, 'web', 'c8e35849bc1645cdb919a5c5719a89b6', 7200, '-1809620334', 1825919124784037889, 2, 1818563452052160514, 'zero');
INSERT INTO "public"."user_token" VALUES ('2024-08-20 23:35:58.576', NULL, 'web', '83f4b479fafb4ae8bb93d39135000ef7', 7200, '-1809620334', 1825919714675195905, 2, 1818563452052160514, 'zero');
INSERT INTO "public"."user_token" VALUES ('2024-08-21 00:09:16.369', '2024-08-21 00:09:16.429', 'web', '9ac057680b6741ec8ebfea5609c3ffe5', 7200, '-1809620334', 1825928094026350593, 2, 1818563452052160514, 'zero');
INSERT INTO "public"."user_token" VALUES ('2024-08-21 00:09:27.042', '2024-08-21 00:09:27.081', 'web', '6dc7347947c7489b8bf2fbe973301c39', 7200, '-1809620334', 1825928138716659714, 2, 1818563452052160514, 'zero');
INSERT INTO "public"."user_token" VALUES ('2024-08-20 11:15:44.196', NULL, 'phone', 'dc865dd09c814f41b969cd8ec4590518', 7200, '123', 1825733427267682305, 3, 1818569534283698177, 'admin');
INSERT INTO "public"."user_token" VALUES ('2024-08-20 11:18:09.702', NULL, 'phone', 'd2484c404dd040dc93e7d383c7942397', 7200, '123', 1825734037564100609, 3, 1818569534283698177, 'admin');
INSERT INTO "public"."user_token" VALUES ('2024-08-21 00:11:35.434', '2024-08-21 00:11:35.471', 'web', '2fc5f2e1842b4336a7b60429de382da3', 7200, '-1809620334', 1825928677219155970, 2, 1818563452052160514, 'zero');
INSERT INTO "public"."user_token" VALUES ('2024-08-21 00:16:44.507', '2024-08-21 00:16:44.522', 'web', '6efb43d305644ce6b1c83c55094904b3', 7200, '-1809620334', 1825929973619802114, 2, 1818563452052160514, 'zero');
INSERT INTO "public"."user_token" VALUES ('2024-08-21 00:16:56.686', '2024-08-21 00:16:56.722', 'web', '2f6642d5c3f24d7abc137244c36768a4', 7200, '-1809620334', 1825930024668676097, 2, 1818563452052160514, 'zero');
INSERT INTO "public"."user_token" VALUES ('2024-08-21 00:21:48.459', '2024-08-21 00:21:48.479', 'web', '83ba0eb009634bc48817d8e6e3d9e19f', 7200, '-1809620334', 1825931248482697218, 2, 1818563452052160514, 'zero');
INSERT INTO "public"."user_token" VALUES ('2024-08-21 00:22:20.497', '2024-08-21 00:22:20.539', 'web', '49a52bab12714763b83ca85cf753cf1a', 7200, '-1809620334', 1825931382843031554, 2, 1818563452052160514, 'zero');
INSERT INTO "public"."user_token" VALUES ('2024-08-21 00:22:27.227', '2024-08-21 00:22:27.267', 'web', 'fb1ae8ae8e2a453ca3a9636f821831d5', 7200, '-1809620334', 1825931411037143041, 2, 1818563452052160514, 'zero');
INSERT INTO "public"."user_token" VALUES ('2024-08-20 11:21:10.877', '2024-08-20 11:21:49.34', 'phone', '8af2fb8abd9a4aaf83375c45e682bbf4', 7200, '123', 1825734797446103042, 0, 1818569534283698177, 'admin');
INSERT INTO "public"."user_token" VALUES ('2024-08-21 00:23:23.391', '2024-08-21 00:23:23.423', 'web', 'de69b28af69e4274b6bb57291c592b0a', 7200, '-1809620334', 1825931646618615810, 3, 1818563452052160514, 'zero');
INSERT INTO "public"."user_token" VALUES ('2024-08-21 15:14:15.452', '2024-08-21 17:58:13.81', 'web', '1f3e843c13fd4b33af9c47b39dbd7572', 7200, '-1809620334', 1826155840874745858, 2, 1818569534283698177, 'admin');
INSERT INTO "public"."user_token" VALUES ('2024-08-21 10:44:39.32', '2024-08-21 11:09:21.523', 'phone', '66ae16bbfba241f1acb9bb3f9d78abcc', 7200, '123', 1826087993259606017, 3, 1818563452052160514, 'zero');
INSERT INTO "public"."user_token" VALUES ('2024-08-21 19:44:26.709', '2024-08-21 20:34:52.182', 'web', '2cf347fcd1b44e249d0a4f96a2ead6f2', 7200, '-1809620334', 1826223835823280130, 2, 1818569534283698177, 'admin');
INSERT INTO "public"."user_token" VALUES ('2024-08-21 20:36:40.527', '2024-08-21 20:36:54.289', 'web', 'd0b18a67eef0460cbf360d67b28e837d', 7200, '-1809620334', 1826236979928924162, 1, 1818563452052160514, 'zero');
INSERT INTO "public"."user_token" VALUES ('2024-08-21 20:35:03.708', '2024-08-21 20:36:19.023', 'web', '3fe19f7d7daf4d928169787360b1017e', 7200, '-1809620334', 1826236573895131137, 2, 1818569534283698177, 'admin');
INSERT INTO "public"."user_token" VALUES ('2024-08-21 20:37:00.952', '2024-08-21 20:54:51.859', 'web', '133f2a762a8349c094cdd8ce14cf1290', 7200, '-1809620334', 1826237065597583361, 1, 1818569534283698177, 'admin');

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
-- Primary Key structure for table t_permission
-- ----------------------------
ALTER TABLE "public"."t_permission" ADD CONSTRAINT "t_permission_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_role
-- ----------------------------
ALTER TABLE "public"."t_role" ADD CONSTRAINT "t_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_role_permission
-- ----------------------------
ALTER TABLE "public"."t_role_permission" ADD CONSTRAINT "t_role_permission_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table user_session
-- ----------------------------
ALTER TABLE "public"."user_session" ADD CONSTRAINT "user_session_pkey" PRIMARY KEY ("session_id");

-- ----------------------------
-- Indexes structure for table user_token
-- ----------------------------
CREATE INDEX "last_access_at_idx" ON "public"."user_token" USING btree (
  "last_access_at" "pg_catalog"."timestamp_ops" ASC NULLS LAST
);
CREATE INDEX "token_idx" ON "public"."user_token" USING btree (
  "token" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table user_token
-- ----------------------------
ALTER TABLE "public"."user_token" ADD CONSTRAINT "user_token_pkey" PRIMARY KEY ("id");
