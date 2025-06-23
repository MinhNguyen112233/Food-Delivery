# 🍕 Food Delivery Backend

Hệ thống backend hoàn chỉnh cho ứng dụng giao đồ ăn trực tuyến, được xây dựng bằng Java Spring Boot với các tính năng quản lý nhà hàng, đặt hàng và giao hàng.

## 📋 Mục lục

- [Tổng quan](#tổng-quan)
- [Tính năng chính](#tính-năng-chính)
- [Công nghệ sử dụng](#công-nghệ-sử-dụng)
- [Đóng góp](#đóng-góp)

## 🎯 Tổng quan

Food Delivery Backend là một hệ thống backend mạnh mẽ được thiết kế để hỗ trợ các ứng dụng giao đồ ăn trực tuyến. Dự án cung cấp các API RESTful hoàn chỉnh để quản lý nhà hàng, thực đơn, đơn hàng, và người dùng với tính bảo mật cao.

## ✨ Tính năng chính

### 👤 Quản lý người dùng chưa đăng nhập
- **Đăng nhập/Đăng ký**: Hệ thống xác thực an toàn với JWT
- **Xem thông tin quán ăn**: Duyệt danh sách nhà hàng và thông tin chi tiết
- **Xem thực đơn**: Truy cập thực đơn các quán ăn
- **Danh mục món ăn**: Phân loại món ăn theo loại (khai vị, món chính, tráng miệng...)
- **Tìm kiếm và lọc**: Tìm kiếm quán ăn theo tên, địa điểm, loại hình

### 🛍️ Quản lý khách hàng (đã đăng nhập)
- **Quy trình đặt hàng hoàn chỉnh**:
  - Chọn món ăn từ thực đơn
  - Xác nhận đơn hàng
  - Chọn địa chỉ giao hàng
  - Đặt hàng thành công
- **Quản lý thông tin cá nhân**: Cập nhật tên, địa chỉ, số điện thoại
- **Quản lý giỏ hàng**: Thêm, xóa, chỉnh sửa món ăn trong giỏ
- **Hệ thống thanh toán**: Hỗ trợ thanh toán tiền mặt và ví điện tử
- **Quán ăn yêu thích**: Lưu và quản lý danh sách quán ăn yêu thích
- **Theo dõi đơn hàng**: Cập nhật trạng thái đơn hàng theo thời gian thực
  - Đã xác nhận
  - Đang chuẩn bị
  - Đang giao hàng
  - Đã giao thành công

## 🛠️ Công nghệ sử dụng

### Backend Framework
- **Java 17+**: Ngôn ngữ lập trình chính
- **Spring Boot 3.x**: Framework chính cho ứng dụng
- **Spring Data JPA**: ORM và quản lý cơ sở dữ liệu
- **Spring Security**: Bảo mật và xác thực
- **Spring Web**: Xây dựng RESTful API

### Database
- **MySQL**: Cơ sở dữ liệu chính

### Tools & Libraries
- **Maven**: Quản lý dependencies
- **JWT**: JSON Web Tokens cho authentication
- **Hibernate**: ORM implementation

### Development Tools
- **IntelliJ IDEA**: IDE chính
- **Postman**: Testing API
- **Git**: Version control

## 📝 License

Dự án này được phân phối dưới giấy phép MIT. Xem file `LICENSE` để biết thêm chi tiết.

## 👨‍💻 Tác giả

- **Minh Nguyen** - *Initial work* - [MinhNguyen112233](https://github.com/MinhNguyen112233)

## 📞 Liên hệ

Nếu bạn có bất kỳ câu hỏi nào, vui lòng liên hệ:
- Email: minhcoi3010@gmail.com

⭐ Nếu dự án này hữu ích, hãy cho một star để ủng hộ nhé!
