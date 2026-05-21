# 测试指南

## 快速测试

编译后用 echo/printf 管道输入：

```bash
JAVA="<JDK路径>/bin/java"
OUT=target/classes

# 示例用例
printf 'MKDIR /usr\nMKDIR /usr/local\nTOUCH /usr/local/test.txt 100\nTOUCH /readme.md 50\nLS /\nINFO /\nINFO /usr\n' | $JAVA -cp $OUT com.filesystem.Main
```

预期输出：
```
readme.md
usr
150
100
```

## 测试用例

### 1. 非法路径（全部静默忽略)

| 输入 | 预期 |
|------|------|
| `MKDIR //a` | 无输出 |
| `MKDIR /a/` | 无输出 |
| `MKDIR /./a` | 无输出 |
| `MKDIR /../a` | 无输出 |
| `TOUCH /a/ 10` | 无输出 |
| `TOUCH /./a 10` | 无输出 |
| `TOUCH /../a 10` | 无输出 |

### 2. 父目录不存在（静默忽略)

| 输入 | 预期 |
|------|------|
| `MKDIR /a/b` | 无输出（`/a` 不存在）|
| `TOUCH /a/b/c.txt 10` | 无输出（`/a/b` 不存在）|

### 3. 正常创建与查询

| 输入 | 预期输出 |
|------|----------|
| `MKDIR /a` | |
| `MKDIR /a/b` | |
| `TOUCH /a/b/c.txt 100` | |
| `TOUCH /a/b/d.txt 50` | |
| `LS /a/b` | `c.txt` `<br>` `d.txt` |
| `INFO /a/b` | `150` |
| `INFO /a/b/c.txt` | `100` |

### 4. MKDIR 覆盖文件为目录

| 输入 | 预期输出 |
|------|----------|
| `MKDIR /a/b/c.txt` |（c.txt 文件→目录）|
| `LS /a/b` | `c.txt` `<br>` `d.txt` |
| `INFO /a/b` | `50`（c.txt 目录为空=0）|

### 5. TOUCH 覆盖目录为文件

| 输入 | 预期输出 |
|------|----------|
| `TOUCH /a/b/c.txt 999` |（c.txt 目录→文件）|
| `LS /a/b` | `c.txt` `<br>` `d.txt` |
| `INFO /a/b/c.txt` | `999` |

### 6. 同名同类型

| 输入 | 预期输出 |
|------|----------|
| `MKDIR /a` | 无输出（已存在目录，保持原样）|
| `TOUCH /a/b/d.txt 777` |（已存在文件，覆盖大小）|
| `INFO /a/b/d.txt` | `777` |

### 7. 根目录操作

| 输入 | 预期输出 |
|------|----------|
| `MKDIR /` | 无输出（根目录已存在）|
| `TOUCH / 10` | 无输出（不能覆盖根目录）|
| `LS /` | 列出根下子节点 |
| `INFO /` | 文件系统总大小 |

## 一体化验证脚本

```bash
JAVA="<JDK路径>/bin/java"
OUT=target/classes

printf 'MKDIR //a
MKDIR /a/
MKDIR /./a
MKDIR /../a
TOUCH /a/ 10
TOUCH /./a 10
TOUCH /../a 10
MKDIR /a/b
TOUCH /a/b/c.txt 10
MKDIR /a
MKDIR /a/b
TOUCH /a/b/c.txt 100
TOUCH /a/b/d.txt 50
LS /a/b
INFO /a/b
INFO /a/b/c.txt
MKDIR /a/b/c.txt
LS /a/b
INFO /a/b
TOUCH /a/b/c.txt 999
LS /a/b
INFO /a/b/c.txt
MKDIR /a
LS /
INFO /
TOUCH /a/b/d.txt 777
INFO /a/b/d.txt
' | $JAVA -cp $OUT com.filesystem.Main
```

预期输出：
```
c.txt
d.txt
150
100
c.txt
d.txt
50
c.txt
d.txt
999
a
1049
777
```
