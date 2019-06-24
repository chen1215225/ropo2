# 服务配置

解压winser.zip

> 修改 `project.xml`

```
<service>
    <id>Lineview_Schedule</id>
    <name>Lineview_Schedule</name>
    <description>Lineview定时调度任务，实现自动备注</description>
    <env name="MYAPP_HOME" value="%BASE%"/>
    <executable>java</executable>
    <arguments>-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=prod -jar</arguments>
    <logmode>rotate</logmode>
</service>
```

> 重命名project.exe为Lineview_Schedule.exe

> 拷贝bees-schedule.jar到winser目录

# 启动服务

```
CMD执行 Lineview_Schedule.exe install
```

# 卸载服务

```
CMD执行 Lineview_Schedule.exe uninstall
```
