#!/bin/bash
set -e

# Create databases
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "postgres" <<-EOSQL
  CREATE DATABASE interaction_db;
EOSQL

# Connect to interaction_db and create tables
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "interaction_db" <<-EOSQL
  CREATE TABLE IF NOT EXISTS agent (
    id VARCHAR(20) PRIMARY KEY,
    available BOOLEAN
  );

  INSERT INTO agent VALUES
    ('A_0001', true),
    ('A_0002', true),
    ('A_0003', true),
    ('A_0004', true),
    ('A_0005', true),
    ('A_0006', true);

  CREATE TABLE IF NOT EXISTS skill (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20)
  );

  INSERT INTO skill (name) VALUES
    ('VoIP'),
    ('System'),
    ('SMS'),
    ('Telco');

  CREATE TABLE IF NOT EXISTS agent_skill (
    agent_id VARCHAR(20),
    skill_id INT
  );

  INSERT INTO agent_skill VALUES
    ('A_0001', 1),
    ('A_0001', 3),
    ('A_0002', 1),
    ('A_0002', 2),
    ('A_0002', 4),
    ('A_0003', 4),
    ('A_0004', 1),
    ('A_0005', 2),
    ('A_0006', 3);
EOSQL

