#!/usr/bin/php
<?php
// By dongxicheng,
// blog:http://dongxicheng.org/
// mapper.php
error_reporting(E_ALL ^ E_NOTICE); 
$word2count = array();
// 标准输入为STDIN (standard input)
while (($line = fgets(STDIN)) !== false) {
   // 移除空白
   $line = trim($line);
   // 將行拆解成若干个单词
   $words = preg_split('/\W/', $line, 0, PREG_SPLIT_NO_EMPTY);
   // 将结果写到 STDOUT (standard output)
   foreach ($words as $word) {
     // 印出 [字 , "tab字符" ,  "数字" , "结束符"]
     echo $word, chr(9), "1", PHP_EOL;
   }
}
?>
