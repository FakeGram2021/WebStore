INSERT INTO WEBSTORE.admin (id, email, password, username)
VALUES ('12bf2831-a19c-4855-ac55-d28063dce1e7', 'admin@mail.com',
        '$2a$10$WyGU0850Gt6l9niernBpb.58pCPz8XXEaI4qvOyj5rdEYIygCat/u', 'admin');

INSERT INTO WEBSTORE.article (id, amount_in_stock, description, image_url, name, price, version) 
VALUES  ('6b112cd2-6e12-48cd-aa13-2b9b381b8fb7', 10, 'Really nice blue shirt',
        'https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg',
        'Blue shirt S', 10, 0),
        ('27b7c07b-115b-4343-8ff2-aed84785fe44', 5, 'Really nice blue shirt',
        'https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg',
        'Blue shirt M', 10, 0),
        ('045474f0-0ecd-43b8-b172-b9324d8882a0', 3, 'Really nice blue shirt',
        'https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg',
        'Blue shirt L', 10, 0),
        ('165bb151-aa28-4170-9b52-7769b5f8fb1f', 0, 'Really nice blue shirt',
        'https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg',
        'Blue shirt XL', 10, 0),
        ('6e9f4ec8-16f4-4f03-8ece-d8c38157c7e5', 10, 'Bluest of shirts',
        'https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg',
        'Blue shirt 1', 10, 0),
        ('240a9b16-27ad-4b5b-91ab-ade05196b693', 2, 'Wow! Shirt, blue.',
        'https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg',
        'Blue shirt 2', 50, 0),
        ('13d2b5e7-14f0-48f4-9a8f-ad69fe267e9f', 0, 'Very well made blue shirt with long description',
        'https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg',
        'Blue shirt 3', 20, 0);