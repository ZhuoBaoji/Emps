drop table xls;
create table xls(
id number(20) primary key,
name varchar2(100),
age number(20),
salary number(9,2),
deptid number(20)
);


create table t_dept(
id number(20) primary key,
dname varchar2(20)
);


create sequence emp_seq start with 1 increment by 1NOCACHE;

SELECT * from t_p_grade_head h where h.index_id=138;