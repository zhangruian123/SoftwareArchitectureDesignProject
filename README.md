# 微型内存文件系统

软件系统设计课程项目 — 迭代一

## 项目简介

基于命令行的微型内存文件系统。从标准输入读取指令，在内存中动态构建并维护一棵文件树，将结果输出到标准输出。

## 项目结构

```
src/main/java/com/filesystem/
├── Main.java                  # 程序入口
├── FileSystem.java            # 文件系统核心逻辑
├── context/
│   └── SizeContext.java       # 大小计算上下文（为迭代二防环预留）
├── node/
│   ├── Node.java              # 文件/目录抽象基类
│   ├── Directory.java         # 目录节点（TreeMap 保证字母序）
│   └── FileNode.java          # 文件节点
└── path/
    ├── PathUtil.java          # 路径校验与切分
    └── InvalidPathException.java
```

## 支持指令

| 指令 | 格式 | 说明 |
|------|------|------|
| MKDIR | `MKDIR <绝对路径>` | 创建目录 |
| TOUCH | `TOUCH <绝对路径> <大小>` | 创建文件 |
| LS | `LS <绝对路径>` | 列出子节点（目录按字母序） |
| INFO | `INFO <绝对路径>` | 查询大小 |

详细行为规范见 `迭代一说明.md`。

## 编译

要求 JDK 17+。

```bash
# 使用 Maven
mvn compile

# 或手动 javac
mkdir -p target/classes
javac -d target/classes src/main/java/com/filesystem/Main.java
```

## 运行

```bash
# Maven
mvn exec:java -Dexec.mainClass="com.filesystem.Main"

# 或直接运行
java -cp target/classes com.filesystem.Main
```

## 输入输出示例

输入：
```
MKDIR /usr
MKDIR /usr/local
TOUCH /usr/local/test.txt 100
TOUCH /readme.md 50
LS /
INFO /
INFO /usr
```

输出：
```
readme.md
usr
150
100
```
