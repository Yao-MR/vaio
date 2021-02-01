#!/usr/bin/python3
# -*- coding: UTF-8 -*-

'''
python 基本数据类型
'''
word = '字符串'
senfence = "这是一个句子"
paragraph = """这是一个
段落
"""

str = 'test'
print(str)                 # 输出字符串
print(str[0:-1])           # 输出第一个到倒数第二个的所有字符
print(str[0])              # 输出字符串第一个字符
print(str[2:5])            # 输出从第三个开始到第五个的字符
print(str[2:])             # 输出从第三个开始后的所有字符
print(str * 2)             # 输出字符串两次
print(str + '你好')        # 连接字符串
print('------------------------------')
print('hello\nrunoob')      # 使用反斜杠(\)+n转义特殊字符

a = b = c = 1
print(a)