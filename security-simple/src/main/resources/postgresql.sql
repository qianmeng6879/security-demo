/*
 Navicat Premium Data Transfer

 Source Server         : localhost_pgsql_5432
 Source Server Type    : PostgreSQL
 Source Server Version : 150007
 Source Host           : localhost:5432
 Source Catalog        : oauth_provider_service
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 150007
 File Encoding         : 65001

 Date: 31/07/2024 16:50:19
*/


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
  "last_login_time" timestamp(6)
)
;

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
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_role";
CREATE TABLE "public"."t_role" (
  "id" int8 NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default"
)
;

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
