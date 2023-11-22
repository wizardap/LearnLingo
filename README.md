# LEARNLINGO (Dictionary App)

#### Table of contents
1. [Tác giả](#author)
2. [Giới thiệu](#introduction)
3. [Tính năng nổi bật](#features)
4. [UML Diagram](#uml)
5. [Cách sử dụng](#user-guide)
6. [Phát triển tương lai](#future)
7. [Cài đặt](#installation)

# Tác giả <a name="author"></a>

Group AETốt
- Lê Hoàng Vũ 22021176
- Vi Văn Quân 22021149
- Nguyễn Đức Anh Tuấn 22021206

# Giới thiệu <a name="introduction"></a>

* Ứng dụng từ điển giúp cho người dùng có thể tra cứu, học từ vựng tiếng Anh một cách nhanh và hiệu quả nhất.
* Ứng dụng được viết bằng ngôn ngữ Java và sử dụng thư viện JavaFX để hỗ trợ tạo giao diện đồ hoạ.
* Ứng dụng còn tích hợp 2 game để người dùng nâng cao được vốn từ cũng như học tiếng anh hiệu quả.
* Ứng dụng còn có các chế độ tìm từ đồng nghĩa trái nghĩa giúp người học có thể hiểu rõ hơn về nghĩa của nó.

<p align="center">
<img width="640" height="480" src="https://i.imgur.com/64c19ON.png">
</p>

# Tính năng nổi bật <a name="features"></a>

* Tìm từ, mỗi khi gõ kí tự vào ô tìm kiếm, ứng dụng sẽ tự động đề xuất những từ bắt đầu bằng những kí tự đã nhập vào.
* Với mỗi từ sẽ có phần giải nghĩa hiển thị ở bên phải của khu vực tìm kiếm.
* Ứng dụng cho phép người dùng chuyển đổi từ điển, có thể sử dụng từ điển Anh-Việt hoặc Việt-Anh, và mỗi khi chuyển đổi, danh sách từ vựng sẽ được cập nhật lại ứng với từ điển mà người dùng muốn sử dụng.
* Ứng dụng cung cấp chức năng lưu từ vựng để người dùng có thể lưu lại những từ vựng cần thiết cho nhu cầu sử dụng.
* Với mỗi lần tra từ, ứng dụng sẽ tự động lưu lại lịch sử tra từ của người dùng theo thời gian gần nhất.
* Ứng dụng có chức năng thêm, xoá hoặc sửa lại nghĩa của từ vựng. Những thay đổi này sẽ được cập nhật vào dữ liệu từ điển của ứng dụng.
* Một tính năng nữa của ứng dụng đó là phát âm từ vựng. Ở mỗi phần giải nghĩa của từ tiếng Anh, sẽ có 2 giọng đọc là Anh-Anh và Anh-Mỹ để người dùng có thể học thêm được nhiều cách phát âm khác nhau. Ngoài ra, ứng dụng còn cho phép người dùng đổi tốc độ đọc của các từ. Với những từ tiếng Việt, ứng dụng cung cấp 1 giọng đọc duy nhất và vẫn có thể thay đổi được tốc độ đọc.
* Trong quá trình sử dụng, những thay đổi của người dùng liên quan tới phần cài đặt như: từ điển Anh-Việt hay Việt-Anh, giọng đọc và tốc độ đọc sẽ được lưu lại cho lần sử dụng sau.
* Người sử dụng có thể tra cứu từ vựng trực tiếp từ dữ liệu của ứng dụng hoặc có thể sử dụng Google API để dịch từ hoặc câu như ứng dụng Google Translate.
* LearnLingo còn sử dụng API của Datamuse để tìm từ đồng nghĩa và trái nghĩa của một từ: https://www.datamuse.com/api/
* LearnLingo còn sử dụng API của Voicerss để phát âm 1 từ tiếng anh theo UK và US: https://www.voicerss.org/    
* Người dùng có thể dịch từ hình ảnh có sẵn thành text trong chế độ Translation.
* App còn có 2 game cho người dùng có thể chơi và trau dồi thêm kiến thức tiếng anh.

# UML Diagram <a name="uml"></a>

<p align="center">
<img width="640" height="480" src="https://i.imgur.com/9kRnzXA.png">
</p>

# Cách sử dụng <a name="user-guide"></a>

* Video hướng dẫn sử dụng tại [Youtube](https://www.youtube.com/watch?v=EhFBY6Y7R4U).
<p align="center">
<img width="640" height="480" src="https://i.imgur.com/TmlMMm9.png">
</p>

# Phát triển tương lai <a name="future"></a>

1. Thêm nhiều từ điển hơn trong app.
2. Tích hợp nhiều game phức tạp.
3. Phát triển thêm tiện ích người dùng.

# Cài đặt <a name="installation"></a>

* Cài đặt JDK 8 tại [đây](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html), có thể sử dụng Intellij để chạy chương trình viết bằng Java.
1. Clone project từ repository
2. Mở project trong IDE Intellij
3. Tìm đến main/DictionaryApplication và run.
