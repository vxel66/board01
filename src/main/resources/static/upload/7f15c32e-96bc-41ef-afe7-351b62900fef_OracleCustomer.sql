create table customer (
    cust_no number(5),  -- 고객번호
    cust_name varchar2(30) not null,    -- 고객이름
    cust_email varchar2(30) not null,   -- 고객전자우편
    cust_address varchar2(60) not null, -- 고객주소
    cust_telephone varchar2(15) not null,    -- 고객연락처
    primary key(cust_no)
);






insert into customer(cust_no, cust_name, cust_email, cust_address, cust_telephone)
    values(11111, '홍길동', 'hong@hong.com', '경기도 안산시 선부동', '010-8989-7878');

insert into customer(cust_no, cust_name, cust_email, cust_address, cust_telephone)
    values(22222, '홍길동', 'hong@hong.com', '서울시 마포구 창전동', '010-3333-2222');
    --ORA-00001: unique constraint (YUL2.SYS_C007001) violated

    
insert into customer(cust_no, cust_name, cust_email, cust_address, cust_telephone)
    values(33333, '홍아름', 'hongs@hongs.com', '부산시 강서구 말갈로', '010-8956-5623');
    
insert into customer(cust_no, cust_name, cust_email, cust_address, cust_telephone)
    values(44444, '김수한', 'kim@kim.com', '서울시 서대문 자유동', '02-4568-7777');

insert into customer(cust_no, cust_name, cust_email, cust_address, cust_telephone)
    values(55555, '고도영', 'godo@godo.com', '경기도 하남시 증평', '010-7897-4568');

select * from customer;

commit;




