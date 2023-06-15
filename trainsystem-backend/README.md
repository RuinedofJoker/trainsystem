jenkins部署流程:
先在服务器上安装并配置好maven和git，已经一个jre11
使用 yum -y install lrzsz 安装Linux/Unix同Windows进行ZModem文件传输的命令行工具
将maven和jenkins的包传输到linux服务器

java11安装流程:
首先查看安装了几个java
update-alternatives --display java
看看里面有没有java11
没有的话使用yum search openjdk查看可安装的java版本，选择一个java11版本

安装完进入/etc/alternatives/目录可看到你刚刚下的java
我的在/etc/alternatives/jre_11_openjdk

将jenkins的包放在当前用户目录下(root用户为/root，非root用户为/home/用户名)
进入当前用户目录
使用运行jenkins nohup /etc/alternatives/jre_11_openjdk/bin/java -jar jenkins.war --httpPort=8088
（我这里把jenkins的端口设置到了8088）
进入jenkins网站(localhost:8088)
设置账号密码，下载插件(推荐下载默认插件)
配置maven和git
取gitee上设置hook，绑定到jenkins里
