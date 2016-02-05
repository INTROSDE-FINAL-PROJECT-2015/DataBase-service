#removes the database if exists
rm -v db.sqlite

# loads the dbinit.sql file and creates the database
# following the rules contained in the dbinit.sql file
sqlite3 -init db_init.sql db.sqlite | echo .quit

# OR

# rm -v db.sqlite && cat dbinit.sql | sqlite3 db.sqlite
