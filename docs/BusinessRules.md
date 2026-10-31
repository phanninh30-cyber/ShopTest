# Business Rules - ShopTest

Tài liệu mô tả quy tắc nghiệp vụ của ứng dụng ShopTest (Jakarta EE, Tomcat 10.1). Đây là tài liệu nguồn dùng để sinh Test Plan và Test Case.

## 1. Đăng ký (/register)
- BR1: username 4-20 ký tự, chỉ gồm chữ, số, dấu gạch dưới.
- BR2: password 6-30 ký tự.
- BR3: email đúng định dạng name@domain.tld.
- BR4: confirm password phải trùng password.
- BR5: username không được trùng với tài khoản đã có.
- Đăng ký thành công: chuyển về /login kèm thông báo "Registration successful. Please log in."
- Tài khoản đăng ký mới có vai trò USER.

## 2. Đăng nhập (/login)
- BR7: username và password không được để trống.
- BR8: sai username hoặc password thì hiển thị "Invalid username or password".
- Đăng nhập thành công: lưu session, chuyển sang /home.
- Tài khoản có sẵn: admin / 123456 (vai trò ADMIN).

## 3. Phân quyền
- BR6: chưa đăng nhập mà vào /home hoặc /admin/* thì chuyển về /login.
- BR6b: vai trò USER vào /admin/* thì nhận lỗi 403.

## 4. Quản lý danh mục (/admin/categories) - chỉ ADMIN
- BR9: tên danh mục bắt buộc ("Category name is required").
- BR10: tên tối đa 50 ký tự ("Category name must be at most 50 characters").
- BR11: tên không được trùng, không phân biệt hoa thường ("Category name already exists").
- BR12: Create (nút Add), Read (bảng danh sách), Update (nút Edit), Delete (nút Delete, có hộp thoại xác nhận).
- Dữ liệu mẫu có sẵn: Laptop, Phone, Accessories.

## 5. Thông báo lỗi (dùng làm kết quả mong đợi)
| Tình huống | Thông báo |
|---|---|
| Username trống khi đăng nhập | Username is required |
| Password trống khi đăng nhập | Password is required |
| Sai thông tin đăng nhập | Invalid username or password |
| Username đăng ký sai định dạng | Username must be 4-20 characters (letters, digits, underscore) |
| Email sai | Email is invalid |
| Password đăng ký sai độ dài | Password must be 6-30 characters |
| Confirm không khớp | Confirm password does not match |
| Username đã tồn tại | Username already exists |
| Tên danh mục trống | Category name is required |
| Tên danh mục quá dài | Category name must be at most 50 characters |
| Tên danh mục trùng | Category name already exists |