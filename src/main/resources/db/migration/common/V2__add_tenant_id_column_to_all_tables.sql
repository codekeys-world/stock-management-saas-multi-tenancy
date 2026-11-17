-- Categories
alter table public.categories
    add tenant_id varchar(255) not null;
comment on column categories.tenant_id is 'Tenant ID';

-- Products
alter table public.products
    add tenant_id varchar(255) not null;
comment on column categories.tenant_id is 'Tenant ID';

-- StockMvt
alter table public.stock_mvts
    add tenant_id varchar(255) not null;
comment on column categories.tenant_id is 'Tenant ID';