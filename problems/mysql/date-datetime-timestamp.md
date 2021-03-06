# DATE、DATETIME和TIMESTAMP的区别

![版本](https://img.shields.io/badge/mysql-8.0.11-blue.svg)

首先我们建一张测试表。

```sql
create table demo
(
    col_date date null,
    col_date_time datetime(6) null,
    col_time_stamp timestamp(6) null
);
```

然后我们往里插入一条数据。

```sql
insert into demo(col_date, col_date_time, col_time_stamp) values ('2019-09-09 12:00:00.123456','2019-09-09 12:00:00.123456','2019-09-09 12:00:00.123456');
```

使用`SELECT`语句后可以看到以下的结果。

```sql
+------------+----------------------------+----------------------------+
| col_date   | col_date_time              | col_time_stamp             |
+------------+----------------------------+----------------------------+
| 2019-09-09 | 2019-09-09 12:00:00.123456 | 2019-09-09 12:00:00.123456 |
+------------+----------------------------+----------------------------+
```

所以我们得到了第一个结论。

- `DATE`存储的数据只能精确到天，如果你尝试给字段设置精度会报错。
- `DATETIME`和`TIMESTAMP`而其它两种数据类型可以保存到微妙，如果经度大于6则会报错。

如果我们把日期调大，再执行插入语句，你会发现数据库会报错。

```sql
insert into demo(col_date, col_date_time, col_time_stamp) values ('3000-09-09 12:00:00.123456','3000-09-09 12:00:00.123456','2039-09-09 12:00:00.123456');

ERROR 1292 (22007): Incorrect datetime value: '2039-09-09 12:00:00.123456' for column 'col_time_stamp' at row 1
```

这是因为`TIMESTAMP`类型支持的时间范围比较小，从1970-01-01 00:00:00到2038-01-09 03:14:07。而`DATETIME`类型支持的范围是1000-01-01 00:00:00到9999-12-31 23:59:59。

相对的`TIMESTAMP`类型占用的空间更少，为4个字节，而`DATETIME`类型占8个字节。

除此之外，`TIMESTAMP`类型的值会受时区的影响，我们执行以下语句修改时区并再次查看表中的数据。

```sql
set time_zone = "+0:00";

select * from demo;

+------------+----------------------------+----------------------------+
| col_date   | col_date_time              | col_time_stamp             |
+------------+----------------------------+----------------------------+
| 2019-09-09 | 2019-09-09 12:00:00.123456 | 2019-09-09 04:00:00.123456 |
+------------+----------------------------+----------------------------+
```

这时会发现`col_time_stamp`列的数据比之前查询时早了8个小时，这是因为之前的时区是`+8:00`，而现在的时区是`+0:00`，因此少了8小时。

此外，从**MySQL 5.6.5**起，`DATETIME`类型和`TIMESTAMP`一样，也支持在初始化时以及在更新时把列的值设置为`CURRENT_TIMESTAMP`。

```sql
create table demo
(
    number int,
    col_date_time datetime(6) default CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    col_time_stamp timestamp(6) default CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
);

insert into demo(number) values (1);

select * from demo;

+--------+----------------------------+----------------------------+
| number | col_date_time              | col_time_stamp             |
+--------+----------------------------+----------------------------+
|      1 | 2018-10-28 22:54:39.785312 | 2018-10-28 14:54:39.785312 |
+--------+----------------------------+----------------------------+
```