# Minh chứng sử dụng AI trong kiểm thử - ShopTest

- Sinh viên: Ninh
- Môn: Kiểm thử nâng cao cùng AI (Java)
- Công cụ AI đã dùng: Claude (Anthropic) trên claude.ai
- Ngày thực hiện phần AI Giai đoạn 1, 2, 5: 31/10/2026
- Phạm vi: chỉ ghi phần sử dụng AI cho hoạt động kiểm thử theo yêu cầu Y6. Mã nguồn ứng dụng ShopTest được xem là đã có sẵn.


---

## Giai đoạn 1: Thiết kế Test Case và Test Plan (Gen AI + RAG)

**Tài liệu RAG đưa cho AI:** docs/BusinessRules.md (quy tắc nghiệp vụ BR1 đến BR12 và bảng thông báo lỗi).

**Yêu cầu thực tế đã giao cho AI:** sinh viên yêu cầu Claude "hoàn thiện toàn bộ phần Minh chứng sử dụng AI, tập trung vào phần kiểm thử". Claude thực hiện bằng cách dùng BusinessRules.md làm tài liệu nguồn (RAG), sinh Test Plan ngắn và bộ test case, rồi đối chiếu với bộ 33 test case sinh viên đã có (TC_U01 đến TC_UI13).

**Bản AI sinh ra (nguyên văn):**

Test Plan ngắn do AI sinh:
1. Mục tiêu: xác nhận Đăng ký, Đăng nhập, Phân quyền và quản lý Danh mục hoạt động đúng theo BR1 đến BR12.
2. Phạm vi: các luồng giao diện và quy tắc dữ liệu trong BusinessRules.md. Ngoài phạm vi: hiệu năng, bảo mật chuyên sâu.
3. Kỹ thuật: phân vùng tương đương, giá trị biên, bảng quyết định, kiểm thử chuyển trạng thái.
4. Công cụ: JUnit 5, Mockito (unit); Selenium + TestNG (UI); Maven; GitHub Actions.
5. Tiêu chí ra: test mức ưu tiên Cao đều pass; lỗi phát hiện được ghi lại.
6. Rủi ro: dữ liệu lưu trong bộ nhớ nên test có thể để lại dữ liệu thừa; quy tắc nghiệp vụ chưa nêu rõ có phân biệt hoa thường hay không ở một số trường.

Bộ test case do AI sinh:

