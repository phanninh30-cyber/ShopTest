# Test Plan - ShopTest

Sinh viên: Ninh | Môn: Kiểm thử nâng cao cùng AI (Java) | Phiên bản: cuối (đã chỉnh sửa sau khi chạy thực tế)

## 1. Mục tiêu
Kiểm thử tự động các chức năng Đăng ký, Đăng nhập, Phân quyền và CRUD Danh mục của ứng dụng ShopTest, nhằm phát hiện lỗi sớm và chạy lại nhanh mỗi khi code thay đổi.

---
## 2. Phạm vi
- Trong phạm vi:
  - Kiểm tra dữ liệu đầu vào (unit test, JUnit 5).
  - Kiểm tra controller với Mockito (LoginServlet).
  - Kiểm tra luồng giao diện (Selenium + TestNG).
- Ngoài phạm vi: hiệu năng, bảo mật chuyên sâu, tương thích nhiều trình duyệt, giao diện di động.

## 3. Nguồn yêu cầu
Các quy tắc nghiệp vụ BR1 đến BR12 trong file BusinessRules.md.

## 4. Kỹ thuật thiết kế test case
- Phân vùng tương đương (EP): username, email.
- Phân tích giá trị biên (BVA): độ dài username (3, 4, 20, 21), độ dài password (5, 6, 30, 31).
- Bảng quyết định: kiểm tra dữ liệu đăng nhập và đăng ký.
- Kiểm thử chuyển trạng thái: vòng đời Create - Read - Update - Delete của danh mục.

## 5. Công cụ
| Mục đích | Công cụ |
|---|---|
| Unit test | JUnit 5 |
| Giả lập đối tượng | Mockito |
| UI test | Selenium WebDriver 4 + TestNG |
| Quản lý build | Maven |
| CI/CD | GitHub Actions |
| Chạy ứng dụng | Tomcat 10.1, JDK 17 |

## 6. Môi trường
Windows, JDK 17 (bắt buộc là JDK, không phải JRE), Eclipse, Google Chrome, Tomcat 10.1, địa chỉ ứng dụng http://localhost:8080/ShopTest.

## 7. Nhóm test (tag/group)
| Nhóm | Ý nghĩa |
|---|---|
| unit | Unit test, chạy không cần bật Tomcat |
| smoke | Luồng chính, chạy nhanh để kiểm tra tổng quát |
| regression | Các trường hợp lỗi và biên |
| login, register, crud, security | Nhóm theo chức năng |

## 8. Tiêu chí vào / ra
- Vào: ứng dụng build và chạy được trên Tomcat; Maven tải đủ thư viện.
- Ra: 100% test nhóm unit và smoke pass; từ 90% test regression pass; mọi lỗi tìm thấy được ghi lại.

## 9. Rủi ro và cách xử lý
| Rủi ro | Cách xử lý |
|---|---|
| Dữ liệu lưu trong bộ nhớ, test lỗi giữa chừng để lại dữ liệu thừa | Restart Tomcat để đưa dữ liệu về ban đầu |
| Locator thay đổi khi sửa giao diện | Gom locator vào các lớp Page Object để chỉ sửa một chỗ |
| Xung đột phiên bản thư viện JUnit (đã gặp khi chạy thật) | Dùng junit-bom để cố định cùng một phiên bản |
| Chạy Maven bằng JRE thay vì JDK (đã gặp khi chạy thật) | Chọn JDK 17 trong tab JRE của cấu hình Maven build |
| Test UI phụ thuộc Chrome và mạng (tải chromedriver lần đầu) | Cài Chrome, bật internet khi chạy lần đầu |

## 10. Sản phẩm bàn giao
Source code đã tích hợp JUnit, TestNG, Selenium; tài liệu test case; minh chứng sử dụng AI; file workflow GitHub Actions.

## 11. Kết quả thực thi
| Nhóm | Số lượng | Kết quả |
|---|---|---|
| Unit test (JUnit 5, Mockito) | 46 lần chạy (42 + 4) | PASS (0 lỗi), chạy ngày 23/10/2026 |
| UI test (Selenium + TestNG) | 13 test | CHƯA CHẠY |
| GitHub Actions | 1 workflow | CHƯA CHẠY |