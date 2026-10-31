# Test Cases - ShopTest

Phiên bản cuối. Cột "Test script" cho biết test case được tự động hóa ở đâu. Kết quả unit test lấy từ log Maven ngày 23/10/2026 (Tests run: 46, Failures: 0, Errors: 0, BUILD SUCCESS).

## A. Unit test (JUnit 5 + Mockito) - nhóm "unit"

| ID | Chức năng | Kỹ thuật | Dữ liệu vào | Kết quả mong đợi | Ưu tiên | Test script | Kết quả thực tế |
|---|---|---|---|---|---|---|---|
| TC_U01 | Username (BR1) | BVA | độ dài 3, 4, 5, 19, 20, 21 | sai, đúng, đúng, đúng, đúng, sai | Cao | AuthValidatorTest#usernameLengthBoundary | PASS |
| TC_U02 | Username (BR1) | EP | ninh, Ninh_01, user_123, ABCD | hợp lệ | Cao | AuthValidatorTest#usernameValidClasses | PASS |
| TC_U03 | Username (BR1) | EP | "ni nh", ninh@, ninh-01, người_dùng, ab!cd | không hợp lệ | Cao | AuthValidatorTest#usernameInvalidClasses | PASS |
| TC_U04 | Username (BR1) | EP | null, chuỗi rỗng | không hợp lệ | Cao | AuthValidatorTest#usernameNullOrEmpty | PASS |
| TC_U05 | Password (BR2) | BVA | độ dài 0, 5, 6, 7, 29, 30, 31 | sai, sai, đúng, đúng, đúng, đúng, sai | Cao | AuthValidatorTest#passwordLengthBoundary | PASS |
| TC_U06 | Password (BR2) | EP | null | không hợp lệ | Trung bình | AuthValidatorTest#passwordNull | PASS |
| TC_U07 | Email (BR3) | EP | a@b.com, ninh.nguyen@fpt.edu.vn, user+tag@mail.org | hợp lệ | Trung bình | AuthValidatorTest#emailValid | PASS |
| TC_U08 | Email (BR3) | EP | abc, abc@, @mail.com, a@b, "a b@mail.com", a@b.c | không hợp lệ | Trung bình | AuthValidatorTest#emailInvalid | PASS |
| TC_U09 | Validate đăng nhập (BR7) | Bảng quyết định | username rỗng, password 123456 | "Username is required" | Cao | AuthValidatorTest#loginMissingUsername | PASS |
| TC_U10 | Validate đăng nhập (BR7) | Bảng quyết định | username ninh, password rỗng | "Password is required" | Cao | AuthValidatorTest#loginMissingPassword | PASS |
| TC_U11 | Validate đăng nhập (BR7) | Bảng quyết định | đủ thông tin | không có lỗi (null) | Cao | AuthValidatorTest#loginOk | PASS |
| TC_U12 | Validate đăng ký | Bảng quyết định | mọi trường hợp lệ | không có lỗi (null) | Cao | AuthValidatorTest#registerOk | PASS |
| TC_U13 | Validate đăng ký (BR1) | Bảng quyết định | username "ab" | có thông báo lỗi | Cao | AuthValidatorTest#registerBadUsername | PASS |
| TC_U14 | Validate đăng ký (BR3) | Bảng quyết định | email "abc" | "Email is invalid" | Trung bình | AuthValidatorTest#registerBadEmail | PASS |
| TC_U15 | Validate đăng ký (BR2) | Bảng quyết định | password "123" | "Password must be 6-30 characters" | Trung bình | AuthValidatorTest#registerShortPassword | PASS |
| TC_U16 | Validate đăng ký (BR4) | Bảng quyết định | confirm khác password | "Confirm password does not match" | Trung bình | AuthValidatorTest#registerConfirmMismatch | PASS |
| TC_U17 | LoginServlet.doGet | EP | gọi doGet | chuyển tới login.jsp | Trung bình | LoginServletTest#doGetForwardsToLoginView | PASS |
| TC_U18 | LoginServlet.doPost (BR8) | EP | admin / 123456 | lưu session, chuyển sang /home | Cao | LoginServletTest#doPostValidCredentials | PASS |
| TC_U19 | LoginServlet.doPost (BR8) | EP | admin / sai_pass | báo lỗi, không chuyển trang | Cao | LoginServletTest#doPostWrongPassword | PASS |
| TC_U20 | LoginServlet.doPost (BR7) | EP | username rỗng | "Username is required" | Cao | LoginServletTest#doPostEmptyUsername | PASS |

