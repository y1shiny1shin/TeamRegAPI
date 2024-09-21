# 使用说明



Oracle Jdk17.0.10 

Mysql 5

使用时运行一次`init.sql`文件

需要修改 `application.properties` 中的数据库参数

运行成功后，访问 http://127.0.0.1:8800/t.html



NOTICE: 第一次导入项目，由于是从外网下载包，建议开梯子导入，再给IDLE配置代理

设置->外观与行为->系统设置->HTTP 代理，默认代理是127.0.0.1:7890 (开clash)

## 简单的接口文档

### 用户接口

#### /api/user/login
登陆接口

```
POST
{
	userid: "2022110416",
	password: "123456"
}
```

```
RETURN
{"msg":"登陆成功"}
{"msg":"登陆失败"}
{"msg":"服务器错误"} // 重启解决98%的问题
```



#### /api/user/register

注册接口，

JSON 传入 `userid username password academy` 参数

学院限定在

```
POST
{
	"userid":"12312312",
	"password":"123456",
	"username":"ceshi",
	"academy":"计算机科学学院"
}
```

```
RETURN
{"msg":"注册成功"}
{"msg":"注册失败"}
{"msg":"三者都不能为空"} // 输入了空的参数
```



#### /api/user/updatepwd

用户修改密码接口

```
POST
{
	"userid":"12312312",
	"password":"123456",
	"newpassword":"1234567"
}
```

```
RETURN
{"msg":"修改成功"}
{"msg":"新密码不能为空"}
{"msg":"登陆后修改密码"}
```

#### /api/user/getuserinfo

这个是自动从JSESSIONID中读已经登陆用户的基本信息的，返回值为JSON

```
RETURN
{
	"academy":"饥渴",
	"username":"禹人"
}
```



### 队伍接口



#### /api/team/joinpmsjtrace

加入平面设计专用赛道，

```
POST
{
  "userId":"1",
  "password":"1",
  "username":"1"
}
RETURN
{"msg":"用户未登录"}
{"msg":"请输入正确的学号和密码"}
{"msg":"该用户已加入过赛道，请勿重复加入"}
{"msg":"加入赛道成功"}
{"msg":"加入赛道失败"}
```



#### /api/team/kickmember

专供给队长踢出队员用

```
POST
{
  "teamName":"123",
  "memberName":"123",
  "memberId":"123"
}
RETURN
{"msg":"用户未登录"}
{"msg":"不可踢出空队员"}
{"msg":"队长不可踢出队长，如有需求请请解散队伍"}
{"msg":"没有权限踢出队员"}
{"msg":"踢出失败"}
{"msg":"踢出成功"}
```

#### /api/team/quitteam

专供给队员退出队伍

```
POST
{"teamName":"hahaha"}
RETURN
{"msg":"请先登陆后再操作"}
{"msg":"不可退出空队伍"}
{"msg":"用户不是该队伍的队员"}
{"msg":"用户不存在"}
{"msg":"退出成功"}
{"msg":"退出失败"}
```

#### /api/team/destroy

删除队伍，

```
POST
{
  "userId":"1",
  "teamName":"asdfs",
  "password":"1"
}
RETURN
{"msg":"用户未登录"}
{"msg":"请输入正确的学号和密码"}
{"msg":"该用户不是该队队长，无法删除该队或该队伍不存在"}
{"msg":"该队伍不存在"}
{"msg":"删除成功"}
```

#### /api/team/create

新建队伍，默认新建队伍的时候不选择赛道

```
POST
{
  "teamName":"xxxxx",
  "captainName":"1",
  "captainId":"1"
}
RETURN
{"msg":"用户未登陆"}
{"msg":"该队伍名已存在"}
{"msg":"服务器错误"}
{"msg":"该用户已加入了3个队伍，无法创建队伍"}
{"msg":"创建成功"}
{"msg":"创建失败"}
{"msg":"Sql语句执行失败"}
```

#### /api/team/groupjoin

队伍加入赛道，仅限队长使用

赛道类型限制在了`{"计算机综合素质竞赛类","微课与课件设计类","程序应用设计类","数字媒体设计类","算法程序设计类"}`

```
POST
{
  "teamName":"123",
  "groupType":"算法程序设计类",
  "captainId":"1"
}
RETURN
{"msg":"该队伍已经参加了三个赛道，到达参加赛道上限"}
{"msg":"该队伍已经参加了该赛道，请勿重复参加赛道"}
{"msg":"赛道类型不正确，请重新选择赛道类型"}
{"msg":"加入赛道成功"}
{"msg":"加入赛道失败"}
{"msg":"该队伍不存在"}
{"msg":"请输入正确的信息"}
{"msg":"用户未登录"}
{"msg":"用户不是该队队长，只能由队长选择赛道"}
```

#### /api/team/join

未做接口验证，直接添加即可

```
POST
{
  "userId":"1",
  "teamName":"hahaha"
}
RETURN
{"msg":"该用户不存在"}
{"msg":"该用户已经加入了该队伍"}
{"msg":"该队伍已经满员"}
{"msg":"此人已经加入了三个队伍"}
{"msg":"数据库执行失败"}
{"msg":"加入成功"}
```

#### /api/team/quitgroup

退出赛道接口，仅限队长可退出赛道

```
POST
{
  "groupName":"算法程序设计类",
  "teamName":"123"
}
RETURN
{"msg":"非队长不能退出赛道"}
{"msg":"退出成功"}
{"msg":"退出失败"}
```

#### /api/team/select

查询登陆的用户所在队伍的所有信息，不需传入数据

```
RETURN
加入三个队伍的状态
{
  "team3": [null],
  "team1": [
    {
      "teamName": "海底小纵队",
      "groupType1": "计算机综合素质竞赛类",
      "groupType2": null,
      "groupType3": null,
      "memberName3": "lll",
      "memberName1": "jjj",
      "memberName2": "hhhh",
      "academy1": "计算机科学学院",
      "academy3": "计算机科学学院",
      "academy2": "计算机科学学院",
      "memberId1": "2022110499",
      "memberId2": "2022110400",
      "memberId3": "2022110388"
    }
  ],
  "team2": [null]
}
```









