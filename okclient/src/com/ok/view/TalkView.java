package com.ok.view;

import com.ok.common.Message;
import com.ok.common.MessageType;
import com.ok.util.SocketUtil;
import com.ok.util.TalkThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;

public class TalkView extends JFrame {

    String currentName;
    String friendName;

    //聊天界面组件
    JTextArea jTextArea = null;//展示历史记录
    JScrollPane jScrollPane = null;
    //输入信息和发送按钮
    JTextField jTextField = null;
    JButton sendButton = null;
    JPanel southJPanel = null;

    //和某人的聊天通道
    Socket socket = null;

    public TalkView(String currentName,String friendName){
        this.currentName = currentName;
        this.friendName = friendName;
    }

    public void createFrame(){
        jTextArea = new JTextArea();
        jTextArea.setFont(new Font(Font.DIALOG,Font.PLAIN,15));
        jTextArea.setEnabled(false);
        jScrollPane = new JScrollPane(jTextArea);
        //历史聊天记录放中间
        this.add(jScrollPane,BorderLayout.CENTER);

        jTextField = new JTextField(13);
        jTextField.setFont(new Font(Font.DIALOG,Font.PLAIN,18));
        sendButton = new JButton("发送");
        sendButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                String content = jTextField.getText();
                content = currentName+"对"+friendName+"说："+content;
                jTextArea.append(content+"\n");
                jTextField.setText("");

                //内容message，包括socket编号
                Message requestMessage = new Message();
                requestMessage.setMessageType(MessageType.TALK);
                requestMessage.setUserName(currentName);
                requestMessage.setFriendName(friendName);
                requestMessage.setContent(content);

                try {
                    SocketUtil.getSocketUtil().sendMessage(socket,requestMessage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        southJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southJPanel.add(jTextField);
        southJPanel.add(sendButton);
        this.add(southJPanel,BorderLayout.SOUTH);


        this.setTitle(currentName+"和"+friendName+"聊天界面");
        this.setBounds(450,180,400,300);
        this.setVisible(true);
        this.setResizable(false);

        try{
            socket = new Socket("127.0.0.1",8888);
            Message requestMessage = new Message();

            //告知服务端message用于和服务端建立连接，curname和friname用于给socket编号用的
            requestMessage.setMessageType(MessageType.TALK_CONNECTION);
            requestMessage.setUserName(currentName);
            requestMessage.setFriendName(friendName);

            //发送到服务端
            SocketUtil.getSocketUtil().sendMessage(socket,requestMessage);

            //建立连接后,为了把内容放到面板上需要传面板过去
            TalkThread talkThread = new TalkThread(socket,jTextArea);
            talkThread.start();

        }catch (Exception e){
            e.printStackTrace();
        }

        //正常关闭逻辑
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Message requestMessage = new Message();
                requestMessage.setMessageType(MessageType.TALK_CLOSE);
                requestMessage.setUserName(currentName);
                requestMessage.setFriendName(friendName);

                try{
                    SocketUtil.getSocketUtil().sendMessage(socket,requestMessage);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

    }
}
