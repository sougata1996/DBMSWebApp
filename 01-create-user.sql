-- Creates a dummy user and given all access rights to the user.
-- Please run this SQL Script along with the queries script
-- As this user is set in context.xml of our code.

CREATE USER 'webstudent'@'localhost' IDENTIFIED BY 'webstudent';

GRANT ALL PRIVILEGES ON * . * TO 'webstudent'@'localhost';
