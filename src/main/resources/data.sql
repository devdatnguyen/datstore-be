-- Xóa dữ liệu cũ để tránh lỗi trùng lặp
DELETE FROM "C##ORC_TEST".PRODUCTS;
DELETE FROM "C##ORC_TEST".CATEGORIES;
COMMIT;

-- Tạo danh mục sản phẩm
INSERT INTO "C##ORC_TEST".CATEGORIES (NAME, DESCRIPTION) VALUES ('Electronics', 'Các sản phẩm điện tử');
INSERT INTO "C##ORC_TEST".CATEGORIES (NAME, DESCRIPTION) VALUES ('Clothing', 'Thời trang cho nam và nữ');
INSERT INTO "C##ORC_TEST".CATEGORIES (NAME, DESCRIPTION) VALUES ('Home & Kitchen', 'Đồ dùng gia đình và nhà bếp');
COMMIT;

-- Thêm sản phẩm (từng câu lệnh INSERT riêng lẻ)
-- Electronics (ID = 1)
INSERT INTO "C##ORC_TEST".PRODUCTS (NAME, DESCRIPTION, PRICE, IMAGE_URL, CATEGORY_ID) VALUES
    ('Laptop Dell XPS 15', 'Laptop cao cấp dành cho dân lập trình', 1500.00, '/uploads/laptop-dell.jpg', 1);

INSERT INTO "C##ORC_TEST".PRODUCTS (NAME, DESCRIPTION, PRICE, IMAGE_URL, CATEGORY_ID) VALUES
    ('iPhone 14 Pro Max', 'Điện thoại cao cấp của Apple', 1200.00, '/uploads/iphone14.jpg', 1);

INSERT INTO "C##ORC_TEST".PRODUCTS (NAME, DESCRIPTION, PRICE, IMAGE_URL, CATEGORY_ID) VALUES
    ('Samsung Galaxy S23', 'Smartphone mạnh mẽ của Samsung', 1100.00, '/uploads/samsung-s23.jpg', 1);

INSERT INTO "C##ORC_TEST".PRODUCTS (NAME, DESCRIPTION, PRICE, IMAGE_URL, CATEGORY_ID) VALUES
    ('Sony WH-1000XM5', 'Tai nghe chống ồn tốt nhất', 350.00, '/uploads/sony-headphone.jpg', 1);

INSERT INTO "C##ORC_TEST".PRODUCTS (NAME, DESCRIPTION, PRICE, IMAGE_URL, CATEGORY_ID) VALUES
    ('Apple Watch Series 8', 'Đồng hồ thông minh Apple', 400.00, '/uploads/apple-watch.jpg', 1);

INSERT INTO "C##ORC_TEST".PRODUCTS (NAME, DESCRIPTION, PRICE, IMAGE_URL, CATEGORY_ID) VALUES
    ('MacBook Air M2', 'Laptop siêu nhẹ của Apple', 1300.00, '/uploads/macbook-air.jpg', 1);

INSERT INTO "C##ORC_TEST".PRODUCTS (NAME, DESCRIPTION, PRICE, IMAGE_URL, CATEGORY_ID) VALUES
    ('GoPro Hero 11', 'Camera hành trình chất lượng 4K', 500.00, '/uploads/gopro-hero11.jpg', 1);

INSERT INTO "C##ORC_TEST".PRODUCTS (NAME, DESCRIPTION, PRICE, IMAGE_URL, CATEGORY_ID) VALUES
    ('JBL Flip 6', 'Loa Bluetooth chống nước', 120.00, '/uploads/jbl-flip6.jpg', 1);

INSERT INTO "C##ORC_TEST".PRODUCTS (NAME, DESCRIPTION, PRICE, IMAGE_URL, CATEGORY_ID) VALUES
    ('Nintendo Switch OLED', 'Máy chơi game cầm tay', 350.00, '/uploads/nintendo-switch.jpg', 1);

INSERT INTO "C##ORC_TEST".PRODUCTS (NAME, DESCRIPTION, PRICE, IMAGE_URL, CATEGORY_ID) VALUES
    ('LG OLED C2', 'Smart TV 4K chất lượng cao', 1800.00, '/uploads/lg-oled-tv.jpg', 1);

-- Clothing (ID = 2)
INSERT INTO "C##ORC_TEST".PRODUCTS (NAME, DESCRIPTION, PRICE, IMAGE_URL, CATEGORY_ID) VALUES
    ('Áo thun nam Nike', 'Áo thun thể thao nam chính hãng', 30.00, '/uploads/nike-tshirt.jpg', 2);

INSERT INTO "C##ORC_TEST".PRODUCTS (NAME, DESCRIPTION, PRICE, IMAGE_URL, CATEGORY_ID) VALUES
    ('Giày Adidas Ultraboost', 'Giày chạy bộ Adidas', 150.00, '/uploads/adidas-ultraboost.jpg', 2);

INSERT INTO "C##ORC_TEST".PRODUCTS (NAME, DESCRIPTION, PRICE, IMAGE_URL, CATEGORY_ID) VALUES
    ('Áo khoác da nam', 'Áo khoác da cao cấp', 200.00, '/uploads/leather-jacket.jpg', 2);

INSERT INTO "C##ORC_TEST".PRODUCTS (NAME, DESCRIPTION, PRICE, IMAGE_URL, CATEGORY_ID) VALUES
    ('Quần jean Levi’s', 'Quần jean cao cấp của Levi’s', 80.00, '/uploads/levis-jeans.jpg', 2);

-- Home & Kitchen (ID = 3)
INSERT INTO "C##ORC_TEST".PRODUCTS (NAME, DESCRIPTION, PRICE, IMAGE_URL, CATEGORY_ID) VALUES
    ('Máy pha cà phê Breville', 'Máy pha cà phê espresso chuyên nghiệp', 600.00, '/uploads/breville-coffee.jpg', 3);

INSERT INTO "C##ORC_TEST".PRODUCTS (NAME, DESCRIPTION, PRICE, IMAGE_URL, CATEGORY_ID) VALUES
    ('Nồi chiên không dầu Philips', 'Nồi chiên không dầu tiện dụng', 250.00, '/uploads/philips-airfryer.jpg', 3);

INSERT INTO "C##ORC_TEST".PRODUCTS (NAME, DESCRIPTION, PRICE, IMAGE_URL, CATEGORY_ID) VALUES
    ('Máy hút bụi Dyson V11', 'Máy hút bụi không dây mạnh mẽ', 500.00, '/uploads/dyson-v11.jpg', 3);

COMMIT;
