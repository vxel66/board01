create table customer (
    cust_no number(5),  -- ����ȣ
    cust_name varchar2(30) not null,    -- ���̸�
    cust_email varchar2(30) not null,   -- �����ڿ���
    cust_address varchar2(60) not null, -- ���ּ�
    cust_telephone varchar2(15) not null,    -- ������ó
    primary key(cust_no)
);






insert into customer(cust_no, cust_name, cust_email, cust_address, cust_telephone)
    values(11111, 'ȫ�浿', 'hong@hong.com', '��⵵ �Ȼ�� ���ε�', '010-8989-7878');

insert into customer(cust_no, cust_name, cust_email, cust_address, cust_telephone)
    values(22222, 'ȫ�浿', 'hong@hong.com', '����� ������ â����', '010-3333-2222');
    --ORA-00001: unique constraint (YUL2.SYS_C007001) violated

    
insert into customer(cust_no, cust_name, cust_email, cust_address, cust_telephone)
    values(33333, 'ȫ�Ƹ�', 'hongs@hongs.com', '�λ�� ������ ������', '010-8956-5623');
    
insert into customer(cust_no, cust_name, cust_email, cust_address, cust_telephone)
    values(44444, '�����', 'kim@kim.com', '����� ���빮 ������', '02-4568-7777');

insert into customer(cust_no, cust_name, cust_email, cust_address, cust_telephone)
    values(55555, '����', 'godo@godo.com', '��⵵ �ϳ��� ����', '010-7897-4568');

select * from customer;

commit;




