#!/usr/bin/php
<?php
// By dongxicheng,
// blog:http://dongxicheng.org/
// reducer.php
error_reporting(E_ALL ^ E_NOTICE); 
$word2count = array();
// 标准输入为 STDIN
while (($line = fgets(STDIN)) !== false) {
    // 移除多余空白
    $line = trim($line);
    // 每一行的格式为(单词 "tab" 数字)，存入($word, $count)
    list($word, $count) = explode(chr(9), $line);
    // 转换格式string -> int
    $count = intval($count);
    //汇总
    $word2count[$word] += $count;
}
// 将结果写到 STDOUT (standard output)
foreach ($word2count as $word => $count) {
    echo $word, chr(9), $count, PHP_EOL;
}
?> 

