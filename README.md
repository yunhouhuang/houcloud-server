## HOUCLOUD快速开发基础套件 JAVA服务端

#### [管理后台页面代码地址](https://gitee.com/houcloud/admin)

#### [在线预览 admin.houcloud.com](https://admin.houcloud.com)
[阿里云服务器特惠活动](https://www.aliyun.com/activity/new/index?userCode=cygt84r6)

> 打不开？打不开就是服务器到期了，没钱续费了

登录账号密码

```text
username: admin
password: houcloud
```

其他账号的密码全部都是 houcloud


---
### 项目介绍

#### 技术栈

- Java17
- Springboot3.x
- MySQL
- Redis
- Logback
- MyBatis
- MyBatisPlus


#### 如何运行项目
1. 将 doc/db/houcloud.sql 恢复到你的数据库
2. 修改对应的 application.yml 文件，如数据库地址密码等
3. 安装连接的的 Redis
4. 运行 Application.class main 方法即可（有IDEA待Maven初始化后无脑点击运行即可）


#### 部署
1. 部署方式每个人都有点差异，这里不限制部署方式
2. 推荐自动流水线（如Jenkins 云效等）CICD + Docker 部署 
3. 项目中已包含了Dockerfile 和 docker-compose.yml （包含MySQL Redis） 脚本和配置文件可参考使用
4. 服务器 [阿里云新人特惠活动](https://www.aliyun.com/activity/new/index?userCode=cygt84r6)


#### 页面预览
![page1.png](docs%2Fimages%2Fpage1.png)
![page2.png](docs%2Fimages%2Fpage2.png)
![page3.png](docs%2Fimages%2Fpage3.png)
![page4.png](docs%2Fimages%2Fpage4.png)
![page5.png](docs%2Fimages%2Fpage5.png)
![page6.png](docs%2Fimages%2Fpage6.png)
---
### 写在最后

#### 如果有需要可以联系我微信

<img src="docs/images/IMG_4343.PNG" height="200" width="200" >

点个星星🌟，让这个套件越来越好用～