| ID | Chức năng | Kỹ thuật | Dữ liệu vào | Kết quả mong đợi | Ưu tiên |
|---|---|---|---|---|---|
| AI-01 | Đăng ký, username 4 ký tự | BVA | username dài 4 | Hợp lệ (biên dưới) | Cao |
| AI-02 | Đăng ký, username 20 và 21 ký tự | BVA | dài 20; dài 21 | 20 hợp lệ; 21 báo lỗi độ dài | Cao |
| AI-03 | Đăng ký, username có khoảng trắng | EP | "ni nh" | Báo lỗi username | Trung bình |
| AI-04 | Đăng ký, password 5, 6, 30, 31 ký tự | BVA | độ dài 5, 6, 30, 31 | 5 và 31 báo lỗi; 6 và 30 hợp lệ | Cao |
| AI-05 | Đăng ký, email thiếu @ | EP | "abc.com" | "Email is invalid" | Trung bình |
| AI-06 | Đăng ký, confirm không khớp | Bảng quyết định | confirm khác password | "Confirm password does not match" | Trung bình |
| AI-07 | Đăng ký, trùng username đã có | EP | admin | "Username already exists" | Cao |
| AI-08 | Đăng ký, trùng username khác hoa thường | EP | Admin | Quy tắc chưa nêu rõ; AI giả định báo "Username already exists" | Trung bình |
| AI-09 | Đăng ký thành công | EP | dữ liệu hợp lệ | Về /login kèm "Registration successful. Please log in." | Cao |
| AI-10 | Tài khoản mới có vai trò USER | EP | đăng ký rồi đăng nhập | Không vào được /admin/*, lỗi 403 | Cao |
| AI-11 | Đăng nhập đúng | EP | admin / 123456 | Vào /home | Cao |
| AI-12 | Đăng nhập sai mật khẩu | EP | admin / sai | "Invalid username or password" | Cao |
| AI-13 | Đăng nhập username khác hoa thường | EP | ADMIN / 123456 | Quy tắc chưa nêu rõ; AI giả định báo sai thông tin | Trung bình |
| AI-14 | Đăng nhập password trống | Bảng quyết định | admin / rỗng | "Password is required" | Cao |
| AI-15 | Đăng nhập username toàn khoảng trắng | EP | "   " / 123456 | "Username is required" | Trung bình |
| AI-16 | Chưa đăng nhập vào /home | EP | mở /home | Chuyển về /login | Cao |
| AI-17 | ADMIN thấy nút Manage categories | EP | đăng nhập admin | Có nút Manage categories | Trung bình |
| AI-18 | USER không thấy nút Manage categories | EP | đăng nhập USER | Không có nút | Trung bình |
| AI-19 | Logout rồi vào /home | Chuyển trạng thái | Logout, mở /home | Chuyển về /login | Cao |
| AI-20 | Danh mục tên 50 và 51 ký tự | BVA | dài 50; dài 51 | 50 thêm được; 51 báo lỗi | Cao |
| AI-21 | Danh mục trùng khác hoa thường | EP | laptop | "Category name already exists" (BR11) | Cao |
| AI-22 | Danh mục tên toàn khoảng trắng | EP | "   " | "Category name is required" | Trung bình |
| AI-23 | Sửa danh mục thành tên trùng danh mục khác | EP | sửa Phone thành Laptop | "Category name already exists" | Trung bình |
| AI-24 | Sửa danh mục giữ nguyên tên | EP | Update không đổi tên | Không báo trùng với chính nó | Trung bình |
| AI-25 | Delete rồi bấm Cancel ở hộp thoại | Chuyển trạng thái | Delete, Cancel | Danh mục vẫn còn | Trung bình |
| AI-26 | Vòng đời CRUD đầy đủ | Chuyển trạng thái | tạo, sửa, xóa | Mỗi bước đúng | Cao |

**Đối chiếu bản AI với bộ test case sinh viên đã có (phân tích của AI):**

| Test case AI | Tình trạng so với bộ 33 test case hiện có |
|---|---|
| AI-01 đến AI-07, AI-09 đến AI-12, AI-14, AI-26 | Đã có (TC_U01 đến TC_U16, TC_UI01 đến TC_UI10) |
| AI-08, AI-13 | Thiếu, và BusinessRules.md chưa nêu rõ phân biệt hoa thường |
| AI-15 | Thiếu |
| AI-16 | Thiếu (TC_UI09 chỉ kiểm tra /admin/categories) |
| AI-17, AI-18 | Thiếu (đã bổ sung AI-18 thành TC_UI16) |
| AI-19 | Thiếu (đã bổ sung thành TC_UI14) |
| AI-20 | Thiếu giá trị 50 (TC_UI13 chỉ có 51) |
| AI-21 | Thiếu (đã bổ sung thành TC_UI15) |
| AI-22, AI-23, AI-24, AI-25 | Thiếu |

**Bản mình chỉnh sửa:** docs/TestPlan.md và TestCases.docx (đã thêm TC_UI14, TC_UI15, TC_UI16 từ các chỗ thiếu ở trên).

**Những chỗ mình đã sửa hoặc quyết định:**
| STT | Vấn đề | Mình sửa thành | Lý do |
|---|---|---|---|
| 1 | Bản AI có AI-08 và AI-13 nhưng kết quả mong đợi chỉ là giả định | [ĐIỀN: ví dụ làm rõ BR5 trong BusinessRules.md, hoặc bỏ hai test case này] | Quy tắc nghiệp vụ không nêu rõ phân biệt hoa thường với username |
| 2 | Bộ test hiện có thiếu các case ở bảng đối chiếu | [ĐIỀN: đã thêm TC_UI14, TC_UI15, TC_UI16; các case còn lại để vòng sau hay thêm luôn] | [ĐIỀN] |
| 3 | [ĐIỀN: thêm một điểm mình tự thấy AI làm chưa đúng] | [ĐIỀN] | [ĐIỀN] |

---

## Giai đoạn 2: Viết Page Object và Test Script (Gen AI)

**Việc cần làm:** viết thêm 3 test cho 3 chỗ còn thiếu ở Giai đoạn 1 (AI-19, AI-21, AI-18), tức TC_UI14, TC_UI15, TC_UI16.

**Yêu cầu thực tế đã giao cho AI:** như Giai đoạn 1. Claude chọn 3 test từ danh sách thiếu và viết code bằng cách dùng lại các lớp Page Object có sẵn (LoginPage, RegisterPage, CategoryPage, BaseTest).

**Code AI sinh ra:**
- LoginPage.java: thêm hàm `logout()` (bấm nút Logout).
- ExtraUiTest.java (file mới), gồm 3 test:
  - `logoutThenHomeRedirectsToLogin` (TC_UI14): đăng nhập admin, Logout, chờ URL chứa /login, mở /home, kiểm tra bị chuyển về /login.
  - `createDuplicateNameDifferentCase` (TC_UI15): đăng nhập admin, thêm danh mục "laptop", kiểm tra báo "Category name already exists".
  - `normalUserDoesNotSeeManageLink` (TC_UI16): đăng ký USER, đăng nhập, kiểm tra không có phần tử `linkCategories`.

**Code cuối cùng của mình:** các file LoginPage.java và ExtraUiTest.java trong src/test/java/com/ninh/asm/ui.

**Khác biệt giữa code AI và code cuối, và lý do:**
[ĐIỀN: nếu không sửa gì thì ghi "Giữ nguyên code AI sinh ra"; nếu có sửa thì ghi đã sửa gì và vì sao]

**Kết quả chạy test:**
| Test | Kết quả |
|---|---|
| TC_UI14 | [ĐIỀN: Pass hoặc Fail] |
| TC_UI15 | [ĐIỀN: Pass hoặc Fail] |
| TC_UI16 | [ĐIỀN: Pass hoặc Fail] |

---

## Giai đoạn 3: Xử lý lỗi và Debug (đưa stack trace cho AI phân tích)

### Lỗi 1: biên dịch thất bại, báo thiếu package JUnit và Mockito

**Log lỗi (trích):**
```
[ERROR] .../src/main/java/com/ninh/asm/unit/AuthValidatorTest.java:[3,36] package org.junit.jupiter.api does not exist
[ERROR] .../src/main/java/com/ninh/asm/unit/LoginServletTest.java:[3,26] package org.mockito does not exist
[INFO] 100 errors
BUILD FAILURE
```
**AI phân tích:** đường dẫn file lỗi nằm trong src/main/java. Maven chỉ nạp thư viện JUnit và Mockito (scope test) cho src/test/java, nên biên dịch src/main thì báo thiếu package. Code và pom.xml đúng, chỉ sai vị trí đặt file.
**Mình xử lý:** chuột phải package com.ninh.asm.unit, chọn Refactor, Move, chuyển sang src/test/java. Cập nhật Maven rồi chạy lại.

### Lỗi 2: "No compiler is provided in this environment"

**Log lỗi (trích):**
```
Building PolyOE - Online Entertainment 1.0 (poly.edu:PolyOE)
[ERROR] No compiler is provided in this environment. Perhaps you are running on a JRE rather than a JDK?
BUILD FAILURE
```
**AI phân tích:** log này thuộc project PolyOE (mình chạy nhầm project), và Maven đang chạy bằng JRE nên không có trình biên dịch javac.
**Mình xử lý:** chạy Maven build trên đúng project ShopTest. Khai báo JDK 17 trong Window, Preferences, Java, Installed JREs. Ở Run As, Maven build, tab JRE chọn JDK 17.

### Lỗi 3: lỗi khi chạy test, Tests run: 0

**Log lỗi (trích):**
```
[ERROR] 'java.util.Set org.junit.platform.engine.TestDescriptor.getAncestors()'
[ERROR] There was an error in the forked process
Tests run: 0, Failures: 0, Errors: 0, Skipped: 0
BUILD FAILURE
```
**AI phân tích:** các thành phần của JUnit Platform không cùng phiên bản (nhiều khả năng testng-engine kéo vào một bản cũ hơn), nên phương thức getAncestors không tồn tại và test bị sập trước khi chạy. AI chưa soi cây phụ thuộc để chứng minh, đây là suy luận từ thông báo lỗi.
**Mình xử lý:** thêm junit-bom (dependencyManagement) để mọi thành phần JUnit dùng cùng phiên bản 5.11.3, thêm junit-platform-launcher vào pom.xml, rồi Maven, Update Project và chạy lại.
**Kết quả sau khi sửa:** Tests run: 46, Failures: 0, Errors: 0, Skipped: 0, BUILD SUCCESS (chạy ngày 23/10/2026).

### Lỗi 4 trở đi (nếu gặp khi chạy Selenium hoặc GitHub Actions)
- Log lỗi: [ĐIỀN hoặc xóa mục này nếu không gặp]
- AI phân tích: [ĐIỀN]
- Mình xử lý: [ĐIỀN]

---

## Giai đoạn 4: Viết GitHub Actions (AI giải thích workflow)

**Yêu cầu thực tế đã giao cho AI:** sinh viên hỏi AI "Workflow ci.yml chạy theo thứ tự nào, báo cáo được lưu ở đâu?" và nhận giải thích bên dưới.

**File YAML:** .github/workflows/ci.yml

**AI giải thích (tóm tắt):**
1. Kích hoạt khi push, pull request, hoặc chạy tay (có ô chọn nhóm test, mặc định smoke).
2. Chạy trên ubuntu-latest, lấy code, cài JDK 17 và cache Maven.
3. mvn package: build file WAR và chạy unit test.
4. Cài Tomcat 10.1, triển khai ShopTest.war, chờ ứng dụng sẵn sàng.
5. Chạy Selenium headless theo nhóm đã chọn (-Dgroups).
6. Lưu báo cáo và ảnh chụp lỗi, upload lên Artifacts (if: always() để luôn lưu kể cả khi test fail).

**Giải thích bằng lời của mình:**
[ĐIỀN: viết lại 4 đến 6 câu bằng lời của bạn]

**Kết quả chạy trên GitHub:** [ĐIỀN: xanh hay đỏ. Nếu đỏ, ghi log lỗi, AI phân tích và cách mình sửa]

---

## Giai đoạn 5: Tối ưu và Review (AI review code)

**Yêu cầu thực tế đã giao cho AI:** sinh viên yêu cầu Claude review mã Page Object và test (BasePage, LoginPage, CategoryPage, AuthUiTest, CategoryCrudTest) và đề xuất cải tiến.

**Danh sách đề xuất của AI và quyết định của mình:**
| STT | Đề xuất của AI | AI khuyến nghị | Quyết định của mình | Lý do của mình |
|---|---|---|---|---|
| 1 | Hàm isDisplayed và textOf trong BasePage chờ đủ 10 giây khi phần tử không tồn tại, làm các kiểm tra "không có" (ví dụ assertFalse(isLoggedIn()) khi sai mật khẩu) chậm. Nên dùng findElements để kiểm tra "không tồn tại" mà không chờ | Nhận | [ĐIỀN: Nhận hoặc Bỏ] | [ĐIỀN] |
| 2 | Test loginSuccess kiểm tra URL ngay sau khi bấm Login, có thể chạy sớm hơn lúc trang chuyển hướng. Nên chờ urlContains("/home") rồi mới kiểm tra | Nhận | [ĐIỀN] | [ĐIỀN] |
| 3 | Các thông báo lỗi mong đợi viết trực tiếp trong nhiều test. Nên gom thành hằng số trong một lớp để sửa một chỗ | Nhận (ưu tiên thấp) | [ĐIỀN] | [ĐIỀN] |
| 4 | CategoryPage ghép tên vào XPath nên sẽ lỗi nếu tên có dấu nháy đơn. Nên xử lý dấu nháy | Bỏ | [ĐIỀN] | [ĐIỀN] |
| 5 | BaseTest mở trình duyệt mới cho mỗi test nên chậm. Có thể dùng chung trình duyệt | Bỏ (mỗi test độc lập, tránh dính phiên đăng nhập của test trước) | [ĐIỀN] | [ĐIỀN] |
| 6 | Test TC_UI08 kiểm tra mã 403 bằng cách tìm chữ "403" trong nội dung trang, khá dễ vỡ | Bỏ (Selenium không đọc mã HTTP trực tiếp) | [ĐIỀN] | [ĐIỀN] |
| 7 | Test không dọn dữ liệu sau khi chạy (user và danh mục tạo ra còn lại trong bộ nhớ) | Bỏ (restart Tomcat là về ban đầu, đã ghi trong Test Plan) | [ĐIỀN] | [ĐIỀN] |
| 8 | Bổ sung các test còn thiếu theo bảng đối chiếu ở Giai đoạn 1 | Nhận | [ĐIỀN] | [ĐIỀN: ví dụ đã thêm TC_UI14 đến TC_UI16] |

**Phần code đã sửa theo đề xuất được nhận:** [ĐIỀN: ghi file và nội dung đã sửa, hoặc ghi "chưa sửa code, chỉ ghi nhận"]