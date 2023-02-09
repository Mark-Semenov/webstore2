update admin_url
set url    = '/admin/product/new',
    action = 'Add Product'
where id = 1;

update admin_url
set url    = '/admin/category/new',
    action = 'Add Category'
where id = 2;

update admin_url
set url    = '/admin/user/new',
    action = 'Add User'
where id = 4;
