CHANGE MASTER TO
  MASTER_HOST='mysql-master-2',
  MASTER_PORT=3306,
  MASTER_USER='repl',
  MASTER_PASSWORD='slave_password',
  GET_MASTER_PUBLIC_KEY=1,
  MASTER_AUTO_POSITION=1;
