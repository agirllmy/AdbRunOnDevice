package com.test.adb.adbtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {
    public static void main(String[] args) {
        try {
            // 1.建立客户端socket连接，指定服务器位置及端口
            Socket socket = new Socket("localhost", 5037);
            // 2.得到socket读写流
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            // 输入流
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            // 3.利用流按照一定的操作，对socket进行读写操作
            // String info =
            // "QAAAALXYw/xjqs8YscrNK6AVWgQUGlCzQsD0341Una14fUQRgl9O5apeQjWtx4jNAOFDZpe8ouBr+V8CH8K3Sli/X8b4Wacp0Rhpdgb0LTIVZf2eHByF0H4IVxBhxG0kHSw5ahllBjEd6CFQguyuFc7Ic48KjKVhR4/ewehuVObKvTZPKQOkC/NL6Ql1GRbzyXwEOjf5Evb+dJqXzonebQpRffB2dUF8UQoHxuZQsAAEUWR0VS5EeXay4DKvbMbS3m0E82jSQ6bh+d21rsycQqkL1rblgpjQDsCFML6copiF3H/XZogf0S5XUdBtb80EO5l2XB14Zdsu96Ywlcxdb3T/2mQrMA+i8R8AoOgjpTfUij0CBSKZtBcEsOFsuTvU6oTDAax0a5Sx42Uq6sdYEXCSreCFHgcl4mrvFsJQNcfkeeFlaZSagnh9lvK3T6D//tInrj3klvAXBk1yHtM12TSktuInZ0qZ1wZr2bHvr6rt+U0c+yQZiIRKehqpyWOFO8BOOcgz72oX1TZ2daFV37B28GFTHdKTkIsPm1KcRkX6Ho43qaMXpOWbSue5rMQxy8RmgUIhfgp7fsFlY/Ph9jy9sSoSmdA/ivGcjR0WBMt3bd/C6isK6M9HZg8w9YCG3nl6yzH65SGFBs1Mn6e5q/yY3wUPDvgSHqNY3NQ9S9Ms/33zDeH+mwEAAQA= unknown@unknown";
            // String info = "000Chost:devices";
            String info = "0021host:transport:192.168.1.100:5555";
            //
            // String info = "0008shell:ls";

            // String info = "0014host:transport-local";
            // String info = "0008shell:ls";
            pw.write(info);
            pw.flush();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            info = "000Chost:devices";
            // info = "0008shell:ls";
            // info = "0027shell:adb -s emulator-5554 shell:logcat";
            // info = "001ashell:logcat -v threadtime";
            // info = "shell:export ANDROID_LOG_TAGS=\"''\"; exec logcat";
            pw.write(info);
            pw.flush();

            // socket.shutdownOutput();
            // 接收服务器的相应
            String reply = null;

            // try {
            // Thread.sleep(1000);
            // } catch (InterruptedException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // }

            while (!((reply = br.readLine()) == null)) {
                System.out.println("接收服务器的信息：" + reply);
            }

            // try {
            // Thread.sleep(2000);
            // } catch (InterruptedException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // }
            // if (null == socket || !socket.isConnected()) {
            // socket = new Socket("localhost", 5037);
            // System.out.println("socket is null ");
            // } else {
            // System.out.println("socket is not null ");
            // }
            //
            // os = socket.getOutputStream();
            // pw = new PrintWriter(os);
            // // 输入流
            // is = socket.getInputStream();
            // br = new BufferedReader(new InputStreamReader(is));
            // // 3.利用流按照一定的操作，对socket进行读写操作
            // info = "0021host:transport:192.168.1.100:5555";
            // pw.write(info);
            // pw.flush();
            // socket.shutdownOutput();
            // // 接收服务器的相应
            // reply = null;
            // while (!((reply = br.readLine()) == null)) {
            // System.out.println("接收服务器的信息：" + reply);
            // }

            // // 4.关闭资源
            br.close();
            is.close();
            pw.close();
            os.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}