## B. UI test (Selenium + TestNG)

| ID | Chức năng | Kỹ thuật | Dữ liệu vào | Kết quả mong đợi | Ưu tiên | Nhóm | Test script | Kết quả thực tế |
|---|---|---|---|---|---|---|---|---|
| TC_UI01 | Đăng nhập | EP | admin / 123456 | vào /home, thấy nút Logout | Cao | smoke, login | AuthUiTest#loginSuccess | CHƯA CHẠY |
| TC_UI02 | Đăng nhập (BR8) | EP | admin / mật khẩu sai | "Invalid username or password" | Cao | regression, login | AuthUiTest#loginWrongPassword | CHƯA CHẠY |
| TC_UI03 | Đăng nhập (BR7) | EP | bỏ trống cả hai ô | "Username is required" | Trung bình | regression, login | AuthUiTest#loginEmpty | CHƯA CHẠY |
| TC_UI04 | Đăng ký | EP | thông tin hợp lệ, username mới | về /login, thông báo thành công, đăng nhập được | Cao | smoke, register | AuthUiTest#registerSuccess | CHƯA CHẠY |
| TC_UI05 | Đăng ký (BR4) | EP | confirm không khớp | "Confirm password does not match" | Trung bình | regression, register | AuthUiTest#registerConfirmMismatch | CHƯA CHẠY |
| TC_UI06 | Đăng ký (BR5) | EP | username admin (đã tồn tại) | "Username already exists" | Cao | regression, register | AuthUiTest#registerDuplicateUser | CHƯA CHẠY |
| TC_UI07 | Đăng ký (BR1) | BVA | username "abc" (3 ký tự) | báo lỗi độ dài username | Trung bình | regression, register | AuthUiTest#registerShortUsername | CHƯA CHẠY |
| TC_UI08 | Phân quyền (BR6b) | EP | USER mở /admin/categories | lỗi 403 | Cao | regression, security | AuthUiTest#normalUserCannotAccessAdmin | CHƯA CHẠY |
| TC_UI09 | Phân quyền (BR6) | EP | chưa đăng nhập mở /admin/categories | chuyển về /login | Cao | regression, security | AuthUiTest#guestRedirectedToLogin | CHƯA CHẠY |
| TC_UI10 | CRUD danh mục (BR12) | Chuyển trạng thái | tạo, sửa, xóa một danh mục | mỗi bước hiển thị đúng | Cao | smoke, crud | CategoryCrudTest#fullCrudFlow | CHƯA CHẠY |
| TC_UI11 | CRUD danh mục (BR9) | EP | tên rỗng | "Category name is required" | Trung bình | regression, crud | CategoryCrudTest#createEmptyName | CHƯA CHẠY |
| TC_UI12 | CRUD danh mục (BR11) | EP | tên "Laptop" (đã có) | "Category name already exists" | Trung bình | regression, crud | CategoryCrudTest#createDuplicateName | CHƯA CHẠY |
| TC_UI13 | CRUD danh mục (BR10) | BVA | tên 51 ký tự | báo lỗi tối đa 50 ký tự | Trung bình | regression, crud | CategoryCrudTest#createTooLongName | CHƯA CHẠY |

## C. Thống kê

| Nhóm | Số test case | Đã chạy | Pass | Fail |
|---|---|---|---|---|
| Unit test (TC_U01 đến TC_U20) | 20 test case (46 lần chạy do tham số hóa) | 20 | 20 | 0 |
| UI test (TC_UI01 đến TC_UI13) | 13 | 0 | 0 | 0 |

## D. Lỗi và vấn đề phát hiện khi thực thi (Y3)

Chưa phát hiện lỗi nghiệp vụ của ứng dụng. Ba vấn đề về môi trường và cấu hình đã gặp và xử lý, chi tiết trong AI_Evidence.md (Giai đoạn 3):

| STT | Vấn đề | Nguyên nhân | Cách xử lý | Trạng thái |
|---|---|---|---|---|
| 1 | Biên dịch lỗi "package org.junit.jupiter.api does not exist" | Hai file test đặt nhầm trong src/main/java | Chuyển package unit sang src/test/java | Đã xử lý |
| 2 | "No compiler is provided in this environment" | Chạy nhầm project khác và dùng JRE thay vì JDK | Chọn đúng project ShopTest và JDK 17 | Đã xử lý |
| 3 | Lỗi "TestDescriptor.getAncestors()" khi chạy test | Lẫn phiên bản các thư viện JUnit | Thêm junit-bom và junit-platform-launcher vào pom.xml | Đã xử lý |