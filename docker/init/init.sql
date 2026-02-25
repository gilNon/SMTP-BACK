CREATE TYPE "configuration_type" AS ENUM (
  'USER_SMTP',
  'PASSWORD_SMTP',
  'PORT_SMTP',
  'HOST_SMTP',
  'SSL_SMTP',
  'TLS_SMTP'
);

CREATE TYPE "email_status" AS ENUM (
  'RECEIVED',
  'READ',
  'DELETED'
);

CREATE TYPE "role" AS ENUM (
  'USER',
  'ADMIN'
);

CREATE TABLE "configurations" (
  "id_configuration" UUID PRIMARY KEY,
  "type" configuration_type NOT NULL UNIQUE,
  "value" VARCHAR(250) NOT NULL,
  "created_at" TIMESTAMP DEFAULT (CURRENT_TIMESTAMP),
  "updated_at" TIMESTAMP DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE "users" (
  "id_user" UUID PRIMARY KEY,
  "first_name" VARCHAR(50) NOT NULL,
  "last_name" VARCHAR(250) NOT NULL,
  "email" VARCHAR(100) UNIQUE NOT NULL,
  "password" VARCHAR(250) NOT NULL,
  "role" role NOT NULL DEFAULT 'USER',
  "active" BOOLEAN NOT NULL DEFAULT true,
  "created_at" TIMESTAMP DEFAULT (CURRENT_TIMESTAMP),
  "updated_at" TIMESTAMP DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE "emails" (
  "id_email" UUID PRIMARY KEY,
  "sender_address" VARCHAR(100),
  "sender_name" VARCHAR(100),
  "recipient_address" VARCHAR(100),
  "recipient_name" VARCHAR(100),
  "cc" VARCHAR(100),
  "bcc" VARCHAR(100),
  "subject" VARCHAR(100),
  "content_type" VARCHAR(100),
  "status" email_status NOT NULL DEFAULT 'RECEIVED',
  "html_content" TEXT,
  "text_content" TEXT,
  "created_at" TIMESTAMP DEFAULT (CURRENT_TIMESTAMP),
  "updated_at" TIMESTAMP DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE "media" (
  "id_media" UUID PRIMARY KEY,
  "id_email" UUID NOT NULL,
  "file_name" VARCHAR(100) NOT NULL,
  "created_at" TIMESTAMP DEFAULT (CURRENT_TIMESTAMP),
  "updated_at" TIMESTAMP DEFAULT (CURRENT_TIMESTAMP)
);

ALTER TABLE "media" ADD CONSTRAINT "media_mail" FOREIGN KEY ("id_email") REFERENCES "emails" ("id_email");
