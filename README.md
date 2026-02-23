# MartialWorld-Text2

一个基于 SpringBoot + MyBatis 的轻量文字武侠游戏，支持玩家注册登录、技能学习与升级、战斗挑战、背包查看及奖励系统。前端采用纯 HTML/CSS/JavaScript，无需额外构建，开箱即用。

## ✨ 功能特性

- **玩家系统**：注册、登录、状态（等级、经验、金钱、HP/MP）持久化
- **技能系统**：查看所有技能、升级技能、将技能装备至8个槽位
- **战斗系统**：挑战动态等级的怪物，使用已装备技能攻击，战斗日志实时记录
- **奖励系统**：胜利后获得经验与金钱，并自动触发玩家升级（属性提升）
- **背包系统**：查看拥有的药品及其数量（支持扩展装备/物品模块）

## 🛠️ 技术栈

- **后端**：Java 17 + Spring Boot 4.0.2 + MyBatis
- **数据库**：MySQL 8.0+
- **构建工具**：Maven
- **前端**：原生 HTML/CSS/JS（无框架）

## 📦 快速开始

### 环境要求

- JDK 17 或更高版本
- MySQL 8.0 或更高版本
- Maven 3.6+（仅打包时需要，本地开发可使用 IDE 直接运行）

### 1. 获取源码

```bash
git clone https://github.com/Qianfuz/MartialWorld-Text2.git
cd MartialWorld-Text2
```

### 2. 创建数据库与导入表结构

在 MySQL 中创建一个数据库（例如 `game`），然后执行项目中的 SQL 文件：

```sql
CREATE DATABASE game CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

SQL 文件位于 `src/main/resources/static/dump-game-202602220448.sql`，请将其导入刚创建的数据库：

```bash
mysql -u root -p game < src/main/resources/static/dump-game-202602220448.sql
```

### 3. 修改数据库连接配置

编辑 `src/main/resources/application.yml`，将数据库用户名和密码改为你的实际配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/game?useSSL=false&serverTimezone=Asia/Shanghai
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root           # 你的数据库用户名
    password: 1234           # 你的数据库密码

mybatis:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
    map-underscore-to-camel-case: true
```

### 4. 运行项目

#### 方式一：使用 IDE（如 IntelliJ IDEA）
直接运行主类 `com.example.game.GameApplication`。

#### 方式二：使用 Maven 打包运行

```bash
mvn clean package
java -jar target/Game-0.0.1-SNAPSHOT.jar
```

### 5. 访问游戏

启动成功后，在浏览器中打开：

- **本地访问**：`http://localhost:8080/game.html`
- **云服务器访问**：将 `localhost` 替换为你的服务器 IP 即可 示例：[http://122.51.118.82:8080/game.html](http://122.51.118.82:8080/game.html)

## 📁 项目结构简述

```
src/main/
├── java/com/example/game/
│   ├── controller/        # 接口层（玩家、技能、战斗、背包等）
│   ├── service/           # 业务逻辑层
│   ├── mapper/            # MyBatis 数据访问层
│   ├── pojo/              # 实体类与 DTO
│   └── util/              # 工具类（如战斗公式计算）
├── resources/
│   ├── static/            # 前端静态文件（game.html, style.css, script.js）
│   │   └── dump-game-202602220448.sql  # 数据库初始数据
│   └── application.yml    # 项目配置文件
```

## 👤 作者

- GitHub：[@Qianfuz](https://github.com/Qianfuz)
- 邮箱：qianfuzy@gmail.com

## 📄 许可证

本项目采用 MIT 许可证，详情请查看 [LICENSE](LICENSE) 文件。
