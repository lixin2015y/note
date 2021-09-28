## netty

### 一、netty的介绍

### 1.1 netty介绍

+ 异步的、基于事件驱动的网络应用框架
+ 基于TCP协议，面向Clients端的高并发应用，
+ 本质是NIO框架，非阻塞式IO

### 1.2 BIO和NIO

+ BIO

  一个线程对应一个连接，客户端一个请求就需要一个线程进行处理，当连接不用的时候会造成不必要线程开销

  <img src="C:\Users\lee\AppData\Roaming\Typora\typora-user-images\image-20210928084221934.png" alt="image-20210928084221934" style="zoom: 67%;" />

  编程模型：

  	+  服务器端启动一个SeverSocket，
  	+  客户端启动Socket，对服务器进行通信，默认情况下，服务器端需要对每个连接启动线程进行通信
  	+  客户端发送请求后，先咨询服务器是否有线程相应，如果没有，则等待或者拒绝，
  	+  客户端等待响应（等待过程中有阻塞）

  

+ NIO

  服务器一个线程可以处理多个请求，客户端发送的连接请求都会注册到多路复用器上，多路复用器轮训到连接IO进行处理

  <img src="C:\Users\lee\AppData\Roaming\Typora\typora-user-images\image-20210928084647410.png" alt="image-20210928084647410" style="zoom: 67%;" />

   

+ AIO  异步非阻塞



