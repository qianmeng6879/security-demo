/*
 Navicat Premium Dump SQL

 Source Server         : postgresql_local
 Source Server Type    : PostgreSQL
 Source Server Version : 150008 (150008)
 Source Host           : localhost:5432
 Source Catalog        : db_endpoint
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 150008 (150008)
 File Encoding         : 65001

 Date: 18/09/2024 17:20:08
*/


-- ----------------------------
-- Sequence structure for permission_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."permission_seq";
CREATE SEQUENCE "public"."permission_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "public"."permission_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for role_permission_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."role_permission_seq";
CREATE SEQUENCE "public"."role_permission_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "public"."role_permission_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for role_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."role_seq";
CREATE SEQUENCE "public"."role_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "public"."role_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for user_role_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."user_role_seq";
CREATE SEQUENCE "public"."user_role_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "public"."user_role_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for user_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."user_seq";
CREATE SEQUENCE "public"."user_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "public"."user_seq" OWNER TO "postgres";

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_permission";
CREATE TABLE "public"."t_permission" (
  "id" int8 NOT NULL DEFAULT nextval('permission_seq'::regclass),
  "name" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."t_permission" OWNER TO "postgres";

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_role";
CREATE TABLE "public"."t_role" (
  "id" int8 NOT NULL DEFAULT nextval('role_seq'::regclass),
  "name" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."t_role" OWNER TO "postgres";

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_role_permission";
CREATE TABLE "public"."t_role_permission" (
  "role_id" int8 NOT NULL,
  "permission_id" int8 NOT NULL,
  "id" int8 NOT NULL DEFAULT nextval('role_permission_seq'::regclass)
)
;
ALTER TABLE "public"."t_role_permission" OWNER TO "postgres";

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_user";
CREATE TABLE "public"."t_user" (
  "id" int8 NOT NULL DEFAULT nextval('user_seq'::regclass),
  "username" varchar(255) COLLATE "pg_catalog"."default",
  "password" varchar(255) COLLATE "pg_catalog"."default",
  "nickname" varchar(255) COLLATE "pg_catalog"."default",
  "phone" varchar(255) COLLATE "pg_catalog"."default",
  "email" varchar(255) COLLATE "pg_catalog"."default",
  "created_time" timestamp(6),
  "updated_time" timestamp(6),
  "last_login_time" timestamp(6),
  "deleted" int2 NOT NULL DEFAULT 0,
  "avatar_url" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."t_user" OWNER TO "postgres";

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_user_role";
CREATE TABLE "public"."t_user_role" (
  "user_id" int8 NOT NULL,
  "role_id" int8 NOT NULL,
  "id" int8 NOT NULL DEFAULT nextval('user_role_seq'::regclass)
)
;
ALTER TABLE "public"."t_user_role" OWNER TO "postgres";

-- ----------------------------
-- Uniques structure for table t_permission
-- ----------------------------
ALTER TABLE "public"."t_permission" ADD CONSTRAINT "uni_permission_name" UNIQUE ("name");

-- ----------------------------
-- Primary Key structure for table t_permission
-- ----------------------------
ALTER TABLE "public"."t_permission" ADD CONSTRAINT "t_permission_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table t_role
-- ----------------------------
ALTER TABLE "public"."t_role" ADD CONSTRAINT "uni_role_name" UNIQUE ("name");

-- ----------------------------
-- Primary Key structure for table t_role
-- ----------------------------
ALTER TABLE "public"."t_role" ADD CONSTRAINT "t_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table t_role_permission
-- ----------------------------
ALTER TABLE "public"."t_role_permission" ADD CONSTRAINT "uni_role_permission" UNIQUE ("role_id", "permission_id");

-- ----------------------------
-- Uniques structure for table t_user
-- ----------------------------
ALTER TABLE "public"."t_user" ADD CONSTRAINT "uni_usename" UNIQUE ("username", "deleted");

-- ----------------------------
-- Primary Key structure for table t_user
-- ----------------------------
ALTER TABLE "public"."t_user" ADD CONSTRAINT "t_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table t_user_role
-- ----------------------------
ALTER TABLE "public"."t_user_role" ADD CONSTRAINT "uni_user_role" UNIQUE ("user_id", "role_id");

-- ----------------------------
-- Primary Key structure for table t_user_role
-- ----------------------------
ALTER TABLE "public"."t_user_role" ADD CONSTRAINT "t_user_role_pkey" PRIMARY KEY ("id");
