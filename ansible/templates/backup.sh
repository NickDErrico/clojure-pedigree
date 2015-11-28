#!/usr/bin/env bash

# Create temp folder
TEMP_DIR="{{ project_dir }}/temp"
mkdir -p $TEMP_DIR

# Backup folders
CURRENT_TIME="$(date +%Y-%m-%d-%H-%M-%S)"
FILE_EXT="zip"
FILE_NAME="{{ project_dir }}/backup/backup-on-$CURRENT_TIME.$FILE_EXT"

DIRS=(
    {{ log_dir }}
    {{ password_dir }}
    {{ person_image_dir }}
    {{ neo4j_path }}
)

for dir_path in "${DIRS[@]}"
do
    cd "$dir_path"
    cd ..
    dir=$(basename "$dir_path")
    zip -r -u $FILE_NAME $dir
done

# Backup postgres database
PGDATABASE={{ db_name }}
PGHOST={{ db_host }}
PGUSER={{ db_user }}
PGPASSWORD={{ db_password }}
PGPORT=5432

mkdir -p $TEMP_DIR/postgres
cd $TEMP_DIR/postgres
pg_dump -cO > postgres.sql
cd $TEMP_DIR
zip -r -u $FILE_NAME postgres

# Delete temp folder
