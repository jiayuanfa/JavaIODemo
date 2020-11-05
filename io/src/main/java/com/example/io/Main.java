package com.example.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import okio.Buffer;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

public class Main {

    public static void main(String[] args) {
//        io4();
//        bufferOutput();
//        bufferOutputAutoClose();
//        readAndWrite();
//        socketIO();
//        serviceSocket();
//        okio();
        okioBuffer();
    }

    /**
     * 使用Okio来读取文件数据
     */
    public static void okio() {
        try(Source source = Okio.source(new File("./io/text.txt"))) {
            Buffer buffer = new Buffer();
            source.read(buffer, 1024);
            System.out.println(buffer.readUtf8Line());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用Okio来读取文件数据
     */
    public static void okioBuffer() {
        try(BufferedSource source = Okio.buffer(Okio.source(new File("./io/text.txt")))) {
            System.out.println(source.readUtf8Line());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读文件
     */
    public static void io2() {
        try {
            InputStream inputStream = new FileInputStream("./io/text.txt");
            System.out.println((char) inputStream.read());
            System.out.println((char) inputStream.read());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接输出字节式的读文件
     * 第一次插管子出来的的字节流
     * 第二次插管子出来的是字符
     */
    public static void io3() {
        try {
            InputStream inputStream = new FileInputStream("./io/text.txt");
            Reader reader = new InputStreamReader(inputStream);
            System.out.println(reader.read());
//            System.out.println(reader.read());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 整行读取文件
     * BufferReader 提供了整行读取文件的方法 例如 readLine()
     * 写进try后面的小括号里面是为了自动关闭
     */
    public static void io4() {
        try (InputStream inputStream = new FileInputStream("./io/text.txt");
             Reader reader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            System.out.println(bufferedReader.readLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入文件
     */
    public static void io1() {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream("./io/text.txt");
            try {
                outputStream.write('a');
                outputStream.write('b');
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 使用Buffer来写入文件
     */
    public static void bufferOutput() {
        try (OutputStream outputStream = new FileOutputStream("./io/text.txt")) {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            bufferedOutputStream.write('c');
            bufferedOutputStream.write('d');
            bufferedOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用Buffer来写入文件
     */
    public static void bufferOutputAutoClose() {
        try (OutputStream outputStream = new FileOutputStream("./io/text.txt");
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {
            bufferedOutputStream.write('e');
            bufferedOutputStream.write('f');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读一个文件，写入到另一个文件中
     * 每次读byte[1024]大小
     * 当读的结果不等于-1的时候，就写入即可
     * 使用Buffer提高性能
     */
    public static void readAndWrite() {
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream("./io/text.txt"));
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream("./io/buffer_text.txt"))) {
            byte[] data = new byte[1024];
            int read;
            while ((read = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ScoketIO
     * 输入输出流示例
     */
    public static void socketIO() {
        try (Socket socket = new Socket("henCoder.com", 80);
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            bufferedWriter.write("GET / HTTP/1.1\n" +
                    "Host: www.example.com\n\n");
            bufferedWriter.flush();
            String message;
            while ((message = bufferedReader.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 服务端
     * Socket实现
     */
    public static void serviceSocket() {
        try(ServerSocket serverSocket = new ServerSocket(80);
            Socket socket = serverSocket.accept();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            writer.write("HTTP/1.1 200 OK\n" +
                    "Server: nginx/1.14.0 (Ubuntu)\n" +
                    "Date: Wed, 04 Nov 2020 16:28:24 GMT\n" +
                    "Content-Type: text/html\n" +
                    "Content-Length: 612\n" +
                    "Last-Modified: Fri, 11 Oct 2019 04:19:03 GMT\n" +
                    "Connection: keep-alive\n" +
                    "ETag: \"5da002b7-264\"\n" +
                    "Accept-Ranges: bytes\n" +
                    "\n" +
                    "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<title>Welcome to nginx!</title>\n" +
                    "<style>\n" +
                    "    body {\n" +
                    "        width: 35em;\n" +
                    "        margin: 0 auto;\n" +
                    "        font-family: Tahoma, Verdana, Arial, sans-serif;\n" +
                    "    }\n" +
                    "</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<h1>Welcome to nginx!</h1>\n" +
                    "<p>If you see this page, the nginx web server is successfully installed and\n" +
                    "working. Further configuration is required.</p>\n" +
                    "\n" +
                    "<p>For online documentation and support please refer to\n" +
                    "<a href=\"http://nginx.org/\">nginx.org</a>.<br/>\n" +
                    "Commercial support is available at\n" +
                    "<a href=\"http://nginx.com/\">nginx.com</a>.</p>\n" +
                    "\n" +
                    "<p><em>Thank you for using nginx.</em></p>\n" +
                    "</body>\n" +
                    "</html>\n\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
